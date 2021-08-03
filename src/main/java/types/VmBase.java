/*
Copyright (c) 2015-2016 Red Hat, Inc.

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

import java.util.Date;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents basic virtual machine configuration.
 * This is used by virtual machines, templates and instance types.
 *
 * @author Marek Libra <mlibra@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface VmBase extends Identified {
    /**
     * Determines whether the virtual machine is optimized for desktop or server.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    VmType type();

    /**
     * The virtual machine's memory, in bytes.
     *
     * For example, to update a virtual machine to contain 1 Gibibyte (GiB) of memory, send the following request:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/vms/123
     * ----
     *
     * With the following request body:
     *
     * [source,xml]
     * ----
     * <vm>
     *   <memory>1073741824</memory>
     * </vm>
     * ----
     *
     * Memory hot plug is supported from {product-name} 3.6 onwards. You can use the example above to increase
     * memory while the virtual machine is in state <<types/vm_status/values/up, up>>. The size increment must be
     * dividable by the value of the `HotPlugMemoryBlockSizeMb` configuration value (256 MiB by default). If the memory
     * size increment is not dividable by this value, the memory size change is only stored to next run configuration.
     * Each successful memory hot plug operation creates one or two new memory devices.
     *
     * Memory hot unplug is supported since {product-name} 4.2 onwards. Memory hot unplug can only be performed
     * when the virtual machine is in state <<types/vm_status/values/up, up>>. Only previously hot plugged memory
     * devices can be removed by the hot unplug operation. The requested memory decrement is rounded down to match sizes
     * of a combination of previously hot plugged memory devices. The requested memory value is stored to next run
     * configuration without rounding.
     *
     * NOTE: Memory in the example is converted to bytes using the following formula: +
     * 1 GiB = 2^30^ bytes = 1073741824 bytes.
     *
     * NOTE: {engine-name} internally rounds values down to whole MiBs (1MiB = 2^20^ bytes)
     *
     * @author Yanir Quinn <yquinn@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Nov 2017
     * @status updated_by_docs
     */
    Integer memory();

    /**
     * The configuration of the virtual machine CPU.
     *
     * The socket configuration can be updated without rebooting the virtual machine. The cores and the threads require
     * a reboot.
     *
     * For example, to change the number of sockets to 4 immediately, and the number of cores and threads to 2 after
     * reboot, send the following request:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/vms/123
     * ----
     *
     * With a request body:
     *
     * [source,xml]
     * ----
     * <vm>
     *   <cpu>
     *     <topology>
     *       <sockets>4</sockets>
     *       <cores>2</cores>
     *       <threads>2</threads>
     *     </topology>
     *   </cpu>
     * </vm>
     * ----
     *
     * @author Jenny Tokar <jtokar@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 8 Dec 2016
     * @status updated_by_docs
     */
    Cpu cpu();
    Integer cpuShares();

    /**
     * Reference to virtual machine's BIOS configuration.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Bios bios();

    /**
     * Operating system type installed on the virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    OperatingSystem os();

    /**
     * The virtual machine creation date.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Date creationTime();

    /**
     * The origin of this virtual machine.
     *
     * Possible values:
     *
     * - `ovirt`
     * - `rhev`
     * - `vmware`
     * - `xen`
     * - `external`
     * - `hosted_engine`
     * - `managed_hosted_engine`
     * - `kvm`
     * - `physical_machine`
     * - `hyperv`
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String origin();

    /**
     * If `true`, the virtual machine is stateless - it's state (disks) are rolled-back after shutdown.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean stateless();

    /**
     * If `true`, the virtual machine cannot be deleted.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean deleteProtected();

    /**
     * The virtual machine high availability configuration.
     * If set, the virtual machine will be automatically restarted when it unexpectedly goes down.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    HighAvailability highAvailability();

    /**
     * The virtual machine display configuration.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Display display();

    /**
     * Reference to the Single Sign On configuration this virtual machine is configured for.
     * The user can be automatically signed in the virtual machine's operating system when console is opened.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Sso sso();

    /**
     * Random Number Generator device configuration for this virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    RngDevice rngDevice();

    /**
     * Console configured for this virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Console console();

    /**
     * Domain configured for this virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Domain domain();

    /**
     * Configuration of USB devices for this virtual machine (count, type).
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Usb usb();

    /**
     * If `true`, the sound card is added to the virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean soundcardEnabled();

    /**
     * If `true`, the network data transfer will be encrypted during virtual machine live migration.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean tunnelMigration();

    /**
     * Maximum time the virtual machine can be non responsive during its live migration to another host in ms.
     *
     * Set either explicitly for the virtual machine or by `engine-config -s DefaultMaximumMigrationDowntime=[value]`
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Integer migrationDowntime();

    /**
     * Reference to VirtIO SCSI configuration.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    VirtioScsi virtioScsi();

    /**
     * Virtual machine's serial number in a cluster.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    SerialNumber serialNumber();

    /**
     * If `true`, the virtual machine will be initially in 'paused' state after start.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean startPaused();

    /**
     * Reference to configuration of migration of running virtual machine to another host.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    MigrationOptions migration();

    /**
     * For performance tuning of IO threading.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Io io();

    /**
     * Properties sent to VDSM to configure various hooks.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    CustomProperty[] customProperties();
    String customEmulatedMachine();
    String customCpuModel();

    /**
     * The virtual machine's time zone set by oVirt.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    TimeZone timeZone();

    /**
     * Virtual machine's small icon. Either set by user or refers to image set according to operating system.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Icon smallIcon();

    /**
     * Virtual machine's large icon. Either set by user or refers to image set according to operating system.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Icon largeIcon();

    /**
     * Reference to the virtual machine's initialization configuration.
     *
     * NOTE: Since {product-name} 4.1.8 this property can be cleared by sending an empty tag.
     *
     * For example, to clear the `initialization` attribute send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/vms/123
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <vm>
     *   <initialization/>
     * </vm>
     * ----
     *
     * The response to such a request, and requests with the header `All-Content: true` will still contain this attribute.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 23 Nov 2017
     * @status updated_by_docs
     */
    Initialization initialization();

    /**
     * Reference to virtual machine's memory management configuration.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    MemoryPolicy memoryPolicy();

    /**
     * Virtual machine custom compatibility version.
     *
     * Enables a virtual machine to be customized to its own compatibility version.  If
     * `custom_compatibility_version` is set, it overrides the cluster's compatibility version
     * for this particular virtual machine.
     *
     * The compatibility version of a virtual machine is limited by the data center the virtual
     * machine resides in, and is checked against capabilities of the host the virtual machine is
     * planned to run on.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 8 Dec 2016
     * @status updated_by_docs
     */
    Version customCompatibilityVersion();

    /**
     * Reference to cluster the virtual machine belongs to.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Cluster cluster();

    /**
     * Reference to storage domain the virtual machine belongs to.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link StorageDomain storageDomain();

    /**
     * Reference to CPU profile used by this virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link CpuProfile cpuProfile();

    /**
     * Reference to quota configuration set for this virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Quota quota();

    /**
     * Reference to the storage domain this virtual machine/template lease reside on.
     *
     * A virtual machine running with a lease requires checking while running that the lease is not taken by another
     * host, preventing another instance of this virtual machine from running on another host.
     * This provides protection against split-brain in highly available virtual machines.
     * A template can also have a storage domain defined for a lease in order to have the virtual machines created
     * from this template to be preconfigured with this storage domain as the location of the leases.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 11 Jan 2017
     * @status added
     * @since 4.1
     */
    StorageDomainLease lease();

    /**
     * Determines how the virtual machine will be resumed after storage error.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 5 Oct 2017
     * @status added
     * @since 4.2
     */
    VmStorageErrorResumeBehaviour storageErrorResumeBehaviour();

    /**
     * The configuration of the virtual machine's placement policy.
     *
     * This configuration can be updated to pin a virtual machine to one or more hosts.
     *
     * NOTE: Virtual machines that are pinned to multiple hosts cannot be live migrated, but in the event of a host
     * failure, any virtual machine configured to be highly available is automatically restarted on one of the other
     * hosts to which the virtual machine is pinned.
     *
     * For example, to pin a virtual machine to two hosts, send the following request:
     *
     * [source]
     * ----
     * PUT /api/vms/123
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <vm>
     *   <high_availability>
     *     <enabled>true</enabled>
     *     <priority>1</priority>
     *   </high_availability>
     *   <placement_policy>
     *     <hosts>
     *       <host>
     *         <name>Host1</name>
     *       </host>
     *       <host>
     *         <name>Host2</name>
     *       </host>
     *     </hosts>
     *     <affinity>pinned</affinity>
     *   </placement_policy>
     * </vm>
     * ----
     *
     * @author Phillip Bailey <phbailey@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 02 Dec 2016
     * @status updated_by_docs
     */
    VmPlacementPolicy placementPolicy();

     /**
     * If `true`, each virtual interface will get the optimal number of queues, depending on the available virtual Cpus.
     *
     * @author Alona Kaplan <alkaplan@redhat.com>
     * @date 14 June 2018
     * @status added
     * @since 4.2.5
     */
    Boolean multiQueuesEnabled();

     /**
     * If `true`, the Virtio-SCSI devices will obtain a number of multiple queues depending on
     * the available virtual Cpus and disks, or according to the specified virtioScsiMultiQueues.
     *
     * @author Steven Rosenberg <srosenbe@redhat.com>
     * @author Saif Abu Saleh <sabusale@redhat.com>
     * @date 05 Jan 2021
     * @status added
     * @since 4.4.5
     */

    Boolean virtioScsiMultiQueuesEnabled();

    /**
     * Number of queues for a Virtio-SCSI contoller
     * this field requires virtioScsiMultiQueuesEnabled to be true
     * see virtioScsiMultiQueuesEnabled for more info
     *
     * @author Saif Abu Saleh <sabusale@redhat.com>
     * @date 06 Jul 2021
     * @status added
     * @since 4.4.8
     */

    Integer virtioScsiMultiQueues();

    /**
     * If `true`, a TPM device is added to the virtual machine. By default the value is `false`.
     * This property is only visible when fetching if "All-Content=true" header is set.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 21 Feb 2021
     * @status added
     * @since 4.4.5
     */
    Boolean tpmEnabled();

    /**
     * Specifies if and how the auto CPU and NUMA configuration is applied.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 03 Aug 2021
     * @status added
     * @since 4.4.8
     */
    AutoPinningPolicy autoPinningPolicy();
}
