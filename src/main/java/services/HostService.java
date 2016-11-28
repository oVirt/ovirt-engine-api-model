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

import services.externalhostproviders.KatelloErrataService;
import types.Cluster;
import types.Host;
import types.HostNic;
import types.IscsiDetails;
import types.NetworkLabel;
import types.NetworkAttachment;
import types.PowerManagement;
import types.Ssh;
import types.StorageDomain;

@Service
@Area("Infrastructure")
public interface HostService extends MeasurableService {
    /**
     * Activate the host for use, such as running virtual machines.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Activate {
        /**
         * Indicates if the activation should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Approve a pre-installed Hypervisor host for usage in the virtualization environment.
     *
     * This action also accepts an optional cluster element to define the target cluster for this host.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Approve {
        @In Cluster cluster();

        /**
         * Indicates if the approval should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Marks the network configuration as good and persists it inside the host.
     *
     * An API user commits the network configuration to persist a host network interface attachment or detachment, or
     * persist the creation and deletion of a bonded interface.
     *
     * IMPORTANT: Networking configuration is only committed after the engine has established that host connectivity is
     * not lost as a result of the configuration changes. If host connectivity is lost, the host requires a reboot and
     * automatically reverts to the previous networking configuration.
     *
     * For example, to commit the network configuration of host with id `123` send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/hosts/123/commitnetconfig
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 16 Aug 2016
     * @status added
     */
    interface CommitNetConfig {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Deactivate the host to perform maintenance tasks.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Deactivate {
        @In String reason();

        /**
         * Indicates if the deactivation should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the gluster service should be stopped as part of deactivating the host. It can be used while
         * performing maintenance operations on the gluster host. Default value for this variable is `false`.
         */
        @In Boolean stopGlusterService();
    }

    /**
     * Enroll certificate of the host. Useful in case you get a warning that it is about to, or already expired.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface EnrollCertificate {
        /**
         * Indicates if the enrollment should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Controls host's power management device.
     *
     * For example, let's assume you want to start the host. This can be done via:
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
     * --header "Version: 4" \
     * --header "Content-Type: application/xml" \
     * --header "Accept: application/xml" \
     * --data '
     * <action>
     *   <fence_type>start</fence_type>
     * </action>
     * ' \
     * "${url}/hosts/123/fence"
     * ----
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Fence {
        @In String fenceType();
        @Out PowerManagement powerManagement();

        /**
         * Indicates if the fencing should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Manually set a host as the storage pool manager (SPM).
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/hosts/123/forceselectspm
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Liron Aravot <laravot@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface ForceSelectSpm {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Get the host details.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Get {
        @Out Host host();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    /**
     * Install VDSM and related software on the host. The host type defines additional parameters for the action.
     *
     * Example of installing a host, using `curl` and JSON, plain:
     *
     * [source,bash]
     * ----
     * curl \
     * --verbose \
     * --cacert /etc/pki/ovirt-engine/ca.pem \
     * --request PUT \
     * --header "Content-Type: application/json" \
     * --header "Accept: application/json" \
     * --header "Version: 4" \
     * --user "admin@internal:..." \
     * --data '
     * {
     *   "root_password": "myrootpassword"
     * }
     * ' \
     * "https://engine.example.com/ovirt-engine/api/hosts/123"
     * ----
     *
     * Example of installing a host, using `curl` and JSON, with hosted engine components:
     *
     * [source,bash]
     * ----
     * curl \
     * curl \
     * --verbose \
     * --cacert /etc/pki/ovirt-engine/ca.pem \
     * --request PUT \
     * --header "Content-Type: application/json" \
     * --header "Accept: application/json" \
     * --header "Version: 4" \
     * --user "admin@internal:..." \
     * --data '
     * {
     *   "root_password": "myrootpassword"
     * }
     * ' \
     * "https://engine.example.com/ovirt-engine/api/hosts/123?deploy_hosted_engine=true"
     * ----
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @author Roy Golan <rgolan@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
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

        /**
         * When set to `true` it means this host should deploy also hosted
         * engine components. Missing value is treated as `true` i.e deploy.
         * Omitting this parameter means `false` and will perform no operation
         * in hosted engine area.
         */
        @In Boolean deployHostedEngine();

