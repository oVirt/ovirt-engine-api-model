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
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Vm;
import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

@Service
@Area("Virtualization")
public interface VmsService {
    /**
     * Creates a new virtual machine.
     *
     * The virtual machine can be created in different ways:
     *
     * - From a template. In this case the identifier or name of the template must be provided. For example, using a
     *   plain shell script and XML:
     *
     * [source,bash]
     * ----
     * #!/bin/sh -ex
     *
     * url="https://engine.example.com/ovirt-engine/api"
     * user="admin@internal"
     * password="..."
     * curl \
     * --verbose \
     * --cacert /etc/pki/ovirt-engine/ca.pem \
     * --user "${user}:${password}" \
     * --request POST \
     * --header "Version: 4" \
     * --header "Content-Type: application/xml" \
     * --header "Accept: application/xml" \
     * --data '
     * <vm>
     *   <name>myvm</name>
     *   <template>
     *     <name>Blank</name>
     *   </template>
     *   <cluster>
     *     <name>mycluster</name>
     *   </cluster>
     * </vm>
     * ' \
     * "${url}/vms"
     * ----
     *
     * - From a snapshot. In this case the identifier of the snapshot has to be provided. For example, using a plain
     *   shel script and XML:
     *
     * [source,bash]
     * ----
     * #!/bin/sh -ex
     *
     * url="https://engine.example.com/ovirt-engine/api"
     * user="admin@internal"
     * password="..."
     * curl \
     * --verbose \
     * --cacert /etc/pki/ovirt-engine/ca.pem \
     * --user "${user}:${password}" \
     * --request POST \
     * --header "Content-Type: application/xml" \
     * --header "Accept: application/xml" \
     * --data '
     * <vm>
     *   <name>myvm</name>
     *   <snapshots>
     *     <snapshot id="266742a5-6a65-483c-816d-d2ce49746680"/>
     *   </snapshots>
     *   <cluster>
     *     <name>mycluster</name>
     *   </cluster>
     * </vm>
     * ' \
     * "${url}/vms"
     * ----
     *
     * When creating a virtual machine from a template or from a snapshot it is usually useful to explicitly indicate
     * in what storage domain to create the disks for the virtual machine. If the virtual machine is created from
     * a template then this is achieved passing a set of `disk_attachment` elements that indicate the mapping:
     *
     * [source,xml]
     * ----
     * <vm>
     *   ...
     *   <disk_attachments>
     *     <disk_attachment>
     *       <disk id="8d4bd566-6c86-4592-a4a7-912dbf93c298">
     *         <storage_domains>
     *           <storage_domain id="9cb6cb0a-cf1d-41c2-92ca-5a6d665649c9"/>
     *         </storage_domains>
     *       </disk>
     *     <disk_attachment>
     *   </disk_attachments>
     * </vm>
     * ----
     *
     * When the virtual machine is created from a snapshot this set of disks is slightly different, it uses the
     * `image_id` attribute instead of `id`.
     *
     * [source,xml]
     * ----
     * <vm>
     *   ...
     *   <disk_attachments>
     *     <disk_attachment>
     *       <disk>
     *         <image_id>8d4bd566-6c86-4592-a4a7-912dbf93c298</image_id>
     *         <storage_domains>
     *           <storage_domain id="9cb6cb0a-cf1d-41c2-92ca-5a6d665649c9"/>
     *         </storage_domains>
     *       </disk>
     *     <disk_attachment>
     *   </disk_attachments>
     * </vm>
     * ----
     *
     * It is possible to specify additional virtual machine parameters in the XML description, e.g. a virtual machine
     * of `desktop` type, with 2 GiB of RAM and additional description can be added sending a request body like the
     * following:
     *
     * [source,xml]
     * ----
     * <vm>
     *   <name>myvm</name>
     *   <description>My Desktop Virtual Machine</description>
     *   <type>desktop</type>
     *   <memory>2147483648</memory>
     *   ...
     * </vm>
     * ----
     *
     * A bootable CDROM device can be set like this:
     *
     * [source,xml]
     * ----
     * <vm>
     *   ...
     *   <os>
     *     <boot dev="cdrom"/>
     *   </os>
     * </vm>
     * ----
     *
     * In order to boot from CDROM, you first need to insert a disk, as described in the
     * <<services/vm_cdrom, CDROM service>>. Then booting from that CDROM can be specified using the `os.boot.devices`
     * attribute:
     *
     * [source,xml]
     * ----
     * <vm>
     *   ...
     *   <os>
     *     <boot>
     *       <devices>
     *         <device>cdrom</device>
     *       </devices>
     *     </boot>
     *   </os>
     * </vm>
     * ----
     *
     * In all cases the name or identifier of the cluster where the virtual machine will be created is mandatory.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        @In @Out Vm vm();

        /**
         * Specifies if the virtual machine should be independent of the template.
         *
         * When a virtual machine is created from a template by default the disks of the virtual machine depend on
         * the disks of the template, they are using the https://en.wikipedia.org/wiki/Copy-on-write[_copy on write_]
         * mechanism so that only the differences from the template take up real storage space. If this parameter is
         * specified and the value is `true` then the disks of the created virtual machine will be _cloned_, and
         * independent of the template. For example, to create an independent virtual machine, send a request like this:
         *
         * [source]
         * ----
         * POST /ovirt-engine/vms?clone=true
         * ----
         *
         * With a request body like this:
         *
         * [source,xml]
         * ----
         * <vm>
         *   <name>myvm<name>
         *   <template>
         *     <name>mytemplate<name>
         *   </template>
         *   <cluster>
         *     <name>mycluster<name>
         *   </cluster>
         * </vm>
         * ----
         *
         * NOTE: When this parameter is `true` the permissions of the template will also be copied, as when using
         * `clone_permissions=true`.
         */
        @In Boolean clone();

