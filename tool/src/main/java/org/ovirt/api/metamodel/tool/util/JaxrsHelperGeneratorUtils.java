package org.ovirt.api.metamodel.tool.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.ovirt.api.metamodel.concepts.MemberInvolvementTree;
import org.ovirt.api.metamodel.concepts.Method;
import org.ovirt.api.metamodel.concepts.Name;
import org.ovirt.api.metamodel.concepts.Parameter;
import org.ovirt.api.metamodel.tool.Names;

@ApplicationScoped
public class JaxrsHelperGeneratorUtils {

    @Inject private Names names;

    public boolean isContained(Method method, Collection<Method> methods) {
        for (Method currentMethod : methods) {
            if (isContained(method, currentMethod)) {
                return true;
            }
        }
        return false;
    }

    public boolean isContained(Method method, Method method2) {
        if (method.getName().equals(method2.getName())) {
            return false; //same method
        }
        if (method.getBase()==null || method2.getBase()==null) {
            return false; //no parent, containment irrelevant
        }
        if (!method.getBase().getName().equals(method2.getBase().getName())) {
            return false; //different parents, containment irrelevant.
        }
        return isParametersContained(method.getMandatoryParameters(), method2.getMandatoryParameters())
                && isAttributesContained(method.getMandatoryAttributes(), method2.getMandatoryAttributes());
    }

    private boolean isParametersContained(List<Parameter> mandatoryParameters1, List<Parameter> mandatoryParameters2) {
        if (mandatoryParameters1.isEmpty() && mandatoryParameters2.isEmpty()) {
            return false;
        }
        List<Name> names1 = new ArrayList<>();
        List<Name> names2 = new ArrayList<>();
        for (Parameter p : mandatoryParameters1) {
            names1.add(p.getName());
        }
        for (Parameter p : mandatoryParameters2) {
            names2.add(p.getName());
        }
        return names2.containsAll(names1);
    }

    private boolean isAttributesContained(List<MemberInvolvementTree> mandatoryAttributes1, List<MemberInvolvementTree> mandatoryAttributes2) {
        if (mandatoryAttributes1.isEmpty() && mandatoryAttributes2.isEmpty()) {
            return false;
        }
        return toNames(mandatoryAttributes2).containsAll(toNames(mandatoryAttributes1));
    }

    private List<Name> toNames(List<MemberInvolvementTree> attributes) {
        List<Name> names = new ArrayList<>();
        for (MemberInvolvementTree attribute : attributes) {
            names.add(attribute.getAlternative()==null ?
                    attribute.shallowToString() : combineAlternatives(attribute));
        }
        return names;
    }

    private Name combineAlternatives(MemberInvolvementTree attribute) {
        Name name1 = attribute.shallowToString();
        Name name2 = attribute.getAlternative().shallowToString();
        if (name1.compareTo(name2) < 0) {                       
            return names.concatenate(name1, name2);
        }
        else {
            return names.concatenate(name2, name1);
        }
    }
}
