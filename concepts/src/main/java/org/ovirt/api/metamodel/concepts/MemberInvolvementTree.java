/*
Copyright (c) 2016-2017 Red Hat, Inc.

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * This class is a tree-like data-structure used by the 'Parameter' class to
 * represent inner fields of the input object and their participation in the
 * API operation at hand. The information held by this data-structure is the
 * path to the field, the type of the field, and whether the field is mandatory
 * or optional for the API operation at hand.
 *
 * An example of one of the MemberInvolvementTrees inside 'Cluster' Parameter:
 *
 *        fencing_policy
 *            enabled                       (boolean, optional)
 *            skip_if_connectivity_broken
 *                 enabled                  (boolean, optional)
 *                 threshold                (int, optional)
 *            skip_if_sd_active
 *                 enabled                  (boolean, optional)
 */
public class MemberInvolvementTree implements Serializable {

    private StructMember member; //the node in the tree. Conatins name and type.
    private List<MemberInvolvementTree> nodes = new ArrayList<>(); //child nodes (if exist)
    private MemberInvolvementTree parent; //parent node (unless root, then no parent)
    private MemberInvolvementTree alternative; //Used to express an 'or' relationship between nodes.
    private boolean mandatory; //for leaves only - is this field mandatory or optional?


    public MemberInvolvementTree(Name name) {
        super();
        setName(name);
    }
    public MemberInvolvementTree(Name name, MemberInvolvementTree parent) {
        super();
        setName(name);
        this.parent = parent;
    }
    public StructMember getMember() {
        return member;
    }
    public void setMember(StructMember member) {
        this.member = member;
    }
    public void setName(Name name) {
        if (member==null) {
           member = new StructMember();
        }
        member.setName(name);
    }
    public Type getType() {
        return member==null ? null : member.getType();
    }
    public void setType(Type type) {
        if (member==null) {
            member = new StructMember();
        }
        member.setType(type);
    }
    public Name getName() {
        return this.member==null ? null : this.member.getName()==null ? null : this.member.getName();
    }
    public boolean isMandatory() {
        return mandatory;
    }
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
    public List<MemberInvolvementTree> getNodes() {
        return nodes;
    }
    public MemberInvolvementTree getParent() {
        return parent;
    }
    public boolean hasParent() {
        return parent!=null;
    }
    public boolean hasChildren() {
        return nodes!=null && !nodes.isEmpty();
    }
    public void setParent(MemberInvolvementTree parent) {
        this.parent = parent;
    }
    public boolean isLeaf() {
        return this.nodes.isEmpty();
    }
    public MemberInvolvementTree getNode(Name name) {
        for (MemberInvolvementTree node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    public List<MemberInvolvementTree> getMandatoryAttributes() {
        List<MemberInvolvementTree> mandatoryAttributes = new ArrayList<>();
        LeafIterator leafIterator = new LeafIterator(this);
        MemberInvolvementTree leaf = leafIterator.next();
        while (leaf!=null) {
            if (leaf.isMandatory()) {
                mandatoryAttributes.add(leaf);
            }
            leaf = leafIterator.next();
        }
        return mandatoryAttributes;
    }

    public boolean isCollection() {
        return member.getType() instanceof ListType;
    }

    @Override
    public String toString() {
        Stack<MemberInvolvementTree> stack = new Stack<>();
        stack.add(this);
        while(stack.peek().hasParent()) {
            stack.add(stack.peek().getParent());
        }
        StringBuilder builder = new StringBuilder();
        while (!stack.isEmpty()) {
            builder.append(stack.pop().getName()).append(".");
        }
        return builder.toString();
    }

    private static class LeafIterator implements Iterator<MemberInvolvementTree> {
        private Stack<MemberInvolvementTree> stack = new Stack<>();
        private Set<MemberInvolvementTree> visited = new HashSet<>();
        public LeafIterator(MemberInvolvementTree tree) {
            stack.push(tree);
        }

        @Override
        public boolean hasNext() {
            //implementing hadNext() on DFS tree search is difficult.
            //the way to use this iterator is to run next() and check
            //if the result it null.
            throw new UnsupportedOperationException();
        }

        @Override
        public MemberInvolvementTree next() {
            while (!stack.isEmpty()) {
                MemberInvolvementTree current = stack.pop();
                if (!visited.contains(current)) {
                    visited.add(current);
                    if (current.isLeaf()) {
                        return current;
                    }
                }
                for (MemberInvolvementTree child : current.getNodes()) {
                    if (!visited.contains(child)) {
                        stack.push(child);
                    }
                }
            }
            return null;
        }
    }

    public MemberInvolvementTree getAlternative() {
        return alternative;
    }

    public boolean hasAlternative() {
        return alternative!=null;
    }

    public void setAlternative(MemberInvolvementTree alternative) {
        this.alternative = alternative;
    }

    public Name shallowToString() {
        Stack<Name> stack = new Stack<>();
        MemberInvolvementTree memberInvolvementTree = this; 
        stack.push(memberInvolvementTree.getName());
        while (memberInvolvementTree.hasParent()) {
            memberInvolvementTree = memberInvolvementTree.getParent();
            stack.push(memberInvolvementTree.getName());
        }
        Name name = new Name();
        while (!stack.isEmpty()) {
            name.addWords(stack.pop().getWords());
        }
        return name;
    }

    public void cutSelf() {
        if (hasParent()) {
            parent.getNodes().remove(this);
            if (parent.getNodes().isEmpty()) {
                parent.cutSelf();
            }
        }
    }
}
