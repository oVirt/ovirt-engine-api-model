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
     * The protocol that the engine uses to communicate with the host.
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
     * The hosted engine status on this host.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
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
    @Link KatelloErratum[] katelloErrata();
    @Link NetworkAttachment[] networkAttachments();
    @Link Nic[] nics();
    @Link NumaNode[] numaNodes();
    @Link Permission[] permissions();
    @Link Statistic[] statistics();
    @Link HostStorage[] storages();
    @Link Tag[] tags();
    @Link UnmanagedNetwork[] unmanagedNetworks();
}
