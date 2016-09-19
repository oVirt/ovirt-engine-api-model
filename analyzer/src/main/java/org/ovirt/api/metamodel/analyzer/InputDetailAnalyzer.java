/*
Copyright (c) 2016 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.ovirt.api.metamodel.analyzer;

import java.util.List;
import java.util.Stack;

import org.ovirt.api.metamodel.concepts.ArrayExpression;
import org.ovirt.api.metamodel.concepts.EnumType;
import org.ovirt.api.metamodel.concepts.Expression;
import org.ovirt.api.metamodel.concepts.ListType;
import org.ovirt.api.metamodel.concepts.MemberInvolvementTree;
import org.ovirt.api.metamodel.concepts.Name;
import org.ovirt.api.metamodel.concepts.NameParser;
import org.ovirt.api.metamodel.concepts.Parameter;
import org.ovirt.api.metamodel.concepts.PrimitiveType;
import org.ovirt.api.metamodel.concepts.StructType;
import org.ovirt.api.metamodel.concepts.Type;
import org.ovirt.api.metamodel.analyzer.MethodExpression;

public class InputDetailAnalyzer {

    //In 'live documentation' certain words are keywords that are searched for.
    //Theses words are defined here as constants.
    private static final Name OPTIONAL = NameParser.parseUsingCase("Optional");
    private static final Name MANDATORY = NameParser.parseUsingCase("Mandatory");
    private static final Name COLLECTION = NameParser.parseUsingCase("Collection");
    private static final Name OR = NameParser.parseUsingCase("Or");

    /**
     * Analyze 'live documentation' code and make updates to the provided Parameters.
     */
    public void analyzeInput(String sourceCode, List<Parameter> parameters) {
        ExpressionAnalyzer expressionAnalyzer = new ExpressionAnalyzer();
        List<Expression> expressions = expressionAnalyzer.analyzeExpressions(sourceCode);
        for (Expression expression : expressions) {
            assert expression instanceof MethodExpression;
            analyzeExpression((MethodExpression)expression, parameters);
        }
    }

    /**
     * Analyze a single 'live documentation' expression. Each expression represents an attribute.
     * The attribute is added to the relevant Parameter.
     */
    private void analyzeExpression(MethodExpression expression, List<Parameter> parameters) {
        boolean mandatory = isMandatory(expression);
        expression = removePrefix(expression);
        if (expression.getMethod().equals(OR)) {
            analyzeOrExpression(expression, parameters, mandatory);
        } else {
            analyzeExpression(expression, parameters, mandatory);
        }
    }

    /**
     * Determine whether the current expression represents a mandatory
     * (vs optional) attribute.
     */
    private boolean isMandatory(MethodExpression expression) {
        return expression.getMethod().equals(MANDATORY) ? true : false;
    }

    /**
     * The purpose of this method is to remove 'mandatory' or 'optional'
     * methods from the Expression, if they exist.
     *
     * For example, the output of this method for the expression:
     * mandatory(disk().format()) would be disk().format(). The output
     * for disk().format() would be disk().format() (unchanged)
     */
    private MethodExpression removePrefix(MethodExpression expression) {
        return expression.getMethod().equals(MANDATORY) || expression.getMethod().equals(OPTIONAL) ?
                (MethodExpression)expression.getParameters().get(0) : expression;
    }

    /**
     * This method is expected to receive at input expressions of the form:
     * disk().quota().id() - meaning chained, parameterless method invocations.
     * The method finds the relevant Parameter among the provided ones and
     * updates the correct MemberInvolvementTree in it with information obtained
     * from the expression.
     */
    private void analyzeExpression(MethodExpression expression, List<Parameter> parameters, boolean mandatory) {
        //when lines such as "disk().quota().id()" are made into MethodExpressions,
        //their elements (disk, quota, id) are reversed in order. A stack is used here
        //to restore the original order.
        Stack<Name> stack = new Stack<>();
        stackExpressionElements(stack, expression);

        //find the relevant parameter.
        Parameter parameter = getParameter(stack.pop(), parameters);

        //update the parameter.
        updateParameter(stack, mandatory, parameter);
    }

    /**
     * Stack elements of the provided expression to reverse their order.
     * Collection elements are flagged with collection=true.
     */
    private void stackExpressionElements(Stack<Name> stack, MethodExpression expression) {
        stackExpressionElements(stack, expression, false);
    }

    private void stackExpressionElements(Stack<Name> stack, MethodExpression expression, boolean nextElementIsACollection) {
        if (expression.getMethod()!=null) {
            stack.push(expression.getMethod());
        }
        if (expression.getTarget()!=null) {
            if (expression.getTarget() instanceof MethodExpression) {
                //call this method recursively; stack the next element.
                stackExpressionElements(stack, (MethodExpression)expression.getTarget());
            } else {
                //stack a collection element.
                stackCollectionExpression(stack, expression);
            }
        }
    }

    /**
     * This method inserts a collection element into the stack. A collection element is an array
     * of the form: ...[COLLECTION]... The way that such an element is handled is:
     * 1) The array index ([COLLECTION]) is discarded.
     * 2) The stacking process continues for the rest of the expression.
     * (Note: the information that an element is a collection will be available later
     * by examining the element-type, that's why [COLLECTION] can be safely discarded).
     */
    private void stackCollectionExpression(Stack<Name> stack, MethodExpression expression) {
        if (expression.getTarget() instanceof ArrayExpression) {
            ArrayExpression arrayExpression = (ArrayExpression)expression.getTarget();
            if (!(arrayExpression.getIndex() instanceof FieldExpression)
                    || !((FieldExpression)arrayExpression.getIndex()).getField().equals(COLLECTION))  {
                throw new IllegalArgumentException("The only valid array expression in live documentation is [COLLECTION]");
            }
            else {  //A valid collection expression
                stackExpressionElements(stack, (MethodExpression)arrayExpression.getArray(), true);
            }
        }
        else {
            throw new IllegalArgumentException("Can't handle " + expression.getClass().getSimpleName()
                   + " in this context; only MethodExpression or ArrayExpression");
        }
    }

    private void updateParameter(Stack<Name> stack, boolean mandatory, Parameter parameter) {
        //handle the special case of a single-element expression, e.g: "fenceType()".
        //Such expressions are only expected to appear in the live documentation of
        //Actions, and they represent primitive (integer, boolean, String) or enum types.
        //These types don't have a member-involvement-tree associated with them in the
        //Parameter object, and whether or not they are mandatory is expressed by the
        //Parameter.mandatory attribute.
        if (stack.isEmpty()) {
            assert parameter.getType() instanceof PrimitiveType || parameter.getType() instanceof EnumType;
            parameter.setMandatory(mandatory);
        } else {
            //find the member-involvement-tree which should be updated.
            MemberInvolvementTree currentNode = getMemberTree(parameter, stack.pop());
            if (currentNode.getType()==null) {
                currentNode.setType(getType(parameter.getType(), currentNode.getName()));
            }
            while (!stack.isEmpty()) {
                Name name = stack.pop();
                MemberInvolvementTree nextNode = currentNode.getNode(name);
                if (nextNode==null) {
                    nextNode = new MemberInvolvementTree(name, currentNode);
                    currentNode.getNodes().add(nextNode);
                }
                if (nextNode.getType()==null) {
                    nextNode.setType(getType(currentNode.getType(), nextNode.getName()));
                }
                currentNode = nextNode;
            }
            //stack has been emptied so this is a leaf. Set 'mandatory' (true/false).
            currentNode.setMandatory(mandatory);
        }
    }

    private Parameter getParameter(Name name, List<Parameter> parameters) {
        for (Parameter parameter : parameters) {
            if (parameter.getName().equals(name)) {
                return parameter;
            }
        }
        throw new IllegalStateException("A parameter by name " + name + " was expected to be found.");
    }

    /**
     * Find the member-involvement-tree with the provided name and return it.
     * If no such tree exist, create it and add it to the Parameter object,
     * then return it.
     */
    private MemberInvolvementTree getMemberTree(Parameter parameter, Name name) {
        for (MemberInvolvementTree tree : parameter.getMemberInvolvementTrees()) {
            if (tree.getName().equals(name)) {
                return tree;
            }
        }
        //if none found, create a new one, add it to the Parameter and return it
        MemberInvolvementTree tree = new MemberInvolvementTree(name);
        parameter.getMemberInvolvementTrees().add(tree);
        return tree;
    }

    private Type getType(Type type, Name name) {
        //all nodes except for leaves in MemberInvovlementTree are assumed
        //to be a StructType or ListType (for collections)
        if (type instanceof StructType) {
            return ((StructType)type).getMember(name).get().getType();
        }
        else if (type instanceof ListType) {
            ListType listType = (ListType)type;
            //use the element-type of the list
            StructType structType = (StructType)listType.getElementType();
            return structType.getMember(name).get().getType();
        }
        else {
            throw new IllegalArgumentException("Expected StructType or ListType element");
        }
    }

    private void analyzeOrExpression(MethodExpression expression, List<Parameter> parameters, boolean mandatory) {
        // TODO Auto-generated method stub
    }

}
