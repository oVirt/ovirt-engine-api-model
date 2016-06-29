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

package org.ovirt.api.metamodel.runtime.util;

import java.util.ArrayList;
import java.util.List;

/**
 * This is helper class so we can support parsing 'link' element.
 * This class stores 'href' attribute. Which we need to store, so
 * we can properly support 'follow link' method.
 */
public class ArrayListWithHref<E> extends ArrayList<E> implements ListWithHref<E> {

    private String href;

    public ArrayListWithHref() {
        super();
    }

    public ArrayListWithHref(List<E> list) {
        super(list);
        if (list instanceof ListWithHref) {
            this.href = ((ListWithHref) list).href();
        }
    }

    @Override
    public String href() {
        return href;
    }

    @Override
    public void href(String href) {
        this.href = href;
    }
}
