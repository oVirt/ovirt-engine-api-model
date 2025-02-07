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

import static org.ovirt.api.metamodel.language.ApiLanguage.or;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

/**
 * A service to manage collection of assignment of tags to specific entities in system.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface AssignedTagsService {

    /**
     * Assign tag to specific entity in the system.
     *
     * For example to assign tag `mytag` to virtual machine with the id `123` send a request like this:
     *
     * ....
     * POST /ovirt-engine/api/vms/123/tags
     * ....
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <tag>
     *   <name>mytag</name>
     * </tag>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            or(mandatory(tag().id()), mandatory(tag().name()));
        }
        /**
         * The assigned tag.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Tag tag();
    }

    /**
     * List all tags assigned to the specific entity.
     *
     * For example to list all the tags of the virtual machine with id `123` send a request like this:
     *
     * ....
     * GET /ovirt-engine/api/vms/123/tags
     * ....
     *
     * [source,xml]
     * ----
     * <tags>
     *   <tag href="/ovirt-engine/api/tags/222" id="222">
     *     <name>mytag</name>
     *     <description>mytag</description>
     *     <vm href="/ovirt-engine/api/vms/123" id="123"/>
     *   </tag>
     * </tags>
     * ----
     *
     * The order of the returned tags isn't guaranteed.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        /**
         * The list of assigned tags.
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
     * Reference to the service that manages assignment of specific tag.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service AssignedTagService tag(String id);
}
