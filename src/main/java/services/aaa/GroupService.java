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

package services.aaa;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import services.AssignedPermissionsService;
import services.AssignedRolesService;
import services.AssignedTagsService;
import types.Group;

/**
 * Manages a group of users. Use this service to either get groups details or remove groups. In order
 * to add new groups please use <<services/groups, service>> that manages the collection of groups.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Service
@Area("Infrastructure")
public interface GroupService {

    /**
     * Gets the system group information.
     *
     * Usage:
     *
     * ....
     * GET /ovirt-engine/api/groups/123
     * ....
     *
     * Will return the group information:
     *
     * [source,xml]
     * ----
     * <group href="/ovirt-engine/api/groups/123" id="123">
     *   <name>mygroup</name>
     *   <link href="/ovirt-engine/api/groups/123/roles" rel="roles"/>
     *   <link href="/ovirt-engine/api/groups/123/permissions" rel="permissions"/>
     *   <link href="/ovirt-engine/api/groups/123/tags" rel="tags"/>
     *   <domain_entry_id>476652557A382F67696B6D2B32762B37796E46476D513D3D</domain_entry_id>
     *   <namespace>DC=example,DC=com</namespace>
     *   <domain href="/ovirt-engine/api/domains/ABCDEF" id="ABCDEF">
     *     <name>myextension-authz</name>
     *   </domain>
     * </group>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface Get extends Follow {
        /**
         * The system group.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @Out Group get();
    }

    /**
     * Removes the system group.
     *
     * Usage:
     *
     * ....
     * DELETE /ovirt-engine/api/groups/123
     * ....
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Reference to the service that manages the collection of roles assigned to this system group.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Service AssignedRolesService roles();

    /**
     * Reference to the service that manages the collection of permissions assigned to this system group.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Service AssignedPermissionsService permissions();

    /**
     * Reference to the service that manages the collection of tags assigned to this system group.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Service AssignedTagsService tags();
}
