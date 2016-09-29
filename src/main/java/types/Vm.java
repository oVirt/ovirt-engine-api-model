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

@Type
public interface Vm extends VmBase {
    VmStatus status();
    String statusDetail();
    String stopReason();
    Date startTime();
    Date stopTime();
    Boolean runOnce();
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
    String fqdn();
    Boolean useLatestTemplateVersion();
    Boolean nextRunConfigurationExists();
    NumaTuneMode numaTuneMode();
    TimeZone guestTimeZone();
    GuestOperatingSystem guestOperatingSystem();

    @Link Host host();
    @Link Template template();
    @Link InstanceType instanceType();
    @Link Nic[] nics();
    @Link Snapshot[] snapshots();
    @Link VmPool vmPool();
    @Link Cdrom[] cdroms();
    @Link Floppy[] floppies();
    @Link ReportedDevice[] reportedDevices();
    @Link Watchdog[] watchdogs();
    @Link Permission[] permissions();
    @Link ExternalHostProvider externalHostProvider();
    @Link AffinityLabel[] affinityLabels();
    @Link Application[] applications();
    @Link GraphicsConsole[] graphicsConsoles();
    @Link HostDevice[] hostDevices();
    @Link KatelloErratum[] katelloErrata();
    @Link NumaNode[] numaNodes();
    @Link Session[] sessions();
    @Link Statistic[] statistics();
    @Link Tag[] tags();

    /**
     * References to the disks attached to the virtual machine.
     */
    @Link DiskAttachment[] diskAttachments();
}
