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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.HostNic;
import types.HostNicVirtualFunctionsConfiguration;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * A service to manage a network interface of a host.
 *
 * @author Leon Goldberg <lgoldber@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Network")
public interface HostNicService extends MeasurableService {

    interface Get extends Follow {
        @Out HostNic nic();

        /**
         * Indicates if all of the attributes of the host network interface should be included in the response.
         *
         * By default the following attributes are excluded:
         *
         * - `virtual_functions_configuration`
         *
         * For example, to retrieve the complete representation network interface '456' of host '123':
         *
         * ```http
         * GET /ovirt-engine/api/hosts/123/nics/456?all_content=true HTTP/1.1
         * ```
         *
         * NOTE: These attributes are not included by default because retrieving them impacts performance. They are
         * seldom used and require additional queries to the database. Use this parameter with caution and only when
         * specifically required.
         *
         * @author Dominik Holler <dholler@redhat.com>
         * @author Eli Marcus <emarcus@redhat.com>
         * @date 17 Jun 2019
         * @status updated_by_docs
         */
        @In Boolean allContent();
    }

    /**
     * The action updates virtual function configuration in case the current resource represents an SR-IOV enabled NIC.
     * The input should be consisted of at least one of the following properties:
     *
     * - `allNetworksAllowed`
     * - `numberOfVirtualFunctions`
     *
     * Please see the `HostNicVirtualFunctionsConfiguration` type for the meaning of the properties.
     */
    interface UpdateVirtualFunctionsConfiguration {
        @InputDetail
        default void inputDetail() {
            optional(virtualFunctionsConfiguration().allNetworksAllowed());
            optional(virtualFunctionsConfiguration().numberOfVirtualFunctions());
        }
        @In HostNicVirtualFunctionsConfiguration virtualFunctionsConfiguration();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Reference to the service that manages the network labels assigned to this network interface.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service NetworkLabelsService networkLabels();

    /**
     * Reference to the service that manages the network attachments assigned to this network interface.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service NetworkAttachmentsService networkAttachments();

    /**
     * Retrieves sub-collection resource of network labels that are allowed on an the virtual functions
     * in case that the current resource represents an SR-IOV physical function NIC.
     */
    @Service NetworkLabelsService virtualFunctionAllowedLabels();

    /**
     * Retrieves sub-collection resource of networks that are allowed on an the virtual functions
     * in case that the current resource represents an SR-IOV physical function NIC.
     */
    @Service VirtualFunctionAllowedNetworksService virtualFunctionAllowedNetworks();

    /**
     * A reference to information elements received by LLDP on the NIC.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 14 Jul 2017
     * @status updated_by_docs
     * @since 4.1.6
     */
    @Service LinkLayerDiscoveryProtocolService linkLayerDiscoveryProtocolElements();
}
