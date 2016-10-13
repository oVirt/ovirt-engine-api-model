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

/**
 * This class represents a document included in the model.
 */
public class Document extends Concept {
    // Indicates if this document is an appendix:
    private boolean appendix;

    public boolean isAppendix() {
        return appendix;
    }

    public void setAppendix(boolean appendix) {
        this.appendix = appendix;
    }
}
