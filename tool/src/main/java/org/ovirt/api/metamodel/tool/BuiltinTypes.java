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

package org.ovirt.api.metamodel.tool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.ovirt.api.metamodel.concepts.Attribute;
import org.ovirt.api.metamodel.concepts.Method;
import org.ovirt.api.metamodel.concepts.Model;
import org.ovirt.api.metamodel.concepts.Name;
import org.ovirt.api.metamodel.concepts.NameParser;
import org.ovirt.api.metamodel.concepts.Parameter;
import org.ovirt.api.metamodel.concepts.Service;
import org.ovirt.api.metamodel.concepts.StructType;

/**
 * This class contains the method used to add the built-in types like {@code Fault} and {@code Action} to a method.
 * These types aren't really part of the model, but required by the XML schema and JAX-RS implementation. To simplify
 * things it is convenient to add them to the model, so that code generators don't have to treat them in special ways.
 */
@ApplicationScoped
public class BuiltinTypes {
    // Reference to objects used to do name calculations:
    @Inject
    private JaxrsNames jaxrsNames;

    /**
     * Adds built-in types to the model.
     */
    public void addBuiltinTypes(Model model) {
        // Note that the order is important. For example, the "Fault" type must be added before the "Action" type
        // because actions have elements whose type is "Fault".
        addFaultType(model);
        addGracePeriodType(model);
        addActionType(model);
    }

    /**
     * Adds the {@code Fault} type to the model.
     */
    private void addFaultType(Model model) {
        // Create the type:
        StructType faultType = new StructType();
        faultType.setName(NameParser.parseUsingCase("Fault"));

        // Add the "reason" attribute:
        Attribute reasonAttribute = new Attribute();
        reasonAttribute.setName(NameParser.parseUsingCase("Reason"));
        reasonAttribute.setType(model.getStringType());
        reasonAttribute.setDeclaringType(faultType);
        faultType.addAttribute(reasonAttribute);

        // Add the "detail" attribute:
        Attribute detailAttribute = new Attribute();
        detailAttribute.setName(NameParser.parseUsingCase("Detail"));
        detailAttribute.setType(model.getStringType());
        detailAttribute.setDeclaringType(faultType);
        faultType.addAttribute(detailAttribute);

        // Add the type to the model:
        faultType.setModule(model.getModule(null));
        model.addType(faultType);
    }

    /**
     * Adds the {@code GracePeriod} type to the model.
     */
    private void addGracePeriodType(Model model) {
        // Create the type:
        StructType gracePeriodType = new StructType();
        gracePeriodType.setName(NameParser.parseUsingCase("GracePeriod"));

        // Add the "expiry" attribute:
        Attribute expiryAttribute = new Attribute();
        expiryAttribute.setName(NameParser.parseUsingCase("Expiry"));
        expiryAttribute.setType(model.getIntegerType());
        expiryAttribute.setDeclaringType(gracePeriodType);
        gracePeriodType.addAttribute(expiryAttribute);

        // Add the type to the model:
        gracePeriodType.setModule(model.getModule(null));
        model.addType(gracePeriodType);
    }

    /**
     * Adds the {@code Action} type to the model.
     */
    private void addActionType(Model model) {
        // Create the type:
        StructType actionType = new StructType();
        actionType.setName(NameParser.parseUsingCase("Action"));

        // Set the base type:
        actionType.setBase(model.getType(NameParser.parseUsingCase("Identified")));

        // Add the "status" attribute:
        Attribute statusAttribute = new Attribute();
        statusAttribute.setName(NameParser.parseUsingCase("Status"));
        statusAttribute.setType(model.getStringType());
        statusAttribute.setDeclaringType(actionType);
        actionType.addAttribute(statusAttribute);

        // Add the "fault" attribute:
        Attribute faultAttribute = new Attribute();
        faultAttribute.setName(NameParser.parseUsingCase("Fault"));
        faultAttribute.setType(model.getType(NameParser.parseUsingCase("Fault")));
        faultAttribute.setDeclaringType(actionType);
        actionType.addAttribute(faultAttribute);

        // Add the "gracePeriod" attribute:
        Attribute gracePeriodAttribute = new Attribute();
        gracePeriodAttribute.setName(NameParser.parseUsingCase("GracePeriod"));
        gracePeriodAttribute.setType(model.getType(NameParser.parseUsingCase("GracePeriod")));
        gracePeriodAttribute.setDeclaringType(actionType);
        actionType.addAttribute(gracePeriodAttribute);

        // Add the "job" attribute:
        Attribute jobAttribute = new Attribute();
        jobAttribute.setName(NameParser.parseUsingCase("Job"));
        jobAttribute.setType(model.getType(NameParser.parseUsingCase("Job")));
        jobAttribute.setDeclaringType(actionType);
        actionType.addAttribute(jobAttribute);

        // Add attributes for all input and output parameters of all the action methods:
        Map<Name, Parameter> parameters = new HashMap<>();
        for (Service service : model.getServices()) {
            for (Method method : service.getMethods()) {
                if (jaxrsNames.isActionName(method.getName())) {
                    for (Parameter parameter : method.getParameters()) {
                        Name name = parameter.getName();
                        Parameter existing = parameters.get(name);
                        if (existing != null) {
                            if (!Objects.equals(parameter.getType(), existing.getType())) {
                                reportIncompatibleParameterTypes(parameter, existing);
                            }
                        }
                        else {
                            parameters.put(parameter.getName(), parameter);
                        }
                    }
                }
            }
        }
        parameters.values().stream().sorted().forEach(parameter -> {
            Attribute parameterAttribute = new Attribute();
            parameterAttribute.setName(parameter.getName());
            parameterAttribute.setType(parameter.getType());
            parameterAttribute.setDeclaringType(actionType);
            actionType.addAttribute(parameterAttribute);
        });

        // Add the type to the model:
        actionType.setModule(model.getModule(null));
        model.addType(actionType);
    }

    private void reportIncompatibleParameterTypes(Parameter offending, Parameter existing) {
        String message = String.format(
            "The type \"%s\" of parameter \"%s\" isn't compatible with the type \"%s\" of existing parameter \"%s\"",
            offending.getType(),
            offending,
            existing.getType(),
            existing
        );
        throw new IllegalArgumentException(message);
    }
}

