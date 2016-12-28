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
import static org.junit.Assert.assertTrue;
import static org.ovirt.engine.api.builders.V4Builders.cpu;
import static org.ovirt.engine.api.builders.V4Builders.disk;
import static org.ovirt.engine.api.builders.V4Builders.sso;
import static org.ovirt.engine.api.builders.V4Builders.ssoMethod;
import static org.ovirt.engine.api.builders.V4Builders.vm;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.output.NullOutputStream;
import org.junit.Ignore;
import org.junit.Test;
import org.ovirt.api.metamodel.runtime.xml.XmlWriter;
import org.ovirt.engine.api.types.V4Sso;
import org.ovirt.engine.api.types.V4SsoMethodId;
import org.ovirt.engine.api.types.V4Vm;
import org.ovirt.engine.api.types.V4VmType;
import org.ovirt.engine.api.xml.V4XmlVmWriter;
import types.SsoMethodId;

/**
 * Tests for the classes that convert V4 objects into XML. Note that the tests are centered around an specific class,
 * the {@code Vm} class, as it is very representative and all of them are automatically generated, so what works/fails
 * with this class will also work/fail with the others.
 */
public class XmlWriterTest {
    /**
     * Checks that writing an empty virtual machine produces the expected XML.
     */
    @Test
    public void testEmpty() {
        V4Vm object = vm().build();
        assertEquals(
            "<vm></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that the {@code id} attribute is written as an XML attribute.
     */
    @Test
    public void testIdIsAttribute() {
        V4Vm object = vm().id("123").build();
        assertEquals(
            "<vm id=\"123\"></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that the {@code id} attribute is written as an XML attribute when the value is an enum.
     */
    @Test
    public void testIdIsAttributeIfEnum() {
        V4Vm object = vm()
            .sso(
                sso()
                .methods(
                    ssoMethod()
                    .id(V4SsoMethodId.GUEST_AGENT)
                )
            )
            .build();
        assertEquals(
            "<vm>" +
              "<sso>" +
                "<methods>" +
                  "<sso_method id=\"guest_agent\"></sso_method>" +
                "</methods>" +
              "</sso>" +
            "</vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that an empty attribute is written as an empty XML attribute.
     */
    @Test
    public void testEmptyId() {
        V4Vm object = vm().id("").build();
        assertEquals(
            "<vm id=\"\"></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that a null attribute is ignored.
     */
    @Test
    public void testNullIdIgnored() {
        V4Vm object = vm().id(null).build();
        assertEquals(
            "<vm></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that the {@code name} attribute is written as an XML element.
     */
    @Test
    public void testNameIsElement() {
        V4Vm object = vm().name("myvm").build();
        assertEquals(
            "<vm><name>myvm</name></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that an empty element is written as an empty XML element.
     */
    @Test
    public void testEmptyName() {
        V4Vm object = vm().name("").build();
        assertEquals(
            "<vm><name></name></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that an null element is ignored.
     */
    @Test
    public void testNullName() {
        V4Vm object = vm().name(null).build();
        assertEquals(
            "<vm></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that attributes that are represented as XML attributes and XML elements can be used simultaneously.
     */
    @Test
    public void testAttributeAndElement() {
        V4Vm object = vm()
            .id("123")
            .name("myvm")
            .build();
        assertEquals(
            "<vm id=\"123\"><name>myvm</name></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that multiple attributes are written correctly.
     */
    @Test
    public void testMultipleElements() {
        V4Vm object = vm()
            .name("myvm")
            .fqdn("myvm.example.com")
            .build();
        assertEquals(
            "<vm><fqdn>myvm.example.com</fqdn><name>myvm</name></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that boolean attributes are written correctly.
     */
    @Test
    public void testBooleanElement() {
        V4Vm object = vm()
            .deleteProtected(true)
            .runOnce(false)
            .build();
        assertEquals(
            "<vm>" +
            "<delete_protected>true</delete_protected>" +
            "<run_once>false</run_once>" +
            "</vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that integer attributes are written correctly.
     */
    @Test
    public void test0() {
        V4Vm object = vm().memory(0).build();
        assertEquals(
            "<vm><memory>0</memory></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that integer attributes are written correctly.
     */
    @Test
    public void test1() {
        V4Vm object = vm().memory(1).build();
        assertEquals(
            "<vm><memory>1</memory></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that negative integer attributes are written correctly (although this doesn't make sense for the
     * {@code memory} attribute).
     */
    @Test
    public void testMinus1() {
        V4Vm object = vm().memory(-1).build();
        assertEquals(
            "<vm><memory>-1</memory></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that long integers ware written correct.
     */
    @Test
    public void testLong() {
        V4Vm object = vm().memory(10L * Integer.MAX_VALUE).build();
        assertEquals(
            "<vm><memory>21474836470</memory></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that integer longer than long are written correctly.
     */
    @Test
    public void testVeryLong() {
        BigInteger memory = BigInteger.TEN.multiply(BigInteger.valueOf(Long.MAX_VALUE));
        V4Vm object = vm().memory(memory).build();
        assertEquals(
            "<vm><memory>92233720368547758070</memory></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that dates are written correctly.
     */
    @Test
    public void testDate() {
        java.util.TimeZone tz = java.util.TimeZone.getTimeZone("UTC+1");
        Calendar calendar = Calendar.getInstance(tz);
        calendar.clear();
        calendar.set(2015, 12, 17, 16, 18, 23);
        calendar.set(Calendar.MILLISECOND, 123);
        Date date = calendar.getTime();
        V4Vm object = vm().creationTime(date).build();
        assertEquals(
            "<vm><creation_time>2016-01-17T16:18:23.123Z</creation_time></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that enum values are written correctly.
     */
    @Test
    public void testEnum() {
        V4Vm object = vm().type(V4VmType.DESKTOP).build();
        assertEquals(
            "<vm><type>desktop</type></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that nested elements are written correctly.
     */
    @Test
    public void testNested() {
        V4Vm object = vm()
            .cpu(cpu().mode("mymode"))
            .build();
        assertEquals(
            "<vm>" +
              "<cpu><mode>mymode</mode></cpu>" +
            "</vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that multiple nested elements are written correctly.
     */
    @Test
    public void testMultipleNested() {
        V4Vm object = vm()
            .disks(disk().id("123").alias("disk1"))
            .disks(disk().id("456").alias("disk2"))
            .build();
        assertEquals(
            "<vm>" +
              "<disks>" +
                "<disk id=\"123\"><alias>disk1</alias></disk>" +
                "<disk id=\"456\"><alias>disk2</alias></disk>" +
              "</disks>" +
            "</vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that using a custom tag with an empty object works correctly.
     */
    @Test
    public void testCustomTagWithEmptyObject() {
        V4Vm object = vm().build();
        assertEquals(
            "<mytag></mytag>",
            objectToXml(object, "mytag")
        );
    }

    /**
     * Checks that using a custom tag with an empty object that isn't empty works correctly.
     */
    @Test
    public void testCustomTagWithNonEmpty() {
        V4Vm object = vm().id("123").name("myvm").build();
        assertEquals(
            "<mytag id=\"123\"><name>myvm</name></mytag>",
            objectToXml(object, "mytag")
        );
    }

    /**
     * Checks that empty lists of objects are written correctly.
     */
    @Test
    public void testEmptyList() {
        List<V4Vm> list = Collections.emptyList();
        assertEquals(
            "<vms></vms>",
            listToXml(list.iterator())
        );
    }

    /**
     * Checks that lists with one object are written correctly.
     */
    @Test
    public void testListWithOneObject() {
        List<V4Vm> list = Collections.singletonList(vm().build());
        assertEquals(
            "<vms><vm></vm></vms>",
            listToXml(list.iterator())
        );
    }

    /**
     * Checks that lists with multiple objects are written correctly.
     */
    @Test
    public void testListWithMultipleObjects() {
        List<V4Vm> list = new ArrayList<>();
        list.add(vm().build());
        list.add(vm().build());
        assertEquals(
            "<vms><vm></vm><vm></vm></vms>",
            listToXml(list.iterator())
        );
    }

    /**
     * Checks that custom tags for lists work correctly.
     */
    @Test
    public void testListWithCustomTag() {
        List<V4Vm> list = Collections.singletonList(vm().build());
        assertEquals(
            "<mylist><myitem></myitem></mylist>",
            listToXml(list.iterator(), "myitem", "mylist")
        );
    }

    /**
     * Test that list of string is written correctly
     */
    @Test
    public void testWriteListOfStrings() {
        List<String> strings = Arrays.asList("value1", "value2");
        V4Vm object = vm().properties(strings).build();
        assertEquals(
            "<vm><properties>value1</properties><properties>value2</properties></vm>",
            objectToXml(object)
        );
    }

    /**
     * Checks that one million of VMs can be serialized in a reasonable time and without exhausting the memory of the
     * virtual machine. Note that the test is currently disabled because if it fails it will block other tests, but it
     * is still useful to run it manually, so please don't remove it.
     */
    @Test
    @Ignore
    public void testOneMillion() throws IOException {
        // Create a iterator that generates the objects:
        Iterator<V4Vm> infinite = new Iterator<V4Vm>() {
            private int count;

            @Override
            public boolean hasNext() {
                return count < 1_000_000;
            }

            @Override
            public V4Vm next() {
                // Note that these are extremely simple objects, they should probably be more complicated for this
                // test to be realistic:
                V4Vm object = vm()
                    .id(String.valueOf(count))
                    .name("vm" + count)
                    .disks(disk().id("123").alias("disk1"))
                    .disks(disk().id("456").alias("disk2"))
                    .build();
                count++;
                return object;
            }
        };

        // Write the objects generated by the iterator to a null stream, so that the only work performed is creating
        // the objects and converting them to XML:
        long before = System.currentTimeMillis();
        try (XmlWriter writer = new XmlWriter(new NullOutputStream(), false)) {
            V4XmlVmWriter.writeMany(infinite, writer);
        }
        long after = System.currentTimeMillis();
        long elapsed = after - before;

        // Check if it took less than a minute (it should take much more less, approx 2s, but lets be
        // conservative:
        assertTrue(elapsed < 60_000_000);
    }

    /**
     * Converts the given object to XML and returns a string containing the XML text. Will use the default tag
     * for the object.
     *
     * @param vm the object to convert
     */
    private String objectToXml(V4Vm vm) {
        return objectToXml(vm, null);
    }

    /**
     * Converts the given object to XML and returns a string containing the XML text. Optionally a custom tag can
     * be specified.
     *
     * @param object the object to convert
     * @param tag the custom tag to use instead of the default one, if {@code null} then the default will be used
     */
    private String objectToXml(V4Vm object, String tag) {
        StringWriter buffer = new StringWriter();
        try (XmlWriter writer = new XmlWriter(buffer, false)) {
            if (tag != null) {
                V4XmlVmWriter.writeOne(object, tag, writer);
            }
            else {
                V4XmlVmWriter.writeOne(object, writer);
            }
            writer.flush();
            return buffer.toString();
        }
    }

    /**
     * Converts the given list of objects to XML and returns a string containing the XML text. Will use the default
     * tags for the list and for the items.
     *
     * @param list the list of objects
     */
    private String listToXml(Iterator<V4Vm> list) {
        return listToXml(list, null, null);
    }

    /**
     * Converts the given list of object to XML and returns a string containing the XML text. Optionally custom tags
     * can be specified for the list and for the items.
     *
     * @param list the list of objects to convert
     * @param singular the custom tag used for the items of the list
     * @param plural the custom tag used for the list
     */
    private String listToXml(Iterator<V4Vm> list, String singular, String plural) {
        StringWriter buffer = new StringWriter();
        try (XmlWriter writer = new XmlWriter(buffer, false)) {
            if (singular != null && plural != null) {
                V4XmlVmWriter.writeMany(list, singular, plural, writer);
            }
            else {
                V4XmlVmWriter.writeMany(list, writer);
            }
            writer.flush();
            return buffer.toString();
        }
    }
}
