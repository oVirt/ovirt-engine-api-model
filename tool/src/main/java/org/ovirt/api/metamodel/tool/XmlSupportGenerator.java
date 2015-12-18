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

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.ovirt.api.metamodel.concepts.EnumType;
import org.ovirt.api.metamodel.concepts.ListType;
import org.ovirt.api.metamodel.concepts.Model;
import org.ovirt.api.metamodel.concepts.Name;
import org.ovirt.api.metamodel.concepts.PrimitiveType;
import org.ovirt.api.metamodel.concepts.StructMember;
import org.ovirt.api.metamodel.concepts.StructType;
import org.ovirt.api.metamodel.concepts.Type;

/**
 * This class generates the XML readers and writers.
 */
public class XmlSupportGenerator extends JavaGenerator {
    // Reference to object used to calculate names:
    @Inject
    private Names names;

    // Reference to the object used to calculate package names:
    @Inject
    private JavaPackages javaPackages;

    // Reference to the object used to calculate Java names:
    @Inject
    @Style("versioned")
    private JavaNames javaNames;

    // Reference to the object used to calculate Java types:
    @Inject
    private JavaTypes javaTypes;

    // Reference to the object used to calculate XML schema names:
    @Inject private SchemaNames schemaNames;

    public void generate(Model model) {
        // Generate classes for each type:
        model.types()
            .filter(StructType.class::isInstance)
            .map(StructType.class::cast)
            .forEach(this::generateClasses);
    }

    private void generateClasses(StructType type) {
        generateBaseReader();
        generateReader(type);
        generateBaseWriter();
        generateWriter(type);
    }

    private void generateBaseReader() {
        JavaClassName baseName = javaTypes.getBaseXmlReaderName();
        javaBuffer = new JavaClassBuffer();
        javaBuffer.setClassName(baseName);
        generateBaseReaderSource(baseName);
        try {
            javaBuffer.write(outDir);
        }
        catch (IOException exception) {
            throw new RuntimeException("Can't write file for base XML reader \"" + baseName + "\"", exception);
        }
    }

