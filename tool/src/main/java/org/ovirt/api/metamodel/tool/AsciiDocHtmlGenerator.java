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
import javax.inject.Named;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.Attributes;
import org.asciidoctor.Options;

/**
 * This class translates model documentation into HTML, assuming that it is formatted using AsciiDoc.
 */
@Named("asciidoc")
public class AsciiDocHtmlGenerator implements HtmlGenerator {
    private Attributes attributes;
    private Options options;
    private Asciidoctor doctor;

    @PostConstruct
    public void init() {
        attributes = new Attributes();
        attributes.setSourceHighlighter("highlightjs");
        options = new Options();
        options.setAttributes(attributes);
        doctor = Asciidoctor.Factory.create();
    }

    public String toHtml(String text) {
        return doctor.convert(text, options);
    }
}

