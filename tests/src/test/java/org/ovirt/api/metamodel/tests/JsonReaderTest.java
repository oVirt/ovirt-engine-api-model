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
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Ignore;
import org.junit.Test;
import org.ovirt.api.metamodel.runtime.json.JsonReader;
import org.ovirt.engine.api.json.V4JsonVmReader;
import org.ovirt.engine.api.types.V4Disk;
import org.ovirt.engine.api.types.V4Vm;
import org.ovirt.engine.api.types.V4VmDisplayType;
import org.ovirt.engine.api.types.V4VmType;

/**
 * Tests for the classes that convert JSON to objects. Note that the tests are centered around an specific class,
 * the {@code Vm} class, as it is very representative and all of them are automatically generated, so what works/fails
 * with this class will also work/fail with the others.
 */
public class JsonReaderTest {
    /**
     * Checks that reading an empty JSON generates an empty, but not {@code null}, object.
     */
    @Test
    public void testEmpty() {
        V4Vm object = objectFromJson("{}");
        assertNotNull(object);
        assertNull(object.id());
        assertNull(object.name());
    }

    /**
     * Checks that the id attribute is read correctly.
     */
    @Test
    public void testId() {
        V4Vm object = objectFromJson("{'id':'123'}");
        assertEquals("123", object.id());
    }

    /**
     * Checks that the empty id attribute is converted to an empty string.
     */
    @Test
    public void testIdEmpty() {
        V4Vm object = objectFromJson("{'id':''}");
        assertEquals("", object.id());
    }

    /**
     * Checks that attributes that aren't present are translated into {@code null}.
     */
    @Test
    public void testIdNull() {
        V4Vm object = objectFromJson("{}");
        assertNull(object.id());
    }

    /**
     * Checks that multiple model attributes work correctly.
     */
    @Test
    public void testMultiple() {
        V4Vm object = objectFromJson("{'fqdn':'myvm.example.com','name':'myvm'}");
        assertEquals("myvm", object.name());
        assertEquals("myvm.example.com", object.fqdn());
    }

    /**
     * Checks that boolean attributes are read correctly.
     */
    @Test
    public void testBoolean() {
        V4Vm object = objectFromJson("{'delete_protected':true,'run_once':false}");
        assertTrue(object.deleteProtected());
        assertFalse(object.runOnce());
    }

    /**
     * Checks that integer attributes are read correctly.
     */
    @Test
    public void test0() {
        V4Vm object = objectFromJson("{'memory':0}");
        assertEquals(BigInteger.ZERO, object.memory());
    }

    /**
     * Checks that integer attributes are read correctly.
     */
    @Test
    public void test1() {
        V4Vm object = objectFromJson("{'memory':1}");
        assertEquals(BigInteger.ONE, object.memory());
    }

    /**
     * Checks that negative integer values are read correctly.
     */
    @Test
    public void testMinus1() {
        V4Vm object = objectFromJson("{'memory':-1}");
        assertEquals(BigInteger.ONE.negate(), object.memory());
    }

    /**
     * Checks that long integers are read correctly.
     */
    @Test
    public void testLong() {
        V4Vm object = objectFromJson("{'memory':21474836470}");
        assertEquals(BigInteger.TEN.multiply(BigInteger.valueOf(Integer.MAX_VALUE)), object.memory());
    }

    /**
     * Checks that very long integers are read correctly.
     */
    @Test
    public void testVeryLong() {
        V4Vm object = objectFromJson("{'memory':92233720368547758070}");
        assertEquals(BigInteger.TEN.multiply(BigInteger.valueOf(Long.MAX_VALUE)), object.memory());
    }

    /**
     * Checks that nested elements are read correctly.
     */
    @Test
    public void testNested() {
        V4Vm object = objectFromJson("{'cpu':{'mode':'mymode'}}");
        assertNotNull(object.cpu());
        assertEquals("mymode", object.cpu().mode());
    }

