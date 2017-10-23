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
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Tag;

/**
 * A service to manage assignment of specific tag to specific entities in system.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface AssignedTagService {

    /**
     * Gets the information about the assigned tag.
     *
     * For example to retrieve the information about the tag with the id `456` which is assigned to virtual machine
     * with id `123` send a request like this:
     *
     * ....
     * GET /ovirt-engine/api/vms/123/tags/456
     * ....
     *
     * [source,xml]
     * ----
     * <tag href="/ovirt-engine/api/tags/456" id="456">
     *   <name>root</name>
     *   <description>root</description>
     *   <vm href="/ovirt-engine/api/vms/123" id="123"/>
     * </tag>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        /**
         * The assigned tag.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Tag tag();
    }

    /**
     * Unassign tag from specific entity in the system.
     *
     * For example to unassign the tag with id `456` from virtual machine with id `123` send a request like this:
     *
     * ....
     * DELETE /ovirt-engine/api/vms/123/tags/456
     * ....
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