        /**
         * Specifies if the permissions of the template should be copied to the virtual machine.
         *
         * If this optional parameter is provided, and its values is `true` then the permissions of the template (only
         * the direct ones, not the inherited ones) will be copied to the created virtual machine. For example, to
         * create a virtual machine from the `mytemplate` template copying its permissions, send a request like this:
         *
         * [source]
         * ----
         * POST /ovirt-engine/api/vms?clone_permissions=true
         * ----
         *
         * With a request body like this:
         *
         * [source,xml]
         * ----
         * <vm>
         *   <name>myvm<name>
         *   <template>
         *     <name>mytemplate<name>
         *   </template>
         *   <cluster>
         *     <name>mycluster<name>
         *   </cluster>
         * </vm>
         * ----
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 16 Aug 2016
         * @status added
         * @since 4.0.0
         */
        @In Boolean clonePermissions();

        /**
         * Relevant for admin users only. Indicates whether to assign UserVmManager role
         * on the created Virtual Machine for this user. This will enable the user to later access
         * the Virtual Machine as though he were a non-admin user, foregoing his admin permissions
         * (by providing filter=true).
         *
         * NOTE: admin-as-user (meaning providing filter=true) POST requests on an existing Virtual
         * Machine will fail unless the Virtual Machine has been previously created by the admin as
         * a user (meaning with filter=true).
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 27 Jun 2018
         * @status added
         * @since 4.0.0
         */
        @In Boolean filter();

        @InputDetail
        default void inputDetail() {
            optional(vm().bios().bootMenu().enabled());
            optional(vm().comment());
            optional(vm().cpu().topology().cores());
            optional(vm().cpu().topology().sockets());
            optional(vm().cpu().topology().threads());
            optional(vm().cpuProfile().id());
            optional(vm().deleteProtected());
            optional(vm().description());
            optional(vm().display().allowOverride());
            optional(vm().display().copyPasteEnabled());
            optional(vm().display().disconnectAction());
            optional(vm().display().fileTransferEnabled());
            optional(vm().display().keyboardLayout());
            optional(vm().display().monitors());
            optional(vm().display().smartcardEnabled());
            optional(vm().display().type());
            optional(vm().domain().name());
            optional(vm().highAvailability().enabled());
            optional(vm().highAvailability().priority());
            optional(vm().largeIcon().data());
            optional(vm().largeIcon().id());
            optional(vm().largeIcon().mediaType());
            optional(vm().memory());
            optional(vm().memoryPolicy().ballooning());
            optional(vm().memoryPolicy().guaranteed());
            optional(vm().migration().autoConverge());
            optional(vm().migration().compressed());
            optional(vm().migrationDowntime());
            optional(vm().numaTuneMode());
            optional(vm().origin());
            optional(vm().os().cmdline());
            optional(vm().os().initrd());
            optional(vm().os().kernel());
            optional(vm().os().type());
            optional(vm().placementPolicy().affinity());
            optional(vm().quota().id());
            optional(vm().rngDevice().rate().bytes());
            optional(vm().rngDevice().rate().period());
            optional(vm().rngDevice().source());
            optional(vm().serialNumber().policy());
            optional(vm().serialNumber().value());
            optional(vm().smallIcon().id());
            optional(vm().startPaused());
            optional(vm().stateless());
            optional(vm().timeZone().name());
            optional(vm().tunnelMigration());
            optional(vm().type());
            optional(vm().usb().enabled());
            optional(vm().usb().type());
            optional(vm().virtioScsi().enabled());
            or(optional(vm().placementPolicy().hosts()[COLLECTION].id()), optional(vm().placementPolicy().hosts()[COLLECTION].name()));
            optional(vm().customProperties()[COLLECTION].name());
            optional(vm().customProperties()[COLLECTION].value());
            optional(vm().payloads()[COLLECTION].files()[COLLECTION].name());
            optional(vm().payloads()[COLLECTION].files()[COLLECTION].content());
            optional(vm().payloads()[COLLECTION].type());
            optional(vm().payloads()[COLLECTION].volumeId());
            optional(vm().sso().methods()[COLLECTION].id());
            optional(vm().cpu().cpuTune().vcpuPins()[COLLECTION].cpuSet());
            optional(vm().cpu().cpuTune().vcpuPins()[COLLECTION].vcpu());
            optional(vm().storageErrorResumeBehaviour());
            optional(vm().multiQueuesEnabled());
        }

