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

package org.ovirt.api.metamodel.runtime.json;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonGeneratorFactory;

/**
 * This class wraps the {@link JsonGenerator} class so that the methods don't send checked exceptions, in order to
 * simplify its usage together with streams and lambdas.
 */
public class JsonWriter implements AutoCloseable {
    // The wrapped JSON generator:
    private JsonGenerator generator;

    /**
     * Thread local used to store the date formats.
     */
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected SimpleDateFormat initialValue() {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            return format;
        }
    };

    /**
     * Creates a JSON writer that will write to the given stream, using UTF-8 as the encoding.
     *
     * @param out the stream where the document will be written
     * @param indent indicates if the output should be indented
     */
    public JsonWriter(OutputStream out, boolean indent) {
        Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        init(writer, indent);
    }

    /**
     * Creates a JSON writer that will write to the given writer.
     *
     * @param writer the writer where the document will be written
     * @param indent indicates if the output should be indented
     */
    public JsonWriter(Writer writer, boolean indent) {
        init(writer, indent);
    }

    /**
     * Creates a writer that will write to the given file, using UTF-8 as the encoding.
     *
     * @param file the file where the document will be written
     * @param indent indicates if the output should be indented
     */
    public JsonWriter(File file, boolean indent) {
        try {
            OutputStream out = new FileOutputStream(file);
            Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            init(writer, indent);
        }
        catch (IOException exception) {
            throw new JsonException("Can't open file \"" + file.getAbsolutePath() + "\" for writing", exception);
        }
    }

    private void init(Writer writer, boolean indent) {
        Map<String, Object> configuration = new HashMap<>();
        if (indent) {
            configuration.put(JsonGenerator.PRETTY_PRINTING, Boolean.TRUE);
        }
        JsonGeneratorFactory factory = Json.createGeneratorFactory(configuration);
        generator = factory.createGenerator(writer);
    }

    /**
     * Writes the start of an object.
     */
    public void writeStartObject() {
        generator.writeStartObject();
    }

    /**
     * Writes the start of an object with a name.
     *
     * @param name the name of the object
     */
    public void writeStartObject(String name) {
        generator.writeStartObject(name);
    }

    /**
     * Writes the start of an array.
     */
    public void writeStartArray() {
        generator.writeStartArray();
    }

    /**
     * Writes the start of an array with a name.
     *
     * @param name the name of the array
     */
    public void writeStartArray(String name) {
        generator.writeStartArray(name);
    }

    /**
     * Writes the end of an object or array, depending on the context.
     */
    public void writeEnd() {
        generator.writeEnd();
    }

    /**
     * Writes a string name and value pair.
     *
     * @param name the name
     * @param value the value
     */
    public void writeString(String name, String value) {
        generator.write(name, value);
    }

    /**
     * Writes a boolean name and value pair.
     *
     * @param name the name
     * @param value the value
     */
    public void writeBoolean(String name, boolean value) {
        generator.write(name, value);
    }

    /**
     * Writes an integer name and value pair.
     *
     * @param name the name
     * @param value the value
     */
    public void writeInteger(String name, BigInteger value) {
        generator.write(name, value);
    }

    /**
     * Writes a decimal name and value pair.
     *
     * @param name the name
     * @param value the value
     */
    public void writeDecimal(String name, BigDecimal value) {
        generator.write(name, value);
    }

    /**
     * Writes a date.
     */
    public void writeDate(String name, Date value) {
        generator.write(name, DATE_FORMAT.get().format(value));
    }

    /**
     * Writes a list of boolean values.
     */
    public void writeBooleans(String name, List<Boolean> values) {
        for (Boolean value : values) {
            writeBoolean(name, value);
        }
    }

    /**
     * Writes a list of integer values.
     */
    public void writeIntegers(String name, List<BigInteger> values) {
        for (BigInteger value : values) {
            writeInteger(name, value);
        }
    }

    /**
     * Writes a list of decimal values.
     */
    public void writeDecimals(String name, List<BigDecimal> values) {
        for (BigDecimal value : values) {
            writeDecimal(name, value);
        }
    }

    /**
     * Writes a list of date values.
     */
    public void writeDates(String name, List<Date> values) {
        for (Date value : values) {
            writeDate(name, value);
        }
    }

    /**
     * Writes a list of string values.
     */
    public void writeStrings(String name, List<String> values) {
        for (String value : values) {
            writeString(name, value);
        }
    }

    /**
     * Closes the JSON generator.
     */
    public void close() {
        generator.close();
    }
}
