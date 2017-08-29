/*
Copyright (c) 2017 Red Hat, Inc.

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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.LinkLayerDiscoveryProtocolElement;

/**
 * A service to fetch information elements received by Link Layer Discovery Protocol (LLDP).
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 14 Jul 2017
 * @status updated_by_docs
 * @since 4.1.6
 */
@Service
@Area("Network")
public interface LinkLayerDiscoveryProtocolService {

    /**
     * Fetches information elements received by LLDP.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 14 Jul 2017
     * @status updated_by_docs
     * @since 4.1.6
     */
    interface List {

        /**
         * Retrieves a list of information elements received by LLDP.
         *
         * For example, to retrieve the information elements received on the NIC `321` on host `123`,
         * send a request like this:
         *
         * ....
         * GET ovirt-engine/api/hosts/123/nics/321/linklayerdiscoveryprotocolelements
         * ....
         *
         * It will return a response like this:
         *
         * [source,xml]
         * ----
         * <link_layer_discovery_protocol_elements>
         *     ...
         *   <link_layer_discovery_protocol_element>
         *     <name>Port Description</name>
         *     <properties>
         *       <property>
         *         <name>port description</name>
         *         <value>Summit300-48-Port 1001</value>
         *       </property>
         *     </properties>
         *     <type>4</type>
         *   </link_layer_discovery_protocol_element>
         *   ...
         * <link_layer_discovery_protocol_elements>
         * ----
         * @author Dominik Holler <dholler@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 14 Jul 2017
         * @status updated_by_docs
         * @since 4.1.6
         */
        @Out LinkLayerDiscoveryProtocolElement[] elements();
    }
}
