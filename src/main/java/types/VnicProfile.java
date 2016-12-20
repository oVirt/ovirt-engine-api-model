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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * A vNIC profile is a collection of settings that can be applied to individual <<types/nics,NIC>>.
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @date 20 Dec 2016
 * @status added
 */
@Type
public interface VnicProfile extends Identified {

    /**
     * Enables port mirroring.
     *
     * Port mirroring copies layer 3 network traffic on a given <<types/network,logical network>>and
     * <<types/host,host>> to a NIC on a <<types/vm,virtual machine>>. This virtual machine
     * can be used for network debugging and tuning, intrusion detection, and monitoring the behavior of other
     * virtual machine on the same host and logical network. The only
     * traffic copied is internal to one logical network on one host. There is no
     * increase on traffic on the network external to the host; however a virtual machine
     * with port mirroring enabled uses more host CPU and RAM than other virtual machines.
     *
     * Port mirroring has the following limitations:
     *
     *  - Hot plugging NIC with a vNIC profile that has port mirroring enabled is not supported.
     *  - Port mirroring cannot be altered when the vNIC profile is attached to a virtual machine.
     *
     * Given the above limitations, it is recommended that you enable port mirroring on an additional,
     * dedicated vNIC profile.
     *
     * IMPORTANT: Enabling port mirroring reduces the privacy of other network users.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 20 Dec 2016
     * @status added
     */
    Boolean portMirroring();

    /**
     * Custom properties applied to the vNIC profile.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 20 Dec 2016
     * @status added
     */
    CustomProperty[] customProperties();

    /**
     * Enables the passthrough to a SR-IOV-enabled <<types/host_nic,host NIC>>.
     *
     * A vNIC profile enables a NIC to be directly connected to a
     * <<types/host_nic_virtual_functions_configuration,virtual function (VF)>> of an SR-IOV-enabled
     * host NIC, if passthrough is enabled. The NIC will then bypass the software network virtualization and
     * connect directly to the VF for direct device assignment.
     *
     * The passthrough cannot be enabled if the vNIC profile is already attached to a NIC.
     * If a vNIC profile has passthrough enabled, `qos` and `port_mirroring` are disabled for the vNIC profile.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 20 Dec 2016
     * @status added
     */
    VnicPassThrough passThrough();

    /**
     * Reference to the network that the vNIC profile is applied to.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 20 Dec 2016
     * @status added
     */
    @Link Network network();

    /**
     * Reference to the quality of service attributes to apply to the vNIC profile.
     *
     * Quality of Service attributes regulate inbound and outbound network traffic of the
     * NIC.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 20 Dec 2016
     * @status added
     */
    @Link Qos qos();

    /**
     * Reference to the top-level network filter that apply to the NICs that use this profile.
     *
     * Network filters will enhance the admin ability to manage the network packets traffic
     * from/to the participated virtual machines.
     * The network filter may either contain a references to other filters, rules for traffic
     * filtering, or hold a combination of both.
     *
     * @author Alona Kaplan <alkaplan@redhat.com>
     * @author Dominik Holler <dholler@redhat.com>
     * @date 20 Dec 2016
     * @status added
     */
    @Link NetworkFilter networkFilter();

    /**
     * Permissions to allow usage of the vNIC profile.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 20 Dec 2016
     * @status added
     */
    @Link Permission[] permissions();
}