    private void generateBaseReaderSource(JavaClassName baseName) {
        // Add imports:
        javaBuffer.addImport(BigDecimal.class);
        javaBuffer.addImport(BigInteger.class);
        javaBuffer.addImport(Date.class);
        javaBuffer.addImport(ParseException.class);
        javaBuffer.addImport(SimpleDateFormat.class);
        javaBuffer.addImport(XMLStreamConstants.class);
        javaBuffer.addImport(XMLStreamException.class);
        javaBuffer.addImport(XMLStreamReader.class);

        // Begin class:
        javaBuffer.addLine("public class %1$s {", baseName.getSimpleName());

        javaBuffer.addDocComment(
            "Thread local used to store the date formats."
        );
        javaBuffer.addLine(
            "private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = " +
                "ThreadLocal.withInitial(() -> new SimpleDateFormat(\"yyyy-MM-dd'T'HH:mm:ss.SSSXXX\"));"
        );
        javaBuffer.addLine();

        // Forward method:
        javaBuffer.addDocComment(
            "Jumps to the next start tag, end tag or end of document. Returns {@code true} if stopped at a start ",
            "tag, {@code false} otherwise."
        );
        javaBuffer.addLine("public static boolean forward(XMLStreamReader cursor) throws XMLStreamException {");
        javaBuffer.addLine(  "for(;;) {");
        javaBuffer.addLine(    "switch (cursor.getEventType()) {");
        javaBuffer.addLine(    "case XMLStreamConstants.START_ELEMENT:");
        javaBuffer.addLine(      "return true;");
        javaBuffer.addLine(    "case XMLStreamConstants.END_ELEMENT:");
        javaBuffer.addLine(    "case XMLStreamConstants.END_DOCUMENT:");
        javaBuffer.addLine(      "return false;");
        javaBuffer.addLine(    "default:");
        javaBuffer.addLine(      "cursor.next();");
        javaBuffer.addLine(    "}");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine("}");

        // Skip method:
        javaBuffer.addDocComment(
            "Skips the current element, and all the inner elements."
        );
        javaBuffer.addLine("public static void skip(XMLStreamReader cursor) throws XMLStreamException {");
        javaBuffer.addLine(  "int depth = 0;");
        javaBuffer.addLine(  "for(;;) {");
        javaBuffer.addLine(    "switch (cursor.getEventType()) {");
        javaBuffer.addLine(    "case XMLStreamConstants.START_ELEMENT:");
        javaBuffer.addLine(      "depth++;");
        javaBuffer.addLine(      "break;");
        javaBuffer.addLine(    "case XMLStreamConstants.END_ELEMENT:");
        javaBuffer.addLine(      "depth--;");
        javaBuffer.addLine(      "if (depth <= 0) {");
        javaBuffer.addLine(        "return;");
        javaBuffer.addLine(      "}");
        javaBuffer.addLine(      "break;");
        javaBuffer.addLine(    "case XMLStreamConstants.END_DOCUMENT:");
        javaBuffer.addLine(      "return;");
        javaBuffer.addLine(    "}");
        javaBuffer.addLine(    "cursor.next();");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine("}");

        // Read boolean method:
        javaBuffer.addDocComment(
            "Reads a boolean value from the given XML reader, assuming that the cursor is positioned at the start ",
            "element that contains the value of the boolean."
        );
        javaBuffer.addLine("public static boolean readBoolean(XMLStreamReader cursor) throws XMLStreamException {");
        javaBuffer.addLine(  "String image = readString(cursor);");
        javaBuffer.addLine(  "if (image.equalsIgnoreCase(\"false\") || image.equals(\"0\")) {");
        javaBuffer.addLine(    "return false;");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "else if (image.equalsIgnoreCase(\"true\") || image.equals(\"1\")) {");
        javaBuffer.addLine(    "return true;");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "else {");
        javaBuffer.addLine(    "throw new XMLStreamException(\"The text \\\"\" + image + \"\\\" isn't a valid boolean value\");");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine("}");

        // Read integer method:
        javaBuffer.addDocComment(
            "Reads an integer value from the given XML reader, assuming that the cursor is positioned at the start ",
            "element that contains the value of the integer."
        );
        javaBuffer.addLine("public static BigInteger readInteger(XMLStreamReader cursor) throws XMLStreamException {");
        javaBuffer.addLine(  "String image = readString(cursor);");
        javaBuffer.addLine(  "try {");
        javaBuffer.addLine(    "return new BigInteger(image);");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "catch (NumberFormatException exception) {");
        javaBuffer.addLine(    "throw new XMLStreamException(\"The text \\\"\" + image + \"\\\" isn't a valid integer value\");");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine("}");

        // Read decimal method:
        javaBuffer.addDocComment(
            "Reads an decimal value from the given XML reader, assuming that the cursor is positioned at the start ",
            "element that contains the value of the decimal."
        );
        javaBuffer.addLine("public static BigDecimal readDecimal(XMLStreamReader cursor) throws XMLStreamException {");
        javaBuffer.addLine(  "String image = readString(cursor);");
        javaBuffer.addLine(  "try {");
        javaBuffer.addLine(    "return new BigDecimal(image).stripTrailingZeros();");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "catch (NumberFormatException exception) {");
        javaBuffer.addLine(    "throw new XMLStreamException(\"The text \\\"\" + image + \"\\\" isn't a valid decimal value\");");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine("}");

        // Read string method:
        javaBuffer.addDocComment(
            "Reads an string value from the given XML reader, assuming that the cursor is positioned at the start ",
            "element that contains the value of the string."
        );
        javaBuffer.addLine("public static String readString(XMLStreamReader cursor) throws XMLStreamException {");
        javaBuffer.addLine(  "try {");
        javaBuffer.addLine(     "return cursor.getElementText();");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "finally {");
        javaBuffer.addLine(    "cursor.next();");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine("}");

        // Read date method:
        javaBuffer.addDocComment(
            "Reads a date value from the given XML reader, assuming that the cursor is positioned at the start ",
            "element that contains the value of the date."
        );
        javaBuffer.addLine("public static Date readDate(XMLStreamReader cursor) throws XMLStreamException {");
        javaBuffer.addLine(  "String image = readString(cursor);");
        javaBuffer.addLine(  "try {");
        javaBuffer.addLine(    "return DATE_FORMAT.get().parse(image);");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "catch(ParseException exception) {");
        javaBuffer.addLine(    "throw new XMLStreamException(\"The text \\\"\" + image + \"\\\" isn't a valid date value\");");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine("}");

        // End class:
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateReader(StructType type) {
        javaBuffer = new JavaClassBuffer();
        JavaClassName readerName = javaTypes.getXmlReaderName(type);
        javaBuffer.setClassName(readerName);
        generateReaderSource(type);
        try {
            javaBuffer.write(outDir);
        }
        catch (IOException exception) {
            throw new RuntimeException("Can't write file for XML reader \"" + readerName + "\"", exception);
        }
    }

    private void generateReaderSource(StructType type) {
        // Begin class:
        JavaClassName baseName = javaTypes.getBaseXmlReaderName();
        JavaClassName readerName = javaTypes.getXmlReaderName(type);
        javaBuffer.addLine("public class %1$s extends %2$s {", readerName.getSimpleName(), baseName.getSimpleName());
        javaBuffer.addLine();

        // Generate methods to read one instance and a list of instances:
        generateReadOne(type);
        generateReadMany(type);

        // End class:
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateReadOne(StructType type) {
        // Get the type and container name:
        JavaClassName typeName = javaTypes.getInterfaceName(type);
        JavaClassName containerName = javaTypes.getContainerName(type);

        // Add the required imports:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(containerName);
        javaBuffer.addImport(XMLStreamConstants.class);
        javaBuffer.addImport(XMLStreamException.class);
        javaBuffer.addImport(XMLStreamReader.class);

        // Generate the method:
        javaBuffer.addLine("public static %1$s readOne(XMLStreamReader cursor) throws XMLStreamException {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "// Do nothing if there aren't more tags:");
        javaBuffer.addLine(  "if (!forward(cursor)) {");
        javaBuffer.addLine(    "return null;");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine();
        javaBuffer.addLine(  "// Create the object:");
        javaBuffer.addLine(  "%1$s object = new %1$s();", containerName.getSimpleName());
        javaBuffer.addLine();
        javaBuffer.addLine(  "// Process the attributes:");
        javaBuffer.addLine(  "for (int i = 0; i < cursor.getAttributeCount(); i++) {");
        javaBuffer.addLine(    "String name = cursor.getAttributeLocalName(i);");
        javaBuffer.addLine(    "String image = cursor.getAttributeValue(i);");
        javaBuffer.addLine(    "switch (name) {");
        Stream.concat(type.attributes(), type.links())
            .filter(x -> schemaNames.isRepresentedAsAttribute(x.getName()))
            .sorted()
            .forEach(this::generateReadMemberFromAttribute);
        javaBuffer.addLine(    "}");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine();
        javaBuffer.addLine(  "// Process the inner elements:");
        javaBuffer.addLine(  "cursor.next();");
        javaBuffer.addLine(  "while (forward(cursor)) {");
        javaBuffer.addLine(    "String name = cursor.getLocalName();");
        javaBuffer.addLine(    "switch (name) {");
        Stream.concat(type.attributes(), type.links())
            .filter(x -> !schemaNames.isRepresentedAsAttribute(x.getName()))
            .sorted()
            .forEach(this::generateReadMemberFromElement);
        javaBuffer.addLine(    "default:");
        javaBuffer.addLine(      "skip(cursor);");
        javaBuffer.addLine(    "}");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine();
        javaBuffer.addLine(  "// Discard the end tag:");
        javaBuffer.addLine(  "cursor.next();");
        javaBuffer.addLine();
        javaBuffer.addLine(  "return object;");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateReadMemberFromAttribute(StructMember member) {
        Name name = member.getName();
        Type type = member.getType();
        if (type instanceof PrimitiveType) {
            String property = javaNames.getJavaPropertyStyleName(name);
            String tag = schemaNames.getSchemaTagName(name);
            javaBuffer.addLine("case \"%1$s\":", tag);
            Model model = type.getModel();
            if (type == model.getBooleanType()) {
                javaBuffer.addLine("object.set%1$s(Boolean.parseBoolean(image));", property);
            }
            else if (type == model.getIntegerType()) {
                javaBuffer.addImport(BigInteger.class);
                javaBuffer.addLine("object.set%1$s(new BigInteger(image));", property);
            }
            else if (type == model.getDecimalType()) {
                javaBuffer.addImport(BigDecimal.class);
                javaBuffer.addLine("object.set%1$s(new BigDecimal(image));", property);
            }
            else if (type == model.getStringType()) {
                javaBuffer.addLine("object.set%1$s(image);", property);
            }
            else if (type == model.getDateType()) {
                javaBuffer.addLine("object.set%1$s(DATE_FORMAT.parse(image));", property);
            }
            javaBuffer.addLine("break;");
        }
    }

    private void generateReadMemberFromElement(StructMember member) {
        Name name = member.getName();
        Type type = member.getType();
        String property = javaNames.getJavaPropertyStyleName(name);
        String tag = schemaNames.getSchemaTagName(name);
        javaBuffer.addLine("case \"%1$s\":", tag);
        if (type instanceof PrimitiveType) {
            Model model = type.getModel();
            if (type == model.getBooleanType()) {
                javaBuffer.addLine("object.set%1$s(readBoolean(cursor));", property);
            }
            else if (type == model.getIntegerType()) {
                javaBuffer.addLine("object.set%1$s(readInteger(cursor));", property);
            }
            else if (type == model.getDecimalType()) {
                javaBuffer.addLine("object.set%1$s(readDecimal(cursor));", property);
            }
            else if (type == model.getStringType()) {
                javaBuffer.addLine("object.set%1$s(readString(cursor));", property);
            }
            else if (type == model.getDateType()) {
                javaBuffer.addLine("object.set%1$s(readDate(cursor));", property);
            }
            else {
                javaBuffer.addLine("skip(cursor);");
            }
        }
        else if (type instanceof StructType) {
            JavaClassName readerName = javaTypes.getXmlReaderName(type);
            javaBuffer.addImport(readerName);
            javaBuffer.addLine("object.set%1$s(%2$s.readOne(cursor));", property, readerName.getSimpleName());
        }
        else if (type instanceof ListType) {
            ListType listType = (ListType) type;
            Type elementType = listType.getElementType();
            JavaClassName readerName = javaTypes.getXmlReaderName(elementType);
            javaBuffer.addImport(readerName);
            if (elementType instanceof StructType) {
                javaBuffer.addLine("object.set%1$s(%2$s.readMany(cursor));", property, readerName.getSimpleName());
            }
        }
        else {
            javaBuffer.addLine("skip(cursor);");
        }
        javaBuffer.addLine("break;");
    }

    private void generateReadMany(StructType type) {
        // Get the type name:
        JavaClassName typeName = javaTypes.getInterfaceName(type);
        JavaClassName readerName = javaTypes.getXmlReaderName(type);

        // Add the required imports:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(ArrayList.class);
        javaBuffer.addImport(Iterator.class);
        javaBuffer.addImport(List.class);
        javaBuffer.addImport(XMLStreamException.class);
        javaBuffer.addImport(XMLStreamReader.class);

        // Iterate method:
        javaBuffer.addLine("public static Iterator<%1$s> iterateMany(XMLStreamReader cursor) throws XMLStreamException {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "return new Iterator<%1$s>() {", typeName.getSimpleName());
        javaBuffer.addLine(    "private boolean first = true;");
        javaBuffer.addLine();
        javaBuffer.addLine(    "@Override");
        javaBuffer.addLine(    "public boolean hasNext() {");
        javaBuffer.addLine(      "try {");
        javaBuffer.addLine(        "if (first) {");
        javaBuffer.addLine(          "if (!forward(cursor)) {");
        javaBuffer.addLine(            "return false;");
        javaBuffer.addLine(          "}");
        javaBuffer.addLine(          "cursor.next();");
        javaBuffer.addLine(          "first = false;");
        javaBuffer.addLine(        "}");
        javaBuffer.addLine(        "if (!forward(cursor)) {");
        javaBuffer.addLine(          "cursor.next();");
        javaBuffer.addLine(          "return false;");
        javaBuffer.addLine(        "}");
        javaBuffer.addLine(        "return true;");
        javaBuffer.addLine(      "}");
        javaBuffer.addLine(      "catch (XMLStreamException exception) {");
        javaBuffer.addLine(        "throw new RuntimeException(exception);");
        javaBuffer.addLine(      "}");
        javaBuffer.addLine(    "}");
        javaBuffer.addLine();
        javaBuffer.addLine(    "@Override");
        javaBuffer.addLine(    "public %1$s next() {", typeName.getSimpleName());
        javaBuffer.addLine(      "try {");
        javaBuffer.addLine(        "return %1$s.readOne(cursor);", readerName.getSimpleName());
        javaBuffer.addLine(      "}");
        javaBuffer.addLine(      "catch (XMLStreamException exception) {");
        javaBuffer.addLine(        "throw new RuntimeException(exception);");
        javaBuffer.addLine(      "}");
        javaBuffer.addLine(    "}");
        javaBuffer.addLine(  "};");
        javaBuffer.addLine("}");
        javaBuffer.addLine();

        // Read method:
        javaBuffer.addLine("public static List<%1$s> readMany(XMLStreamReader cursor) throws XMLStreamException {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "List<%1$s> list = new ArrayList<>();", typeName.getSimpleName());
        javaBuffer.addLine(  "Iterator<%1$s> iterator = iterateMany(cursor);", typeName.getSimpleName());
        javaBuffer.addLine(  "while (iterator.hasNext()) {");
        javaBuffer.addLine(    "list.add(iterator.next());");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "return list;");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateBaseWriter() {
        JavaClassName baseName = javaTypes.getBaseXmlWriterName();
        javaBuffer = new JavaClassBuffer();
        javaBuffer.setClassName(baseName);
        generateBaseWriterSource(baseName);
        try {
            javaBuffer.write(outDir);
        }
        catch (IOException exception) {
            throw new RuntimeException("Can't write file for base XML writer \"" + baseName + "\"", exception);
        }
    }

    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(SimpleDateFormat::new);

    private void generateBaseWriterSource(JavaClassName baseName) {
        // Add imports:
        javaBuffer.addImport(BigDecimal.class);
        javaBuffer.addImport(BigInteger.class);
        javaBuffer.addImport(Date.class);
        javaBuffer.addImport(SimpleDateFormat.class);
        javaBuffer.addImport(XMLStreamException.class);
        javaBuffer.addImport(XMLStreamWriter.class);

        // Begin class:
        javaBuffer.addLine("public class %1$s {", baseName.getSimpleName());
        javaBuffer.addLine();

        javaBuffer.addDocComment(
            "Thread local used to store the date formats."
        );
        javaBuffer.addLine(
            "private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = " +
            "ThreadLocal.withInitial(() -> new SimpleDateFormat(\"yyyy-MM-dd'T'HH:mm:ss.SSSXXX\"));"
        );
        javaBuffer.addLine();

        // Write boolean method:
        javaBuffer.addDocComment(
            "Writes a boolean value to the given XML writer."
        );
        javaBuffer.addLine("public static void writeBoolean(String name, boolean value, XMLStreamWriter cursor) throws XMLStreamException {");
        javaBuffer.addLine(  "writeString(name, Boolean.toString(value), cursor);");
        javaBuffer.addLine("}");

        // Write integer method:
        javaBuffer.addDocComment(
            "Writes an integer value to the given XML writer."
        );
        javaBuffer.addLine("public static void writeInteger(String name, BigInteger value, XMLStreamWriter cursor) throws XMLStreamException {");
        javaBuffer.addLine(  "writeString(name, value.toString(), cursor);");
        javaBuffer.addLine("}");

        // Write decimal method:
        javaBuffer.addDocComment(
            "Writes a decimal value to the given XML writer."
        );
        javaBuffer.addLine("public static void writeDecimal(String name, BigDecimal value, XMLStreamWriter cursor) throws XMLStreamException {");
        javaBuffer.addLine(  "writeString(name, value.toString(), cursor);");
        javaBuffer.addLine("}");

        // Write date method:
        javaBuffer.addDocComment(
            "Writes a string to the given XML writer."
        );
        javaBuffer.addLine("public static void writeString(String name, String value, XMLStreamWriter cursor) throws XMLStreamException {");
        javaBuffer.addLine(  "cursor.writeStartElement(name);");
        javaBuffer.addLine(  "cursor.writeCharacters(value);");
        javaBuffer.addLine(  "cursor.writeEndElement();");
        javaBuffer.addLine("}");

        // Write date method:
        javaBuffer.addDocComment(
            "Writes a date to the given XML writer."
        );
        javaBuffer.addLine("public static void writeDate(String name, Date value, XMLStreamWriter cursor) throws XMLStreamException {");
        javaBuffer.addLine(  "writeString(name, DATE_FORMAT.get().format(value), cursor);");
        javaBuffer.addLine("}");

        // End class:
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateWriter(StructType type) {
        javaBuffer = new JavaClassBuffer();
        JavaClassName writerName = javaTypes.getXmlWriterName(type);
        javaBuffer.setClassName(writerName);
        generateWriterSource(type);
        try {
            javaBuffer.write(outDir);
        }
        catch (IOException exception) {
            throw new RuntimeException("Can't write file for XML writer \"" + writerName + "\"", exception);
        }
    }

    private void generateWriterSource(StructType type) {
        // Begin class:
        JavaClassName writerName = javaTypes.getXmlWriterName(type);
        JavaClassName baseName = javaTypes.getBaseXmlWriterName();
        javaBuffer.addLine("public class %1$s extends %2$s {", writerName.getSimpleName(), baseName.getSimpleName());
        javaBuffer.addLine();

        // Generate methods to write one instance and a list of instances:
        generateWriteOne(type);
        generateWriteMany(type);

        // End class:
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateWriteOne(StructType type) {
        // Calculate the name of the type and the XML tag:
        JavaClassName typeName = javaTypes.getInterfaceName(type);
        String tag = schemaNames.getSchemaTagName(type.getName());

        // Add the required imports:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(XMLStreamWriter.class);
        javaBuffer.addImport(XMLStreamException.class);

        // Generate the method that uses the default tag name:
        javaBuffer.addLine(
            "public static void writeOne(%1$s object, XMLStreamWriter cursor) throws XMLStreamException {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "writeOne(object, \"%1$s\", cursor);", tag);
        javaBuffer.addLine("}");
        javaBuffer.addLine();

        // Generate the method that receives the tag name as parameter:
        javaBuffer.addLine(
            "public static void writeOne(%1$s object, String tag, XMLStreamWriter cursor) throws XMLStreamException {",
            typeName.getSimpleName());
        javaBuffer.addLine("cursor.writeStartElement(tag);");
        Stream.concat(type.attributes(), type.links())
            .filter(x -> schemaNames.isRepresentedAsAttribute(x.getName()))
            .sorted()
            .forEach(this::generateWriteMemberAsAttribute);
        Stream.concat(type.attributes(), type.links())
            .filter(x -> !schemaNames.isRepresentedAsAttribute(x.getName()))
            .sorted()
            .forEach(this::generateWriteMemberAsElement);
        javaBuffer.addLine("cursor.writeEndElement();");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateWriteMemberAsAttribute(StructMember member) {
        Name name = member.getName();
        Type type = member.getType();
        String property = javaNames.getJavaPropertyStyleName(name);
        String tag = schemaNames.getSchemaTagName(name);
        javaBuffer.addLine("if (object.has%1$s()) {", property);
        if (type instanceof PrimitiveType) {
            Model model = type.getModel();
            if (type == model.getBooleanType() || type == model.getIntegerType() || type == model.getDecimalType()) {
                javaBuffer.addLine("cursor.writeAttribute(\"%1$s\", object.get%2$s().toString());", tag, property);
            }
            else if (type == model.getStringType()) {
                javaBuffer.addLine("cursor.writeAttribute(\"%1$s\", object.get%2$s());", tag, property);
            }
            else if (type == model.getDateType()) {
                // TODO: This isn't the XML schema format.
                javaBuffer.addLine("cursor.writeAttribute(\"%1$s\", object.get%2$s().toString());", tag, property);
            }
        }
        else if (type instanceof EnumType) {
            // TODO: Should get the value calling the "getValue()" method.
            javaBuffer.addLine("cursor.writeAttribute(\"%1$s\", object.get%2$s().toString());", tag, property);
        }
        javaBuffer.addLine("}");
    }

    private void generateWriteMemberAsElement(StructMember member) {
        Name name = member.getName();
        Type type = member.getType();
        String property = javaNames.getJavaPropertyStyleName(name);
        String tag = schemaNames.getSchemaTagName(name);
        javaBuffer.addLine("if (object.has%1$s()) {", property);
        if (type instanceof PrimitiveType) {
            Model model = type.getModel();
            if (type == model.getBooleanType()) {
                javaBuffer.addLine("writeBoolean(\"%1$s\", object.get%2$s(), cursor);", tag, property);
            }
            else if (type == model.getIntegerType()) {
                javaBuffer.addLine("writeInteger(\"%1$s\", object.get%2$s(), cursor);", tag, property);
            }
            else if (type == model.getDecimalType()) {
                javaBuffer.addLine("writeDecimal(\"%1$s\", object.get%2$s(), cursor);", tag, property);
            }
            else if (type == model.getStringType()) {
                javaBuffer.addLine("writeString(\"%1$s\", object.get%2$s(), cursor);", tag, property);
            }
            else if (type == model.getDateType()) {
                javaBuffer.addLine("writeDate(\"%1$s\", object.get%2$s(), cursor);", tag, property);
            }
        }
        else if (type instanceof EnumType) {
            // TODO: Should get the value calling the "getValue()" method.
            javaBuffer.addLine("cursor.writeStartElement(\"%1$s\");", tag);
            javaBuffer.addLine("cursor.writeCharacters(object.get%1$s().toString());", property);
            javaBuffer.addLine("cursor.writeEndElement();");
        }
        else if (type instanceof StructType) {
            JavaClassName writerName = javaTypes.getXmlWriterName(type);
            javaBuffer.addImport(writerName);
            javaBuffer.addLine("%1$s.writeOne(object.get%2$s(), \"%3$s\", cursor);",
                writerName.getSimpleName(), property, tag);
        }
        else if (type instanceof ListType) {
            ListType listType = (ListType) type;
            Type elementType = listType.getElementType();
            if (elementType instanceof StructType) {
                JavaClassName writerName = javaTypes.getXmlWriterName(elementType);
                javaBuffer.addImport(writerName);
                javaBuffer.addLine("%1$s.writeMany(object.get%2$s().iterator(), cursor);",
                    writerName.getSimpleName(), property);
            }
        }
        javaBuffer.addLine("}");
    }

    private void generateWriteMany(StructType type) {
        // Calculate the tag names:
        Name singularName = type.getName();
        Name pluralName = names.getPlural(singularName);
        String singularTag = schemaNames.getSchemaTagName(singularName);
        String pluralTag = schemaNames.getSchemaTagName(pluralName);

        // Get the name of the type and writer:
        JavaClassName typeName = javaTypes.getInterfaceName(type);
        JavaClassName writerName = javaTypes.getXmlWriterName(type);

        // Add the required imports:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(writerName);
        javaBuffer.addImport(Iterator.class);
        javaBuffer.addImport(XMLStreamWriter.class);
        javaBuffer.addImport(XMLStreamException.class);

        // Generate the method that uses the default tag names:
        javaBuffer.addLine(
            "public static void writeMany(Iterator<%1$s> list, XMLStreamWriter cursor) throws XMLStreamException {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "writeMany(list, \"%1$s\", \"%2$s\", cursor);", singularTag, pluralTag);
        javaBuffer.addLine("}");
        javaBuffer.addLine();

        // Generate the method that uses custom tag names:
        javaBuffer.addLine(
            "public static void writeMany(Iterator<%1$s> list, String singular, String plural, XMLStreamWriter cursor) throws XMLStreamException {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "cursor.writeStartElement(plural);");
        javaBuffer.addLine(  "while (list.hasNext()) {");
        javaBuffer.addLine(    "%1$s.writeOne(list.next(), singular, cursor);", writerName.getSimpleName());
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "cursor.writeEndElement();");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }
}
