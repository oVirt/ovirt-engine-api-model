/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2005-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.ovirt.api.metamodel.runtime.xml;

import java.util.Stack;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * The contents of this class have been copied from
 * the class by the same name in com.sun.xml.txw2,
 * since this package is no longer supported in RHEL8.
 * Original author of this class is Kohsuke Kawaguchi
 */
public class IndentingXMLStreamWriter implements XMLStreamWriter {

    private final static Object SEEN_NOTHING = new Object();
    private final static Object SEEN_ELEMENT = new Object();
    private final static Object SEEN_DATA = new Object();

    private Object state = SEEN_NOTHING;

    private final XMLStreamWriter writer;

    private Stack<Object> stateStack = new Stack<Object>();

    private String indentStep = "  ";
    private int depth = 0;

    public IndentingXMLStreamWriter(XMLStreamWriter writer) {
        this.writer = writer;
    }

    @Override
    public void writeCharacters(String text) throws XMLStreamException {
        state = SEEN_DATA;
        writer.writeCharacters(text);
    }

    @Override
    public void writeEndElement() throws XMLStreamException {
        onEndElement();
        writer.writeEndElement();
    }

    @Override
    public void writeStartDocument() throws XMLStreamException {
        writer.writeStartDocument();
        writeCharacters("\n");
    }

    @Override
    public void writeStartElement(String localName) throws XMLStreamException {
        onStartElement();
        writer.writeStartElement(localName);
    }

    @Override
    public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
        onStartElement();
        writer.writeStartElement(namespaceURI, localName);
    }

    private void onStartElement() throws XMLStreamException {
        stateStack.push(SEEN_ELEMENT);
        state = SEEN_NOTHING;
        if (depth > 0) {
            writeCharacters("\n");
        }
        doIndent();
        depth++;
    }

    private void onEndElement() throws XMLStreamException {
        depth--;
        if (state == SEEN_ELEMENT) {
            writeCharacters("\n");
            doIndent();
        }
        state = stateStack.pop();
    }

    private void doIndent() throws XMLStreamException {
        if (depth > 0) {
            for (int i = 0; i < depth; i++)
                writeCharacters(indentStep);
        }
    }

    @Override
    public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        writer.writeStartElement(prefix, localName, namespaceURI);
    }

    @Override
    public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
        writer.writeEmptyElement(namespaceURI, localName);
    }

    @Override
    public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        writer.writeEmptyElement(prefix, localName, namespaceURI);
    }

    @Override
    public void writeEmptyElement(String localName) throws XMLStreamException {
        writer.writeEmptyElement(localName);
    }

    @Override
    public void writeEndDocument() throws XMLStreamException {
        writer.writeEndDocument();
    }

    @Override
    public void close() throws XMLStreamException {
        writer.close();
    }

    @Override
    public void flush() throws XMLStreamException {
        writer.flush();
    }

    @Override
    public void writeAttribute(String localName, String value) throws XMLStreamException {
        writer.writeAttribute(localName, value);
    }

    @Override
    public void writeAttribute(String prefix, String namespaceURI, String localName, String value)
            throws XMLStreamException {
        writer.writeAttribute(prefix, namespaceURI, localName, value);
    }

    @Override
    public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
        writer.writeAttribute(namespaceURI, localName, value);
    }

    @Override
    public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
        writer.writeNamespace(prefix, namespaceURI);
    }

    @Override
    public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
        writer.writeDefaultNamespace(namespaceURI);
    }

    @Override
    public void writeComment(String data) throws XMLStreamException {
        writer.writeComment(data);
    }

    @Override
    public void writeProcessingInstruction(String target) throws XMLStreamException {
        writer.writeProcessingInstruction(target);
    }

    @Override
    public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
        writer.writeProcessingInstruction(target, data);
    }

    @Override
    public void writeCData(String data) throws XMLStreamException {
        writer.writeCData(data);
    }

    @Override
    public void writeDTD(String dtd) throws XMLStreamException {
        writer.writeDTD(dtd);
    }

    @Override
    public void writeEntityRef(String name) throws XMLStreamException {
        writer.writeEntityRef(name);
    }

    @Override
    public void writeStartDocument(String version) throws XMLStreamException {
        writer.writeStartDocument(version);
    }

    @Override
    public void writeStartDocument(String encoding, String version) throws XMLStreamException {
        writer.writeStartDocument(encoding, version);
    }

    @Override
    public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
        writer.writeCharacters(text, start, len);
    }

    @Override
    public String getPrefix(String uri) throws XMLStreamException {
        return writer.getPrefix(uri);
    }

    @Override
    public void setPrefix(String prefix, String uri) throws XMLStreamException {
        writer.setPrefix(prefix, uri);
    }

    @Override
    public void setDefaultNamespace(String uri) throws XMLStreamException {
        writer.setDefaultNamespace(uri);
    }

    @Override
    public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
        writer.setNamespaceContext(context);
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return writer.getNamespaceContext();
    }

    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        return writer.getProperty(name);
    }
}