        /**
         * add a virtual machine to the system from scratch
         */
        interface FromScratch extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(vm().name());
                or(mandatory(vm().cluster().id()), mandatory(vm().cluster().name()));
                or(mandatory(vm().template().id()), mandatory(vm().template().name()));
                optional(vm().console().enabled());
                optional(vm().cpu().architecture());
                optional(vm().cpu().mode());
                optional(vm().cpuShares());
                optional(vm().customCpuModel());
                optional(vm().customEmulatedMachine());
                optional(vm().display().singleQxlPci());
                optional(vm().initialization().configuration().data());
                optional(vm().initialization().configuration().type());
                optional(vm().io().threads());
                optional(vm().soundcardEnabled());
                optional(vm().useLatestTemplateVersion());
                or(optional(vm().instanceType().id()), optional(vm().instanceType().name()));
                optional(vm().os().boot().devices()[COLLECTION]);
            }
        }

        /**
         * add a virtual machine to the system by cloning from a snapshot
         */
        interface FromSnapshot extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(vm().name());
                or(mandatory(vm().cluster().id()), mandatory(vm().cluster().name()));
                or(mandatory(vm().template().id()), mandatory(vm().template().name()));
                mandatory(vm().snapshots()[COLLECTION].id());
                optional(vm().console().enabled());
                optional(vm().cpu().architecture());
                optional(vm().cpuShares());
                optional(vm().customCpuModel());
                optional(vm().customEmulatedMachine());
                optional(vm().display().singleQxlPci());
                optional(vm().soundcardEnabled());
            }
        }

        /**
         * add a virtual machine to the system from a configuration - requires the configuration type and the configuration data
         */
        interface FromConfiguration extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(vm().initialization().configuration().data());
                mandatory(vm().initialization().configuration().type());
                optional(vm().cpu().mode());
//                optional(vm().disks().clone());
                optional(vm().initialization().regenerateIds());
                optional(vm().name());
                optional(vm().os().boot().devices()[COLLECTION]);
            }
        }
    }

    /**
     * Returns the list of virtual machines of the system.
     *
     * The order of the returned list of virtual machines is guaranteed only if the `sortby` clause is included
     * in the `search` parameter.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out Vm[] vms();

        /**
         * A query string used to restrict the returned virtual machines.
         */
        @In String search();

        /**
         * The maximum number of results to return.
         */
        @In Integer max();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();

        /**
         * Indicates if all the attributes of the virtual machines should be included in the response.
         *
         * By default the following attributes are excluded:
         *
         * - `console`
         * - `initialization.configuration.data` - The OVF document describing the virtual machine.
         * - `rng_source`
         * - `soundcard`
         * - `virtio_scsi`
         *
         * For example, to retrieve the complete representation of the virtual machines send a request like this:
         *
         * ....
         * GET /ovirt-engine/api/vms?all_content=true
         * ....
         *
         * NOTE: The reason for not including these attributes is performance: they are seldom used and they require
         * additional queries to the database. So try to use the this parameter only when it is really needed.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 11 Oct 2016
         * @status added
         * @since 4.0.6
         */
        @In Boolean allContent();
    }

    @Service VmService vm(String id);
}
