/*
Copyright (c) 2010 Red Hat, Inc.

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

/**
 * Provides read-only access to the global set of roles
 */
@Service
@Area("Infrastructure")
public interface RolesService {

    /**
     * Create a new role. The role can be administrative or non-administrative and can have different permits.
     *
     * For example, to add the `MyRole` non-administrative role with permits to login and create virtual machines
     * send a request like this (note that you have to pass permit id):
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/roles
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <role>
     *   <name>MyRole</name>
     *   <description>My custom role to create virtual machines</description>
     *   <administrative>false</administrative>
     *   <permits>
     *     <permit id="1"/>
     *     <permit id="1300"/>
     *   </permits>
     * </group>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        @In @Out Role role();
    }

    /**
     * List roles.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/roles
     * ----
     *
     * You will receive response in XML like this one:
     *
     * [source,xml]
     * ----
     * <roles>
     *   <role id="123">
     *      <name>SuperUser</name>
     *      <description>Roles management administrator</description>
     *      <link href="/ovirt-engine/api/roles/123/permits" rel="permits"/>
     *      <administrative>true</administrative>
     *      <mutable>false</mutable>
     *   </role>
     *   ...
     * </roles>
     * ----
     *
     * @autho Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        @Out Role[] roles();

        /**
         * Sets the maximum number of roles to return. If not specified all the roles are returned.
         */
        @In Integer max();
    }

    /**
     * Sub-resource locator method, returns individual role resource on which the remainder of the URI is dispatched.
     */
    @Service RoleService role(String id);
}
