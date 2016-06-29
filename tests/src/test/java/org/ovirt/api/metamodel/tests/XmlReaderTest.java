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

package org.ovirt.api.metamodel.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.xml.stream.XMLStreamConstants;

import org.junit.Ignore;
import org.junit.Test;
import org.ovirt.api.metamodel.runtime.util.ListWithHref;
import org.ovirt.api.metamodel.runtime.xml.XmlException;
import org.ovirt.api.metamodel.runtime.xml.XmlReader;
import org.ovirt.engine.api.types.V4Disk;
import org.ovirt.engine.api.types.V4Vm;
import org.ovirt.engine.api.types.V4VmDisplayType;
import org.ovirt.engine.api.types.V4VmType;
import org.ovirt.engine.api.xml.V4XmlVmReader;

/**
 * Tests for the classes that convert XML into V4 objects. Note that the tests are centered around an specific class,
 * the {@code Vm} class, as it is very representative and all of them are automatically generated, so what works/fails
 * with this class will also work/fail with the others.
 */
public class XmlReaderTest {
    /**
     * Check that that given some preceding text before an start element the {@code forward} methods moves to the next
     * start element and returns {@code true}.
     */
    @Test
    public void testForwardMoveToStartTag() {
        XmlReader reader = openReader("<root>text<target></target></root>");
        reader.next();
        boolean result = reader.forward();
        assertTrue(result);
        assertEquals(XMLStreamConstants.START_ELEMENT, reader.getEventType());
        assertEquals("target", reader.getLocalName());
    }

    /**
     * Check that the {@code forward} method skips comments before the root element.
     */
    @Test
    public void testForwardIgnoresCommentBeforeRoot() {
        XmlReader reader = openReader("<!-- Hello! --><root>text</root>");
        reader.next();
        boolean result = reader.forward();
        assertTrue(result);
        assertEquals(XMLStreamConstants.START_ELEMENT, reader.getEventType());
        assertEquals("root", reader.getLocalName());
    }

    /**
     * Check that the {@code forward} method skips comments before the target element.
     */
    @Test
    public void testForwardIgnoresCommentBeforeTarget() {
        XmlReader reader = openReader("<root><!-- Hello! --><target></target></root>");
        reader.next();
        reader.next();
        boolean result = reader.forward();
        assertTrue(result);
        assertEquals(XMLStreamConstants.START_ELEMENT, reader.getEventType());
        assertEquals("target", reader.getLocalName());
    }

    /**
     * Check that that given some preceding text before an end element the {@code forward} methods moves to the next
     * end element and returns {@code false}.
     */
    @Test
    public void testForwardMoveToEndTag() {
        XmlReader reader = openReader("<root><target>text</target></root>");
        reader.next();
        reader.next();
        reader.next();
        boolean result = reader.forward();
        assertFalse(result);
        assertEquals(XMLStreamConstants.END_ELEMENT, reader.getEventType());
        assertEquals("target", reader.getLocalName());
    }

    /**
     * Check that that when the cursor is positioned at the end of the document calling {@code forward} doesn't move
     * it.
     */
    @Test
    public void testForwardDoesNotMovePastEndDocument() {
        XmlReader reader = openReader("<root/>");
        reader.next();
        reader.next();
        boolean result = reader.forward();
        assertFalse(result);
        assertEquals(XMLStreamConstants.END_DOCUMENT, reader.getEventType());
    }

    /**
     * Check that when the cursor is positioned in an start element the {@code forward} method returns {@code true}
     * and stays at the start element.
     */
    @Test
    public void testForwardDoesNotMovePastStartElement() {
        XmlReader reader = openReader("<root><target></target></root>");
        reader.next();
        boolean result = reader.forward();
        assertTrue(result);
        assertEquals(XMLStreamConstants.START_ELEMENT, reader.getEventType());
        assertEquals("target", reader.getLocalName());
    }

    /**
     * Check that when the cursor is positioned in an end element the {@code forward} method returns {@code false}
     * and stays at the end element.
     */
    @Test
    public void testForwardDoesNotMovePastEndElement() {
        XmlReader reader = openReader("<root><target></target></root>");
        reader.next();
        reader.next();
        boolean result = reader.forward();
        assertFalse(result);
        assertEquals(XMLStreamConstants.END_ELEMENT, reader.getEventType());
        assertEquals("target", reader.getLocalName());
    }

