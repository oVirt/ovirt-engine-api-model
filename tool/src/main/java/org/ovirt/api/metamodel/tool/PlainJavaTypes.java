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

import org.ovirt.api.metamodel.concepts.EnumType;
import org.ovirt.api.metamodel.concepts.ListType;
import org.ovirt.api.metamodel.concepts.Model;
import org.ovirt.api.metamodel.concepts.Name;
import org.ovirt.api.metamodel.concepts.NameParser;
import org.ovirt.api.metamodel.concepts.PrimitiveType;
import org.ovirt.api.metamodel.concepts.StructType;
import org.ovirt.api.metamodel.concepts.Type;

/**
 * This class calculates the type references for the Java classes generated from the model.
 */
@ApplicationScoped
public class PlainJavaTypes implements JavaTypes {
    // Suffixes to add to the different types of classes generated for each type:
    private static final Name CONTAINER_NAME = NameParser.parseUsingCase("Container");
    private static final Name BUILDER_NAME = NameParser.parseUsingCase("Builder");
    private static final Name READER_NAME = NameParser.parseUsingCase("Reader");
    private static final Name WRITER_NAME = NameParser.parseUsingCase("Writer");

    // Prefixes for the XML and JSON readers and writers:
    private static final Name XML_PREFIX = NameParser.parseUsingCase("Xml");
    private static final Name JSON_PREFIX = NameParser.parseUsingCase("Json");

    // Reference to the objects used to calculate package names:
    @Inject
    private JavaPackages javaPackages;

    // Reference to the object used to calculate Java names:
    @Inject
    @Style("versioned")
    private JavaNames javaNames;

    @Override
    public JavaClassName getInterfaceName(Type type) {
        return getTypeName(type, javaPackages.getTypesPackageName(), null, null);
    }

    @Override
    public JavaClassName getEnumName(Type type) {
        return getTypeName(type, javaPackages.getTypesPackageName(), null, null);
    }

    @Override
    public JavaClassName getBaseContainerName() {
        JavaClassName name = new JavaClassName();
        name.setPackageName(javaPackages.getContainersPackageName());
        name.setSimpleName(javaNames.getJavaClassStyleName(CONTAINER_NAME));
        return name;
    }

    @Override
    public JavaClassName getContainerName(Type type) {
        return getTypeName(type, javaPackages.getContainersPackageName(), null, CONTAINER_NAME);
    }

    @Override
    public JavaClassName getBaseBuilderName() {
        JavaClassName name = new JavaClassName();
        name.setPackageName(javaPackages.getBuildersPackageName());
        name.setSimpleName(javaNames.getJavaClassStyleName(BUILDER_NAME));
        return name;
    }

    @Override
    public JavaClassName getBuilderName(Type type) {
        return getTypeName(type, javaPackages.getBuildersPackageName(), null, BUILDER_NAME);
    }

    @Override
    public JavaClassName getBaseJsonReaderName() {
        JavaClassName name = new JavaClassName();
        name.setPackageName(javaPackages.getJsonPackageName());
        name.setSimpleName(javaNames.getJavaClassStyleName(decorateName(READER_NAME, JSON_PREFIX, null)));
        return name;
    }

    @Override
    public JavaClassName getJsonReaderName(Type type) {
        return getTypeName(type, javaPackages.getJsonPackageName(), JSON_PREFIX, READER_NAME);
    }

    @Override
    public JavaClassName getBaseJsonWriterName() {
        JavaClassName name = new JavaClassName();
        name.setPackageName(javaPackages.getJsonPackageName());
        name.setSimpleName(javaNames.getJavaClassStyleName(decorateName(WRITER_NAME, JSON_PREFIX, null)));
        return name;
    }

    @Override
    public JavaClassName getJsonWriterName(Type type) {
        return getTypeName(type, javaPackages.getJsonPackageName(), JSON_PREFIX, WRITER_NAME);
    }

    @Override
    public JavaClassName getBaseXmlReaderName() {
        JavaClassName name = new JavaClassName();
        name.setPackageName(javaPackages.getXmlPackageName());
        name.setSimpleName(javaNames.getJavaClassStyleName(decorateName(READER_NAME, XML_PREFIX, null)));
        return name;
    }

    @Override
    public JavaClassName getXmlReaderName(Type type) {
        return getTypeName(type, javaPackages.getXmlPackageName(), XML_PREFIX, READER_NAME);
    }

    @Override
    public JavaClassName getBaseXmlWriterName() {
        JavaClassName name = new JavaClassName();
        name.setPackageName(javaPackages.getXmlPackageName());
        name.setSimpleName(javaNames.getJavaClassStyleName(decorateName(WRITER_NAME, XML_PREFIX, null)));
        return name;
    }

    @Override
    public JavaClassName getXmlWriterName(Type type) {
        return getTypeName(type, javaPackages.getXmlPackageName(), XML_PREFIX, WRITER_NAME);
    }

    private JavaClassName getTypeName(Type type, String packageName, Name prefix, Name suffix) {
        if (type instanceof PrimitiveType) {
            return getPrimitiveTypeName((PrimitiveType) type, true);
        }
        if (type instanceof StructType || type instanceof EnumType) {
            Name name = decorateName(type.getName(), prefix, suffix);
            JavaClassName typeName = new JavaClassName();
            typeName.setPackageName(packageName);
            typeName.setSimpleName(javaNames.getJavaClassStyleName(name));
            return typeName;
        }
        throw new RuntimeException("Don't know how to calculate the Java type name for type \"" + type + "\"");
    }

    private JavaClassName getPrimitiveTypeName(PrimitiveType type, boolean preferWrapper) {
        JavaClassName name = new JavaClassName();
        Model model = type.getModel();
        if (type == model.getBooleanType()) {
            if (preferWrapper) {
                name.setClass(Boolean.class);
            }
            else {
                name.setSimpleName("boolean");
            }
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

    public JavaTypeReference getTypeReference(Type type, boolean preferWrapper) {
        if (type instanceof PrimitiveType) {
            JavaClassName name = getPrimitiveTypeName((PrimitiveType) type, preferWrapper);
            JavaTypeReference reference = new JavaTypeReference();
            reference.addImport(name);
            reference.setText(name.getSimpleName());
            return reference;
        }
        if (type instanceof StructType) {
            return getStructReference((StructType) type);
        }
        if (type instanceof EnumType) {
            return getEnumReference((EnumType) type);
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

    private JavaTypeReference getEnumReference(EnumType type) {
        JavaTypeReference reference = new JavaTypeReference();
        String text = javaNames.getJavaClassStyleName(type.getName());
        reference.setText(text);
        reference.addImport(javaPackages.getTypesPackageName(), text);
        return reference;
    }

    private JavaTypeReference getListReference(ListType type) {
        Type elementType = type.getElementType();
        JavaTypeReference reference = getTypeReference(elementType, true);
        if (reference != null) {
	        reference.addImport(List.class);
	        reference.setText("List<" + reference.getText() + ">");
        }
        return reference;
    }

    /**
     * Decorates a name with optional prefix and suffix.
     */
    private Name decorateName(Name name, Name prefix, Name suffix) {
        List<String> words = name.getWords();
        if (prefix != null) {
            words.addAll(0, prefix.getWords());
        }
        if (suffix != null) {
            words.addAll(suffix.getWords());
        }
        return new Name(words);
    }
}

