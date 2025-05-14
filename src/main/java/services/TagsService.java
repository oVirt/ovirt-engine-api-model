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

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * Represents a service to manage collection of the tags in the system.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface TagsService {
    /**
     * Add a new tag to the system.
     *
     * For example, to add new tag with name `mytag` to the system send a request like this:
     *
     * ```http
     * POST /ovirt-engine/api/tags
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <tag>
     *   <name>mytag</name>
     * </tag>
     * ```
     *
     * NOTE: The root tag is a special pseudo-tag assumed as the default parent tag if no parent tag is specified.
     * The root tag cannot be deleted nor assigned a parent tag.
     *
     * To create new tag with specific parent tag send a request body like this:
     *
     * ```xml
     * <tag>
     *   <name>mytag</name>
     *   <parent>
     *     <name>myparenttag</name>
     *   </parent>
     * </tag>
     * ```
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(tag().name());
            optional(tag().description());
            or(optional(tag().parent().id()), optional(tag().parent().name()));
        }
        /**
         * The added tag.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Tag tag();
    }

    /**
     * List the tags in the system.
     *
     * For example to list the full hierarchy of the tags in the system send a request like this:
     *
     * ```http
     * GET /ovirt-engine/api/tags
     * ```
     *
     * ```xml
     * <tags>
     *   <tag href="/ovirt-engine/api/tags/222" id="222">
     *     <name>root2</name>
     *     <description>root2</description>
     *     <parent href="/ovirt-engine/api/tags/111" id="111"/>
     *   </tag>
     *   <tag href="/ovirt-engine/api/tags/333" id="333">
     *     <name>root3</name>
     *     <description>root3</description>
     *     <parent href="/ovirt-engine/api/tags/222" id="222"/>
     *   </tag>
     *   <tag href="/ovirt-engine/api/tags/111" id="111">
     *     <name>root</name>
     *     <description>root</description>
     *   </tag>
     * </tags>
     * ```
     *
     * In the previous XML output you can see the following hierarchy of the tags:
     *
     * ```
     * root:        (id: 111)
     *   - root2    (id: 222)
     *     - root3  (id: 333)
     * ```
     *
     * The order of the returned list of tags isn't guaranteed.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        /**
         * List of all tags in the system.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Tag[] tags();

        /**
         * Sets the maximum number of tags to return. If not specified all the tags are returned.
         */
        @In Integer max();
    }

    /**
     * Reference to the service that manages a specific tag.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service TagService tag(String id);
}
