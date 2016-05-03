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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

/**
 * This class represents a concept of the metamodel.
 */
public abstract class Concept implements Named {
    private Name name;
    private String doc;
    private String source;
    private List<Annotation> annotations = new ArrayList<>();

    /**
     * Returns the name of this concept.
     */
    @Override
    public Name getName() {
        return name;
    }

    /**
     * Sets the name of this concept.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Returns the documentation of this concet, which may be {@code null} if the concept isn't documented.
     */
    public String getDoc() {
        return doc;
    }

    /**
     * Sets the documentation of this concept.
     */
    public void setDoc(String newDoc) {
        doc = newDoc;
    }

    /**
     * Returns the source code of this concept. Usually the source code will be the Java text that represents the
     * concept, but it may be {@code null} if the concept has been created manually and not as the result of
     * associated, for example if it has been created manually.
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the source code of this concept.
     */
    public void setSource(String newSource) {
        source = newSource;
    }

    /**
     * Returns the list of annotations that are applied to this concept. The returned list is a copy of the one used
     * internally, so it is safe to modify it in any way. If you aren't going to modify the list consider using the
     * {@link #annotations()} method instead.
     */
    public List<Annotation> getAnnotations() {
        return new CopyOnWriteArrayList<>(annotations);
    }

    /**
     * Returns a stream that delivers the annotations that are applied to this concept.
     */
    public Stream<Annotation> annotations() {
        return annotations.stream();
    }

    /**
     * Returns the annotations with given name that are applied to this concept, or {@code null} if there is no such
     * annotation.
     */
    public Annotation getAnnotation(Name name) {
        return annotations.stream().filter(Named.named(name)).findFirst().orElse(null);
    }

    /**
     * Applies an annotation to this concept.
     */
    public void addAnnotation(Annotation newAnnotation) {
        annotations.add(newAnnotation);
    }

    /**
     * Applies a list of annotations to this concept.
     */
    public void addAnnotations(List<Annotation> newAnnotations) {
        annotations.addAll(newAnnotations);
    }

    /**
     * Generates a string representation of this concept, usually just its name.
     */
    @Override
    public String toString() {
        return name != null? name.toString(): "";
    }

    /**
     * Checks if the given object is equal to this concept. Only the name is taken into account in this comparison, the
     * documentation and the source are ignored.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Concept) {
            Concept that = (Concept) object;
            return Objects.equals(this.name, that.name);
        }
        return false;
    }

    /**
     * Computes a hash code for this concept. Only the name is taken into account for this computation, the
     * documentation and the source are ignored.
     */
    @Override
    public int hashCode() {
        if (name != null) {
            return name.hashCode();
        }
        return super.hashCode();
    }
}

