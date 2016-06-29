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

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * This class is an copy of java.util.Collections.UnmodifiableList class. Since this class can't be used outside
 * of its own package. We need to have copy of this class, because we need to store 'href' attribute to properly
 * support 'follow link' method.
 */
public final class UnmodifiableListWithHref<E> implements Collection<E>, ListWithHref<E>, Serializable {

    private static final long serialVersionUID = -283361236065247728L;
    final ListWithHref<? extends E> list;
    private String href;

    @Override
    public String href() {
        return href;
    }

    @Override
    public void href(String href) {
        this.href = href;
    }

    public UnmodifiableListWithHref(ListWithHref<? extends E> list) {
        if (list == null) {
            throw new NullPointerException();
        }
        this.list = list;
        this.href = list.href();
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean contains(Object o) {
        return list.contains(o);
    }

    public Object[] toArray() {
        return list.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    public String toString() {
        return list.toString();
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private final Iterator<? extends E> i = list.iterator();

            public boolean hasNext() {
                return i.hasNext();
            }

            public E next() {
                return i.next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection<?> coll) {
        return list.containsAll(coll);
    }

    public boolean addAll(Collection<? extends E> coll) {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection<?> coll) {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection<?> coll) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean equals(Object o) {
        return o == this || list.equals(o);
    }

    public int hashCode() {
        return list.hashCode();
    }

    public E get(int index) {
        return list.get(index);
    }

    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }

    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    public ListIterator<E> listIterator(final int index) {
        return new ListIterator<E>() {
            private final ListIterator<? extends E> i = list.listIterator(index);

            public boolean hasNext() {
                return i.hasNext();
            }

            public E next() {
                return i.next();
            }

            public boolean hasPrevious() {
                return i.hasPrevious();
            }

            public E previous() {
                return i.previous();
            }

            public int nextIndex() {
                return i.nextIndex();
            }

            public int previousIndex() {
                return i.previousIndex();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }

            public void set(E e) {
                throw new UnsupportedOperationException();
            }

            public void add(E e) {
                throw new UnsupportedOperationException();
            }
        };
    }

    public ListWithHref<E> subList(int fromIndex, int toIndex) {
        return new UnmodifiableListWithHref<>((ListWithHref<E>) list.subList(fromIndex, toIndex));
    }
}
