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
import types.Permission;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

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
        //TODO: this interface is re-used by many classes, which creates a
        //problem writing live documentation, as it is context-dependent.
        //The interface should probably be split-up.
        @InputDetail
        default void inputDetail() {
            or(mandatory(permission().role().id()), mandatory(permission().role().name()));
        }
        /**
         * The permission.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Permission permission();

        /**
         * Add a new user level permission for a given virtual machine.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface UserLevel extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(permission().user().id());
            }
        }

        /**
         * Add a new group level permission for a given virtual machine.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface GroupLevel extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(permission().group().id());
            }
        }

        /**
         * Add a new permission on the data center to the group in the system.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface DataCenterPermission extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(permission().dataCenter().id());
            }
        }

        /**
         * Add a new permission on the cluster to the group in the system.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface ClusterPermission extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(permission().cluster().id());
            }
        }

        /**
         * Add a new permission on the host to the group in the system.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface HostPermission extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(permission().host().id());
            }
        }

        /**
         * Add a new permission on the storage domain to the group in the system.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface StorageDomainPermission extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(permission().storageDomain().id());
            }
        }

        /**
         * Add a new permission on the vm to the group in the system.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface VmPermission extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(permission().vm().id());
            }
        }

        /**
         * Add a new permission on the vm pool to the group in the system.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface vmPoolPermission extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(permission().vmPool().id());
            }
        }

        /**
         * Add a new permission on the template to the group in the system.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface TemplatePermission extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(permission().template().id());
            }
        }
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
     * The order of the returned permissions isn't guaranteed.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
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
