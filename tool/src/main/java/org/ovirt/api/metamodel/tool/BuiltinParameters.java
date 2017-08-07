/*
Copyright (c) 2017 Red Hat, Inc.

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

import javax.enterprise.context.ApplicationScoped;

import org.ovirt.api.metamodel.concepts.Method;
import org.ovirt.api.metamodel.concepts.Model;
import org.ovirt.api.metamodel.concepts.Name;
import org.ovirt.api.metamodel.concepts.NameParser;
import org.ovirt.api.metamodel.concepts.Parameter;
import org.ovirt.api.metamodel.concepts.PrimitiveType;
import org.ovirt.api.metamodel.concepts.Service;

/**
 * This class contains the method used to add the built-in types like {@code follow} to a method.
 * These parameters are common to sets of methods, rather than being tied specific business flows.
 * For example,  {@code follow} is relevant for all GET requests.
 *
 * @author oliel
 */
@ApplicationScoped
public class BuiltinParameters {

    private static final Name GET_METHOD_NAME = NameParser.parseUsingCase("Get");
    private static final Name LIST_METHOD_NAME = NameParser.parseUsingCase("List");
    private static final Name FOLLOW_PARAMETER_NAME = NameParser.parseUsingCase("Follow");

    public void addBuiltinParameters(Model model) {
        //add 'follow' parameter to get(), list() methods.
        model.services().forEach(this::addFollowParameters);
    }

    /**
     * Add the 'follow' parameter to get(), list() methods.
     * The value of this parameter indicates which inner links
     * should be 'followed', i.e their actual contents fetched
     * and returned along with the entity.
     */
    private void addFollowParameters(Service service) {
        if (service.getMethod(GET_METHOD_NAME)!=null) {
            addFollowParameter(service.getMethod(GET_METHOD_NAME));
        }
        if (service.getMethod(LIST_METHOD_NAME)!=null) {
            addFollowParameter(service.getMethod(LIST_METHOD_NAME));
        }
    }

    private void addFollowParameter(Method method) {
        Parameter followParameter = new Parameter();
        followParameter.setName(FOLLOW_PARAMETER_NAME);
        followParameter.setIn(true);
        StringBuilder doc = new StringBuilder("Indicates which inner links should be `followed`;")
                .append(" i.e their actual contents fetched and returned along with the object.")
                .append(" Links are separated by commas, and levels of depth are denoted by periods.")
                .append(" Example value for fetching vms: `cluster,nics,disk_attachments.disk`");
        followParameter.setDoc(doc.toString());
        followParameter.setType(method.getDeclaringService().getModel().getStringType());
        followParameter.setDeclaringMethod(method);
        method.addParameter(followParameter);
    }
}
