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

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import javax.inject.Inject;

import org.ovirt.api.metamodel.concepts.EnumType;
import org.ovirt.api.metamodel.concepts.ListType;
import org.ovirt.api.metamodel.concepts.Model;
import org.ovirt.api.metamodel.concepts.Name;
import org.ovirt.api.metamodel.concepts.PrimitiveType;
import org.ovirt.api.metamodel.concepts.StructMember;
import org.ovirt.api.metamodel.concepts.StructType;
import org.ovirt.api.metamodel.concepts.Type;
import org.ovirt.api.metamodel.runtime.xml.XmlReader;
import org.ovirt.api.metamodel.runtime.xml.XmlWriter;

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
        generateReader(type);
        generateWriter(type);
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
        JavaClassName readerName = javaTypes.getXmlReaderName(type);
        javaBuffer.addLine("public class %1$s {", readerName.getSimpleName());
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
        javaBuffer.addImport(XmlReader.class);

        // Generate the method:
        List<StructMember> allMembers = new ArrayList<>();
        allMembers.addAll(type.getAttributes());
        allMembers.addAll(type.getLinks());
        List<StructMember> asAttributes = allMembers.stream()
            .filter(x -> schemaNames.isRepresentedAsAttribute(x.getName()))
            .collect(toList());
        List<StructMember> asElements = allMembers.stream()
            .filter(x -> !schemaNames.isRepresentedAsAttribute(x.getName()))
            .collect(toList());
        javaBuffer.addLine("public static %1$s readOne(XmlReader reader) {", typeName.getSimpleName());
        javaBuffer.addLine(  "// Do nothing if there aren't more tags:");
        javaBuffer.addLine(  "if (!reader.forward()) {");
        javaBuffer.addLine(    "return null;");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine();
        javaBuffer.addLine(  "// Create the object:");
        javaBuffer.addLine(  "%1$s object = new %1$s();", containerName.getSimpleName());
        javaBuffer.addLine();
        if (!asAttributes.isEmpty()) {
            javaBuffer.addLine("// Process the attributes:");
            javaBuffer.addLine("for (int i = 0; i < reader.getAttributeCount(); i++) {");
            javaBuffer.addLine(  "String name = reader.getAttributeLocalName(i);");
            javaBuffer.addLine(  "String image = reader.getAttributeValue(i);");
            javaBuffer.addLine(  "switch (name) {");
            asAttributes.stream()
                .sorted()
                .forEach(this::generateReadMemberFromAttribute);
            javaBuffer.addLine(  "default:");
            javaBuffer.addLine(    "break;");
            javaBuffer.addLine(  "}");
            javaBuffer.addLine("}");
            javaBuffer.addLine();
        }
        javaBuffer.addLine(  "// Process the inner elements:");
        javaBuffer.addLine(  "reader.next();");
        javaBuffer.addLine(  "while (reader.forward()) {");
        if (!asElements.isEmpty()) {
            javaBuffer.addLine("String name = reader.getLocalName();");
            javaBuffer.addLine("switch (name) {");
            asElements.stream()
                .sorted()
                .forEach(this::generateReadMemberFromElement);
            javaBuffer.addLine("default:");
            javaBuffer.addLine(  "reader.skip();");
            javaBuffer.addLine(  "break;");
            javaBuffer.addLine("}");
        }
        else {
            javaBuffer.addLine("reader.skip();");
        }
        javaBuffer.addLine("}");
        javaBuffer.addLine();
        javaBuffer.addLine(  "// Discard the end tag:");
        javaBuffer.addLine(  "reader.next();");
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
                javaBuffer.addLine("object.set%1$s(reader.readBoolean());", property);
            }
            else if (type == model.getIntegerType()) {
                javaBuffer.addLine("object.set%1$s(reader.readInteger());", property);
            }
            else if (type == model.getDecimalType()) {
                javaBuffer.addLine("object.set%1$s(reader.readDecimal());", property);
            }
            else if (type == model.getStringType()) {
                javaBuffer.addLine("object.set%1$s(reader.readString());", property);
            }
            else if (type == model.getDateType()) {
                javaBuffer.addLine("object.set%1$s(reader.readDate());", property);
            }
            else {
                javaBuffer.addLine("reader.skip();");
            }
        }
        else if (type instanceof StructType) {
            JavaClassName readerName = javaTypes.getXmlReaderName(type);
            javaBuffer.addImport(readerName);
            javaBuffer.addLine("object.set%1$s(%2$s.readOne(reader));", property, readerName.getSimpleName());
        }
        else if (type instanceof ListType) {
            ListType listType = (ListType) type;
            Type elementType = listType.getElementType();
            JavaClassName readerName = javaTypes.getXmlReaderName(elementType);
            javaBuffer.addImport(readerName);
            if (elementType instanceof StructType) {
                javaBuffer.addLine("object.set%1$s(%2$s.readMany(reader));", property, readerName.getSimpleName());
            }
        }
        else {
            javaBuffer.addLine("reader.skip();");
        }
        javaBuffer.addLine("break;");
    }

    private void generateReadMany(StructType type) {
        // Get the type name:
        JavaClassName typeName = javaTypes.getInterfaceName(type);

        // Add the required imports:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(ArrayList.class);
        javaBuffer.addImport(Iterator.class);
        javaBuffer.addImport(List.class);
        javaBuffer.addImport(NoSuchElementException.class);
        javaBuffer.addImport(XmlReader.class);

        // Iterate method:
        javaBuffer.addLine("public static Iterator<%1$s> iterateMany(XmlReader reader) {", typeName.getSimpleName());
        javaBuffer.addLine(  "return new Iterator<%1$s>() {", typeName.getSimpleName());
        javaBuffer.addLine(    "private boolean first = true;");
        javaBuffer.addLine();
        javaBuffer.addLine(    "@Override");
        javaBuffer.addLine(    "public boolean hasNext() {");
        javaBuffer.addLine(      "if (first) {");
        javaBuffer.addLine(        "if (!reader.forward()) {");
        javaBuffer.addLine(          "return false;");
        javaBuffer.addLine(        "}");
        javaBuffer.addLine(        "reader.next();");
        javaBuffer.addLine(        "first = false;");
        javaBuffer.addLine(      "}");
        javaBuffer.addLine(      "if (!reader.forward()) {");
        javaBuffer.addLine(        "reader.next();");
        javaBuffer.addLine(        "return false;");
        javaBuffer.addLine(      "}");
        javaBuffer.addLine(      "return true;");
        javaBuffer.addLine(    "}");
        javaBuffer.addLine();
        javaBuffer.addLine(    "@Override");
        javaBuffer.addLine(    "public %1$s next() {", typeName.getSimpleName());
        javaBuffer.addLine(      "%1$s next = readOne(reader);", typeName.getSimpleName());
        javaBuffer.addLine(      "if (next == null) {");
        javaBuffer.addLine(        "throw new NoSuchElementException();");
        javaBuffer.addLine(      "}");
        javaBuffer.addLine(      "return next;");
        javaBuffer.addLine(    "}");
        javaBuffer.addLine(  "};");
        javaBuffer.addLine("}");
        javaBuffer.addLine();

        // Read method:
        javaBuffer.addLine("public static List<%1$s> readMany(XmlReader reader) {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "List<%1$s> list = new ArrayList<>();", typeName.getSimpleName());
        javaBuffer.addLine(  "Iterator<%1$s> iterator = iterateMany(reader);", typeName.getSimpleName());
        javaBuffer.addLine(  "while (iterator.hasNext()) {");
        javaBuffer.addLine(    "list.add(iterator.next());");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "return list;");
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
        javaBuffer.addLine("public class %1$s {", writerName.getSimpleName());
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
        javaBuffer.addImport(XmlWriter.class);

        // Generate the method that uses the default tag name:
        javaBuffer.addLine("public static void writeOne(%1$s object, XmlWriter writer) {", typeName.getSimpleName());
        javaBuffer.addLine(  "writeOne(object, \"%1$s\", writer);", tag);
        javaBuffer.addLine("}");
        javaBuffer.addLine();

        // Generate the method that receives the tag name as parameter:
        javaBuffer.addLine( "public static void writeOne(%1$s object, String tag, XmlWriter writer) {",
            typeName.getSimpleName());
        javaBuffer.addLine("writer.writeStartElement(tag);");
        Stream.concat(type.attributes(), type.links())
            .filter(x -> schemaNames.isRepresentedAsAttribute(x.getName()))
            .sorted()
            .forEach(this::generateWriteMemberAsAttribute);
        Stream.concat(type.attributes(), type.links())
            .filter(x -> !schemaNames.isRepresentedAsAttribute(x.getName()))
            .sorted()
            .forEach(this::generateWriteMemberAsElement);
        javaBuffer.addLine("writer.writeEndElement();");
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
                javaBuffer.addLine("writer.writeAttribute(\"%1$s\", object.get%2$s().toString());", tag, property);
            }
            else if (type == model.getStringType()) {
                javaBuffer.addLine("writer.writeAttribute(\"%1$s\", object.get%2$s());", tag, property);
            }
            else if (type == model.getDateType()) {
                // TODO: This isn't the XML schema format.
                javaBuffer.addLine("writer.writeAttribute(\"%1$s\", object.get%2$s().toString());", tag, property);
            }
        }
        else if (type instanceof EnumType) {
            // TODO: Should get the value calling the "getValue()" method.
            javaBuffer.addLine("writer.writeAttribute(\"%1$s\", object.get%2$s().toString());", tag, property);
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
                javaBuffer.addLine("writer.writeBoolean(\"%1$s\", object.get%2$s());", tag, property);
            }
            else if (type == model.getIntegerType()) {
                javaBuffer.addLine("writer.writeInteger(\"%1$s\", object.get%2$s());", tag, property);
            }
            else if (type == model.getDecimalType()) {
                javaBuffer.addLine("writer.writeDecimal(\"%1$s\", object.get%2$s());", tag, property);
            }
            else if (type == model.getStringType()) {
                javaBuffer.addLine("writer.writeElement(\"%1$s\", object.get%2$s());", tag, property);
            }
            else if (type == model.getDateType()) {
                javaBuffer.addLine("writer.writeDate(\"%1$s\", object.get%2$s());", tag, property);
            }
        }
        else if (type instanceof EnumType) {
            // TODO: Should get the value calling the "getValue()" method.
            javaBuffer.addLine("writer.writeStartElement(\"%1$s\");", tag);
            javaBuffer.addLine("writer.writeCharacters(object.get%1$s().toString());", property);
            javaBuffer.addLine("writer.writeEndElement();");
        }
        else if (type instanceof StructType) {
            JavaClassName writerName = javaTypes.getXmlWriterName(type);
            javaBuffer.addImport(writerName);
            javaBuffer.addLine("%1$s.writeOne(object.get%2$s(), \"%3$s\", writer);",
                writerName.getSimpleName(), property, tag);
        }
        else if (type instanceof ListType) {
            ListType listType = (ListType) type;
            Type elementType = listType.getElementType();
            if (elementType instanceof StructType) {
                JavaClassName writerName = javaTypes.getXmlWriterName(elementType);
                javaBuffer.addImport(writerName);
                javaBuffer.addLine("%1$s.writeMany(object.get%2$s().iterator(), writer);",
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
        javaBuffer.addImport(XmlWriter.class);

        // Generate the method that uses the default tag names:
        javaBuffer.addLine("public static void writeMany(Iterator<%1$s> list, XmlWriter writer) {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "writeMany(list, \"%1$s\", \"%2$s\", writer);", singularTag, pluralTag);
        javaBuffer.addLine("}");
        javaBuffer.addLine();

        // Generate the method that uses custom tag names:
        javaBuffer.addLine(
            "public static void writeMany(Iterator<%1$s> list, String singular, String plural, XmlWriter writer) {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "writer.writeStartElement(plural);");
        javaBuffer.addLine(  "while (list.hasNext()) {");
        javaBuffer.addLine(    "%1$s.writeOne(list.next(), singular, writer);", writerName.getSimpleName());
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "writer.writeEndElement();");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }
}
