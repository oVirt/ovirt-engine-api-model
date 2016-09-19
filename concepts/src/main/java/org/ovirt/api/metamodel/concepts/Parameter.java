/*
Copyright (c) 2015 Red Hat, Inc.

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

package org.ovirt.api.metamodel.concepts;

import java.util.ArrayList;
import java.util.List;

public class Parameter extends Concept {

    public Parameter() {
        super();
    }

    // The method that declared this parameter:
    private Method declaringMethod;

    // The direction of this parameter:
    private boolean in = false;
    private boolean out = false;

    // The type of this parameter:
    private Type type;

    // The default value of this parameter:
    private Expression defaultValue;

    // Is this parameter mandatory? Relevant only for simple parameters
    // (booleans, integers, string...) the mandatory-ness of complex
    // parameters is handled by member-involvement-trees (see below).
    // (A simple parameter being mandatory is a scenario which happens only in 'Action's).
    private boolean mandatory = false;

    /*
     * For parameters which are complex and contain numerous inner fields (as opposed
     * to simpler parameters, such as booleans, integers or strings), we need to state
     * which inner fields (or 'members') are mandatory, which are optional and which
     * are irrelevant for the flow in which this parameter participates.
     *
     * These attributes will be represented by trees, e.g:
     *
     *    (cluster)
     *        name                              (string, mandatory)
     *        -----------------------------------------------------
     *        version
     *            major                         (int, mandatory)
     *            minor                         (int, mandatory)
     *        -----------------------------------------------------
     *        cpu
     *            type                          (string, mandatory)
     *        -----------------------------------------------------
     *        data_center
     *            id                            (string, mandatory)
     *        -----------------------------------------------------
     *        fencing_policy
     *            enabled                       (boolean, optional)
     *            skip_if_connectivity_broken
     *                 enabled                  (boolean, optional)
     *                 threshold                (int, optional)
     *            skip_if_sd_active
     *                 enabled                  (boolean, optional)
     *        -----------------------------------------------------
     *        ...
     * for simple parameters, such as 'max', 'search', 'case-sensitive',
     * who's type is not a structured type, this tree will be 'null'.
     */
    private List<MemberInvolvementTree> memberInvolvementTrees = new ArrayList<>();

    public List <MemberInvolvementTree> getMemberInvolvementTrees() {
        return memberInvolvementTrees;
    }

    /**
     * Returns the method where this parameter is declared.
     */
    public Method getDeclaringMethod() {
        return declaringMethod;
    }

    /**
     * Sets the method that declared this parameter.
     */
    public void setDeclaringMethod(Method newDeclaringMethod) {
        declaringMethod = newDeclaringMethod;
    }

    public boolean isIn() {
        return in;
    }

    public void setIn(boolean in) {
        this.in = in;
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Expression getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Expression value) {
        this.defaultValue = value;
    }

    public MemberInvolvementTree getMemberInvolvemnetTree(Name name) {
        for (MemberInvolvementTree tree : memberInvolvementTrees) {
            if (tree.getName().equals(name)) {
                return tree;
            }
        }
        return null;
    }
    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public List<MemberInvolvementTree> getMandatoryAttributes() {
        List<MemberInvolvementTree> mandatoryAttributes = new ArrayList<>();
        for (MemberInvolvementTree memberInvolvementTree : memberInvolvementTrees) {
            mandatoryAttributes.addAll(memberInvolvementTree.getMandatoryAttributes());
        }
        return mandatoryAttributes;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        if (declaringMethod != null) {
            buffer.append(declaringMethod);
            buffer.append(":");
        }
        buffer.append(getName());
        return buffer.toString();
    }
}

