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

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import services.externalhostproviders.KatelloErrataService;
import types.Cluster;
import types.Host;
import types.HostNic;
import types.IscsiDetails;
import types.Label;
import types.NetworkAttachment;
import types.PowerManagement;
import types.Ssh;
import types.StorageDomain;

@Service
public interface HostService extends MeasurableService {
    interface Activate {
        /**
         * Indicates if the activation should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Approve {
        @In Cluster cluster();

        /**
         * Indicates if the approval should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface CommitNetConfig {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Deactivate {
        @In String reason();

        /**
         * Indicates if the deactivation should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface EnrollCertificate {
        /**
         * Indicates if the enrollment should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Fence {
        @In String fenceType();
        @Out PowerManagement powerManagement();

        /**
         * Indicates if the fencing should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface ForceSelectSpm {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Get {
        @Out Host host();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    interface Install {
        /**
         * The password of of the `root` user, used to connect to the host via SSH.
         */
        @In String rootPassword();

        /**
         * The SSH details used to connect to the host.
         */
        @In Ssh ssh();

        /**
         * This `override_iptables` property is used to indicate if the firewall configuration should be
         * replaced by the default one.
         */
        @In Host host();

        /**
         * When installing an oVirt node a image ISO file is needed.
         */
        @In String image();

        /**
         * Indicates if the installation should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface IscsiDiscover {
        @In IscsiDetails iscsi();
        @Out String[] iscsiTargets();

        /**
         * Indicates if the discovery should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface IscsiLogin {
        @In IscsiDetails iscsi();

        /**
         * Indicates if the login should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface UnregisteredStorageDomainsDiscover {
        @In IscsiDetails iscsi();
        @Out StorageDomain[] storageDomains();

        /**
         * Indicates if the discovery should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Update {
        @In @Out Host host();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Upgrade {
        /**
         * Indicates if the upgrade should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Refresh {
        /**
         * Indicates if the refresh should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * This method is used to change the configuration of the network interfaces of a host.
     *
     * For example, lets assume that you have a host with three network interfaces `eth0`, `eth1` and `eth2` and that
     * you want to configure a new bond using `eth0` and `eth1`, and put a VLAN on top of it. Using a simple shell
     * script and the `curl` command line HTTP client that can be done as follows:
     *
     * [source]
     * ----
     * #!/bin/sh -ex
     *
     * url="https://engine.example.com/ovirt-engine/api"
     * user="admin@internal"
     * password="..."
     *
     * curl \
     * --verbose \
     * --cacert /etc/pki/ovirt-engine/ca.pem \
     * --user "${user}:${password}" \
     * --request POST \
     * --header "Content-Type: application/xml" \
     * --header "Accept: application/xml" \
     * --data '
     * <action>
     *   <modified_bonds>
     *     <host_nic>
     *       <name>bond0</name>
     *       <bonding>
     *         <options>
     *           <option>
     *             <name>mode</name>
     *             <value>4</value>
     *           </option>
     *           <option>
     *             <name>miimon</name>
     *             <value>100</value>
     *           </option>
     *         </options>
     *         <slaves>
     *           <host_nic>
     *             <name>eth1</name>
     *           </host_nic>
     *           <host_nic>
     *             <name>eth2</name>
     *           </host_nic>
     *         </slaves>
     *       </bonding>
     *     </host_nic>
     *   </modified_bonds>
     *   <modified_network_attachments>
     *     <network_attachment>
     *       <network>
     *         <name>myvlan</name>
     *       </network>
     *       <host_nic>
     *         <name>bond0</name>
     *       </host_nic>
     *       <ip_address_assignments>
     *         <assignment_method>static</assignment_method>
     *         <ip_address_assignment>
     *           <ip>
     *             <address>192.168.122.10</address>
     *             <netmask>255.255.255.0</netmask>
     *           </ip>
     *         </ip_address_assignment>
     *       </ip_address_assignments>
     *     </network_attachment>
     *   </modified_network_attachments>
     *  </action>
     * ' \
     * "${url}/hosts/1ff7a191-2f3b-4eff-812b-9f91a30c3acc/setupnetworks"
     * ----
     *
     * Note that this is valid for version 4 of the API. In previous versions some elements were represented as XML
     * attributes instead of XML elements. In particular the `options` and `ip` elements were represented as follows:
     *
     * [source,xml]
     * ----
     * <options name="mode" value="4"/>
     * <options name="miimon" value="100"/>
     * <ip address="192.168.122.10" netmask="255.255.255.0"/>
     * ----
     *
     * Using the Python SDK the same can be done with the following code:
     *
     * [source,python]
     * ----
     * host.setupnetworks(
     *   params.Action(
     *     modified_bonds=params.HostNics(
     *       host_nic=[
     *         params.HostNIC(
     *           name="bond0",
     *           bonding=params.Bonding(
     *             options=params.Options(
     *               option=[
     *                 params.Option(name="mode", value="4"),
     *                 params.Option(name="miimon", value="100"),
     *               ],
     *             ),
     *             slaves=params.Slaves(
     *               host_nic=[
     *                 params.HostNIC(name="eth1"),
     *                 params.HostNIC(name="eth2"),
     *               ],
     *             ),
     *           ),
     *         ),
     *       ],
     *     ),
     *     modified_network_attachments=params.NetworkAttachments(
     *       network_attachment=[
     *         params.NetworkAttachment(
     *           network=params.Network(name="myvlan"),
     *           host_nic=params.HostNIC(name="bond0"),
     *           ip_address_assignments=params.IpAddressAssignments(
     *             ip_address_assignment=[
     *               params.IpAddressAssignment(
     *                 assignment_method="static",
     *                 ip=params.IP(
     *                   address="192.168.122.10",
     *                   netmask="255.255.255.0",
     *                 ),
     *               ),
     *             ],
     *           ),
     *         ),
     *       ],
     *     ),
     *   ),
     * )
     * ----
     */
    interface SetupNetworks {
        @In NetworkAttachment[] modifiedNetworkAttachments();
        @In NetworkAttachment[] removedNetworkAttachments();
        @In NetworkAttachment[] synchronizedNetworkAttachments();
        @In HostNic[] modifiedBonds();
        @In HostNic[] removedBonds();
        @In Label[] modifiedLabels();
        @In Label[] removedLabels();
        @In Boolean checkConnectivity();
        @In Integer connectivityTimeout();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service AssignedPermissionsService permissions();
    @Service AssignedTagsService tags();
    @Service FenceAgentsService fenceAgents();
    @Service HostDevicesService devices();
    @Service HostHooksService hooks();
    @Service HostNicsService nics();
    @Service HostNumaNodesService numaNodes();
    @Service HostStorageService storage();
    @Service KatelloErrataService katelloErrata();
    @Service NetworkAttachmentsService networkAttachments();
    @Service StorageServerConnectionExtensionsService storageConnectionExtensions();
    @Service UnmanagedNetworksService unmanagedNetworks();
}
