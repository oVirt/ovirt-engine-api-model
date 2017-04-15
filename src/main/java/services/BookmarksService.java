/*
Copyright (c) 2015 Red Hat, Inc.

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

package services;

import annotations.Area;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Bookmark;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

/**
 * A service to manage bookmarks.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface BookmarksService {
    /**
     * Adding a new bookmark.
     *
     * Example of adding a bookmark:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/bookmarks
     * ----
     *
     * [source,xml]
     * ----
     * <bookmark>
     *   <name>new_example_vm</name>
     *   <value>vm: name=new_example*</value>
     * </bookmark>
     * ----
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(bookmark().name());
            mandatory(bookmark().value());
        }
        /**
         * The added bookmark.
         *
         * @author Oved Ourfali <oourfali@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Bookmark bookmark();
    }

    /**
     * Listing all the available bookmarks.
     *
     * Example of listing bookmarks:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/bookmarks
     * ----
     *
     * [source,xml]
     * ----
     * <bookmarks>
     *   <bookmark href="/ovirt-engine/api/bookmarks/123" id="123">
     *     <name>database</name>
     *     <value>vm: name=database*</value>
     *   </bookmark>
     *   <bookmark href="/ovirt-engine/api/bookmarks/456" id="456">
     *     <name>example</name>
     *     <value>vm: name=example*</value>
     *   </bookmark>
     * </bookmarks>
     * ----
     *
     * The order of the returned bookmarks isn't guaranteed.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        /**
         * The list of available bookmarks.
         *
         * @author Oved Ourfali <oourfali@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Bookmark[] bookmarks();

        /**
         * Sets the maximum number of bookmarks to return. If not specified all the bookmarks are returned.
         */
        @In Integer max();
    }

    /**
     * A reference to the service managing a specific bookmark.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service BookmarkService bookmark(String id);
}
