package org.ovirt.api.metamodel.tool.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import org.ovirt.api.metamodel.concepts.ListType;
import org.ovirt.api.metamodel.concepts.MemberInvolvementTree;
import org.ovirt.api.metamodel.concepts.Method;
import org.ovirt.api.metamodel.concepts.Name;
import org.ovirt.api.metamodel.concepts.NameParser;
import org.ovirt.api.metamodel.concepts.Parameter;
import org.ovirt.api.metamodel.concepts.StructType;

@ApplicationScoped
public class JaxrsGeneratorUtils {

    // The names of the methods that require special treatment:
    public static final Name ADD = NameParser.parseUsingCase("Add");
    public static final Name GET = NameParser.parseUsingCase("Get");
    public static final Name LIST = NameParser.parseUsingCase("List");
    public static final Name REMOVE = NameParser.parseUsingCase("Remove");
    public static final Name UPDATE = NameParser.parseUsingCase("Update");

    public Map<Method, Set<Method>> getBaseMethodsMap(List<Method> methods) {
        Map<Method, Set<Method>> baseMethods = new HashMap<>();
        methods.forEach(x -> updateBaseMethodsMap(x, methods, baseMethods));
        return baseMethods;
    }

    private void updateBaseMethodsMap(Method method, List<Method> methods, Map<Method, Set<Method>> baseMethods) {
        for (Method currentMethod : methods) {
            if (currentMethod.getBase()!=null && currentMethod.getBase().equals(method)) {
                add(baseMethods, method, currentMethod);
            }
        }
    }

    private void add(Map<Method, Set<Method>> baseMethods, Method method, Method currentMethod) {
        if (!baseMethods.containsKey(method)) {
            baseMethods.put(method, new HashSet<>());
        }
        Set<Method> signatures = (Set<Method>)(baseMethods.get(method));
        signatures.add(currentMethod);
    }

    public Parameter getMainAddParameter(Method method) {
        Parameter mainParameter = method.getParameters().stream()
            .filter(x -> x.getType() instanceof StructType || x.getType() instanceof ListType)
            .findFirst()
            .orElse(null);
        return mainParameter;
    }

    public Parameter getMainUpdateParameter(Method method) {
        Parameter mainParameter = method.getParameters().stream()
            .filter(x -> x.getType() instanceof StructType)
            .findFirst()
            .orElse(null);
        return mainParameter;
    }

    /**
     * Return true if this method has ADD as its base.
     * Methods which have a base method represent 'signatures' of the same method.
     */
    public boolean isAddSignature(Method method) {
        return method.getBase()!=null && method.getBase().getName().equals(ADD);
    }

    /**
     * Return true if this method has UPDATE as its base.
     * Methods which have a base method represent 'signatures' of the same method.
     */
    public boolean isUpdateSignature(Method method) {
        return method.getBase()!=null && method.getBase().getName().equals(UPDATE);
    }

    /**
     * Return true if this method has any 'action' (e.g: install) as its base.
     * Methods which have a base method represent 'signatures' of the same method.
     */
    public boolean isActionSignature(Method method) {
        return method.getBase()!=null && !method.getBase().getName().equals(ADD)
               && !method.getBase().getName().equals(UPDATE);
    }

}