    /**
     * Checks that {@code skip} method moves to next element.
     */
    @Test
    public void testSkipMovesToNextElement() {
        XmlReader reader = openReader("<root><current><inner>text</inner></current><next/></root>");
        reader.next();
        reader.skip();
        assertEquals(XMLStreamConstants.START_ELEMENT, reader.getEventType());
        assertEquals("next", reader.getLocalName());
    }

    /**
     * Checks that given the text {@code true} the {@code readBoolean} method returns the correct value.
     */
    @Test
    public void testReadBooleanTrue() {
        XmlReader reader = openReader("<value>true</value>");
        assertTrue(reader.readBoolean());
    }

    /**
     * Checks that given the text {@code TRUE} the {@code readBoolean} method returns the correct value, thus ignoring
     * the case.
     */
    @Test
    public void testReadBooleanTRUE() {
        XmlReader reader = openReader("<value>TRUE</value>");
        assertTrue(reader.readBoolean());
    }

    /**
     * Checks that given the text {@code 1} the {@code readBoolean} method returns {@code true}.
     */
    @Test
    public void testReadBoolean1() {
        XmlReader reader = openReader("<value>1</value>");
        assertTrue(reader.readBoolean());
    }

    /**
     * Checks that given the text {@code false} the {@code readBoolean} method returns the correct value.
     */
    @Test
    public void testReadBooleanFalse() {
        XmlReader reader = openReader("<value>false</value>");
        assertFalse(reader.readBoolean());
    }

    /**
     * Checks that given the text {@code FALSE} the {@code readBoolean} method returns the correct value, thus ignoring
     * the case.
     */
    @Test
    public void testReadBooleanFALSE() {
        XmlReader reader = openReader("<value>FALSE</value>");
        assertFalse(reader.readBoolean());
    }

    /**
     * Checks that given the text {@code 0} the {@code readBoolean} method returns {@code false}.
     */
    @Test
    public void testReadBoolean0() {
        XmlReader reader = openReader("<value>0</value>");
        assertFalse(reader.readBoolean());
    }

    /**
     * Checks that given {@code readBoolean} leaves the cursor positioned at event after the one that contains the
     * value.
     */
    @Test
    public void testReadBooleanPositionsCursorAtEndTag() {
        XmlReader reader = openReader("<root><value>true</value><next/></root>");
        reader.next();
        reader.readBoolean();
        assertEquals(XMLStreamConstants.START_ELEMENT, reader.getEventType());
        assertEquals("next", reader.getLocalName());
    }

    /**
     * Checks that given an invalid boolean value the {@code readBoolean} method throws an exception.
     */
    @Test(expected = XmlException.class)
    public void testReadBooleanInvalidValueThrowsException() {
        XmlReader reader = openReader("<value>ugly</value>");
        reader.readBoolean();
    }

    /**
     * Checks that given the text {@code 0} the {@code readInteger} method parses it correctly.
     */
    @Test
    public void testReadInteger0() {
        XmlReader reader = openReader("<value>0</value>");
        assertEquals(BigInteger.ZERO, reader.readInteger());
    }

    /**
     * Checks that given the text {@code 1} the {@code readInteger} method parses it correctly.
     */
    @Test
    public void testReadInteger1() {
        XmlReader reader = openReader("<value>1</value>");
        assertEquals(BigInteger.ONE, reader.readInteger());
    }

    /**
     * Checks that given a negative number the {@code readInteger} method parses it correctly.
     */
    @Test
    public void testReadIntegerMinus1() {
        XmlReader reader = openReader("<value>-1</value>");
        assertEquals(BigInteger.ONE.negate(), reader.readInteger());
    }

    /**
     * Checks very long integer values (longer than what the {@code long} type supports) are parsed correctly.
     */
    @Test
    public void testReadVeryLongInteger() {
        XmlReader reader = openReader("<value>92233720368547758070</value>");
        assertEquals(BigInteger.valueOf(Long.MAX_VALUE).multiply(BigInteger.TEN), reader.readInteger());
    }

