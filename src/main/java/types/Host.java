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

@Type
public interface Host extends Identified {
    String address();
    HostStatus status();
    String statusDetail();
    Certificate certificate();
    ExternalStatus externalStatus();
    Integer port();

    /**
     * Indicates if the host contains a full installation of the operating system or a scaled down version intended
     * only to host virtual machines.
     */
    HostType type();

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
     * Will respond:
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
     * @date 14 Sept 2016
     * @status added
     */
    Version version();
    HardwareInformation hardwareInformation();
    PowerManagement powerManagement();
    Ksm ksm();
    TransparentHugePages transparentHugePages();
    IscsiDetails iscsi();

    /**
     * When creating a new host, a root password is required in case password authentication method is chosen,
     * but this is not subsequently included in the representation.
     */
    String rootPassword();

    Ssh ssh();
    Cpu cpu();
    Integer memory();
    Integer maxSchedulingMemory();
    VmSummary summary();
    Boolean overrideIptables();
    HostProtocol protocol();
    OperatingSystem os();
    Version libvirtVersion();

    /**
     * Optionally specify the display address of this host explicitly.
     */
    Display display();

    HostedEngine hostedEngine();
    KdumpStatus kdumpStatus();
    SeLinux seLinux();
    AutoNumaStatus autoNumaStatus();
    Boolean numaSupported();
    Boolean updateAvailable();
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
