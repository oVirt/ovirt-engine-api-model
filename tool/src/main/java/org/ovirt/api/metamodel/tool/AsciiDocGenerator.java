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

import static java.util.Comparator.comparing;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.ovirt.api.metamodel.concepts.Attribute;
import org.ovirt.api.metamodel.concepts.Concept;
import org.ovirt.api.metamodel.concepts.Document;
import org.ovirt.api.metamodel.concepts.EnumType;
import org.ovirt.api.metamodel.concepts.EnumValue;
import org.ovirt.api.metamodel.concepts.Link;
import org.ovirt.api.metamodel.concepts.ListType;
import org.ovirt.api.metamodel.concepts.Locator;
import org.ovirt.api.metamodel.concepts.Method;
import org.ovirt.api.metamodel.concepts.Model;
import org.ovirt.api.metamodel.concepts.Name;
import org.ovirt.api.metamodel.concepts.Named;
import org.ovirt.api.metamodel.concepts.Parameter;
import org.ovirt.api.metamodel.concepts.Point;
import org.ovirt.api.metamodel.concepts.PrimitiveType;
import org.ovirt.api.metamodel.concepts.Service;
import org.ovirt.api.metamodel.concepts.StructMember;
import org.ovirt.api.metamodel.concepts.StructType;
import org.ovirt.api.metamodel.concepts.Type;

/**
 * This class takes a model and generates the corresponding AsciiDoc documentation.
 */
@ApplicationScoped
public class AsciiDocGenerator {
    // The regular expression to detect internal cross references:
    private static final Pattern CROSS_REFERENCE_RE = Pattern.compile("<<(?<id>(\\w|/)+)(?<rest>,.*)??>>");

    // Reference to the object used to calculate names:
    @Inject private Names names;

    // Reference to the object that stores the AsciiDoc configuration:
    @Inject private AsciiDocConfiguration configuration;

    // The directory were the output will be generated:
    private File outDir;

    // The buffer used to generate the AsciiDoc source code:
    private AsciiDocBuffer docBuffer;

    /**
     * Set the directory were the output will be generated.
     */
    public void setOutDir(File newOutDir) {
        outDir = newOutDir;
    }

