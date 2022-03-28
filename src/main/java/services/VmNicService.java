/*
Copyright (c) 2015 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Nic;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("Network")
public interface VmNicService extends MeasurableService {
    // TODO remove on 4.1
    interface Activate {
        /**
         * Indicates if the activation should be performed asynchronously.
         */
        @In Boolean async();
    }

    // TODO remove on 4.1
    interface Deactivate {
        /**
         * Indicates if the deactivation should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Get extends Follow {
        @Out Nic nic();
    }

    /**
     * Updates the NIC.
     *
     * For example, to update the NIC having with `456` belonging to virtual the machine with id `123` send a request
     * like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/vms/123/nics/456
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <nic>
     *   <name>mynic</name>
     *   <interface>e1000</interface>
     *   <vnic_profile id='789'/>
     * </nic>
     * ----
     *
     * [IMPORTANT]
     * ====
     * The hotplugging feature only supports virtual machine operating systems with hotplugging operations.
     * Example operating systems include:
     *
     * - Red Hat Enterprise Linux 6
     * - Red Hat Enterprise Linux 5
     * - Windows Server 2008 and
     * - Windows Server 2003
     * ====
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
//            optional(nic()._interface()); TODO: uncomment when able to handle '_'
            optional(nic().linked());
            optional(nic().mac().address());
            optional(nic().name());
            optional(nic().plugged());
            optional(nic().vnicProfile().id());
        }
        @In @Out Nic nic();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes the NIC.
     *
     * For example, to remove the NIC with id `456` from the virtual machine with id `123` send a request like this:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/vms/123/nics/456
     * ----
     *
     * [IMPORTANT]
     * ====
     * The hotplugging feature only supports virtual machine operating systems with hotplugging operations.
     * Example operating systems include:
     *
     * - Red Hat Enterprise Linux 6
     * - Red Hat Enterprise Linux 5
     * - Windows Server 2008 and
     * - Windows Server 2003
     * ====
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service VmReportedDevicesService reportedDevices();

    /**
     * Reference to the service that manages the network filter parameters of the NIC.
     *
     * A single top-level network filter may assigned to the NIC by the NIC's <<types/vnic_profile,vNIC Profile>>.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 13 Dec 2016
     * @status added
     * @since 4.2
     */
    @Service NicNetworkFilterParametersService networkFilterParameters();
}