    /**
     * Checks that {@code readInteger} leaves the cursor positioned at the end element that contains the value.
     */
    @Test
    public void testReadIntegerPositionsCursorAtEndTag() {
        XmlReader reader = openReader("<root><value>0</value><next/></root>");
        reader.next();
        reader.readInteger();
        assertEquals(XMLStreamConstants.START_ELEMENT, reader.getEventType());
        assertEquals("next", reader.getLocalName());
    }

    /**
     * Checks that given an invalid integer value the {@code readInteger} method throws an exception.
     */
    @Test(expected = XmlException.class)
    public void testReadIntegerInvalidValueThrowsException() {
        XmlReader reader = openReader("<value>ugly</value>");
        reader.readInteger();
    }

    /**
     * Checks that given the text {@code 0.0} the {@code readDecimal} method parses it correctly.
     */
    @Test
    public void testReadDecimalZero() {
        XmlReader reader = openReader("<value>0.0</value>");
        assertEquals(BigDecimal.ZERO, reader.readDecimal());
    }

    /**
     * Checks that given the text {@code 1} the {@code readInteger} method parses it correctly.
     */
    @Test
    public void testReadDecimal1() {
        XmlReader reader = openReader("<value>1.0</value>");
        assertEquals(BigDecimal.ONE, reader.readDecimal());
    }

    /**
     * Checks that given a negative decimal number the {@code readInteger} method parses it correctly.
     */
    @Test
    public void testReadDecimalMinus1() {
        XmlReader reader = openReader("<value>-1.0</value>");
        assertEquals(BigDecimal.ONE.negate(), reader.readDecimal());
    }

    /**
     * Checks very long decimal values are parsed correctly.
     */
    @Test
    public void testReadVeryLongDecimal() {
        XmlReader reader = openReader("<value>3.14159265358979323846264338327</value>");
        BigDecimal pi = new BigDecimal("3.14159265358979323846264338327");
        assertEquals(pi, reader.readDecimal());
    }

    /**
     * Checks that {@code readDecimal} leaves the cursor positioned at the end element that contains the value.
     */
    @Test
    public void testReadDecimalPositionsCursorAtEndTag() {
        XmlReader reader = openReader("<root><value>0.0</value><next/></root>");
        reader.next();
        reader.readDecimal();
        assertEquals(XMLStreamConstants.START_ELEMENT, reader.getEventType());
        assertEquals("next", reader.getLocalName());
    }

    /**
     * Checks that given an invalid decimal value the {@code readDecimal} method throws an exception.
     */
    @Test(expected = XmlException.class)
    public void testReadDecimalInvalidValueThrowsException() {
        XmlReader reader = openReader("<value>ugly</value>");
        reader.readDecimal();
    }

