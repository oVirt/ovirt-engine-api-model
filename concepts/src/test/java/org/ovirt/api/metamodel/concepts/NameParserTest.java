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

package org.ovirt.api.metamodel.concepts;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NameParserTest {
    @Test
    public void testTwoWords() {
        Name name = NameParser.parseUsingCase("HelloWorld");
        assertEquals("hello_world", name.toString());
    }

    @Test
    public void testOneAcronym() {
        Name name = NameParser.parseUsingCase("Scsi");
        assertEquals("scsi", name.toString());
    }

    @Test
    public void testOneAcronymAndOneWord() {
        Name name = NameParser.parseUsingCase("ScsiDisk");
        assertEquals("scsi_disk", name.toString());
    }
}
