/*
Copyright (c) 2015-2017 Red Hat, Inc.

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
 * Type representing a host.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 01 Dec 2016
 * @status added
 */
@Type
public interface Host extends Identified {
    /**
     * The host address (FQDN/IP).
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    String address();

    /**
     * The host status.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    HostStatus status();

    /**
     * The host status details. Relevant for Gluster hosts.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    String statusDetail();

    /**
     * The host certificate.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    Certificate certificate();

    /**
     * The host external status.
     * This can be used by third-party software to change the host external status
     * in case of an issue.
     * This has no effect on the host lifecycle, unless a third-party software checks for this
     * status and acts accordingly.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    ExternalStatus externalStatus();

    /**
     * The host port.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    Integer port();

    /**
     * Indicates if the host contains a full installation of the operating system or a scaled-down version intended
     * only to host virtual machines.
     *
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    HostType type();

    /**
     * The host _storage pool manager_ (SPM) status and definition.
     * Use it to set the SPM priority of this host, and to see whether this is the current
     * SPM or not.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    Spm spm();

    /**
     * The version of VDSM.
     *
     * For example:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/hosts/123
     * ----
     *
     * This `GET` request will return the following output:
     *
     * [source,xml]
     * ----
     * <host>
     *   ...
     *   <version>
     *     <build>999</build>
     *     <full_version>vdsm-4.18.999-419.gitcf06367.el7</full_version>
     *     <major>4</major>
     *     <minor>18</minor>
     *     <revision>0</revision>
     *   </version>
     *   ...
     * </host>
     * ----
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    Version version();

    /**
     * The host hardware information.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    HardwareInformation hardwareInformation();

    /**
     * The host power management definitions.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    PowerManagement powerManagement();

    /**
     * Kernel SamePage Merging (KSM) reduces references to memory pages from multiple identical pages to a single page
     * reference. This helps with optimization for memory density.
     *
     * For example, to enable KSM for host `123`, send a request like this:
     *
     * ....
     * PUT /ovirt-engine/api/hosts/123
     * ....
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <host>
     *   <ksm>
     *     <enabled>true</enabled>
     *   </ksm>
     * </host>
     * ----
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sept 2016
     * @status added
     */
    Ksm ksm();

    /**
     * Transparent huge page support expands the size of memory pages beyond the standard 4 KiB limit. This reduces
     * memory consumption and increases host performance.
     *
     * For example, to enable transparent huge page support for host `123`, send a request like this:
     *
     * ....
     * PUT /ovirt-engine/api/hosts/123
     * ....
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <host>
     *   <transparent_hugepages>
     *     <enabled>true</enabled>
     *   </transparent_hugepages>
     * </host>
     * ----
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sept 2016
     * @status added
     */
    TransparentHugePages transparentHugePages();

    /**
     * The host iSCSI details.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    IscsiDetails iscsi();

    /**
     * When creating a new host, a root password is required if the password authentication method is chosen,
     * but this is not subsequently included in the representation.
     *
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    String rootPassword();

    /**
     * The SSH definitions.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    Ssh ssh();

    /**
     * The CPU type of this host.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    Cpu cpu();

    /**
     * The amount of physical memory on this host in bytes.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    Integer memory();

    /**
     * The max scheduling memory on this host in bytes.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    Integer maxSchedulingMemory();

    /**
     * The virtual machine summary - how many are active, migrating and total.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    VmSummary summary();

    /**
     * Specifies whether we should override firewall definitions.
     * This applies only when the host is installed or re-installed.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    Boolean overrideIptables();

    /**
     * The protocol that the engine uses to communicate with
     * the host.
     *
     * WARNING: Since version 4.1 of the engine the protocol
     * is always set to `stomp` since `xml` was removed.
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    HostProtocol protocol();

    /**
     * The operating system on this host.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    OperatingSystem os();

    /**
     * The host libvirt version.
     * For more information on libvirt please go to https://libvirt.org[libvirt].
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    Version libvirtVersion();

    /**
     * Optionally specify the display address of this host explicitly.
     *
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    Display display();

    /**
     * The self-hosted engine status of this host.
     *
     * IMPORTANT: When a host or collection of hosts is retrieved, this attribute is not included unless the
     * `all_content` parameter of the operation is explicitly set to `true`. See the documentation of the
     * operations that retrieve <<services/host/methods/get/parameters/all_content, one>> or
     * <<services/hosts/methods/list/parameters/all_content, multiple>> hosts for details.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 21 Apr 2017
     * @status updated_by_docs
     */
    HostedEngine hostedEngine();

