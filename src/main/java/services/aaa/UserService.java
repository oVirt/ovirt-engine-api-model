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
import types.User;

/**
 * A service to manage a user in the system.
 * Use this service to either get users details or remove users.
 * In order to add new users please use
 * <<services/users>>.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 28 Nov 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface UserService {
    /**
     * Gets the system user information.
     *
     * Usage:
     *
     * ....
     * GET /ovirt-engine/api/users/1234
     * ....
     *
     * Will return the user information:
     *
     * [source,xml]
     * ----
     * <user href="/ovirt-engine/api/users/1234" id="1234">
     *   <name>admin</name>
     *   <link href="/ovirt-engine/api/users/1234/sshpublickeys" rel="sshpublickeys"/>
     *   <link href="/ovirt-engine/api/users/1234/roles" rel="roles"/>
     *   <link href="/ovirt-engine/api/users/1234/permissions" rel="permissions"/>
     *   <link href="/ovirt-engine/api/users/1234/tags" rel="tags"/>
     *   <department></department>
     *   <domain_entry_id>23456</domain_entry_id>
     *   <email>user1@domain.com</email>
     *   <last_name>Lastname</last_name>
     *   <namespace>*</namespace>
     *   <principal>user1</principal>
     *   <user_name>user1@domain-authz</user_name>
     *   <domain href="/ovirt-engine/api/domains/45678" id="45678">
     *     <name>domain-authz</name>
     *   </domain>
     * </user>
     * ----
     *
     * @author Ravi Nori <rnori@redhat.com>
     * @date 7 Dec 2017
     * @status added
     */
    interface Get extends Follow {
        /**
         * The system user.
         *
         * @author Ravi Nori <rnori@redhat.com>
         * @date 7 Dec 2017
         * @status added
         */
        @Out User user();
    }

   /**
    * Removes the system user.
    *
    * Usage:
    *
    * ....
    * DELETE /ovirt-engine/api/users/1234
    * ....
    *
    * @author Ravi Nori <rnori@redhat.com>
    * @date 7 Dec 2017
    * @status added
    */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service AssignedRolesService roles();
    @Service AssignedPermissionsService permissions();
    @Service AssignedTagsService tags();
    @Service SshPublicKeysService sshPublicKeys();
}
