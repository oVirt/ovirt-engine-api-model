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

package org.ovirt.api.metamodel.tool;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.ovirt.api.metamodel.concepts.ListType;
import org.ovirt.api.metamodel.concepts.Model;
import org.ovirt.api.metamodel.concepts.PrimitiveType;
import org.ovirt.api.metamodel.concepts.StructType;
import org.ovirt.api.metamodel.concepts.Type;

/**
 * This class calculates the type references for the Java classes generated from the model.
 */
@ApplicationScoped
public class PlainJavaTypes implements JavaTypes {
    // Suffixes to add to the different types of classes generated for each type:
    private static final String INTERFACE_SUFFIX = "";
    private static final String CONTAINER_SUFFIX = "Container";
    private static final String BUILDER_SUFFIX = "Builder";

    // Reference to the objects used to calculate package names:
    @Inject
    private JavaPackages javaPackages;

    // Reference to the object used to calculate Java names:
    @Inject
    @Style("versioned")
    private JavaNames javaNames;

    @Override
    public JavaClassName getInterfaceName(Type type) {
        return getTypeName(type, javaPackages.getTypesPackageName(), INTERFACE_SUFFIX);
    }

    @Override
    public JavaClassName getContainerName(Type type) {
        return getTypeName(type, javaPackages.getContainersPackageName(), CONTAINER_SUFFIX);
    }

    @Override
    public JavaClassName getBuilderName(Type type) {
        return getTypeName(type, javaPackages.getBuildersPackageName(), BUILDER_SUFFIX);
    }

    private JavaClassName getTypeName(Type type, String packageName, String suffix) {
        if (type instanceof PrimitiveType) {
            return getPrimitiveTypeName((PrimitiveType) type);
        }
        if (type instanceof StructType) {
            JavaClassName name = new JavaClassName();
            name.setPackageName(packageName);
            name.setSimpleName(javaNames.getJavaClassStyleName(type.getName()) + suffix);
            return name;
        }
        throw new RuntimeException("Don't know how to calculate the Java type name for type \"" + type + "\"");
    }

    private JavaClassName getPrimitiveTypeName(PrimitiveType type) {
        JavaClassName name = new JavaClassName();
        Model model = type.getModel();
        if (type == model.getBooleanType()) {
            name.setClass(Boolean.class);
        }
        else if (type == model.getStringType()) {
            name.setClass(String.class);
        }
        else if (type == model.getIntegerType()) {
            name.setClass(BigInteger.class);
        }
        else if (type == model.getDateType()) {
            name.setClass(Date.class);
        }
        else if (type == model.getDecimalType()) {
            name.setClass(BigDecimal.class);
        }
        else {
            throw new RuntimeException("Don't know how to calculate the Java type reference for type \"" + type + "\"");
        }
        return name;
    }

    public JavaTypeReference getTypeReference(Type type) {
        if (type instanceof PrimitiveType) {
            JavaClassName name = getPrimitiveTypeName((PrimitiveType) type);
            JavaTypeReference reference = new JavaTypeReference();
            reference.addImport(name);
            reference.setText(name.getSimpleName());
            return reference;
        }
        if (type instanceof StructType) {
            return getStructReference((StructType) type);
        }
        if (type instanceof ListType) {
            return getListReference((ListType) type);
        }
        throw new RuntimeException("Don't know how to calculate the Java type reference for type \"" + type + "\"");
    }

    private JavaTypeReference getStructReference(StructType type) {
        JavaTypeReference reference = new JavaTypeReference();
        String text = javaNames.getJavaClassStyleName(type.getName());
        reference.setText(text);
        reference.addImport(javaPackages.getTypesPackageName(), text);
        return reference;
    }

    private JavaTypeReference getListReference(ListType type) {
        Type elementType = type.getElementType();
        JavaTypeReference reference = getTypeReference(elementType);
        reference.addImport(List.class);
        reference.setText("List<" + reference.getText() + ">");
        return reference;
    }
}