    /**
     * The host KDUMP status.
     * KDUMP happens when the host kernel has crashed and it is now going through memory dumping.
     *
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    KdumpStatus kdumpStatus();

    /**
     * The host SElinux status.
     * _Security-Enhanced Linux (SELinux)_ is a component in the Linux kernel
     * that provides a mechanism for supporting access control security policies.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    SeLinux seLinux();

    /**
     * The host auto _non uniform memory access_ (NUMA) status.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    AutoNumaStatus autoNumaStatus();

    /**
     * Specifies whether _non uniform memory access_ (NUMA) is supported on this host.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    Boolean numaSupported();

    /**
     * Specifies whether there is an oVirt-related update on this host.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    Boolean updateAvailable();

    /**
     * Specifies whether a network-related operation, such as 'setup networks' or
     * 'sync networks', is currently being executed on this host.
     *
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @since 4.2.6
     * @date 08 Aug 2018
     * @status updated_by_docs
     */
    Boolean networkOperationInProgress();

    /**
     * Specifies whether host device passthrough is enabled on this host.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    HostDevicePassthrough devicePassthrough();

    @Link Cluster cluster();
    @Link Hook[] hooks();
    @Link ExternalHostProvider externalHostProvider();
    @Link StorageConnectionExtension[] storageConnectionExtensions();
    @Link AffinityLabel[] affinityLabels();
    @Link Device[] devices();
    @Link Agent[] agents();

    /**
     * Lists all the Katello errata assigned to the host.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/hosts/123/katelloerrata
     * ----
     *
     * You will receive response in XML like this one:
     *
     * [source,xml]
     * ----
     * <katello_errata>
     *   <katello_erratum href="/ovirt-engine/api/katelloerrata/456" id="456">
     *     <name>RHBA-2013:XYZ</name>
     *     <description>The description of the erratum</description>
     *     <title>some bug fix update</title>
     *     <type>bugfix</type>
     *     <issued>2013-11-20T02:00:00.000+02:00</issued>
     *     <solution>Few guidelines regarding the solution</solution>
     *     <summary>Updated packages that fix one bug are now available for XYZ</summary>
     *     <packages>
     *       <package>
     *         <name>libipa_hbac-1.9.2-82.11.el6_4.i686</name>
     *       </package>
     *       ...
     *     </packages>
     *   </katello_erratum>
     *   ...
     * </katello_errata>
     * ----
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link KatelloErratum[] katelloErrata();

    @Link NetworkAttachment[] networkAttachments();
    @Link HostNic[] nics();
    @Link NumaNode[] numaNodes();
    @Link Permission[] permissions();

    /**
     * Each host resource exposes a statistics sub-collection for host-specific statistics.
     *
     * An example of an XML representation:
     *
     * [source,xml]
     * ----
     * <statistics>
     *   <statistic href="/ovirt-engine/api/hosts/123/statistics/456" id="456">
     *     <name>memory.total</name>
     *     <description>Total memory</description>
     *     <kind>gauge</kind>
     *     <type>integer</type>
     *     <unit>bytes</unit>
     *     <values>
     *       <value>
     *         <datum>25165824000</datum>
     *       </value>
     *     </values>
     *     <host href="/ovirt-engine/api/hosts/123" id="123"/>
     *   </statistic>
     *     ...
     * </statistics>
     * ----
     *
     * NOTE: This statistics sub-collection is read-only.
     *
     * The following list shows the statistic types for hosts:
     *
     * |===
     * |Name |Description
     *
     * |`memory.total`
     * |Total memory in bytes on the host.
     *
     * |`memory.used`
     * |Memory in bytes used on the host.
     *
     * |`memory.free`
     * |Memory in bytes free on the host.
     *
     * |`memory.shared`
     * |Memory in bytes shared on the host.
     *
     * |`memory.buffers`
     * |I/O buffers in bytes.
     *
     * |`memory.cached`
     * |OS caches in bytes.
     *
     * |`swap.total`
     * |Total swap memory in bytes on the host.
     *
     * |`swap.free`
     * |Swap memory in bytes free on the host.
     *
     * |`swap.used`
     * |Swap memory in bytes used on the host.
     *
     * |`swap.cached`
     * |Swap memory in bytes also cached in host's memory.
     *
     * |`ksm.cpu.current`
     * |Percentage of CPU usage for Kernel SamePage Merging.
     *
     * |`cpu.current.user`
     * |Percentage of CPU usage for user slice.
     *
     * |`cpu.current.system`
     * |Percentage of CPU usage for system.
     *
     * |`cpu.current.idle`
     * |Percentage of idle CPU usage.
     *
     * |`cpu.load.avg.5m`
     * |CPU load average per five minutes.
     *
     * |`boot.time`
     * |Boot time of the machine.
     * |===
     *
     * @author Artyom Lukianov <alukiano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Statistic[] statistics();
    @Link HostStorage[] storages();
    @Link Tag[] tags();
    @Link UnmanagedNetwork[] unmanagedNetworks();

    /**
     * External network providers provisioned on the host.
     *
     * External network providers on the host can be controlled when <<services/hosts/methods/add,adding the host>>.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 09 Oct 2017
     * @status updated_by_docs
     * @since 4.2
     */
    @Link ExternalNetworkProviderConfiguration[] externalNetworkProviderConfigurations();
}