    public void generate(Model model) {
        // Generate the AsciiDoc file:
        docBuffer = new AsciiDocBuffer();
        docBuffer.setName("model");
        documentModel(model);
        try {
            docBuffer.write(outDir);
        }
        catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void documentModel(Model model) {
        // Header:
        docBuffer.addLine("= Model");
        docBuffer.addLine();

        // Copy the attributes from the configuration:
        configuration.getAttributes().map().forEach((name, value) -> {
            if (value != null) {
                docBuffer.addLine(":%1$s: %2$s", name, value);
            }
            else {
                docBuffer.addLine(":%1$s:", name);
            }
        });

        // Include all the documents, pushing titles one level down:
        docBuffer.addLine(":leveloffset: 1");
        model.documents().sorted().forEach(this::addDocument);
        docBuffer.addLine(":leveloffset: 0");

        // Requests:
        docBuffer.addId("requests");
        docBuffer.addLine("== Requests");
        docBuffer.addLine();
        docBuffer.addLine("This section enumerates all the requests that are available in the API.");
        docBuffer.addLine();
        model.points()
            .sorted(comparing(this::getSortKey))
            .forEach(this::documentRequest);
        docBuffer.addLine();

        // Services:
        docBuffer.addId("services");
        docBuffer.addLine("== Services");
        docBuffer.addLine();
        docBuffer.addLine("This section enumerates all the services that are available in the API.");
        docBuffer.addLine();
        docBuffer.addLine();
        model.services().sorted().forEach(this::documentService);

        // Types:
        docBuffer.addId("types");
        docBuffer.addLine("== Types");
        docBuffer.addLine();
        docBuffer.addLine("This section enumerates all the data types that are available in the API.");
        docBuffer.addLine();
        model.types().sorted().forEach(this::documentType);
    }

    private void addDocument(Document document) {
        docBuffer.addId(getId(document));
        docBuffer.addLine(document.getSource());
        docBuffer.addLine();
    }

    private void documentRequest(Point point) {
        Method method = point.getMethod();
        Service service = method.getDeclaringService();
        StringBuilder buffer = new StringBuilder();
        buffer.append(String.format("* <<%s,%s>> ", getId(service, method), getHttpMethod(method)));
        point.path().forEach(locator -> {
            buffer.append("/");
            String link = String.format("<<%s,%s>>", getId(locator.getService()), getUrlSegment(locator));
            buffer.append(link);
        });
        if (method.isAction()) {
            buffer.append("/");
            String link = String.format("<<%s,%s>>", getId(service), getUrlSegment(method));
            buffer.append(link);
        }
        docBuffer.addLine(buffer.toString());
    }

    private void documentService(Service service) {
        // General description:
        docBuffer.addId(getId(service));
        docBuffer.addLine("=== %s", getName(service));
        docBuffer.addLine();
        addDoc(service);

        // Table of methods:
        List<Method> methods = service.getMethods();
        if (!methods.isEmpty()) {
            docBuffer.addLine(".Methods summary (%d)", methods.size());
            docBuffer.addLine("[cols=\"20,80\"]");
            docBuffer.addLine("|===");
            docBuffer.addLine("|Name |Summary");
            docBuffer.addLine();
            methods.stream().sorted().forEach(method -> {
                docBuffer.addLine("|`%s`", getName(method));
                docBuffer.addLine("|%s", getSummary(method));
                docBuffer.addLine();
            });
            docBuffer.addLine("|===");
            docBuffer.addLine();
        }

        // Methods detail:
        methods.stream().sorted().forEach(method -> documentMethod(service, method));
    }

    private void documentMethod(Service service, Method method) {
        // General description:
        docBuffer.addId(getId(service, method));
        docBuffer.addLine("==== %s [small]#%s#", getName(method), getHttpMethod(method));
        docBuffer.addLine();
        addDoc(method);

        // Table of parameters:
        List<Parameter> parameters = method.getParameters();
        if (!parameters.isEmpty()) {
            docBuffer.addLine(".Parameters summary (%d)", parameters.size());
            docBuffer.addLine("[cols=\"15,15,10,60\"]");
            docBuffer.addLine("|===");
            docBuffer.addLine("|Name |Type |Direction |Summary");
            docBuffer.addLine();
            parameters.stream().sorted().forEach(parameter -> {
                docBuffer.addLine("|`%s`", getName(parameter));
                docBuffer.addLine("|%s", getLink(parameter.getType()));
                docBuffer.addLine("|%s", getDirection(parameter));
                docBuffer.addLine("|%s", getSummary(parameter));
                docBuffer.addLine();
            });
            docBuffer.addLine("|===");
            docBuffer.addLine();
        }

        // Detail of parameters:
        parameters.stream().sorted().forEach(parameter-> documentParameter(service, method, parameter));
    }

    private void documentParameter(Service service, Method method, Parameter parameter) {
        if (!onlyHasSummary(parameter)) {
            docBuffer.addId(getId(service, method, parameter));
            docBuffer.addLine("===== %s", getName(parameter));
            docBuffer.addLine();
            addDoc(parameter);
        }
    }

    private void documentType(Type type) {
        if (type instanceof EnumType) {
            documentEnum((EnumType) type);
        }
        else if (type instanceof StructType) {
            documentStruct((StructType) type);
        }
    }

    private void documentEnum(EnumType type) {
        // General description:
        docBuffer.addId(getId(type));
        docBuffer.addLine("=== %s [small]#enum#", getName(type));
        docBuffer.addLine();
        addDoc(type);

        // Table of values:
        List<EnumValue> values = type.getValues();
        if (!values.isEmpty()) {
            docBuffer.addLine(".Values summary (%d)", values.size());
            docBuffer.addLine("[cols=\"20,89\"]");
            docBuffer.addLine("|===");
            docBuffer.addLine("|Name |Summary");
            docBuffer.addLine();
            values.stream().sorted().forEach(value -> {
                docBuffer.addLine("|`%s`", getName(value));
                docBuffer.addLine("|%s", getSummary(value));
                docBuffer.addLine();
            });
            docBuffer.addLine("|===");
            docBuffer.addLine();
        }

        // Detail of values:
        values.stream().sorted().forEach(this::documentValue);
    }

    private void documentValue(EnumValue value) {
        if (!onlyHasSummary(value)) {
            docBuffer.addId(getId(value));
            docBuffer.addLine("==== %s", getName(value));
            docBuffer.addLine();
            addDoc(value);
        }
    }

    private void documentStruct(StructType type) {
        // General description:
        docBuffer.addId(getId(type));
        docBuffer.addLine("=== %s [small]#struct#", getName(type));
        docBuffer.addLine();
        addDoc(type);

        // Table of attributes:
        List<Attribute> attributes = type.getAttributes();
        if (!attributes.isEmpty()) {
            docBuffer.addLine(".Attributes summary (%d)", attributes.size());
            docBuffer.addLine("[cols=\"20,20,60\"]");
            docBuffer.addLine("|===");
            docBuffer.addLine("|Name |Type |Summary");
            docBuffer.addLine();
            attributes.stream().sorted().forEach(attribute -> {
                docBuffer.addLine("|`%s`", getName(attribute));
                docBuffer.addLine("|%s", getLink(attribute.getType()));
                docBuffer.addLine("|%s", getSummary(attribute));
                docBuffer.addLine();
            });
            docBuffer.addLine("|===");
            docBuffer.addLine();
        }

        // Detail of attributes:
        attributes.stream().sorted().forEach(attribute -> documentMember(type, attribute));

        // Table of links:
        List<Link> links = type.getLinks();
        if (!links.isEmpty()) {
            docBuffer.addLine(".Links summary (%d)", links.size());
            docBuffer.addLine("[cols=\"20,20,60\"]");
            docBuffer.addLine("|===");
            docBuffer.addLine("|Name |Type |Summary");
            docBuffer.addLine();
            links.stream().sorted().forEach(link -> {
                docBuffer.addLine("|`%s`", getName(link));
                docBuffer.addLine("|%s", getLink(link.getType()));
                docBuffer.addLine("|%s", getSummary(link));
                docBuffer.addLine();
            });
            docBuffer.addLine("|===");
            docBuffer.addLine();
        }

        // Detail of links:
        links.stream().sorted().forEach(link -> documentMember(type, link));
    }

    private void documentMember(StructType type, StructMember member) {
        if (!onlyHasSummary(member)) {
            docBuffer.addId(getId(type, member));
            docBuffer.addLine("==== %s", getName(member));
            docBuffer.addLine();
            addDoc(member);
        }
    }

    private String getName(Type type) {
        return names.getCapitalized(type.getName());
    }

    private String getName(StructMember member) {
        return names.getLowerJoined(member.getName(), "_");
    }

    private String getName(EnumValue value) {
        return names.getLowerJoined(value.getName(), "_");
    }

    private String getName(Service service) {
        return names.getCapitalized(service.getName());
    }

    private String getName(Method method) {
        return names.getLowerJoined(method.getName(), "");
    }

    private String getName(Parameter parameter) {
        return names.getLowerJoined(parameter.getName(), "_");
    }

    private String getSummary(Concept concept) {
        String doc = concept.getDoc();
        if (doc != null) {
            // The summary is the first sentence of the documentation, or the complete documentation if there is no dot
            // to end the first sentence.
            int index = doc.indexOf('.');
            if (index != -1) {
                return doc.substring(0, index + 1);
            }

            // Replace the forward slash with the id separator inside cross references:
            doc = fixCrossReferences(doc);

            return doc;
        }
        return "";
    }

    private String getDirection(Parameter parameter) {
        if (parameter.isIn() && parameter.isOut()) {
            return "In/Out";
        }
        if (parameter.isIn()) {
            return "In";
        }
        if (parameter.isOut()) {
            return "Out";
        }
        return "";
    }

    private String getId(Document document) {
        return joinIds("documents", getIdSegment(document));
    }

    private String getId(Type type) {
        return joinIds("types", getIdSegment(type));
    }

    private String getId(Service service) {
        return joinIds("services", getIdSegment(service));
    }

    private String getId(Type type, StructMember member) {
        String kind = member instanceof Attribute? "attributes": "links";
        return joinIds(getId(type), kind, getIdSegment(member));
    }

    private String getId(Service service, Method method) {
        return joinIds(getId(service), "methods", getIdSegment(method));
    }

    private String getId(Service service, Method method, Parameter parameter) {
        return joinIds(getId(service, method), "parameters", getIdSegment(parameter));
    }

    private String getId(EnumValue value) {
        return joinIds(getId(value.getDeclaringType()), "values", getIdSegment(value));
    }

    private String joinIds(String... ids) {
        return String.join(configuration.getSeparator(), ids);
    }

    private String getIdSegment(Named named) {
        return names.getLowerJoined(named.getName(), "_");
    }

    private String getLink(Type type) {
        String id = null;
        String text = null;
        if (type instanceof PrimitiveType || type instanceof EnumType || type instanceof StructType) {
            id = getId(type);
            text = getName(type);
        }
        else if (type instanceof ListType) {
            ListType listType = (ListType) type;
            Type elementType = listType.getElementType();
            id = getId(elementType);
            text = getName(elementType) + "[]";
        }
        StringBuilder buffer = new StringBuilder();
        buffer.append("<<");
        buffer.append(id);
        if (text != null) {
            buffer.append(",");
            buffer.append(text);
        }
        buffer.append(">>");
        return buffer.toString();
    }

    private void addDoc(Concept concept) {
        // Do nothing if the concept doesn't have documentation:
        String doc = concept.getDoc();
        if (doc == null) {
            return;
        }

        // Replace the forward slash with the id separator inside cross references:
        doc = fixCrossReferences(doc);

        // Split the documentation into lines, and add them to the buffer:
        List<String> lines = new ArrayList<>();
        Collections.addAll(lines, doc.split("\n"));
        lines.forEach(docBuffer::addLine);
        docBuffer.addLine();
    }

    private String fixCrossReferences(String doc) {
        Matcher matcher = CROSS_REFERENCE_RE.matcher(doc);
        StringBuffer buffer = new StringBuffer();
        if (matcher.find()) {
            String id = matcher.group("id");
            id = id.replace("/", configuration.getSeparator());
            String rest = matcher.group("rest");
            if (rest == null) {
                rest = "";
            }
            String replacement = "<<" + id + rest + ">>";
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * Checks if the documentation of the given document only has a summary.
     *
     * @return {@code true} if the documentation of the concept only has a summary, {@code false} otherwise
     */
    private boolean onlyHasSummary(Concept concept) {
        String doc = concept.getDoc();
        if (doc == null || doc.isEmpty()) {
            return true;
        }
        String summary = getSummary(concept);
        if (summary == null || summary.isEmpty()) {
            return true;
        }
        return Objects.equals(doc, summary);
    }

    private String getSortKey(Point point) {
        StringBuilder buffer = new StringBuilder();
        point.path().forEach(locator -> {
            buffer.append("/");
            buffer.append(getUrlSegment(locator));
        });
        Method method = point.getMethod();
        if (method.isAction()) {
            buffer.append("/");
            buffer.append(getUrlSegment(method));
        }
        return buffer.toString();
    }

    private String getUrlSegment(Locator locator) {
        Optional<Parameter> parameter = locator.parameters().findFirst();
        String segment = names.getLowerJoined(locator.getName(), "");
        if (parameter.isPresent()) {
            segment = String.format("{%s:%s}", segment, names.getLowerJoined(parameter.get().getName(), ""));
        }
        return segment;
    }

    private String getUrlSegment(Method method) {
        return names.getLowerJoined(method.getName(), "");
    }

    private String getHttpMethod(Method method) {
        Name name = method.getName();
        switch (name.toString().toLowerCase()) {
        case "get":
        case "list":
            return "GET";
        case "update":
            return "PUT";
        case "remove":
            return "DELETE";
        default:
            return "POST";
        }
    }
}

