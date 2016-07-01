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

import static java.util.stream.Collectors.joining;

import java.io.File;
import java.io.IOException;
import javax.enterprise.context.ApplicationScoped;

import org.ovirt.api.metamodel.concepts.Annotation;
import org.ovirt.api.metamodel.concepts.AnnotationParameter;
import org.ovirt.api.metamodel.concepts.Attribute;
import org.ovirt.api.metamodel.concepts.Concept;
import org.ovirt.api.metamodel.concepts.EnumType;
import org.ovirt.api.metamodel.concepts.Link;
import org.ovirt.api.metamodel.concepts.Method;
import org.ovirt.api.metamodel.concepts.Model;
import org.ovirt.api.metamodel.concepts.Name;
import org.ovirt.api.metamodel.concepts.NameParser;
import org.ovirt.api.metamodel.concepts.Service;
import org.ovirt.api.metamodel.concepts.StructMember;
import org.ovirt.api.metamodel.concepts.StructType;
import org.ovirt.api.metamodel.concepts.Type;

/**
 * This class takes a model and generates a report in CSV format of the status of the documentation.
 */
@ApplicationScoped
public class DocReportGenerator {
    // Names of the columns:
    private static final Name AREA = NameParser.parseUsingCase("Area");
    private static final Name AUTHOR = NameParser.parseUsingCase("Author");
    private static final Name DATE = NameParser.parseUsingCase("Date");
    private static final Name DOCUMENTS = NameParser.parseUsingCase("Documents");
    private static final Name ITEMS = NameParser.parseUsingCase("Items");
    private static final Name KIND = NameParser.parseUsingCase("Kind");
    private static final Name NAME = NameParser.parseUsingCase("Name");
    private static final Name STATUS = NameParser.parseUsingCase("Status");

    // The file were the output will be generated:
    private File outFile;

    // The buffer used to generate CSV file:
    private CsvBuffer csvBuffer;

    /**
     * Set the file were the output will be generated.
     */
    public void setOutFile(File newOutFile) {
        outFile = newOutFile;
    }

    public void generate(Model model) {
        csvBuffer = new CsvBuffer();
        csvBuffer.setFile(outFile);
        generateReport(model);
        try {
            csvBuffer.write();
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void generateReport(Model model) {
        // Headers:
        csvBuffer.setHeaders(
            KIND.toString(),
            NAME.toString(),
            ITEMS.toString(),
            DOCUMENTS.toString(),
            AREA.toString(),
            AUTHOR.toString(),
            DATE.toString(),
            STATUS.toString()
        );

        // Rows for types and services:
        model.types().sorted().forEach(this::generateRows);
        model.services().sorted().forEach(this::generateRows);
    }

    private void generateRows(Concept concept) {
        csvBuffer.addRow(
            getKind(concept),
            getName(concept),
            String.valueOf(countItems(concept)),
            String.valueOf(countDocuments(concept)),
            getAnnotation(concept, AREA),
            getAnnotation(concept, AUTHOR),
            getAnnotation(concept, DATE),
            getAnnotation(concept, STATUS)
        );
        if (concept instanceof StructType) {
            StructType type = (StructType) concept;
            type.attributes().sorted().forEach(this::generateRows);
        }
        else if (concept instanceof Service) {
            Service service = (Service) concept;
            service.methods().sorted().forEach(this::generateRows);
        }
    }

    private String getKind(Concept concept) {
        if (concept instanceof Type) {
            return "type";
        }
        if (concept instanceof Attribute) {
            return "attribute";
        }
        if (concept instanceof Link) {
            return "attribute";
        }
        if (concept instanceof Service) {
            return "service";
        }
        if (concept instanceof Method) {
            return "method";
        }
        return "";
    }

    private String getName(Concept concept) {
        String name = concept.getName().toString();
        String parent = null;
        if (concept instanceof StructMember) {
            StructMember member = (StructMember) concept;
            parent = member.getDeclaringType().getName().toString();
        }
        else if (concept instanceof Method) {
            Method method = (Method) concept;
            parent = method.getDeclaringService().getName().toString();
        }
        if (parent != null) {
            name = parent + "/" + name;
        }
        return name;
    }

    /**
     * Counts the total items that can be potentially documented in a concept. For example, in a struct type with two
     * attributes there are three items: the type itself and the two attributes.
     */
    private int countItems(Concept concept) {
        int count = 1;
        if (concept instanceof StructType) {
            StructType type = (StructType) concept;
            count += type.attributes().mapToInt(this::countItems).sum();
            count += type.links().mapToInt(this::countItems).sum();
        }
        else if (concept instanceof EnumType) {
            EnumType type = (EnumType) concept;
            count += type.values().mapToInt(this::countItems).sum();
        }
        else if (concept instanceof Service) {
            Service service = (Service) concept;
            count += service.methods().mapToInt(this::countItems).sum();
            count += service.locators().mapToInt(this::countItems).sum();
        }
        else if (concept instanceof Method) {
            Method method = (Method) concept;
            count += method.parameters().mapToInt(this::countItems).sum();
        }
        return count;
    }

    /**
     * Counts the number of items in a concept tha are documented.
     */
    private int countDocuments(Concept concept) {
        int count = 0;
        if (concept.getDoc() != null) {
            count++;
        }
        if (concept instanceof StructType) {
            StructType type = (StructType) concept;
            count += type.attributes().mapToInt(this::countDocuments).sum();
            count += type.links().mapToInt(this::countDocuments).sum();
        }
        else if (concept instanceof EnumType) {
            EnumType type = (EnumType) concept;
            count += type.values().mapToInt(this::countDocuments).sum();
        }
        else if (concept instanceof Service) {
            Service service = (Service) concept;
            count += service.methods().mapToInt(this::countDocuments).sum();
            count += service.locators().mapToInt(this::countDocuments).sum();
        }
        else if (concept instanceof Method) {
            Method method = (Method) concept;
            count += method.parameters().mapToInt(this::countDocuments).sum();
        }
        return count;
    }

    private String getAnnotation(Concept concept, Name name) {
        Annotation annotation = concept.getAnnotation(name);
        if (annotation != null) {
            return annotation.parameters()
                .flatMap(AnnotationParameter::values)
                .collect(joining(", "));
        }
        return "";
    }
}

