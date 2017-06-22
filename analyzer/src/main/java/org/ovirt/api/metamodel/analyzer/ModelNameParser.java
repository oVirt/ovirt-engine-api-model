/*
Copyright (c) 2017 Red Hat, Inc.

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

package org.ovirt.api.metamodel.analyzer;

import org.ovirt.api.metamodel.concepts.Name;
import org.ovirt.api.metamodel.concepts.NameParser;

/**
 * This class contains methods useful for parsing the kind of names used in the model language.
 */
public class ModelNameParser {
    /**
     * Creates a model name from a Java name, doing any processing that is required, for example removing the prefixes
     * or suffixes that are used to avoid conflicts with Java reserved words.
     */
    public static Name parseJavaName(String text) {
        // Remove the underscore prefixes and suffixes, as they only make sense to avoid conflicts with Java reserved
        // words and they aren't needed in the model:
        while (text.startsWith("_")) {
            text = text.substring(1);
        }
        while (text.endsWith("_")) {
            text = text.substring(0, text.length() - 1);
        }

        // Once the name is clean it can be parsed:
        return NameParser.parseUsingCase(text);
    }
}
