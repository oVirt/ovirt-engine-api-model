/*
Copyright (c) 2016 Red Hat, Inc.

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

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.asciidoctor.Attributes;

/**
 * This class stores the configuration used to generate AsciiDoc text.
 */
@ApplicationScoped
public class AsciiDocConfiguration {
    private Attributes attributes;
    private String separator;

    @PostConstruct
    private void init() {
        // Set the default attributes:
        attributes = new Attributes();
        attributes.setSourceHighlighter("highlightjs");

        // Set the default separator:
        separator = "/";
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttribute(String name, String value) {
        attributes.setAttribute(name, value);
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String newSeparator) {
        separator = newSeparator;
    }
}

