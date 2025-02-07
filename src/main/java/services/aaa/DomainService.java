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

package services.aaa;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Domain;

/**
 * A service to view details of an authentication domain in the system.
 *
 * @author Ravi Nori <rnori@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface DomainService {
    /**
     * Gets the authentication domain information.
     *
     * Usage:
     *
     * ....
     * GET /ovirt-engine/api/domains/5678
     * ....
     *
     * Will return the domain information:
     *
     * [source,xml]
     * ----
     * <domain href="/ovirt-engine/api/domains/5678" id="5678">
     *   <name>internal-authz</name>
     *   <link href="/ovirt-engine/api/domains/5678/users" rel="users"/>
     *   <link href="/ovirt-engine/api/domains/5678/groups" rel="groups"/>
     *   <link href="/ovirt-engine/api/domains/5678/users?search={query}" rel="users/search"/>
     *   <link href="/ovirt-engine/api/domains/5678/groups?search={query}" rel="groups/search"/>
     * </domain>
     * ----
     *
     * @author Ravi Nori <rnori@redhat.com>
     * @date 12 Dec 2017
     * @status added
     */
    interface Get extends Follow {
        /**
         * The authentication domain.
         *
         * @author Ravi Nori <rnori@redhat.com>
         * @date 12 Dec 2017
         * @status added
         */
        @Out Domain domain();
    }

    /**
     * Reference to a service to manage domain users.
     *
     * @author Ravi Nori <rnori@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service DomainUsersService users();

    /**
     * Reference to a service to manage domain groups.
     *
     * @author Ravi Nori <rnori@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service DomainGroupsService groups();
}
