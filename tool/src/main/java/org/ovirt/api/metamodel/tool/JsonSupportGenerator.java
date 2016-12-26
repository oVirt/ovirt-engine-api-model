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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import javax.inject.Inject;
import javax.json.stream.JsonParser;

import org.ovirt.api.metamodel.concepts.EnumType;
import org.ovirt.api.metamodel.concepts.ListType;
import org.ovirt.api.metamodel.concepts.Model;
import org.ovirt.api.metamodel.concepts.Name;
import org.ovirt.api.metamodel.concepts.PrimitiveType;
import org.ovirt.api.metamodel.concepts.StructMember;
import org.ovirt.api.metamodel.concepts.StructType;
import org.ovirt.api.metamodel.concepts.Type;
import org.ovirt.api.metamodel.runtime.json.JsonReader;
import org.ovirt.api.metamodel.runtime.json.JsonWriter;

/**
 * This class generates the XML readers and writers.
 */
public class JsonSupportGenerator extends JavaGenerator {
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
            .forEach(this::generateStructSupportClasses);

        // Generate classes for each enum type:
        model.types()
            .filter(EnumType.class::isInstance)
            .map(EnumType.class::cast)
            .forEach(this::generateEnumSupportClasses);
    }

    private void generateStructSupportClasses(StructType type) {
        generateStructReader(type);
        generateStructWriter(type);
    }

    private void generateEnumSupportClasses(EnumType type) {
        generateEnumReader(type);
        generateEnumWriter(type);
    }

    private void generateStructReader(StructType type) {
        javaBuffer = new JavaClassBuffer();
        JavaClassName readerName = javaTypes.getJsonReaderName(type);
        javaBuffer.setClassName(readerName);
        generateStructReaderSource(type);
        try {
            javaBuffer.write(outDir);
        }
        catch (IOException exception) {
            throw new RuntimeException("Can't write file for JSON reader \"" + readerName + "\"", exception);
        }
    }

    private void generateStructReaderSource(StructType type) {
        // Begin class:
        JavaClassName readerName = javaTypes.getJsonReaderName(type);
        javaBuffer.addLine("public class %1$s {", readerName.getSimpleName());
        javaBuffer.addLine();

        // Generate methods to read one instance and a list of instances:
        generateStructReadOne(type);
        generateStructReadMany(type);

        // End class:
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateStructReadOne(StructType type) {
        // Get the type and container name:
        JavaClassName typeName = javaTypes.getInterfaceName(type);
        JavaClassName containerName = javaTypes.getContainerName(type);

        // Add the required imports:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(containerName);
        javaBuffer.addImport(JsonParser.class);
        javaBuffer.addImport(JsonReader.class);

        // Generate the that assumes that parsing of the object hasn't started yet, so it will expect the start of the
        // object as the first event:
        javaBuffer.addLine("public static %1$s readOne(JsonReader reader) {", typeName.getSimpleName());
        javaBuffer.addLine(  "return readOne(reader, false);");
        javaBuffer.addLine("}");
        javaBuffer.addLine();

        // Generate the method that receives a boolean parameter indicating if parsing of the object has already
        // started. In that case the start event will have been consumed already.
        List<StructMember> members = new ArrayList<>();
        members.addAll(type.getAttributes());
        members.addAll(type.getLinks());
        javaBuffer.addLine("public static %1$s readOne(JsonReader reader, boolean started) {", typeName.getSimpleName());
        javaBuffer.addLine(  "if (!started) {");
        javaBuffer.addLine(    "reader.expect(JsonParser.Event.START_OBJECT);");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "%1$s object = new %1$s();", containerName.getSimpleName());
        javaBuffer.addLine(  "while (reader.next() == JsonParser.Event.KEY_NAME) {");
        if (members.isEmpty()) {
            javaBuffer.addLine("reader.skip();");
        }
        else {
            javaBuffer.addLine("String name = reader.getString();");
            javaBuffer.addLine("switch (name) {");
            members.stream().sorted().forEach(this::generateStructReadMember);
            javaBuffer.addLine("default:");
            javaBuffer.addLine(  "reader.skip();");
            javaBuffer.addLine("}");
        }
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "return object;");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateStructReadMember(StructMember member) {
        Name name = member.getName();
        Type type = member.getType();
        String field = javaNames.getJavaMemberStyleName(name);
        String tag = schemaNames.getSchemaTagName(name);
        javaBuffer.addLine("case \"%1$s\":", tag);
        if (type instanceof PrimitiveType) {
            Model model = type.getModel();
            if (type == model.getBooleanType()) {
                javaBuffer.addLine("object.%1$s(reader.readBoolean());", field);
            }
            else if (type == model.getIntegerType()) {
                javaBuffer.addLine("object.%1$s(reader.readInteger());", field);
            }
            else if (type == model.getDecimalType()) {
                javaBuffer.addLine("object.%1$s(reader.readDecimal());", field);
            }
            else if (type == model.getStringType()) {
                javaBuffer.addLine("object.%1$s(reader.readString());", field);
            }
            else if (type == model.getDateType()) {
                javaBuffer.addLine("object.%1$s(reader.readDate());", field);
            }
            else {
                javaBuffer.addLine("reader.skip();");
            }
        }
        else if (type instanceof StructType || type instanceof EnumType) {
            JavaClassName readerName = javaTypes.getJsonReaderName(type);
            javaBuffer.addImport(readerName);
            javaBuffer.addLine("object.%1$s(%2$s.readOne(reader));", field, readerName.getSimpleName());
        }
        else if (type instanceof ListType) {
            ListType listType = (ListType) type;
            Type elementType = listType.getElementType();
            JavaClassName readerName = javaTypes.getJsonReaderName(elementType);
            javaBuffer.addImport(readerName);
            if (elementType instanceof StructType || elementType instanceof EnumType) {
                javaBuffer.addLine("object.%1$s(%2$s.readMany(reader));", field, readerName.getSimpleName());
            }
            else if(elementType instanceof PrimitiveType) {
                Model model = type.getModel();
                if (elementType == model.getBooleanType()) {
                    javaBuffer.addLine("object.%1$s(reader.readBooleans());", field);
                }
                else if (elementType == model.getIntegerType()) {
                    javaBuffer.addLine("object.%1$s(reader.readIntegers());", field);
                }
                else if (elementType == model.getDecimalType()) {
                    javaBuffer.addLine("object.%1$s(reader.readDecimals());", field);
                }
                else if (elementType == model.getStringType()) {
                    javaBuffer.addLine("object.%1$s(reader.readStrings());", field);
                }
                else if (elementType == model.getDateType()) {
                    javaBuffer.addLine("object.%1$s(reader.readDates());", field);
                }
                else {
                    javaBuffer.addLine("reader.skip();");
                }
            }
        }
        else {
            javaBuffer.addLine("reader.skip();");
        }
        javaBuffer.addLine("break;");
    }

    private void generateStructReadMany(StructType type) {
        // Get the type name:
        JavaClassName typeName = javaTypes.getInterfaceName(type);

        // Iterate method:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(Iterator.class);
        javaBuffer.addImport(JsonParser.class);
        javaBuffer.addImport(JsonReader.class);
        javaBuffer.addImport(NoSuchElementException.class);
        javaBuffer.addLine("public static Iterator<%1$s> iterateMany(JsonReader reader) {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "return new Iterator<%1$s>() {", typeName.getSimpleName());
        javaBuffer.addLine(    "private boolean first = true;");
        javaBuffer.addLine();
        javaBuffer.addLine(    "@Override");
        javaBuffer.addLine(    "public boolean hasNext() {");
        javaBuffer.addLine(      "if (first) {");
        javaBuffer.addLine(        "reader.expect(JsonParser.Event.START_ARRAY);");
        javaBuffer.addLine(        "first = false;");
        javaBuffer.addLine(      "}");
        javaBuffer.addLine(      "return reader.next() == JsonParser.Event.START_OBJECT;");
        javaBuffer.addLine(    "}");
        javaBuffer.addLine();
        javaBuffer.addLine(    "@Override");
        javaBuffer.addLine(    "public %1$s next() {", typeName.getSimpleName());
        javaBuffer.addLine(      "%1$s next = readOne(reader, true);", typeName.getSimpleName());
        javaBuffer.addLine(      "if (next == null) {");
        javaBuffer.addLine(        "throw new NoSuchElementException();");
        javaBuffer.addLine(      "}");
        javaBuffer.addLine(      "return next;");
        javaBuffer.addLine(    "}");
        javaBuffer.addLine(  "};");
        javaBuffer.addLine("}");
        javaBuffer.addLine();

        // Read method:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(ArrayList.class);
        javaBuffer.addImport(Iterator.class);
        javaBuffer.addImport(JsonReader.class);
        javaBuffer.addImport(List.class);
        javaBuffer.addLine("public static List<%1$s> readMany(JsonReader reader) {", typeName.getSimpleName());
        javaBuffer.addLine(  "List<%1$s> list = new ArrayList<>();", typeName.getSimpleName());
        javaBuffer.addLine(  "Iterator<%1$s> iterator = iterateMany(reader);", typeName.getSimpleName());
        javaBuffer.addLine(  "while (iterator.hasNext()) {");
        javaBuffer.addLine(    "list.add(iterator.next());");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "return list;");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateEnumReadMany(EnumType type) {
        // Get the type name:
        JavaClassName typeName = javaTypes.getInterfaceName(type);

        // Read method:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(ArrayList.class);
        javaBuffer.addImport(JsonParser.class);
        javaBuffer.addImport(JsonReader.class);
        javaBuffer.addImport(List.class);

        javaBuffer.addLine("public static List<%1$s> readMany(JsonReader reader) {", typeName.getSimpleName());
        javaBuffer.addLine(  "List<%1$s> list = new ArrayList<>();", typeName.getSimpleName());
        javaBuffer.addLine(  "while (reader.next() != JsonParser.Event.START_ARRAY) {");
        javaBuffer.addLine(  "// Empty on purpose");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine();
        javaBuffer.addLine(  "boolean enumArrayEnd = false;");
        javaBuffer.addLine(  "while (!enumArrayEnd) {");
        javaBuffer.addLine(    "JsonParser.Event next = reader.next();");
        javaBuffer.addLine(    "switch (next) {");
        javaBuffer.addLine(    "case VALUE_STRING:");
        javaBuffer.addLine(    "  String value = reader.getString();");
        javaBuffer.addLine(    "  list.add(%1$s.fromValue(value));", typeName.getSimpleName());
        javaBuffer.addLine(    "  break;");
        javaBuffer.addLine(    "case END_ARRAY:");
        javaBuffer.addLine(    "  enumArrayEnd = true;");
        javaBuffer.addLine(    "  break;");
        javaBuffer.addLine(    "default:");
        javaBuffer.addLine(    "  reader.skip();");
        javaBuffer.addLine(    "}");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "reader.next();");
        javaBuffer.addLine(  "return list;");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateEnumReader(EnumType type) {
        javaBuffer = new JavaClassBuffer();
        JavaClassName readerName = javaTypes.getJsonReaderName(type);
        javaBuffer.setClassName(readerName);
        generateEnumReaderSource(type);
        try {
            javaBuffer.write(outDir);
        }
        catch (IOException exception) {
            throw new RuntimeException("Can't write file for XML reader \"" + readerName + "\"", exception);
        }
    }
    private void generateEnumReaderSource(EnumType type) {
        // Begin class:
        JavaClassName readerName = javaTypes.getJsonReaderName(type);
        javaBuffer.addLine("public class %1$s {", readerName.getSimpleName());
        javaBuffer.addLine();

        // Generate methods to read one instance and a list of instances:
        generateEnumReadOne(type);
        generateEnumReadMany(type);

        // End class:
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateEnumReadOne(EnumType type) {
        // Get the type and container name:
        JavaClassName typeName = javaTypes.getEnumName(type);

        // Add the required imports:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(JsonReader.class);

        javaBuffer.addLine("public static %1$s readOne(JsonReader reader) {", typeName.getSimpleName());
        javaBuffer.addLine(  "return %1$s.fromValue(reader.readString());", typeName.getSimpleName());
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateStructWriter(StructType type) {
        javaBuffer = new JavaClassBuffer();
        JavaClassName writerName = javaTypes.getJsonWriterName(type);
        javaBuffer.setClassName(writerName);
        generateStructWriterSource(type);
        try {
            javaBuffer.write(outDir);
        }
        catch (IOException exception) {
            throw new RuntimeException("Can't write file for JSON writer \"" + writerName + "\"", exception);
        }
    }

    private void generateStructWriterSource(StructType type) {
        // Begin class:
        JavaClassName writerName = javaTypes.getJsonWriterName(type);
        javaBuffer.addLine("public class %1$s {", writerName.getSimpleName());
        javaBuffer.addLine();

        // Generate methods to write one instance and a list of instances:
        generateStructWriteOne(type);
        generateStructWriteMany(type);

        // End class:
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateStructWriteOne(StructType type) {
        // Calculate the name of the type and the XML tag:
        JavaClassName typeName = javaTypes.getInterfaceName(type);

        // Add the required imports:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(JsonWriter.class);

        // Generate the method that uses the default name:
        javaBuffer.addLine("public static void writeOne(%1$s object, JsonWriter writer) {",
            typeName.getSimpleName());
        javaBuffer.addLine("  writeOne(object, null, writer);");
        javaBuffer.addLine("}");
        javaBuffer.addLine();

        // Generate the method that receives the name as parameter:
        javaBuffer.addLine("public static void writeOne(%1$s object, String name, JsonWriter writer) {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "if (name != null) {");
        javaBuffer.addLine(    "writer.writeStartObject(name);");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "else {");
        javaBuffer.addLine(    "writer.writeStartObject();");
        javaBuffer.addLine(  "}");
        Stream.concat(type.attributes(), type.links()).sorted().forEach(this::generateStructWriteMember);
        javaBuffer.addLine(  "writer.writeEnd();");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateStructWriteMember(StructMember member) {
        Name name = member.getName();
        Type type = member.getType();
        String field = javaNames.getJavaMemberStyleName(name);
        String tag = schemaNames.getSchemaTagName(name);
        javaBuffer.addLine("if (object.%1$sPresent()) {", field);
        if (type instanceof PrimitiveType) {
            Model model = type.getModel();
            if (type == model.getBooleanType()) {
                javaBuffer.addLine("writer.writeBoolean(\"%1$s\", object.%2$s());", tag, field);
            }
            else if (type == model.getIntegerType()) {
                javaBuffer.addLine("writer.writeInteger(\"%1$s\", object.%2$s());", tag, field);
            }
            else if (type == model.getDecimalType()) {
                javaBuffer.addLine("writer.writeDecimal(\"%1$s\", object.%2$s());", tag, field);
            }
            else if (type == model.getStringType()) {
                javaBuffer.addLine("writer.writeString(\"%1$s\", object.%2$s());", tag, field);
            }
            else if (type == model.getDateType()) {
                javaBuffer.addLine("writer.writeDate(\"%1$s\", object.%2$s());", tag, field);
            }
        }
        else if (type instanceof EnumType) {
            JavaClassName writerName = javaTypes.getJsonWriterName(type);
            javaBuffer.addImport(writerName);
            javaBuffer.addLine("writer.writeString(\"%1$s\", object.%2$s().value());", tag, field);
        }
        else if (type instanceof StructType) {
            JavaClassName writerName = javaTypes.getJsonWriterName(type);
            javaBuffer.addImport(writerName);
            javaBuffer.addLine("%1$s.writeOne(object.%2$s(), \"%3$s\", writer);", writerName.getSimpleName(), field,
                tag);
        }
        else if (type instanceof ListType) {
            ListType listType = (ListType) type;
            Type elementType = listType.getElementType();
            if (elementType instanceof StructType || elementType instanceof EnumType) {
                JavaClassName writerName = javaTypes.getJsonWriterName(elementType);
                javaBuffer.addImport(writerName);
                javaBuffer.addLine("%1$s.writeMany(object.%2$s().iterator(), \"%3$s\", writer);",
                    writerName.getSimpleName(), field, tag);
            }
            else if (elementType instanceof PrimitiveType) {
                Model model = type.getModel();
                if (elementType == model.getBooleanType()) {
                    javaBuffer.addLine("writer.writeBooleans(\"%1$s\", object.%2$s());", tag, field);
                }
                else if (elementType == model.getIntegerType()) {
                    javaBuffer.addLine("writer.writeIntegers(\"%1$s\", object.%2$s());", tag, field);
                }
                else if (elementType == model.getDecimalType()) {
                    javaBuffer.addLine("writer.writeDecimals(\"%1$s\", object.%2$s());", tag, field);
                }
                else if (elementType == model.getStringType()) {
                    javaBuffer.addLine("writer.writeStrings(\"%1$s\", object.%2$s());", tag, field);
                }
                else if (elementType == model.getDateType()) {
                    javaBuffer.addLine("writer.writeDates(\"%1$s\", object.%2$s());", tag, field);
                }
            }
        }
        javaBuffer.addLine("}");
    }

    private void generateStructWriteMany(StructType type) {
        generateWriteMany(type);
    }

    private void generateEnumWriteMany(EnumType type) {
        generateWriteMany(type);
    }

    private void generateWriteMany(Type type) {
        // Get the name of the type and writer:
        JavaClassName typeName = javaTypes.getInterfaceName(type);
        JavaClassName writerName = javaTypes.getJsonWriterName(type);

        // Add the required imports:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(writerName);
        javaBuffer.addImport(Iterator.class);
        javaBuffer.addImport(JsonWriter.class);

        // Generate the method that writes an array without a name:
        javaBuffer.addLine("public static void writeMany(Iterator<%1$s> iterator, JsonWriter writer) {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "writeMany(iterator, null, writer);");
        javaBuffer.addLine("}");
        javaBuffer.addLine();

        // Generate the method that writes an array with a name:
        javaBuffer.addLine(
            "public static void writeMany(Iterator<%1$s> iterator, String name, JsonWriter writer) {",
            typeName.getSimpleName());
        javaBuffer.addLine(  "if (name != null) {");
        javaBuffer.addLine(    "writer.writeStartArray(name);");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "else {");
        javaBuffer.addLine(    "writer.writeStartArray();");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "while (iterator.hasNext()) {");
        javaBuffer.addLine(    "writeOne(iterator.next(), writer);");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "writer.writeEnd();");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateEnumWriter(EnumType type) {
        javaBuffer = new JavaClassBuffer();
        JavaClassName writerName = javaTypes.getJsonWriterName(type);
        javaBuffer.setClassName(writerName);
        generateEnumWriterSource(type);
        try {
            javaBuffer.write(outDir);
        }
        catch (IOException exception) {
            throw new RuntimeException("Can't write file for XML writer \"" + writerName + "\"", exception);
        }
    }

    private void generateEnumWriterSource(EnumType type) {
        // Begin class:
        JavaClassName writerName = javaTypes.getJsonWriterName(type);
        javaBuffer.addLine("public class %1$s {", writerName.getSimpleName());
        javaBuffer.addLine();

        // Generate methods to write one instance and a list of instances:
        generateEnumWriteOne(type);
        generateEnumWriteMany(type);

        // End class:
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }

    private void generateEnumWriteOne(EnumType type) {
        // Calculate the name of the type and the XML tag:
        JavaClassName typeName = javaTypes.getEnumName(type);

        // Add the required imports:
        javaBuffer.addImport(typeName);
        javaBuffer.addImport(JsonWriter.class);

        javaBuffer.addLine("public static void writeOne(%1$s object, JsonWriter writer) {",
                typeName.getSimpleName());
        javaBuffer.addLine("  writeOne(object, null, writer);");
        javaBuffer.addLine("}");
        javaBuffer.addLine();

        // Generate the method that receives the name as parameter:
        javaBuffer.addLine("public static void writeOne(%1$s object, String name, JsonWriter writer) {",
                typeName.getSimpleName());
        javaBuffer.addLine(  "if (name != null) {");
        javaBuffer.addLine(    "writer.writeStartObject(name);");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "else {");
        javaBuffer.addLine(    "writer.writeStartObject();");
        javaBuffer.addLine(  "}");
        javaBuffer.addLine(  "writer.writeString(name, object.value());");
        javaBuffer.addLine(  "writer.writeEnd();");
        javaBuffer.addLine("}");
        javaBuffer.addLine();
    }
}
