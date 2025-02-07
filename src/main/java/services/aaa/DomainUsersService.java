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
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.User;

/**
 * A service to list all domain users in the system.
 *
 * @author Ravi Nori <rnori@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface DomainUsersService {
    /**
     * List all the users in the domain.
     *
     * Usage:
     *
     * ....
     * GET /ovirt-engine/api/domains/5678/users
     * ....
     *
     * Will return the list of users in the domain:
     *
     * [source,xml]
     * ----
     * <users>
     *   <user href="/ovirt-engine/api/domains/5678/users/1234" id="1234">
     *     <name>admin</name>
     *     <namespace>*</namespace>
     *     <principal>admin</principal>
     *     <user_name>admin@internal-authz</user_name>
     *     <domain href="/ovirt-engine/api/domains/5678" id="5678">
     *       <name>internal-authz</name>
     *     </domain>
     *     <groups/>
     *   </user>
     * </users>
     * ----
     *
     * The order of the returned list of users isn't guaranteed.
     *
     * @author Ravi Nori <rnori@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        /**
         * The list of users in the domain.
         *
         * @author Ravi Nori <rnori@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out User[] users();

        /**
         * Sets the maximum number of users to return. If not specified all the users are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned users.
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
     * Reference to a service to view details of a domain user.
     *
     * @author Ravi Nori <rnori@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service DomainUserService user(String id);
}
