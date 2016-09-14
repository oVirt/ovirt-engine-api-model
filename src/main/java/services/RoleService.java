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
import types.Role;

@Service
@Area("Infrastructure")
public interface RoleService {
    interface Get {
        @Out Role role();
    }

    /**
     * Removes the role.
     *
     * To remove the role you need to know its id, then send request like this:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/roles/{role_id}
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 14 Sep 2016
     * @status added
     *
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Updates a role. You are allowed to update `name`, `description` and `administrative` attributes after role is
     * created. Within this endpoint you can't add or remove roles permits you need to use
     * <<services/roles/permits, service>> that manages permits of role.
     *
     * For example to update role's `name`, `description` and `administrative` attributes send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/roles/123
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <role>
     *   <name>MyNewRoleName</name>
     *   <description>My new description of the role</description>
     *   <administrative>true</administrative>
     * </group>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @In @Out Role role();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service PermitsService permits();
}
