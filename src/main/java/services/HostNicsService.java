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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.HostNic;

/**
 * A service to manage the network interfaces of a host.
 *
 * @author Leon Goldberg <lgoldber@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Network")
public interface HostNicsService {
    /**
     * Returns the list of network interfaces of the host.
     *
     * The order of the returned list of network interfaces isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out HostNic[] nics();

        /**
         * Sets the maximum number of NICs to return. If not specified all the NICs are returned.
         */
        @In Integer max();

        /**
         * Indicates if all of the attributes of the host network interface should be included in the response.
         *
         * By default the following attributes are excluded:
         *
         * - `virtual_functions_configuration`
         *
         * For example, to retrieve the complete representation of network interface '456' of host '123':
         *
         * ....
         * GET /ovirt-engine/api/hosts/123/nics?all_content=true
         * ....
         *
         * NOTE: These attributes are not included by default because retrieving them impacts performance. They are
         * seldom used and require additional queries to the database. Use this parameter with caution and only when
         * specifically required.
         *
         * @author Dominik Holler <dholler@redhat.com>
         * @date 11 Jun 2019
         * @status added
         */
        @In Boolean allContent();
    }

    /**
     * Reference to the service that manages a single network interface.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 17 Jun 2019
     * @status updated_by_docs
     */
    @Service HostNicService nic(String id);
}
