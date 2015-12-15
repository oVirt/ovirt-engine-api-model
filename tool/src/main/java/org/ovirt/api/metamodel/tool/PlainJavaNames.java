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

package org.ovirt.api.metamodel.tool;

import static java.util.stream.Collectors.joining;

import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import org.ovirt.api.metamodel.concepts.Name;

/**
 * This class contains the rules used to calculate the names of generated Java concepts, using the traditional Java
 * mapping rules.
 */
@ApplicationScoped
@Style("plain")
@Default
public class PlainJavaNames implements JavaNames {
    // Reference to the object used to do calculations with words:
    @Inject private Words words;

    // We need the Java reserved words in order to avoid producing names that aren't legal in Java:
    @Inject
    @ReservedWords(language = "java")
    private Set<String> javaReservedWords;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getJavaClassStyleName(Name name) {
        return name.words().map(words::capitalize).collect(joining());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getJavaMemberStyleName(Name name) {
        StringBuilder buffer = new StringBuilder();
        name.words().findFirst().map(String::toLowerCase).ifPresent(buffer::append);
        name.words().skip(1).map(words::capitalize).forEach(buffer::append);
        String result = buffer.toString();
        if (javaReservedWords.contains(result)) {
            result = result + "_";
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getJavaPropertyStyleName(Name name) {
        return getJavaClassStyleName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getJavaConstantStyleName(Name name) {
        return name.words().map(String::toUpperCase).collect(joining("_"));
    }
}

