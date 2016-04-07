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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.json.Json;
import javax.json.JsonException;
import javax.json.stream.JsonParser;

/**
 * This class wraps the {@link JsonParser} class so that the methods don't send checked exceptions, in order to
 * simplify its usage together with streams and lambdas.
 */
public class JsonReader implements AutoCloseable {
    // The wrapped JSON parser:
    private JsonParser parser;

    /**
     * Thread local used to store the date formats.
     */
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format;
    });

    /**
     * Creates a JSON reader that will read from the given stream, using UTF-8 as the encoding.
     *
     * @param in the stream where the document will be read from
     */
    public JsonReader(InputStream in) {
        Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        init(reader);
    }

    /**
     * Creates a JSON reader that will read from the given reader.
     *
     * @param reader the reader where the document will be read from
     */
    public JsonReader(Reader reader) {
        init(reader);
    }

    /**
     * Creates a reader that will read from the given file, using UTF-8 as the encoding.
     *
     * @param file the file where the document will be written
     */
    public JsonReader(File file) {
        try {
            InputStream in = new FileInputStream(file);
            Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
            init(reader);
        }
        catch (IOException exception) {
            throw new JsonException("Can't open file \"" + file.getAbsolutePath() + "\" for reading", exception);
        }
    }

    private void init(Reader reader) {
        parser = Json.createParser(reader);
    }

    /**
     * Returns the next event.
     */
    public JsonParser.Event next() {
        return parser.next();
    }

    /**
     * Returns the string from the current name and value pair.
     */
    public String getString() {
        return parser.getString();
    }

    /**
     * Checks that the next event is of the given type, and throw and exception if it isn't.
     */
    public void expect(JsonParser.Event expected) {
        JsonParser.Event actual = parser.next();
        if (actual != expected) {
            throw new JsonException("Unexpected event");
        }
    }

    /**
     * Skips the current JSON object or array.
     */
    public void skip() {
        int depth = 1;
        while (parser.hasNext()) {
            switch (parser.next()) {
            case START_ARRAY:
            case START_OBJECT:
                depth++;
                break;
            case END_ARRAY:
            case END_OBJECT:
                depth--;
                if (depth == 0) {
                    return;
                }
                break;
            }
        }
    }

    /**
     * Reads a boolean value from the JSON parser.
     */
    public boolean readBoolean() {
        JsonParser.Event event = parser.next();
        switch (event) {
        case VALUE_FALSE:
            return false;
        case VALUE_TRUE:
            return true;
        default:
            throw new JsonException("Expected boolean value");
        }
    }

    /**
     * Reads an integer value from the JSON parser.
     */
    public BigInteger readInteger() {
        JsonParser.Event event = parser.next();
        switch (event) {
        case VALUE_NUMBER:
            return parser.getBigDecimal().toBigInteger();
        default:
            throw new JsonException("Expected integer value");
        }
    }

    /**
     * Reads a decimal value from the JSON parser.
     */
    public BigDecimal readDecimal() {
        JsonParser.Event event = parser.next();
        switch (event) {
        case VALUE_NUMBER:
            return parser.getBigDecimal();
        default:
            throw new JsonException("Expected decimal value");
        }
    }

    /**
     * Reads a string value from the JSON parser.
     */
    public String readString() {
        JsonParser.Event event = parser.next();
        switch (event) {
        case VALUE_STRING:
            return parser.getString();
        default:
            throw new JsonException("Expected string value");
        }
    }

    /**
     * Reads a date value from the JSON parser.
     */
    public Date readDate() {
        JsonParser.Event event = parser.next();
        switch (event) {
            case VALUE_STRING:
                String image = parser.getString();
                try {
                    return DATE_FORMAT.get().parse(image);
                }
                catch (ParseException exception) {
                    throw new JsonException("The text \"" + image + "\" isn't a valid date", exception);
                }
            default:
                throw new JsonException("Expected date value");
        }
    }

    /**
     * Reads a list of booleans from JSON parser. Skips unexpected values.
     */
    public List<Boolean> readBooleans() {
        List<Boolean> list = new ArrayList<>();
        expect(JsonParser.Event.START_ARRAY);
        boolean listEnd = false;
        while (!listEnd) {
            JsonParser.Event next = next();
            switch (next) {
                case VALUE_FALSE:
                    list.add(false);
                    break;
                case VALUE_TRUE:
                    list.add(true);
                    break;
                case END_ARRAY:
                    listEnd = true;
                    break;
                default:
                    continue;
            }
        }
        return list;
    }

    /**
     * Reads a list of integers from JSON parser. Skips unexpected values.
     */
    public List<BigInteger> readIntegers() {
        List<BigInteger> list = new ArrayList<>();
        expect(JsonParser.Event.START_ARRAY);
        boolean listEnd = false;
        while (!listEnd) {
            JsonParser.Event next = next();
            switch (next) {
                case VALUE_NUMBER:
                    list.add(parser.getBigDecimal().toBigInteger());
                    break;
                case END_ARRAY:
                    listEnd = true;
                    break;
                default:
                    continue;
            }
        }
        return list;
    }

    /**
     * Reads a list of decimals from JSON parser. Skips unexpected values.
     */
    public List<BigDecimal> readDecimals() {
        List<BigDecimal> list = new ArrayList<>();
        expect(JsonParser.Event.START_ARRAY);
        boolean listEnd = false;
        while (!listEnd) {
            JsonParser.Event next = next();
            switch (next) {
                case VALUE_NUMBER:
                    list.add(parser.getBigDecimal());
                    break;
                case END_ARRAY:
                    listEnd = true;
                    break;
                default:
                    continue;
            }
        }
        return list;
    }

    /**
     * Reads a list of dates from JSON parser. Skips unexpected values.
     */
    public List<Date> readDates() {
        List<Date> list = new ArrayList<>();
        expect(JsonParser.Event.START_ARRAY);
        boolean listEnd = false;
        while (!listEnd) {
            JsonParser.Event next = next();
            switch (next) {
                case VALUE_STRING:
                    String image = parser.getString();
                    try {
                        list.add(DATE_FORMAT.get().parse(image));
                    }
                    catch (ParseException exception) {
                        throw new JsonException("The text \"" + image + "\" isn't a valid date", exception);
                    }
                case END_ARRAY:
                    listEnd = true;
                    break;
                default:
                    continue;
            }
        }
        return list;
    }

    /**
     * Reads a list of strings from JSON parser. Skips unexpected values.
     */
    public List<String> readStrings() {
        List<String> list = new ArrayList<>();
        expect(JsonParser.Event.START_ARRAY);
        boolean listEnd = false;
        while (!listEnd) {
            JsonParser.Event next = next();
            switch (next) {
                case VALUE_STRING:
                    list.add(getString());
                    break;
                case END_ARRAY:
                    listEnd = true;
                    break;
                default:
                    continue;
            }
        }
        return list;
    }

    /**
     * Closes the JSON document and the underlying source.
     */
    public void close() {
        parser.close();
    }
}
