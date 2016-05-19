package org.ovirt.api.metamodel.tool;

import static java.lang.String.join;
import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.util.Iterator;
import javax.inject.Inject;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.ovirt.api.metamodel.concepts.EnumType;
import org.ovirt.api.metamodel.concepts.EnumValue;
import org.ovirt.api.metamodel.concepts.Model;
import org.ovirt.api.metamodel.concepts.Type;

public class EnumGeneratorJaxb extends JavaGenerator {

    @Inject
    private JavaPackages javaPackages;
    @Inject
    private JavaNames javaNames;

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
        enumName.setPackageName(javaPackages.getXjcPackageName());
        enumName.setSimpleName(javaNames.getJavaClassStyleName(type.getName()));
        return enumName;
    }

    private void generateEnumSource(EnumType enumType) {
        String enumName = javaBuffer.getClassName().getSimpleName();
        writeImports();
        writeAnnotations(enumName);
        writeClassHeader(enumName);
        writeEnumValues(enumType);
        writeValueMethod();
        writeFromValueMethod(enumName, enumType);
        writeAdapter(enumName);
        writeClassClose();
    }

    private void writeImports() {
        javaBuffer.addImport(XmlJavaTypeAdapter.class);
        javaBuffer.addImport(XmlType.class);
        javaBuffer.addLine("import org.ovirt.engine.api.utils.InvalidEnumValueException;");
        javaBuffer.addLine();
    }

    private void writeAnnotations(String enumName) {
        javaBuffer.addLine("@XmlType(name = \"%1$s\")", enumName);
        javaBuffer.addLine("@XmlJavaTypeAdapter(%1$s.Adapter.class)", enumName);
    }

    private void writeAdapter(String enumName) {
        javaBuffer.addImport(XmlAdapter.class);
        javaBuffer.addLine("public static class Adapter extends XmlAdapter<String, %1$s> {", enumName);
        javaBuffer.addLine(  "@Override");
        javaBuffer.addLine(  "public %1$s unmarshal(String image) throws Exception {", enumName);
        javaBuffer.addLine(    "return fromValue(image);");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine();
        javaBuffer.addLine(  "@Override");
        javaBuffer.addLine(  "public String marshal(%1$s value) throws Exception {", enumName);
        javaBuffer.addLine(    "return value.value();");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void writeClassHeader(String enumName) {
        javaBuffer.addLine("public enum %s {", enumName);
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
        javaBuffer.addLine(  "try {");
        javaBuffer.addLine(    "return valueOf(value.toUpperCase());");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "catch (IllegalArgumentException e) {");
        javaBuffer.addLine(    "throw new InvalidEnumValueException(\"" + nonExistingValueMessage(enumName, enumType)  + "\");");
        javaBuffer.addLine(  "}");
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
