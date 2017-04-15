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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Domain;

/**
 * A service to list all authentication domains in the system.
 *
 * @author Ravi Nori <rnori@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface DomainsService {
    /**
     * List all the authentication domains in the system.
     *
     * Usage:
     *
     * ....
     * GET /ovirt-engine/api/domains
     * ....
     *
     * Will return the list of domains:
     *
     * [source,xml]
     * ----
     * <domains>
     *   <domain href="/ovirt-engine/api/domains/5678" id="5678">
     *     <name>internal-authz</name>
     *     <link href="/ovirt-engine/api/domains/5678/users" rel="users"/>
     *     <link href="/ovirt-engine/api/domains/5678/groups" rel="groups"/>
     *     <link href="/ovirt-engine/api/domains/5678/users?search={query}" rel="users/search"/>
     *     <link href="/ovirt-engine/api/domains/5678/groups?search={query}" rel="groups/search"/>
     *   </domain>
     * </domains>
     * ----
     *
     * The order of the returned list of domains isn't guaranteed.
     *
     * @author Ravi Nori <rnori@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        /**
         * The list of domains.
         *
         * @author Ravi Nori <rnori@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Domain[] domains();

        /**
         * Sets the maximum number of domains to return. If not specified all the domains are returned.
         */
        @In Integer max();
    }

    /**
     * Reference to a service to view details of a domain.
     *
     * @author Ravi Nori <rnori@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service DomainService domain(String id);
}
