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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Permission;

/**
 * Represents a permission sub-collection, scoped by user, group or some entity type.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface AssignedPermissionsService {

    /**
     * Assign a new permission to a user or group for specific entity.
     *
     * For example, to assign the `UserVmManager` role to the virtual machine with id `123` to the user with id `456`
     * send a request like this:
     *
     * ....
     * POST /ovirt-engine/api/vms/123/permissions
     * ....
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <permission>
     *   <role>
     *     <name>UserVmManager</name>
     *   </role>
     *   <user id="456"/>
     * </permission>
     * ----
     *
     * To assign the `SuperUser` role to the system to the user with id `456` send a request like this:
     *
     * ....
     * POST /ovirt-engine/api/permissions
     * ....
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <permission>
     *   <role>
     *     <name>SuperUser</name>
     *   </role>
     *   <user id="456"/>
     * </permission>
     * ----
     *
     * If you want to assign permission to the group instead of the user please replace the `user` element with the
     * `group` element with proper `id` of the group. For example to assign the `UserRole` role to the cluster with
     * id `123` to the group with id `789` send a request like this:
     *
     * ....
     * POST /ovirt-engine/api/clusters/123/permissions
     * ....
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <permission>
     *   <role>
     *     <name>UserRole</name>
     *   </role>
     *   <group id="789"/>
     * </permission>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        /**
         * The permission.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Permission permission();
    }

    /**
     * List all the permissions of the specific entity.
     *
     * For example to list all the permissions of the cluster with id `123` send a request like this:
     *
     * ....
     * GET /ovirt-engine/api/clusters/123/permissions
     * ....
     *
     * [source,xml]
     * ----
     * <permissions>
     *   <permission id="456">
     *     <cluster id="123"/>
     *     <role id="789"/>
     *     <user id="451"/>
     *   </permission>
     *   <permission id="654">
     *     <cluster id="123"/>
     *     <role id="789"/>
     *     <group id="127"/>
     *   </permission>
     * </permissions>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        /**
         * The list of permissions.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Permission[] permissions();
    }

    /**
     * Sub-resource locator method, returns individual permission resource on which the remainder of the URI is
     * dispatched.
     */
    @Service PermissionService permission(String id);
}
