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

package org.ovirt.api.metamodel.runtime.xml;

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
import java.util.List;
import java.util.TimeZone;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import com.sun.xml.txw2.output.IndentingXMLStreamWriter;

/**
 * This class wraps the {@link XMLStreamWriter} class so that the methods don't send checked exceptions, in order to
 * simplify its usage together with streams and lambdas.
 */
public class XmlWriter implements AutoCloseable {
    // The wrapped XML writer:
    private XMLStreamWriter writer;

    /**
     * Thread local used to store the date formats.
     */
    private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal.withInitial(() -> {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format;
    });

    /**
     * Creates an XML writer that will write to the given result, using UTF-8 as the encoding.
     *
     * @param result the result where the document will be written
     * @param indent indicates if the output should be indented
     */
    public XmlWriter(Result result, boolean indent) {
        init(result, indent);
    }

    /**
     * Creates an XML writer that will write to the given stream, using UTF-8 as the encoding.
     *
     * @param out the stream where the document will be written
     * @param indent indicates if the output should be indented
     */
    public XmlWriter(OutputStream out, boolean indent) {
        Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
        Result result = new StreamResult(writer);
        init(result, indent);
    }

    /**
     * Creates an XML writer that will write to the given writer.
     *
     * @param out the writer where the document will be written
     * @param indent indicates if the output should be indented
     */
    public XmlWriter(Writer out, boolean indent) {
        Result result = new StreamResult(out);
        init(result, indent);
    }

    /**
     * Creates a writer that will write to the given file, using UTF-8 as the encoding.
     *
     * @param file the file where the document will be written
     * @param indent indicates if the output should be indented
     */
    public XmlWriter(File file, boolean indent) {
        try {
            OutputStream out = new FileOutputStream(file);
            Writer writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
            Result result = new StreamResult(writer);
            init(result, indent);
        }
        catch (IOException exception) {
            throw new XmlException("Can't open file \"" + file.getAbsolutePath() + "\" for writing", exception);
        }
    }

    private void init(Result result, boolean indent) {
        try {
            XMLOutputFactory factory = XMLOutputFactory.newFactory();
            writer = factory.createXMLStreamWriter(result);
            if (indent) {
                IndentingXMLStreamWriter indenter = new IndentingXMLStreamWriter(writer);
                indenter.setIndentStep("  ");
                writer = indenter;
            }
        }
        catch (XMLStreamException exception) {
            throw new XmlException("Can't create XML writer", exception);
        }
    }

    /**
     * Writes the start of the document.
     *
     * @param encoding the character encoding used in the document
     * @param version the XML version used in the document
     */
    public void writeStartDocument(String encoding, String version) {
        try {
            writer.writeStartDocument(encoding, version);
        }
        catch (XMLStreamException exception) {
            throw new XmlException("Can't write start of document", exception);
        }
    }

    /**
     * Writes the end of the document.
     */
    public void writeEndDocument() {
        try {
            writer.writeEndDocument();
        }
        catch (XMLStreamException exception) {
            throw new XmlException("Can't write end of document", exception);
        }
    }

    /**
     * Associates a namespace prefix with a namespace URI.
     *
     * @param prefix the namespace prefix
     * @param uri the namespace URI
     */
    public void setPrefix(String prefix, String uri) {
        try {
            writer.setPrefix(prefix, uri);
        }
        catch (XMLStreamException exception) {
            throw new XmlException(
                "Can't associate prefix \"" + prefix + "\" with URI \"" + uri + "\"",
                exception
            );
        }
    }

    /**
     * Writes an XML element with the given name and value. For example, if the name is {@code size} and the value is
     * {@code 100} it will write {@code <size>100</size>}.
     *
     * @param uri the namespace URI
     * @param name the name of the XML element
     * @param value the text content of the XML element
     */
    public void writeElement(String uri, String name, String value) {
        writeStartElement(uri, name);
        writeCharacters(value);
        writeEndElement();
    }

    /**
     * Writes an XML element with the given name and value. For example, if the name is {@code size} and the value is
     * {@code 100} it will write {@code <size>100</size>}.
     *
     * @param name the name of the XML element
     * @param value the text content of the XML element
     */
    public void writeElement(String name, String value) {
        writeStartElement(name);
        writeCharacters(value);
        writeEndElement();
    }