        /**
         * When set to `true` it means this host should un-deploy hosted engine
         * components and this host will not function as part of the High
         * Availability cluster. Missing value is treated as `true` i.e un-deploy
         * Omitting this parameter means `false` and will perform no operation
         * in hosted engine area.
         */
        @In Boolean undeployHostedEngine();
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

    /**
     * Update the host properties.
     *
     * For example, to update a the kernel command line of a host send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/hosts/123
     * ----
     *
     * With request body like this:
     *
     * [source, xml]
     * ----
     * <host>
     *   <os>
     *     <custom_kernel_cmdline>vfio_iommu_type1.allow_unsafe_interrupts=1</custom_kernel_cmdline>
     *   </os>
     * </host>
     * ----
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @In @Out Host host();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Upgrade VDSM and selected software on the host.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Upgrade {
        /**
         * Indicates if the upgrade should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Refresh the host devices and capabilities.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 28 Nov 2016
     * @status added
     */
    interface Refresh {
        /**
         * Indicates if the refresh should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Remove the host from the system.
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
     * --request DELETE \
     * --header "Version: 4" \
     * "${url}/hosts/1ff7a191-2f3b-4eff-812b-9f91a30c3acc"
     * ----
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
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
     * --header "Version: 4" \
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
     *
     * IMPORTANT: To make sure that the network configuration has been saved in the host, and that it will be applied
     * when the host is rebooted, remember to call <<services/host/methods/commit_net_config, commitnetconfig>>.
     */
    interface SetupNetworks {
        @In NetworkAttachment[] modifiedNetworkAttachments();
        @In NetworkAttachment[] removedNetworkAttachments();
        @In NetworkAttachment[] synchronizedNetworkAttachments();
        @In HostNic[] modifiedBonds();
        @In HostNic[] removedBonds();
        @In NetworkLabel[] modifiedLabels();
        @In NetworkLabel[] removedLabels();
        @In Boolean checkConnectivity();
        @In Integer connectivityTimeout();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Reference to the host permission service.
     * Use this service to manage permissions on the host object.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 28 Nov 2016
     * @status added
     */
    @Service AssignedPermissionsService permissions();

    /**
     * Reference to the host tags service.
     * Use this service to manage tags on the host object.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 28 Nov 2016
     * @status added
     */
    @Service AssignedTagsService tags();

    /**
     * Reference to the fence agents service.
     * Use this service to manage fence and power management agents on the host object.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 28 Nov 2016
     * @status added
     */
    @Service FenceAgentsService fenceAgents();

    /**
     * Reference to the host devices service.
     * Use this service to view the devices of the host object.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 28 Nov 2016
     * @status added
     */
    @Service HostDevicesService devices();

    /**
     * Reference to the host hooks service.
     * Use this service to view the hooks available in the host object.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 28 Nov 2016
     * @status added
     */
    @Service HostHooksService hooks();

    /**
     * Reference to the service that manages the network interface devices on the host.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    @Service HostNicsService nics();
    @Service HostNumaNodesService numaNodes();
    @Service HostStorageService storage();

    /**
     * Reference to the service that can show the applicable errata available on the host.
     * This information is taken from Katello.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 28 Nov 2016
     * @status added
     */
    @Service KatelloErrataService katelloErrata();

    /**
     * Reference to the network attachments service. You can use this service to attach
     * Logical networks to host interfaces.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 28 Nov 2016
     * @status added
     */
    @Service NetworkAttachmentsService networkAttachments();
    @Service StorageServerConnectionExtensionsService storageConnectionExtensions();
    @Service UnmanagedNetworksService unmanagedNetworks();

    /**
     * List of scheduling labels assigned to this host.
     */
    @Service AssignedAffinityLabelsService affinityLabels();
}
