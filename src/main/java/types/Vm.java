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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

import java.util.Date;

/**
 * Represents a virtual machine.
 *
 * @author Marek Libra <mlibra@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface Vm extends VmBase {
    /**
     * The current status of the virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    VmStatus status();

    /**
     * Human readable detail of current status.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String statusDetail();

    /**
     * The reason the virtual machine was stopped.
     * Optionally set by user when shutting down the virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String stopReason();

    /**
     * The date in which the virtual machine was started.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Date startTime();

    /**
     * The date in which the virtual machine was stopped.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Date stopTime();

    /**
     * If `true`, the virtual machine has been started using the _run once_ command, meaning
     * it's configuration might differ from the stored one for the purpose of this single run.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean runOnce();

    /**
     * Optional payloads of the virtual machine, used for ISOs to configure it.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Payload[] payloads();

    /**
     * The configuration of the virtual machine's placement policy.
     *
     * This configuration can be updated to pin a virtual machine to one or more hosts.
     *
     * NOTE: Virtual machines that are pinned to multiple hosts cannot be live migrated, but in the event of a host
     * failure, any virtual machine configured to be highly available is automatically restarted on one of the other
     * hosts to which the virtual machine is pinned.
     *
     * For example, to pin a virtual machine to two hosts, you would send a request like the following:
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
     * @date 14 Sep 2016
     * @status added
     */
    VmPlacementPolicy placementPolicy();

    /**
     * Fully qualified domain name of the virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String fqdn();

    /**
     * If `true`, the virtual machine is reconfigured to the latest version of it's template when it is started.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean useLatestTemplateVersion();

    /**
     * Virtual machine configuration has been changed and requires restart of the virtual machine.
     * Changed configuration is applied at processing the virtual machine's _shut down_.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean nextRunConfigurationExists();

    /**
     * How the NUMA topology is applied.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    NumaTuneMode numaTuneMode();

    /**
     * What time zone is used by the virtual machine (as returned by guest agent).
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    TimeZone guestTimeZone();

    /**
     * What operating system is installed on the virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    GuestOperatingSystem guestOperatingSystem();

    /**
     * Reference to the host the virtual machine is running on.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Host host();

    /**
     * Reference to the template the virtual machine is based on.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Template template();

    /**
     * References to the original template the virtual machine was created from.
     *
     * If the virtual machine is cloned from a template or another virtual machine,
     * the `template` links to the Blank template and the `original_template`
     * is used to track history.
     *
     * Otherwise the `template` and `original_template` are the same.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 8 Jul 2016
     * @status added
     */
    @Link Template originalTemplate();

    /**
     * The virtual machine configuration can be optionally predefined via one of the instance types.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link InstanceType instanceType();

    /**
     * Link to the the list of network interface devices on the virtual machine.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    @Link Nic[] nics();

    /**
     * Refers to all snapshots taken from the virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Snapshot[] snapshots();

    /**
     * Reference to the pool the virtual machine is optionally member of.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link VmPool vmPool();

    /**
     * Reference to the ISO mounted to the CDROM.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Cdrom[] cdroms();

    /**
     * Reference to the ISO mounted to the floppy.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Floppy[] floppies();
    @Link ReportedDevice[] reportedDevices();

    /**
     * Refers to the Watchdog configuration.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Watchdog[] watchdogs();

    /**
     * Permissions set for this virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Permission[] permissions();
    @Link ExternalHostProvider externalHostProvider();

    /**
     * Optional. Used for labeling of sub-clusters.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link AffinityLabel[] affinityLabels();

    /**
     * List of applications installed on the virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Application[] applications();

    /**
     * List of graphics consoles configured for this virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link GraphicsConsole[] graphicsConsoles();

    /**
     * References devices associated to this virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link HostDevice[] hostDevices();
    @Link KatelloErratum[] katelloErrata();

    /**
     * Refers to the NUMA Nodes configuration used by this virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link NumaNode[] numaNodes();

    /**
     * List of user sessions opened for this virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Session[] sessions();

    /**
     * Statistics data collected from this virtual machine.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Statistic[] statistics();
    @Link Tag[] tags();

    /**
     * References to the disks attached to the virtual machine.
     */
    @Link DiskAttachment[] diskAttachments();
}
