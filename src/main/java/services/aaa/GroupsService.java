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
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Group;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * Manages the collection of groups of users.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Service
@Area("Infrastructure")
public interface GroupsService {

    /**
     * Add group from a directory service. Please note that domain name is name of the authorization provider.
     *
     * For example, to add the `Developers` group from the `internal-authz` authorization provider send a request
     * like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/groups
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <group>
     *   <name>Developers</name>
     *   <domain>
     *     <name>internal-authz</name>
     *   </domain>
     * </group>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(group().name());
            optional(group().namespace());
//            optional(group().principal()); TODO: check correctness
        }
        /**
         * The group to be added.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @In @Out Group group();
    }

    /**
     * List all the groups in the system.
     *
     * Usage:
     *
     * ....
     * GET /ovirt-engine/api/groups
     * ....
     *
     * Will return the list of groups:
     *
     * [source,xml]
     * ----
     * <groups>
     *   <group href="/ovirt-engine/api/groups/123" id="123">
     *     <name>mygroup</name>
     *     <link href="/ovirt-engine/api/groups/123/roles" rel="roles"/>
     *     <link href="/ovirt-engine/api/groups/123/permissions" rel="permissions"/>
     *     <link href="/ovirt-engine/api/groups/123/tags" rel="tags"/>
     *     <domain_entry_id>476652557A382F67696B6D2B32762B37796E46476D513D3D</domain_entry_id>
     *     <namespace>DC=example,DC=com</namespace>
     *     <domain href="/ovirt-engine/api/domains/ABCDEF" id="ABCDEF">
     *       <name>myextension-authz</name>
     *     </domain>
     *   </group>
     *   ...
     * </groups>
     * ----
     *
     * The order of the returned list of groups isn't guaranteed.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface List {
        /**
         * The list of groups.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @Out Group[] groups();

        /**
         * Sets the maximum number of groups to return. If not specified all the groups are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned groups.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();
    }

    /**
     * Reference to the service that manages a specific group.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Service GroupService group(String id);
}
