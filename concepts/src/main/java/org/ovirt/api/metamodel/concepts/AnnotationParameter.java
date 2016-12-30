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

public class AnnotationParameter implements Named, Serializable {
    private Name name;
    private List<String> values = new ArrayList<>();

    /**
     * Returns the name of this parameter.
     */
    @Override
    public Name getName() {
        return name;
    }

    /**
     * Sets the name of this parameter.
     */
    public void setName(Name name) {
        this.name = name;
    }

    /**
     * Returns the list of values of this parameter. The returned list is a copy of the one used internally, so it
     * is safe to modify it. If you aren't going to modify the list consider using the {@link #values()} method
     * instead.
     */
    public List<String> getValues() {
        return new CopyOnWriteArrayList<>(values);
    }

    /**
     * Returns a stream that delivers the valeus of this parameter.
     */
    public Stream<String> values() {
        return values.stream();
    }

    /**
     * Adds a new value to this parameter.
     */
    public void addValue(String newValue) {
        values.add(newValue);
    }

    /**
     * Adds a new list of values to this parameter.
     */
    public void addValues(List<String> newValues) {
        values.addAll(newValues);
    }

    /**
     * Generates a string representation of this annotation, usually just its name.
     */
    @Override
    public String toString() {
        return name != null? name.toString(): "";
    }

    /**
     * Checks if the given object is equal to this parameter. Only the name is taken into account in this comparison,
     * the value is ignored.
     */
    @Override
    public boolean equals(Object object) {
        if (object instanceof AnnotationParameter) {
            AnnotationParameter that = (AnnotationParameter) object;
            return Objects.equals(this.name, that.name);
        }
        return false;
    }

    /**
     * Computes a hash code for this annotation parameter. Only the name is taken into account for this computation, the
     * value is ignored.
     */
    @Override
    public int hashCode() {
        if (name != null) {
            return name.hashCode();
        }
        return super.hashCode();
    }
}

