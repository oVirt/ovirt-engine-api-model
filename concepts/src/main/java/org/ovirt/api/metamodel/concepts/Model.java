/*
Copyright (c) 2015-2016 Red Hat, Inc.

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

import static java.util.stream.Collectors.toCollection;
import static org.ovirt.api.metamodel.concepts.Named.named;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class Model {
    // The list of modules of the model:
    private List<Module> modules = new ArrayList<>();

    // The list of types of the model:
    private List<Type> types = new ArrayList<>();

    // The list of services of the model:
    private List<Service> services = new ArrayList<>();

    // The list of documents included in the model:
    private List<Document> documents = new ArrayList<>();

    // The list of points of the model. It will be calculated lazily.
    private volatile List<Point> points;

    // The root of the tree of services:
    private Service root;

    // The builtin types:
    private Type booleanType;
    private Type stringType;
    private Type integerType;
    private Type dateType;
    private Type decimalType;

    public Model() {
        // Create the anonymous module:
        Module anonymousModule = new Module();
        anonymousModule.setModel(this);
        modules.add(anonymousModule);

        // Add the builtin types:
        booleanType = new PrimitiveType();
        booleanType.setName(NameParser.parseUsingCase("Boolean"));
        booleanType.setModule(anonymousModule);
        addType(booleanType);

        stringType = new PrimitiveType();
        stringType.setName(NameParser.parseUsingCase("String"));
        stringType.setModule(anonymousModule);
        addType(stringType);

        integerType = new PrimitiveType();
        integerType.setName(NameParser.parseUsingCase("Integer"));
        integerType.setModule(anonymousModule);
        addType(integerType);

        dateType = new PrimitiveType();
        dateType.setName(NameParser.parseUsingCase("Date"));
        dateType.setModule(anonymousModule);
        addType(dateType);

        decimalType = new PrimitiveType();
        decimalType.setName(NameParser.parseUsingCase("Decimal"));
        decimalType.setModule(anonymousModule);
        addType(decimalType);
    }

    /**
     * Adds a module to the list of modules of this model.
     */
    public void addModule(Module newModule) {
        modules.add(newModule);
    }

    /**
     * Returns the list of modules of this model. The returned list is a copy of the one used internally, so it is safe
     * to modify it in any way. If you aren't going to modify the list consider using the {@link #modules()} method
     * instead.
     */
    public List<Module> getModules() {
        return new CopyOnWriteArrayList<>(modules);
    }

    /**
     * Returns a stream that delivers the modules of this model.
     */
    public Stream<Module> modules() {
        return modules.stream();
    }

    /**
     * Returns the module that has the given name, or {@code null} if there is no such module.
     */
    public Module getModule(Name name) {
        return modules.stream().filter(named(name)).findFirst().orElse(null);
    }

    /**
     * Adds a type to the list of types of this model.
     */
    public void addType(Type newType) {
        types.add(newType);
    }

    /**
     * Returns the list of types of this model. The returned list is a copy of the one used internally, so it is safe to
     * modify it in any way. If you aren't going to modify the list consider using the {@link #types()} method instead.
     */
    public List<Type> getTypes() {
        return new CopyOnWriteArrayList<>(types);
    }

    /**
     * Returns a stream that delivers the types of this model.
     */
    public Stream<Type> types() {
        return types.stream();
    }

    /**
     * Returns the type that has the given name, or {@code null} if there is no such type.
     */
    public Type getType(Name name) {
        return types.stream().filter(named(name)).findFirst().orElse(null);
    }

    /**
     * Adds a service to the list of services of this model.
     */
    public void addService(Service newService) {
        services.add(newService);
    }

    /**
     * Returns the list of services of this model. The returned list is a copy of the one used internally, so it is safe
     * to modify it in any way. If you aren't going to modify the list consider using the {@link #types()} method
     * instead.
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     * Returns a stream that delivers the services of this model.
     */
    public Stream<Service> services() {
        return services.stream();
    }

    /**
     * Returns a stream that delivers the services of this model.
     */
    public Service getService(Name name) {
        return services.stream().filter(named(name)).findFirst().orElse(null);
    }

    /**
     * Returns the list of documents of this model. The returned list is a copy of the one used internally, so it is
     * safe to modify it in any way. If you aren't going to modify the list consider using the {@link #documents()}
     * method instead.
     */
    public List<Document> getDocuments() {
        return documents;
    }

    /**
     * Returns a stream that delivers the documents of this model.
     */
    public Stream<Document> documents() {
        return documents.stream();
    }

    /**
     * Adds a document to the list of documents of this model.
     */
    public void addDocument(Document newDocument) {
        documents.add(newDocument);
    }

    /**
     * Returns the root of the services tree of this model.
     */
    public Service getRoot() {
        return root;
    }

    /**
     * Sets the root of the services tree of this model.
     */
    public void setRoot(Service newRoot) {
        root = newRoot;
    }

    /**
     * Returns a reference to the built-in string type of this model.
     */
    public Type getStringType() {
        return stringType;
    }

    /**
     * Returns a reference to the built-in integer type of this model.
     */
    public Type getIntegerType() {
        return integerType;
    }

    /**
     * Returns a reference to the built-in date type of this model.
     */
    public Type getDateType() {
        return dateType;
    }

    /**
     * Returns a reference to the built-in boolean type of this model.
     */
    public Type getBooleanType() {
        return booleanType;
    }

    /**
     * Returns a reference to the built-in decimal type of this model.
     */
    public Type getDecimalType() {
        return decimalType;
    }

    /**
     * Returns the list of points of this model. A point is a pair containing a list of locators and a method. If the
     * list of locators is invoked in sequence, starting from the root service, the result is a service that contains
     * the method. The returned list is a copy of the one used internally, so it is safe to modify it. If you don't
     * plan to modify it consider using the {@link #points()} method instead.
     */
    public List<Point> getPoints() {
        if (points == null) {
            synchronized (this) {
                if (points == null) {
                    points = calculatePoints();
                }
            }
        }
        return points;
    }

    /**
     * Returns an stream that delivers the points of this model.
     */
    public Stream<Point> points() {
        if (points == null) {
            synchronized (this) {
                if (points == null) {
                    points = calculatePoints();
                }
            }
        }
        return points.stream();
    }

    private List<Point> calculatePoints() {
        // First we need to find all the possible paths:
        List<List<Locator>> paths = new ArrayList<>();

        // We will start the walk with a simple path for each locator of the root service:
        Deque<List<Locator>> pending = root.locators()
            .map(Collections::singletonList)
            .collect(toCollection(ArrayDeque::new));

        // Extract paths from the pending queue and expand them, till the queue is empty:
        while (!pending.isEmpty()) {
            List<Locator> current = pending.removeFirst();
            paths.add(current);
            int size = current.size();
            Service service = current.get(size - 1).getService();
            service.locators().forEach(locator -> {
                List<Locator> next = new ArrayList<>(current);
                next.add(locator);
                pending.addLast(next);
            });
        }

        // Now, for each path, we need to create a point for each method:
        List<Point> points = new ArrayList<>(paths.size());
        paths.forEach(path -> {
            int size = path.size();
            if (size > 0) {
                Service service = path.get(size -1).getService();
                service.methods().forEach(method -> {
                    Point point = new Point();
                    point.setPath(path);
                    point.setMethod(method);
                    points.add(point);
                });
            }
        });

        return points;
    }
}

