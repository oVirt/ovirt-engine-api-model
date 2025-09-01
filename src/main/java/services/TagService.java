/*
The oVirt Project - oVirt Engine Api Model

Copyright oVirt Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the Apache License, Version 2.0 is included with the program
in the file ASL2.
*/

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Tag;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * A service to manage a specific tag in the system.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface TagService {

    /**
     * Gets the information about the tag.
     *
     * For example to retrieve the information about the tag with the id `123` send a request like this:
     *
     * ```http
     * GET /ovirt-engine/api/tags/123 HTTP/1.1
     * ```
     *
     * ```xml
     * <tag href="/ovirt-engine/api/tags/123" id="123">
     *   <name>root</name>
     *   <description>root</description>
     * </tag>
     * ```
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        /**
         * The tag.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Tag tag();
    }

    /**
     * Updates the tag entity.
     *
     * For example to update parent tag to tag with id `456` of the tag with id `123` send a request like this:
     *
     * ```http
     * PUT /ovirt-engine/api/tags/123 HTTP/1.1
     * ```
     *
     * With request body like:
     *
     * ```xml
     * <tag>
     *   <parent id="456"/>
     * </tag>
     * ```
     *
     * You may also specify a tag name instead of id. For example to update parent tag to tag with name `mytag`
     * of the tag with id `123` send a request like this:
     *
     * ```xml
     * <tag>
     *   <parent>
     *     <name>mytag</name>
     *   </parent>
     * </tag>
     * ```
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(tag().description());
            optional(tag().name());
            or(optional(tag().parent().id()), optional(tag().parent().name()));
        }
        /**
         * The updated tag.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Tag tag();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes the tag from the system.
     *
     * For example to remove the tag with id `123` send a request like this:
     *
     * ```http
     * DELETE /ovirt-engine/api/tags/123 HTTP/1.1
     * ```
     *
     * @author Ondra Machacek <omachace@redhat.com>
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
