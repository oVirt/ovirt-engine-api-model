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
import static org.ovirt.engine.api.builders.V4Builders.vm;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.output.NullOutputStream;
import org.junit.Ignore;
import org.junit.Test;
import org.ovirt.api.metamodel.runtime.json.JsonWriter;
import org.ovirt.engine.api.json.V4JsonVmWriter;
import org.ovirt.engine.api.types.V4Vm;

/**
 * Tests for the classes that convert V4 objects to JSON. Note that the tests are centered around an specific class,
 * the {@code Vm} class, as it is very representative and all of them are automatically generated, so what works/fails
 * with this class will also work/fail with the others.
 */
public class JsonWriterTest {
    /**
     * Checks that writing an empty virtual machine produces the expected JSON.
     */
    @Test
    public void testEmpty() {
        V4Vm object = vm().build();
        assertEquals(
            "{}",
            objectToJson(object)
        );
    }

    /**
     * Checks that the {@code id} attribute is written as correctly.
     */
    @Test
    public void testId() {
        V4Vm object = vm().id("123").build();
        assertEquals(
            "{'id':'123'}",
            objectToJson(object)
        );
    }

    /**
     * Checks that an empty attribute is written correctly.
     */
    @Test
    public void testEmptyId() {
        V4Vm object = vm().id("").build();
        assertEquals(
            "{'id':''}",
            objectToJson(object)
        );
    }

    /**
     * Checks that a null attribute is ignored.
     */
    @Test
    public void testNullIdIgnored() {
        V4Vm object = vm().id(null).build();
        assertEquals(
            "{}",
            objectToJson(object)
        );
    }

    /**
     * Checks that the {@code name} attribute is written correctly.
     */
    @Test
    public void testName() {
        V4Vm object = vm().name("myvm").build();
        assertEquals(
            "{'name':'myvm'}",
            objectToJson(object)
        );
    }

    /**
     * Checks that an empty name is written correctly.
     */
    @Test
    public void testEmptyName() {
        V4Vm object = vm().name("").build();
        assertEquals(
            "{'name':''}",
            objectToJson(object)
        );
    }

    /**
     * Checks that an null element is ignored.
     */
    @Test
    public void testNullName() {
        V4Vm object = vm().name(null).build();
        assertEquals(
            "{}",
            objectToJson(object)
        );
    }

    /**
     * Checks that multiple attributes are written correctly.
     */
    @Test
    public void testMultipleAttributes() {
        V4Vm object = vm()
            .name("myvm")
            .fqdn("myvm.example.com")
            .build();
        assertEquals(
            "{'fqdn':'myvm.example.com','name':'myvm'}",
            objectToJson(object)
        );
    }

    /**
     * Checks that boolean attributes are written correctly.
     */
    @Test
    public void testBooleanAttribute() {
        V4Vm object = vm()
            .deleteProtected(true)
            .runOnce(false)
            .build();
        assertEquals(
            "{" +
            "'delete_protected':true," +
            "'run_once':false" +
            "}",
            objectToJson(object)
        );
    }

    /**
     * Checks that integer attributes are written correctly.
     */
    @Test
    public void test0() {
        V4Vm object = vm().memory(0).build();
        assertEquals(
            "{'memory':0}",
            objectToJson(object)
        );
    }

    /**
     * Checks that integer attributes are written correctly.
     */
    @Test
    public void test1() {
        V4Vm object = vm().memory(1).build();
        assertEquals(
            "{'memory':1}",
            objectToJson(object)
        );
    }

    /**
     * Checks that negative integer attributes are written correctly.
     */
    @Test
    public void testMinus1() {
        V4Vm object = vm().memory(-1).build();
        assertEquals(
            "{'memory':-1}",
            objectToJson(object)
        );
    }

    /**
     * Checks that long integers ware written correct.
     */
    @Test
    public void testLong() {
        V4Vm object = vm().memory(10L * Integer.MAX_VALUE).build();
        assertEquals(
            "{'memory':21474836470}",
            objectToJson(object)
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
            "{'memory':92233720368547758070}",
            objectToJson(object)
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
            "{'creation_time':'2016-01-17T16:18:23.123Z'}",
            objectToJson(object)
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
            "{'cpu':{'mode':'mymode'}}",
            objectToJson(object)
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
            "{'disks':[" +
                "{'alias':'disk1','id':'123'}," +
                "{'alias':'disk2','id':'456'}" +
            "]}",
            objectToJson(object)
        );
    }

    /**
     * Checks that empty lists of objects are written correctly.
     */
    @Test
    public void testEmptyList() {
        List<V4Vm> list = Collections.emptyList();
        assertEquals(
            "[]",
            listToJson(list.iterator())
        );
    }

    /**
     * Checks that lists with one object are written correctly.
     */
    @Test
    public void testListWithOneObject() {
        List<V4Vm> list = Collections.singletonList(vm().build());
        assertEquals(
            "[{}]",
            listToJson(list.iterator())
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
            "[{},{}]",
            listToJson(list.iterator())
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
        try (JsonWriter writer = new JsonWriter(new NullOutputStream(), false)) {
            V4JsonVmWriter.writeMany(infinite, writer);
        }
        long after = System.currentTimeMillis();
        long elapsed = after - before;

        // Check if it took less than a minute (it should take much more less, approx 2s, but lets be
        // conservative:
        assertTrue(elapsed < 60_000_000);
    }

    /**
     * Converts the given object to JSON and returns a string containing the JSON text. Double quotes in the generated
     * JSON are replaced by single quotes to simplify writing and reading the string constants that are used in the
     * tests.
     *
     * @param object the object to convert
     * @return the JSON text with double quotes replaced by single quotes
     */
    private String objectToJson(V4Vm object) {
        // Generate the JSON:
        StringWriter buffer = new StringWriter();
        try (JsonWriter writer = new JsonWriter(buffer, false)) {
            V4JsonVmWriter.writeOne(object, writer);
        }
        String text = buffer.toString();

        // Replace double quotes with single quotes:
        text = text.replace('"', '\'');

        return text;
    }

    /**
     * Converts the given list of objects to JSON and returns a string containing the JSON text. Double quotes in the
     * generated JSON are replaced by single quotes to simplify writing and reading the string constants that are used
     * in the tests.
     *
     * @param iterator the iterator that provides the objects to convert
     * @return the JSON text with double quotes replaced by single quotes
     */
    private String listToJson(Iterator<V4Vm> iterator) {
        // Generate the JSON:
        StringWriter buffer = new StringWriter();
        try (JsonWriter writer = new JsonWriter(buffer, false)) {
            V4JsonVmWriter.writeMany(iterator, writer);
        }
        String text = buffer.toString();

        // Replace double quotes with single quotes:
        text = text.replace('"', '\'');

        return text;
    }
}
