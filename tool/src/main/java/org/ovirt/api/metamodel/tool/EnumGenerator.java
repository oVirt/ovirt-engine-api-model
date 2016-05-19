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

import java.io.IOException;
import java.util.Iterator;
import javax.inject.Inject;

import org.ovirt.api.metamodel.concepts.EnumType;
import org.ovirt.api.metamodel.concepts.EnumValue;
import org.ovirt.api.metamodel.concepts.Model;
import org.ovirt.api.metamodel.concepts.Type;

import static java.lang.String.join;
import static java.util.stream.Collectors.joining;

public class EnumGenerator extends JavaGenerator {

    @Inject
    private JavaPackages javaPackages;
    @Inject
    @Style("versioned")
    private VersionedJavaNames versionedJavaNames;

    // The buffer used to generate the source code:
    private JavaClassBuffer javaBuffer;

    public void generate(Model model) {
        for (Type type : model.getTypes()) {
            if (type instanceof EnumType) {
                EnumType enumType = (EnumType) type;
                generateEnum(enumType);
            }
        }
    }

    private void generateEnum(EnumType type) {
        javaBuffer = new JavaClassBuffer();
        JavaClassName enumName = getEnumName(type);
        javaBuffer.setClassName(enumName);
        generateEnumSource(type);
        try {
            javaBuffer.write(outDir);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private JavaClassName getEnumName(EnumType type) {
        JavaClassName enumName = new JavaClassName();
        enumName.setPackageName(javaPackages.getTypesPackageName());
        enumName.setSimpleName(versionedJavaNames.getJavaClassStyleName(type.getName()));
        return enumName;
    }

    private void generateEnumSource(EnumType enumType) {
        String enumName = javaBuffer.getClassName().getSimpleName();
        writeImports();
        writeClassHeader(enumName);
        writeEnumValues(enumType);
        writeLogger(enumName);
        writeValueMethod();
        writeFromValueMethod(enumName, enumType);
        writeClassClose();
    }

    private void writeImports() {
        javaBuffer.addLine("import org.slf4j.Logger;");
        javaBuffer.addLine("import org.slf4j.LoggerFactory;");
        javaBuffer.addLine();
    }

    private void writeClassHeader(String enumName) {
        javaBuffer.addLine("public enum %s {", enumName);
        javaBuffer.addLine();
    }

    private void writeLogger(String enumName) {
        javaBuffer.addLine("private static final Logger LOG = LoggerFactory.getLogger(" + enumName + ".class);");
        javaBuffer.addLine();
    }

    private void writeEnumValues(EnumType type) {
        Iterator<EnumValue> iterator = type.getValues().iterator();
        while (iterator.hasNext()) {
            EnumValue value = iterator.next();
            javaBuffer.addLine(getEnumValueLine(value, !iterator.hasNext()));
        }
        javaBuffer.addLine();
    }

    private String getEnumValueLine(EnumValue enumValue, boolean lastValue) {
        String value = join("_", enumValue.getName().getWords()).toUpperCase();
        return value + (lastValue ? ";" : ",");
    }

    private void writeValueMethod() {
        javaBuffer.addLine("public String value() {");
        javaBuffer.addLine("return name().toLowerCase();");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void writeFromValueMethod(String enumName, EnumType enumType) {
        javaBuffer.addLine("public static " + enumName + " fromValue(String value) {");
        javaBuffer.addLine("try {");
        javaBuffer.addLine("return valueOf(value.toUpperCase());");
        javaBuffer.addLine("}");
        javaBuffer.addLine("catch (IllegalArgumentException e) {");
        javaBuffer.addLine("LOG.error(\"" + nonExistingValueMessage(enumName, enumType)  + "\");");
        javaBuffer.addLine("return null;");
        javaBuffer.addLine("}");
        javaBuffer.addLine("}");
    }

    private String nonExistingValueMessage(String enumName, EnumType enumType) {
        StringBuilder builder = new StringBuilder();
        builder.append("The string '\" + value + \"' isn't a valid value for the '")
                .append(enumName)
                .append("' enumerated type. ")
                .append("Valid values are: ")
                .append(getValueValues(enumType))
                .append(".");
        return builder.toString();
    }

    private String getValueValues(EnumType enumType) {
        StringBuilder builder = new StringBuilder();
        for (EnumValue enumValue : enumType.getValues()) {
            builder.append("'")
            .append(getValueText(enumValue))
            .append("', ");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.deleteCharAt(builder.length()-1);
        return builder.toString();
    }

    private String getValueText(EnumValue value) {
        return value.getName().words().map(String::toLowerCase).collect(joining("_"));
    }

    private void writeClassClose() {
        javaBuffer.addLine();
        javaBuffer.addLine("}");
    }
}
