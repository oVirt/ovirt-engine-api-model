/*
Copyright (c) 2015 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Bookmark;

/**
 * A service to manage a bookmark.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface BookmarkService {
    /**
     * Get a bookmark.
     *
     * An example for getting a bookmark:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/bookmarks/123
     * ----
     *
     * [source,xml]
     * ----
     * <bookmark href="/ovirt-engine/api/bookmarks/123" id="123">
     *   <name>example_vm</name>
     *   <value>vm: name=example*</value>
     * </bookmark>
     * ----
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        /**
         * The requested bookmark.
         *
         * @author Oved Ourfali <oourfali@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Bookmark bookmark();
    }

    /**
     * Update a bookmark.
     *
     * An example for updating a bookmark:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/bookmarks/123
     * ----
     *
     * With the request body:
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
    interface Update {
        /**
         * The updated bookmark.
         *
         * @author Oved Ourfali <oourfali@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Bookmark bookmark();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Remove a bookmark.
     *
     * An example for removing a bookmark:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/bookmarks/123
     * ----
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
