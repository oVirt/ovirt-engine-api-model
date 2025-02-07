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

package services.externalhostproviders;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.ExternalDiscoveredHost;

/**
 * This service manages external discovered hosts.
 *
 * @author Yaniv Bronhaim <ybronhei@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Service
@Area("Infrastructure")
public interface ExternalDiscoveredHostsService {
    /**
     * Get list of discovered hosts' information.
     *
     * Discovered hosts are fetched from third-party providers such as Foreman.
     *
     * To list all discovered hosts for provider `123` send the following:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/externalhostproviders/123/discoveredhost
     * ----
     *
     * [source,xml]
     * ----
     * <external_discovered_hosts>
     *  <external_discovered_host href="/ovirt-engine/api/externalhostproviders/123/discoveredhosts/456" id="456">
     *   <name>mac001a4ad04031</name>
     *   <ip>10.34.67.42</ip>
     *   <last_report>2017-04-24 11:05:41 UTC</last_report>
     *   <mac>00:1a:4a:d0:40:31</mac>
     *   <subnet_name>sat0</subnet_name>
     *   <external_host_provider href="/ovirt-engine/api/externalhostproviders/123" id="123"/>
     *  </external_discovered_host>
     *  <external_discovered_host href="/ovirt-engine/api/externalhostproviders/123/discoveredhosts/789" id="789">
     *   <name>mac001a4ad04040</name>
     *   <ip>10.34.67.43</ip>
     *   <last_report>2017-04-24 11:05:41 UTC</last_report>
     *   <mac>00:1a:4a:d0:40:40</mac>
     *   <subnet_name>sat0</subnet_name>
     *   <external_host_provider href="/ovirt-engine/api/externalhostproviders/123" id="123"/>
     *  </external_discovered_host>
     *  ...
     * </external_discovered_hosts>
     * ----
     *
     * The order of the returned list of hosts isn't guaranteed.
     *
     * @author Yaniv Bronhaim <ybronhei@redhat.com>
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface List extends Follow {
        /**
         * List of discovered hosts
         */
        @Out ExternalDiscoveredHost[] hosts();

        /**
         * Sets the maximum number of hosts to return. If not specified all the hosts are returned.
         */
        @In Integer max();
    }

    @Service ExternalDiscoveredHostService host(String id);
}
