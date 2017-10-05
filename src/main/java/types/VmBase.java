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
     * NOTE: Memory in the example is converted to bytes using the following formula: +
     * 1 GiB = 2^30^ bytes = 1073741824 bytes.
     *
     * NOTE: Memory hot plug is supported from {product-name} 3.6 onwards. You can use the example above to increase
     * memory while the virtual machine is running.
     *
     * @author Yanir Quinn <yquinn@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 8 Dec 2016
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
     * Reference to virtual machine's initialization configuration.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
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
}
