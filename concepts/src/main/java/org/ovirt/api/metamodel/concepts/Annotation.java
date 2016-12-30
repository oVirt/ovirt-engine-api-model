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

package org.ovirt.api.metamodel.concepts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class Annotation implements Named, Serializable {
    private Name name;
    private List<AnnotationParameter> parameters = new ArrayList<>();

    /**
     * Returns the name of this annotation.
     */
    @Override
    public Name getName() {
        return name;
    }

    /**
     * Sets the name of this annotation.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Returns the list of parameters of this annotation. The returned list is a copy of the one used internally, so it
     * is safe to modify it. If you aren't going to modify the list consider using the {@link #parameters()} method
     * instead.
     */
    public List<AnnotationParameter> getParameters() {
        return new CopyOnWriteArrayList<>(parameters);
    }

    /**
     * Returns a stream that delivers the parameters of this annotation.
     */
    public Stream<AnnotationParameter> parameters() {
        return parameters.stream();
    }

    /**
     * Finds a parameter of this annotation with the given name.
     *
     * @param name the name of the parameter to find
     * @return the parameter with the given name or {@code null if no such parameter exists}
     */
    public AnnotationParameter getParameter(Name name) {
        return parameters.stream().filter(Named.named(name)).findFirst().orElse(null);
    }

    /**
     * Adds a new parameter to this annotation.
     */
    public void addParameter(AnnotationParameter newParameter) {
        parameters.add(newParameter);
    }

    /**
     * Adds a new list of parameters to this annotation.
     */
    public void addParameters(List<AnnotationParameter> newParameters) {
        parameters.addAll(newParameters);
    }

    /**
     * Generates a string representation of this annotation, usually just its name.
     */
    @Override
    public String toString() {
        return name != null? name.toString(): "";
    }

    /**
     * Checks if the given object is equal to this annotation. Only the name is taken into account in this comparison,
     * the values are ignored.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof Annotation) {
            Annotation that = (Annotation) object;
            return Objects.equals(this.name, that.name);
        }
        return false;
    }

    /**
     * Computes a hash code for this annotation. Only the name is taken into account for this computation, the values
     * are ignored.
     */
    @Override
    public int hashCode() {
        if (name != null) {
            return name.hashCode();
        }
        return super.hashCode();
    }
}
