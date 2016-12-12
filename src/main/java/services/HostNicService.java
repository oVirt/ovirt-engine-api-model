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

import types.HostNic;
import types.HostNicVirtualFunctionsConfiguration;

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

    interface Get {
        @Out HostNic nic();
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
}