    @Test
    public void testMultipleNested() {
        V4Vm object = objectFromJson(
            "{'disks':[" +
                "{'id':'123','alias':'disk1'}," +
                "{'id':'456','alias':'disk2'}" +
            "]}"
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
        V4Vm object = objectFromJson("{'creation_time':'2016-01-17T17:18:23.123+01:00'}");
        java.util.TimeZone tz = java.util.TimeZone.getTimeZone("UTC+1");
        Calendar calendar = Calendar.getInstance(tz);
        calendar.clear();
        calendar.set(2015, 12, 17, 16, 18, 23);
        calendar.set(Calendar.MILLISECOND, 123);
        Date date = calendar.getTime();
        assertEquals(date, object.creationTime());
    }

    /**
     * Checks that lower case enums are read correctly.
     */
    @Test
    public void testLowerCaseEnum() {
        V4Vm object = objectFromJson("{'type':'desktop'}");
        assertEquals(V4VmType.DESKTOP, object.type());
    }

    /**
     * Checks that upper case enums are read correctly.
     */
    @Test
    public void testUpperCaseEnum() {
        V4Vm object = objectFromJson("{'type':'DESKTOP'}");
        assertEquals(V4VmType.DESKTOP, object.type());
    }

    /**
     * Checks that mixed case enums are read correctly.
     */
    @Test
    public void testMixedCaseEnum() {
        V4Vm object = objectFromJson("{'type':'DeskTop'}");
        assertEquals(V4VmType.DESKTOP, object.type());
    }

    /**
     * Checks that list of enums are read correctly.
     */
    @Test
    public void testEnumReadMany() {
        V4Vm object = objectFromJson("{'display_types': {'display_type': ['spice', 'VNC']}}");
        List<V4VmDisplayType> expected = Arrays.asList(V4VmDisplayType.SPICE, V4VmDisplayType.VNC);
        assertEquals(expected, object.displayTypes());
    }

    /**
     * Checks that empty lists of objects are read correctly.
     */
    @Test
    public void testEmptyList() {
        List<V4Vm> list = listFromJson("[]");
        assertNotNull(list);
        assertTrue(list.isEmpty());
    }

    /**
     * Checks that lists with one object are read correctly.
     */
    @Test
    public void testListWithOneObject() {
        List<V4Vm> list = listFromJson("[{}]");
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
        List<V4Vm> list = listFromJson("[{},{}]");
        assertNotNull(list);
        assertEquals(2, list.size());
        V4Vm item1 = list.get(0);
        assertNotNull(item1);
        V4Vm item2 = list.get(1);
        assertNotNull(item2);
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
            "{'disks':[" +
                "{'id':'123','alias':'disk1'}," +
                "{'id':'456','alias':'disk2'}" +
            "]}";

        // Replace single quotes with double quotes:
        String json = text.replace('\'', '\"');

        // Prepare the pipe that will generate the input for the JSON parser:
        PipedReader in = new PipedReader();
        PipedWriter out = new PipedWriter();
        in.connect(out);

        // Create a thread that writes characters to the pipe:
        Thread thread = new Thread(() -> {
            try {
                out.write("[");
                for (int i = 0; i < 1_000_000; i++) {
                    if (i > 0) {
                        out.write(",");
                    }
                    out.write(json);
                }
                out.write("]");
                out.close();
            }
            catch (IOException ignore) {
            }
        });

        // Start the writer:
        thread.start();

        // Start the reader:
        long before = System.currentTimeMillis();
        try (JsonReader reader = new JsonReader(in)) {
            Iterator<V4Vm> iterator = V4JsonVmReader.iterateMany(reader);
            while (iterator.hasNext()) {
                iterator.next();
            }
        }
        long after = System.currentTimeMillis();
        long elapsed = after - before;

        // Wait for the writer thread:
        thread.join();

        // Check if it took less than a minute (it should take much more less, approx 5, but lets be
        // conservative:
        assertTrue(elapsed < 60_000_000);
    }

    /**
     * Checks that iterators throw {@link NoSuchElementException} when there are no more elements to read.
     */
    @Test(expected = NoSuchElementException.class)
    public void testThrowsNoSuchElement() {
        try (StringReader buffer = new StringReader("[]"); JsonReader reader = new JsonReader(buffer)) {
            Iterator<V4Vm> iterator = V4JsonVmReader.iterateMany(reader);
            iterator.hasNext();
            iterator.next();
        }
    }

    /**
     * Converts the given JSON text to an object. Single quotes in the JSON text are replaced by double quotes before
     * performing the conversion, to simplify writing and reading the strings used by the texts.
     *
     * @param text the JSON text
     * @return the object
     */
    private V4Vm objectFromJson(String text) {
        // Replace single quotes with double quotes:
        text = text.replace('\'', '\"');

        // Perform the conversion:
        StringReader buffer = new StringReader(text);
        try (JsonReader reader = new JsonReader(buffer)) {
            return V4JsonVmReader.readOne(reader);
        }
    }

    /**
     * Converts the given JSON to a list of objects.
     *
     * @param text the JSON text
     * @return the list
     */
    private List<V4Vm> listFromJson(String text) {
        // Replace single quotes with double quotes:
        text = text.replace('\'', '\"');

        // Perform the conversion:
        StringReader buffer = new StringReader(text);
        try (JsonReader reader = new JsonReader(buffer)) {
            return V4JsonVmReader.readMany(reader);
        }
    }
}