    /**
     * Writes the start of an XML element with the given name. For example, if the name is {@code size} it will
     * write {@code <size>}.
     *
     * @param uri the namespace URI
     * @param name the name of the XML element
     */
    public void writeStartElement(String uri, String name) {
        try {
            writer.writeStartElement(uri, name);
        }
        catch (XMLStreamException exception) {
            throw new XmlException("Can't write start element for tag \"" + name + "\"", exception);
        }
    }

    /**
     * Writes the start of an XML element with the given name. For example, if the name is {@code size} it will
     * write {@code <size>}.
     *
     * @param name the name of the XML element
     */
    public void writeStartElement(String name) {
        try {
            writer.writeStartElement(name);
        }
        catch (XMLStreamException exception) {
            throw new XmlException("Can't write start element for tag \"" + name + "\"", exception);
        }
    }

    /**
     * Closes the latest XML element started with the {@link #writeStartElement(String)}.
     */
    public void writeEndElement() {
        try {
            writer.writeEndElement();
        }
        catch (XMLStreamException exception) {
            throw new XmlException("Can't write end element", exception);
        }
    }

    /**
     * Writes an XML attribute with the given name and value. For example, if the name is {@code size} and the value
     * is {@code 100} it will write {@code size="100"}.
     *
     * @param name the name of the XML attribute
     * @param value the text content of the XML attribute
     */
    public void writeAttribute(String name, String value) {
        try {
            writer.writeAttribute(name, value);
        }
        catch (XMLStreamException exception) {
            throw new XmlException(
                "Can't write attribute with name \"" + name + "\" and value \"" + value + "\"",
                exception
            );
        }
    }

    /**
     * Writes the given characters as text content.
     *
     * @param text the characters to write
     */
    public void writeCharacters(String text) {
        try {
            writer.writeCharacters(text);
        }
        catch (XMLStreamException exception) {
            throw new XmlException("Can't write text \"" + text + "\"", exception);
        }
    }

    /**
     * Writes a blank line.
     *
     */
    public void writeLine() {
        writeCharacters("\n");
    }

    /**
     * Writes a boolean value.
     */
    public void writeBoolean(String name, boolean value) {
        writeElement(name, renderBoolean(value));
    }

    /**
     * Writes an integer value.
     */
    public void writeInteger(String name, BigInteger value) {
        writeElement(name, renderInteger(value));
    }

    /**
     * Writes a decimal value.
     */
    public void writeDecimal(String name, BigDecimal value) {
        writeElement(name, renderDecimal(value));
    }

    /**
     * Writes a date.
     */
    public void writeDate(String name, Date value) {
        writeElement(name, renderDate(value));
    }

    /**
     * Writes a string.
     */
    public void writeString(String name, String value) {
        writeElement(name, renderString(value));
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
    public void writeElements(String name, List<String> values) {
        for (String value : values) {
            writeElement(name, value);
        }
    }

    /**
     * Render a string representation of string value
     */
    public static String renderString(String value) {
        return value;
    }

    /**
     * Render a string representation of boolean value
     */
    public static String renderBoolean(boolean value) {
        return Boolean.toString(value);
    }

    /**
     * Render a string representation of BigInteger value
     */
    public static String renderInteger(BigInteger value) {
        return value.toString();
    }

    /**
     * Render a string representation of BigDecimal value
     */
    public static String renderDecimal(BigDecimal value) {
        return value.toString();
    }

    /**
     * Render a string representation of Date value
     */
    public static String renderDate(Date value) {
        return DATE_FORMAT.get().format(value);
    }

    /**
     * Flushes the output.
     */
    public void flush() {
        try {
            writer.flush();
        }
        catch (XMLStreamException exception) {
            throw new XmlException("Can't flush", exception);
        }
    }

    /**
     * Closes the XML document and the underlying result.
     */
    public void close() {
        try {
            writer.writeEndDocument();
            writer.close();
        }
        catch (XMLStreamException exception) {
            throw new XmlException("Can't close", exception);
        }
    }
}