    /**
     * Checks that {@code readString} methods returns an empty string for an empty tag.
     */
    @Test
    public void testReadStringEmptyTag() {
        XmlReader reader = openReader("<root><value></value></root>");
        reader.next();
        String result = reader.readString();
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * Checks that {@code readString} methods returns an empty string for an empty tag without the close tag.
     */
    @Test
    public void testReadStringEmptyTagNoClose() {
        XmlReader reader = openReader("<root><value/></root>");
        reader.next();
        String result = reader.readString();
        assertNotNull(result);
        assertEquals("", result);
    }

    /**
     * Checks that {@code readString} methods returns the expected value.
     */
    @Test
    public void testReadStringExpected() {
        XmlReader reader = openReader("<root><value>hi</value></root>");
        reader.next();
        String result = reader.readString();
        assertNotNull(result);
        assertEquals("hi", result);
    }

    /**
     * Checks that {@code readString} leaves the cursor positioned at the end element that contains the value.
     */
    @Test
    public void testReadStringPositionsCursorAtEndTag() {
        XmlReader reader = openReader("<root><value>hi</value><next/></root>");
        reader.next();
        reader.readString();
        assertEquals(XMLStreamConstants.START_ELEMENT, reader.getEventType());
        assertEquals("next", reader.getLocalName());
    }

    /**
     * Checks that {@code readStrings} leaves the cursor positioned at the end element that contains the value.
     */
    @Test
    public void testReadStringListEndElement() {
        XmlReader reader = openReader("<action><value>1</value><value>2</value></action>");
        reader.next();
        reader.readStrings();
        assertEquals(XMLStreamConstants.END_ELEMENT, reader.getEventType());
        assertEquals("action", reader.getLocalName());
    }

    /**
     * Checks that {@code readBooleans} leaves the cursor positioned at the end element that contains the value.
     */
    @Test
    public void testReadBooleanListEndElement() {
        XmlReader reader = openReader("<action><value>true</value><value>false</value></action>");
        reader.next();
        reader.readBooleans();
        assertEquals(XMLStreamConstants.END_ELEMENT, reader.getEventType());
        assertEquals("action", reader.getLocalName());

    }

    /**
     * Checks that {@code readStrings} read all elements correctly
     */
    @Test
    public void testReadStringList() {
        List<String> expected = Arrays.asList("1", "2");
        XmlReader reader = openReader("<action><value>1</value><value>2</value></action>");
        reader.next();
        List<String> strings = reader.readStrings();
        assertEquals(expected, strings);
    }

    /**
     * Checks that {@code readBooleans} read all elements correctly
     */
    @Test
    public void testReadBooleanList() {
        List<Boolean> expected = Arrays.asList(true, false);
        XmlReader reader = openReader("<action><value>true</value><value>false</value></action>");
        reader.next();
        List<Boolean> booleans = reader.readBooleans();
        assertEquals(expected, booleans);
    }

    /**
     * Checks that reading an empty element generates an empty, but not {@code null}, object.
     */
    @Test
    public void testEmpty() {
        V4Vm object = objectFromXml("<vm></vm>");
        assertNotNull(object);
        assertNull(object.id());
        assertNull(object.name());
    }

    /**
     * Checks that reading an empty element without the close tag generates an empty, but not {@code null}, object.
     */
    @Test
    public void testEmptyNoCloseTag() {
        V4Vm object = objectFromXml("<vm/>");
        assertNotNull(object);
        assertNull(object.id());
        assertNull(object.name());
    }

    /**
     * Checks that attributes that are represented as XML attributes are read correctly.
     */
    @Test
    public void testAttr() {
        V4Vm object = objectFromXml("<vm id=\"123\"></vm>");
        assertEquals("123", object.id());
    }

    /**
     * Checks that empty XML attributes are represented as empty strings.
     */
    @Test
    public void testEmptyAttr() {
        V4Vm object = objectFromXml("<vm id=\"\"></vm>");
        assertEquals("", object.id());
    }

    /**
     * Checks that XML attributes that aren't present are translated into {@code null}.
     */
    @Test
    public void testNullAttr() {
        V4Vm object = objectFromXml("<vm></vm>");
        assertNull(object.id());
    }

    /**
     * Checks that attributes that are represented as XML elements are parsed correctly.
     */
    @Test
    public void testSimpleElement() {
        V4Vm object = objectFromXml("<vm><name>myvm</name></vm>");
        assertEquals("myvm", object.name());
    }

    /**
     * Checks that an empty element is represented as an empty string.
     */
    @Test
    public void testEmptyElement() {
        V4Vm object = objectFromXml("<vm><name></name></vm>");
        assertEquals("", object.name());
    }

    /**
     * Checks that an element that isn't present is represented as {@code null}.
     */
    @Test
    public void testNullElement() {
        V4Vm object = objectFromXml("<vm></vm>");
        assertNull(object.name());
    }

    /**
     * Checks that model attributes that are represented as XML attributes and model attributes that are represented as
     * as elements can be used simultaneously.
     */
    @Test
    public void testAttrAndElement() {
        V4Vm object = objectFromXml("<vm id=\"123\"><name>myvm</name></vm>");
        assertEquals("123", object.id());
        assertEquals("myvm", object.name());
    }

    /**
     * Checks that multiple model attributes represented as XML elements work correctly.
     */
    @Test
    public void testMultipleElements() {
        V4Vm object = objectFromXml("<vm><name>myvm</name><fqdn>myvm.example.com</fqdn></vm>");
        assertEquals("myvm", object.name());
        assertEquals("myvm.example.com", object.fqdn());
    }

    /**
     * Checks that boolean attributes are read correctly.
     */
    @Test
    public void testBooleanElement() {
        V4Vm object = objectFromXml(
            "<vm>" +
            "<delete_protected>true</delete_protected>" +
            "<run_once>false</run_once>" +
            "</vm>"
        );
        assertTrue(object.deleteProtected());
        assertFalse(object.runOnce());
    }

    /**
     * Checks that integer attributes are read correctly.
     */
    @Test
    public void test0() {
        V4Vm object = objectFromXml("<vm><memory>0</memory></vm>");
        assertEquals(BigInteger.ZERO, object.memory());
    }

    /**
     * Checks that integer attributes are read correctly.
     */
    @Test
    public void test1() {
        V4Vm object = objectFromXml("<vm><memory>1</memory></vm>");
        assertEquals(BigInteger.ONE, object.memory());
    }

    /**
     * Checks that negative integer values are read correctly.
     */
    @Test
    public void testMinus1() {
        V4Vm object = objectFromXml("<vm><memory>-1</memory></vm>");
        assertEquals(BigInteger.ONE.negate(), object.memory());
    }

    /**
     * Checks that long integers are read correctly.
     */
    @Test
    public void testLong() {
        V4Vm object = objectFromXml("<vm><memory>21474836470</memory></vm>");
        assertEquals(BigInteger.TEN.multiply(BigInteger.valueOf(Integer.MAX_VALUE)), object.memory());
    }

    /**
     * Checks that very long integers are read correctly.
     */
    @Test
    public void testVeryLong() {
        V4Vm object = objectFromXml("<vm><memory>92233720368547758070</memory></vm>");
        assertEquals(BigInteger.TEN.multiply(BigInteger.valueOf(Long.MAX_VALUE)), object.memory());
    }

    /**
     * Checks that lower case enums are read correctly.
     */
    @Test
    public void testLowerCaseEnum() {
        V4Vm object = objectFromXml("<vm><type>desktop</type></vm>");
        assertEquals(V4VmType.DESKTOP, object.type());
    }

    /**
     * Checks that upper case enums are read correctly.
     */
    @Test
    public void testUpperCaseEnum() {
        V4Vm object = objectFromXml("<vm><type>DESKTOP</type></vm>");
        assertEquals(V4VmType.DESKTOP, object.type());
    }

    /**
     * Checks that mixed case enums are read correctly.
     */
    @Test
    public void testMixedCaseEnum() {
        V4Vm object = objectFromXml("<vm><type>DeskTop</type></vm>");
        assertEquals(V4VmType.DESKTOP, object.type());
    }

    /**
     * Checks that list of enums are read correctly.
     */
    @Test
    public void testEnumReadMany() {
        V4Vm object = objectFromXml(
            "<vm>" +
              "<display_types>" +
                "<display_type>spice</display_type>" +
                "<display_type>VNC</display_type>" +
              "</display_types>" +
            "</vm>"
        );
        List<V4VmDisplayType> expected = Arrays.asList(V4VmDisplayType.SPICE, V4VmDisplayType.VNC);
        assertEquals(expected, object.displayTypes());
    }

    /**
     * Checks that nested elements are read correctly.
     */
    @Test
    public void testNested() {
        V4Vm object = objectFromXml(
            "<vm>" +
            "<cpu><mode>mymode</mode></cpu>" +
            "</vm>"
        );
        assertNotNull(object.cpu());
        assertEquals("mymode", object.cpu().mode());
    }

    /**
     * Checks that after reading one object the cursor is positioned at the next event.
     */
    @Test
    public void testReadOneMovesToNextEvent() {
        XmlReader reader = openReader("<root><vm></vm><next/></root>");
        reader.next();
        V4XmlVmReader.readOne(reader);
        assertEquals(XMLStreamConstants.START_ELEMENT, reader.getEventType());
        assertEquals("next", reader.getLocalName());
    }

    /**
     * Checks that after reading one object the cursor is positioned at the next, even if the object contain nested
     * elements.
     */
    @Test
    public void testReadOneMovesToNextEventWithNestedElements() {
        XmlReader reader = openReader("<root><vm><name>myvm</name></vm><next/></root>");
        reader.next();
        V4XmlVmReader.readOne(reader);
        assertEquals(XMLStreamConstants.START_ELEMENT, reader.getEventType());
        assertEquals("next", reader.getLocalName());
    }

    /**
     * Checks that after reading one object the cursor is positioned at the next, even if the object contain nested
     * elements.
     */
    @Test
    public void testReadOneMovesToNextEventWithMultipleNestedElements() {
        XmlReader reader = openReader(
            "<root>" +
              "<vm>" +
                 "<name>myvm</name>" +
                 "<description>My VM</description>" +
                 "<disks>" +
                   "<disk id=\"123\"><alias>disk1</alias></disk>" +
                   "<disk id=\"456\"><alias>disk2</alias></disk>" +
                 "</disks>" +
              "</vm>" +
              "<next/>" +
            "</root>"
        );
        reader.next();
        V4XmlVmReader.readOne(reader);
        assertEquals(XMLStreamConstants.START_ELEMENT, reader.getEventType());
        assertEquals("next", reader.getLocalName());
    }

    /**
     * Checks that unknonwn elements are skipped and ignored.
     */
    @Test
    public void testReadOneSkipsAndIngoresUnknownElements() {
        XmlReader reader = openReader(
            "<vm>" +
              "<junk>ugly</junk>" +
              "<name>myvm</name>" +
            "</vm>"
        );
        V4Vm vm = V4XmlVmReader.readOne(reader);
        assertEquals("myvm", vm.name());
    }

    /**
     * Checks that multiple nested elements are read correctly.
     */
    @Test
    public void testMultipleNested() {
        V4Vm object = objectFromXml(
            "<vm>" +
              "<disks>" +
                "<disk id=\"123\"><alias>disk1</alias></disk>" +
                "<disk id=\"456\"><alias>disk2</alias></disk>" +
              "</disks>" +
            "</vm>"
        );
        List<V4Disk> items = object.disks();
        assertNotNull(items);
        assertEquals(2, items.size());
        V4Disk item1 = items.get(0);
        assertEquals("123", item1.id());
        assertEquals("disk1", item1.alias());
        V4Disk item2 = items.get(1);
        assertEquals("456", item2.id());
        assertEquals("disk2", item2.alias());
    }

    /**
     * Checks that dates are written correctly.
     */
    @Test
    public void testDate() {
        V4Vm object = objectFromXml("<vm><creation_time>2016-01-17T17:18:23.123+01:00</creation_time></vm>");
        java.util.TimeZone tz = java.util.TimeZone.getTimeZone("UTC+1");
        Calendar calendar = Calendar.getInstance(tz);
        calendar.clear();
        calendar.set(2015, 12, 17, 16, 18, 23);
        calendar.set(Calendar.MILLISECOND, 123);
        Date date = calendar.getTime();
        assertEquals(date, object.creationTime());
    }

    /**
     * Checks that empty lists of objects are read correctly.
     */
    @Test
    public void testEmptyList() {
        List<V4Vm> list = listFromXml("<vms></vms>");
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    /**
     * Checks that lists with one object are read correctly.
     */
    @Test
    public void testListWithOneObject() {
        List<V4Vm> list = listFromXml("<vms><vm></vm></vms>");
        assertNotNull(list);
        assertEquals(1, list.size());
        V4Vm item = list.get(0);
        assertNotNull(item);
    }

    /**
     * Checks that lists with multiple objects are read correctly.
     */
    @Test
    public void testListWithMultipleObjects() {
        List<V4Vm> list = listFromXml("<vms><vm></vm><vm></vm></vms>");
        assertNotNull(list);
        assertEquals(2, list.size());
        V4Vm item1 = list.get(0);
        assertNotNull(item1);
        V4Vm item2 = list.get(1);
        assertNotNull(item2);
    }

    /**
     * Checks that custom tags for lists work correctly.
     */
    @Test
    public void testListWithCustomTag() {
        List<V4Vm> list = listFromXml("<mylist><myitem></myitem></mylist>");
        assertNotNull(list);
        assertEquals(1, list.size());
        V4Vm item1 = list.get(0);
        assertNotNull(item1);
    }

    /**
     * Checks that href is read correctly in link element
     */
    @Test
    public void testLinkHrefAttribute() {
        V4Vm vm = objectFromXml("<vm><link rel=\"permissions\" href=\"123\"/></vm>");
        assertNotNull(vm);
        assertNotNull(vm.permissions());
        assertTrue(vm.permissions() instanceof ListWithHref);
        assertEquals("123", ((ListWithHref) vm.permissions()).href());
    }

    /**
     * Checks that link element is read correctly if href is not present
     */
    @Test
    public void testLinkWithoutHref() {
        V4Vm vm = objectFromXml("<vm><link rel=\"permissions\"/></vm>");
        assertNotNull(vm);
        assertNotNull(vm.permissions());
        assertTrue(vm.disks() instanceof List);
    }

    /**
     * Checks that non-related rel attribute is skipped
     */
    @Test
    public void testLinkIncorrectRelElement() {
        V4Vm vm = objectFromXml("<vm><link rel=\"ugly\" href=\"123\"/></vm>");
        assertNotNull(vm);
        assertTrue(vm.permissions().isEmpty());
    }

    /**
     * Checks that readLink don't fail if link element is not present
     */
    @Test
    public void testLinkIsNotPresent() {
        V4Vm vm = objectFromXml("<vm></vm>");
        assertNotNull(vm);
        assertTrue(vm.permissions().isEmpty());
    }

    /**
     * Checks that one million of VMs can be deserialize in a reasonable time and without exhausting the memory of the
     * virtual machine. Note that the test is currently disabled because if it fails it will block other tests, but it
     * is still useful to run it manually, so please don't remove it.
     */
    @Test
    @Ignore
    public void testOneMillion() throws IOException, InterruptedException {
        // The text of the object:
        String text =
            "<vm>" +
              "<disks>" +
                "<disk id=\"123\"><alias>disk1</alias></disk>" +
                "<disk id=\"456\"><alias>disk2</alias></disk>" +
              "</disks>" +
            "</vm>";

        // Prepare the pipe that will generate the input for the XML parser:
        PipedReader in = new PipedReader();
        PipedWriter out = new PipedWriter();
        in.connect(out);

        // Create a thread that writes characters to the pipe:
        Thread thread = new Thread(() -> {
            try {
                out.write("<vms>");
                for (int i = 0; i < 1_000_000; i++) {
                    out.write(text);
                }
                out.write("</vms>");
                out.close();
            }
            catch (IOException ignore) {
            }
        });

        // Start the writer:
        thread.start();

        // Start the reader:
        XmlReader reader = new XmlReader(in);
        long before = System.currentTimeMillis();
        Iterator<V4Vm> iterator = V4XmlVmReader.iterateMany(reader);
        while (iterator.hasNext()) {
            iterator.next();
        }
        reader.close();
        long after = System.currentTimeMillis();
        long elapsed = after - before;

        // Wait for the writer thread:
        thread.join();

        // Check if it took less than a minute (it should take much more less, approx 10s, but lets be
        // conservative:
        assertTrue(elapsed < 60_000_000);
    }

    /**
     * Checks that iterators throw {@link NoSuchElementException} when there are no more elements to read.
     */
    @Test(expected = NoSuchElementException.class)
    public void testThrowsNoSuchElement() {
        try (XmlReader reader = openReader("<vms></vms>")) {
            Iterator<V4Vm> iterator = V4XmlVmReader.iterateMany(reader);
            iterator.hasNext();
            iterator.next();
        }
    }

    /**
     * Opens a cursor for reading the given XML text and positions it in the first event.
     */
    private XmlReader openReader(String text) {
        StringReader buffer = new StringReader(text);
        XmlReader reader = new XmlReader(buffer);
        reader.next();
        return reader;
    }

    /**
     * Converts the given XML to an object.
     *
     * @param text the XML text
     * @return the object
     */
    private V4Vm objectFromXml(String text) {
        StringReader buffer = new StringReader(text);
        try (XmlReader reader = new XmlReader(buffer)){
            return V4XmlVmReader.readOne(reader);
        }
    }

    /**
     * Converts the given XML to list of objects.
     *
     * @param text the XML text
     * @return the list
     */
    private List<V4Vm> listFromXml(String text) {
        StringReader buffer = new StringReader(text);
        try (XmlReader reader = new XmlReader(buffer)) {
            return V4XmlVmReader.readMany(reader);
        }
    }
}
