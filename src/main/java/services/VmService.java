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
import services.externalhostproviders.KatelloErrataService;
import types.AuthorizedKey;
import types.Cluster;
import types.Disk;
import types.Host;
import types.Snapshot;
import types.StorageDomain;
import types.StorageDomainLease;
import types.Vm;
import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

@Service
@Area("Virtualization")
public interface VmService extends MeasurableService {
    /**
     * This operation stops any migration of a virtual machine to another physical host.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/cancelmigration
     * ----
     *
     * The cancel migration action does not take any action specific parameters;
     * therefore, the request body should contain an empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface CancelMigration {
        /**
         * Indicates if the migration should cancelled asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Permanently restores the virtual machine to the state of the previewed snapshot.
     *
     * See the <<services/vm/methods/preview_snapshot, preview_snapshot>> operation for details.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface CommitSnapshot {
        /**
         * Indicates if the snapshots should be committed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    interface Clone {
        @In Vm vm();

        /**
         * Indicates if the clone should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();

        @InputDetail
        default void inputDetail() {
            mandatory(vm().name());
        }
    }

    /**
     * Detaches a virtual machine from a pool.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/detach
     * ----
     *
     * The detach action does not take any action specific parameters; therefore, the request body should contain an
     * empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface Detach {
        /**
         * Indicates if the detach action should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Exports the virtual machine.
     *
     * A virtual machine can be exported to a storage domain.
     * For example, to export virtual machine `123` to the storage domain `mysd`:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/export
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <action>
     *   <storage_domain>
     *     <name>mysd</name>
     *   </storage_domain>
     *   <exported_vm_name>myvm-export</exported_vm_name>
     *   <discard_snapshots>true</discard_snapshots>
     * </action>
     * ----
     *
     * Since version 4.2 of the engine it is also possible to export a virtual machine as a virtual appliance (OVA).
     * For example, to export virtual machine `123` as an OVA file named `myvm.ova` that is placed in the directory `/home/ovirt/` on host `myhost`:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/export
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <action>
     *   <host>
     *     <name>myhost</name>
     *   </host>
     *   <directory>/home/ovirt</directory>
     *   <filename>myvm.ova</filename>
     * </action>
     * ----
     *
     * @author Tal Nisan <tisan@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 10 Nov 2017
     * @status added
     */
    interface Export {
        /**
         * Use the `discard_snapshots` parameter when the virtual machine should be exported with all of its
         * snapshots collapsed.
         *
         * @author Tal Nisan <tisan@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean discardSnapshots();

        /**
         * Use the `exclusive` parameter when the virtual machine should be exported even if another copy of
         * it already exists in the export domain (override).
         *
	 * @deprecated This parameter will be ignored, a unique name should be chosen for the exported VM
         * @author Tal Nisan <tisan@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean exclusive();

        /**
         * Use the optional `exported_vm_name` parameter to set the name for the exported virtual machine.
         * The name has to be unique. If the name is not set, the exported virtual machine name
         * will have an `-export`. For example: `myvm-export`.
         *
         * @author Benny Zlotnik
         * @date 23 Oct 2019
         * @status added
	 * @since 4.4
         */
        @In String exportedVmName();

        /**
         * The (export) storage domain to export the virtual machine to.
         *
         * @author Arik Hadas <ahadas@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 14 Nov 2017
         * @status updated_by_docs
         */
        @In StorageDomain storageDomain();

        /**
         * Indicates if the export should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();

        /**
         * Exports a virtual machine to an export domain.
         *
         * @author Arik Hadas <ahadas@redhat.com>
         * @date 10 Nov 2017
         * @status added
         */
        interface ToExportDomain extends Export {
            @InputDetail
            default void inputDetail() {
                optional(discardSnapshots());
                optional(exclusive());
                or(mandatory(storageDomain().id()), mandatory(storageDomain().name()));
            }
        }

        /**
         * Exports a virtual machine as an OVA file to a given path on a specified host.
         *
         * @author Arik Hadas <ahadas@redhat.com>
         * @date 09 Nov 2017
         * @status added
         * @since 4.2
         */
        interface ToPathOnHost extends Export {
            /**
             * The host to generate the OVA file on.
             *
             * @author Arik Hadas <ahadas@redhat.com>
             * @date 09 Nov 2017
             * @status added
             * @since 4.2
             */
            @In Host host();

            /**
             * An absolute path of a directory on the host to generate the OVA file in.
             *
             * @author Arik Hadas <ahadas@redhat.com>
             * @date 09 Nov 2017
             * @status added
             * @since 4.2
             */
            @In String directory();

            /**
             * The name of the OVA file.
             *
             * This is an optional parameter, if it is not specified then the name of OVA file is determined according
             * to the name of the virtual machine. It will conform the following pattern: "<virtual machine name>.ova".
             *
             * @author Arik Hadas <ahadas@redhat.com>
             * @date 14 Nov 2017
             * @status added
             * @since 4.2
             */
            @In String filename();

            @InputDetail
            default void inputDetail() {
                mandatory(directory());
                optional(filename());
                or(mandatory(host().id()), mandatory(host().name()));
            }
        }

        @InputDetail
        default void inputDetail() {
            optional(exportedVmName());
        }
    }

    /**
     * Freezes virtual machine file systems.
     *
     * This operation freezes a virtual machine's file systems using the QEMU guest agent when taking a live snapshot of
     * a running virtual machine. Normally, this is done automatically by the manager, but this must be executed
     * manually with the API for virtual machines using OpenStack Volume (Cinder) disks.
     *
     * Example:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/freezefilesystems
     * ----
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Daniel Erez <derez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     * @status added
     */
    interface FreezeFilesystems {
        /**
         * Indicates if the freeze should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Retrieves the description of the virtual machine.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface Get extends Follow {
        /**
         * Description of the virtual machine.
         *
         * @author Shmuel Melamud <smelamud@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @Out Vm vm();

        /**
         * Indicates if the returned result describes the virtual machine as it is currently running or if describes
         * the virtual machine with the modifications that have already been performed but that will only come into
         * effect when the virtual machine is restarted. By default the value is `false`.
         *
         * If the parameter is included in the request, but without a value, it is assumed that the value is `true`. The
         * the following request:
         *
         * [source]
         * ----
         * GET /vms/{vm:id};next_run
         * ----
         *
         * Is equivalent to using the value `true`:
         *
         * [source]
         * ----
         * GET /vms/{vm:id};next_run=true
         * ----
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean nextRun();

        /**
         * Indicates if all of the attributes of the virtual machine should be included in the response.
         *
         * By default the following attributes are excluded:
         *
         * - `console`
         * - `initialization.configuration.data` - The OVF document describing the virtual machine.
         * - `rng_source`
         * - `soundcard`
         * - `virtio_scsi`
         *
         * For example, to retrieve the complete representation of the virtual machine '123':
         *
         * ....
         * GET /ovirt-engine/api/vms/123?all_content=true
         * ....
         *
         * NOTE: These attributes are not included by default as they reduce performance. These attributes are seldom used
         * and require additional queries to the database. Only use this parameter when required as it will reduce performance.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         * @since 4.0.6
         */
        @In Boolean allContent();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean filter();
    }

    /**
     * Initiates the automatic user logon to access a virtual machine from an external console.
     *
     * This action requires the `ovirt-guest-agent-gdm-plugin` and the `ovirt-guest-agent-pam-module` packages to be
     * installed and the `ovirt-guest-agent` service to be running on the virtual machine.
     *
     * Users require the appropriate user permissions for the virtual machine in order to access the virtual machine
     * from an external console.
     *
     * For example:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/logon
     * ----
     *
     * Request body:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Vinzenz Feenstra <vfeenstr@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface Logon {
        /**
         * Indicates if the logon should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Sets the global maintenance mode on the hosted engine virtual machine.
     *
     * This action has no effect on other virtual machines.
     *
     * Example:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/maintenance
     * ----
     *
     * [source,xml]
     * ----
     * <action>
     *   <maintenance_enabled>true<maintenance_enabled/>
     * </action>
     * ----
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface Maintenance {
        /**
         * Indicates if global maintenance should be enabled or disabled.
         *
         * @author Andrej Krejcir <akrejcir@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */

        @In Boolean maintenanceEnabled();

        /**
         * Indicates if the global maintenance action should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();

        @InputDetail
        default void inputDetail() {
            mandatory(maintenanceEnabled());
        }
    }

    /**
     * Migrates a virtual machine to another physical host.
     *
     * Example:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/migrate
     * ----
     *
     * To specify a specific host to migrate the virtual machine to:
     *
     * [source,xml]
     * ----
     * <action>
     *   <host id="2ab5e1da-b726-4274-bbf7-0a42b16a0fc3"/>
     * </action>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface Migrate {
        /**
         * Specifies the cluster the virtual machine should migrate to. This is an optional parameter. By default, the
         * virtual machine is migrated to another host within the same cluster.
         *
         * WARNING: Live migration to another cluster is not supported. Strongly consider the target cluster's hardware
         * architecture and network architecture before attempting a migration.
         *
         * @author Arik Hadas <ahadas@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 09 Jul 2018
         * @status updated_by_docs
         */
        @In Cluster cluster();

        /**
         * Specifies that the virtual machine should migrate even if the virtual machine is defined as non-migratable.
         * This is an optional parameter. By default, it is set to `false`.
         *
         * @author Arik Hadas <ahadas@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean force();

        /**
         * Specifies a specific host that the virtual machine should migrate to. This is an optional parameter. By default,
         * the {engine-name} automatically selects a default host for migration within the same cluster. If an API user
         * requires a specific host, the user can specify the host with either an `id` or `name` parameter.
         *
         * @author Arik Hadas <ahadas@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Host host();

        /**
         * Migrate also all other virtual machines in positive enforcing affinity groups with this virtual machine,
         * that are running on the same host.
         *
         * The default value is `false`.
         *
         * @author Andrej Krejcir <akrejcir@redhat.com>
         * @date 15 Apr 2019
         * @status added
         */
        @In Boolean migrateVmsInAffinityClosure();

        /**
         * Indicates if the migration should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();

        @InputDetail
        default void inputDetail() {
            optional(cluster().id());
            optional(force());
            or(optional(host().id()), optional(host().name()));
        }
    }

    /**
     * Temporarily restores the virtual machine to the state of a snapshot.
     *
     * The snapshot is indicated with the `snapshot.id` parameter. It is restored temporarily, so that the content can
     * be inspected. Once that inspection is finished, the state of the virtual machine can be made permanent, using the
     * <<services/vm/methods/commit_snapshot, commit_snapshot>> method, or discarded using the
     * <<services/vm/methods/undo_snapshot, undo_snapshot>> method.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface PreviewSnapshot {
        /**
         * Specify the disks included in the snapshot's preview.
         *
         * For each disk parameter, it is also required to specify its `image_id`.
         *
         * For example, to preview a snapshot with identifier `456` which includes a disk with identifier `111` and its
         * `image_id` as `222`, send a request like this:
         *
         * [source]
         * ----
         * POST /ovirt-engine/api/vms/123/previewsnapshot
         * ----
         *
         * Request body:
         *
         * [source,xml]
         * ----
         * <action>
         *   <disks>
         *     <disk id="111">
         *       <image_id>222</image_id>
         *     </disk>
         *   </disks>
         *   <snapshot id="456"/>
         * </action>
         * ----
         *
         * @author Shani Leviim <sleviim@redhat.com>
         * @author Byron Gravenorst <bgraveno@redhat.com>
         * @date 28 Feb 2018
         * @status updated_by_docs
         */
        @In Disk[] disks();
        @In Boolean restoreMemory();

        /**
         * Specify the lease storage domain ID to use in the preview of the snapshot.
         * If lease parameter is not passed, then the previewed snapshot lease storage domain will be used.
         * If lease parameter is passed with empty storage domain parameter, then no lease will be used
         * for the snapshot preview.
         * If lease parameter is passed with storage domain parameter then the storage domain ID can be
         * only one of the leases domain IDs that belongs to one of the virtual machine snapshots.
         * This is an optional parameter, set by default to `null`
         *
         * @author Eyal Shenitzky <eshenitz@redhat.com>
         * @date 11 Jan 2018
         * @status added
         * @since 4.2.2
         */
        @In StorageDomainLease lease();

        @In Snapshot snapshot();
        @In Vm vm();

        /**
         * Indicates if the preview should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();

        @InputDetail
        default void inputDetail() {
            mandatory(snapshot().id());
            optional(restoreMemory());
            optional(lease());
            optional(disks()[COLLECTION].id());
            optional(disks()[COLLECTION].imageId());
            optional(disks()[COLLECTION].snapshot().id());
        }
    }

    /**
     * Update the virtual machine in the system for the given virtual machine id.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Update {
        @In @Out Vm vm();
        /**
         * Indicates if the update should be applied to the virtual machine immediately or if it should be applied only
         * when the virtual machine is restarted. The default value is `false`, so by default changes are applied
         * immediately.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean nextRun();

        /**
         * Indicates if the update should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();

        @InputDetail
        default void inputDetail() {
            optional(vm().bios().bootMenu().enabled());
            optional(vm().comment());
            optional(vm().console().enabled());
            optional(vm().cpu().architecture());
            optional(vm().cpu().mode());
            optional(vm().cpu().topology().cores());
            optional(vm().cpu().topology().sockets());
            optional(vm().cpu().topology().threads());
            optional(vm().cpuProfile().id());
            optional(vm().cpuShares());
            optional(vm().customCpuModel());
            optional(vm().customEmulatedMachine());
            optional(vm().deleteProtected());
            optional(vm().description());
            optional(vm().display().allowOverride());
            optional(vm().display().copyPasteEnabled());
            optional(vm().display().fileTransferEnabled());
            optional(vm().display().keyboardLayout());
            optional(vm().display().monitors());
            optional(vm().display().singleQxlPci());
            optional(vm().display().smartcardEnabled());
            optional(vm().display().type());
            optional(vm().domain().name());
            optional(vm().externalHostProvider().id());
            optional(vm().highAvailability().enabled());
            optional(vm().highAvailability().priority());
            optional(vm().io().threads());
            optional(vm().largeIcon().data());
            optional(vm().largeIcon().id());
            optional(vm().largeIcon().mediaType());
            optional(vm().memory());
            optional(vm().memoryPolicy().ballooning());
            optional(vm().memoryPolicy().guaranteed());
            optional(vm().migration().autoConverge());
            optional(vm().migration().compressed());
            optional(vm().migrationDowntime());
            optional(vm().name());
            optional(vm().numaTuneMode());
            optional(vm().origin());
            optional(vm().os().cmdline());
            optional(vm().os().initrd());
            optional(vm().os().kernel());
            optional(vm().os().type());
            optional(vm().placementPolicy().affinity());
            optional(vm().rngDevice().rate().bytes());
            optional(vm().rngDevice().rate().period());
            optional(vm().rngDevice().source());
            optional(vm().serialNumber().policy());
            optional(vm().serialNumber().value());
            optional(vm().smallIcon().id());
            optional(vm().soundcardEnabled());
            optional(vm().startPaused());
            optional(vm().stateless());
            optional(vm().timeZone().name());
            optional(vm().tunnelMigration());
            optional(vm().type());
            optional(vm().usb().enabled());
            optional(vm().usb().type());
            optional(vm().useLatestTemplateVersion());
            optional(vm().virtioScsi().enabled());
            or(optional(vm().cluster().id()), optional(vm().cluster().name()));
            or(optional(vm().instanceType().id()), optional(vm().instanceType().name()));
            optional(vm().os().boot().devices()[COLLECTION]);
            or(optional(vm().placementPolicy().hosts()[COLLECTION].id()), optional(vm().placementPolicy().hosts()[COLLECTION].name()));
            optional(vm().cpu().cpuTune().vcpuPins()[COLLECTION].cpuSet());
            optional(vm().cpu().cpuTune().vcpuPins()[COLLECTION].vcpu());
            optional(vm().customProperties()[COLLECTION].name());
            optional(vm().customProperties()[COLLECTION].value());
            optional(vm().sso().methods()[COLLECTION].id());
            optional(vm().payloads()[COLLECTION].files()[COLLECTION].name());
            optional(vm().payloads()[COLLECTION].files()[COLLECTION].content());
            optional(vm().payloads()[COLLECTION].type());
            optional(vm().payloads()[COLLECTION].volumeId());
            optional(vm().storageErrorResumeBehaviour());
            optional(vm().multiQueuesEnabled());
        }
    }

    /**
     * Sends a reboot request to a virtual machine.
     *
     * For example:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/reboot
     * ----
     *
     * The reboot action does not take any action specific parameters; therefore, the request body should contain an
     * empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface Reboot {
        /**
         * Indicates if the reboot should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Removes the virtual machine, including the virtual disks attached to it.
     *
     * For example, to remove the virtual machine with identifier `123`:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/vms/123
     * ----
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();

        /**
         * Indicates if the attached virtual disks should be detached first and preserved instead of being removed.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean detachOnly();

        /**
         * Indicates if the virtual machine should be forcibly removed.
         *
         * Locked virtual machines and virtual machines with locked disk images
         * cannot be removed without this flag set to true.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean force();
    }

    interface ReorderMacAddresses {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * This operation sends a shutdown request to a virtual machine.
     *
     * For example:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/shutdown
     * ----
     *
     * The shutdown action does not take any action specific parameters;
     * therefore, the request body should contain an empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface Shutdown {
        /**
         * Indicates if the shutdown should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();

        /**
         * The reason the virtual machine was stopped.
         * Optionally set by user when shutting down the virtual machine.
         *
         * @author Liran Rotenberg <lrotenbe@redhat.com>
         * @date 12 Sep 2019
         * @status added
         * @since 4.4
         */
        @In String reason();
    }

    /**
     * Starts the virtual machine.
     *
     * If the virtual environment is complete and the virtual machine contains all necessary components to function,
     * it can be started.
     *
     * This example starts the virtual machine:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/start
     * ----
     *
     * With a request body:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface Start {
        @In AuthorizedKey authorizedKey();

        /**
         * If set to `true`, start the virtual machine in paused mode. The default is `false`.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean pause();

        /**
         * The definition of the virtual machine for this specific run.
         *
         * For example:
         *
         * [source,xml]
         * ----
         * <action>
         *   <vm>
         *     <os>
         *       <boot>
         *         <devices>
         *           <device>cdrom</device>
         *         </devices>
         *       </boot>
         *     </os>
         *   </vm>
         * </action>
         * ----
         *
         * This will set the boot device to the CDROM only for this specific start. After the virtual machine is
         * powered off, this definition will be reverted.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Vm vm();

        /**
         * If set to `true`, the initialization type is set to _cloud-init_. The default value is `false`.
         * See https://cloudinit.readthedocs.io/en/latest[this] for details.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean useCloudInit();

        /**
         * If set to `true`, the initialization type is set to _Sysprep_. The default value is `false`.
         * See https://en.wikipedia.org/wiki/Sysprep[this] for details.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean useSysprep();

        /**
         * Indicates if the start action should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean filter();

        /**
         * Indicates that this run configuration will be discarded even in the case of guest-initiated reboot.
         * The default value is `false`.
         *
         * @author Martin Betak <mbetak@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 23 Jun 2017
         * @status updated_by_docs
         * @since 4.2.0
         */
        @In Boolean _volatile();

        @InputDetail
        default void inputDetail() {
            optional(pause());
            optional(useCloudInit());
            optional(useSysprep());
            optional(vm().customCpuModel());
            optional(vm().customEmulatedMachine());
            optional(vm().display().type());
            optional(vm().domain().name());
            optional(vm().domain().user().password());
            optional(vm().domain().user().userName());
            optional(vm().initialization().activeDirectoryOu());
            optional(vm().initialization().authorizedSshKeys());
            optional(vm().initialization().cloudInit().host().address());
            optional(vm().initialization().cloudInit().regenerateSshKeys());
            optional(vm().initialization().cloudInit().timezone());
            optional(vm().initialization().customScript());
            optional(vm().initialization().dnsSearch());
            optional(vm().initialization().dnsServers());
            optional(vm().initialization().domain());
            optional(vm().initialization().hostName());
            optional(vm().initialization().inputLocale());
            optional(vm().initialization().orgName());
            optional(vm().initialization().regenerateSshKeys());
            optional(vm().initialization().rootPassword());
            optional(vm().initialization().systemLocale());
            optional(vm().initialization().timezone());
            optional(vm().initialization().uiLanguage());
            optional(vm().initialization().userName());
            optional(vm().initialization().windowsLicenseKey());
            optional(vm().os().cmdline());
            optional(vm().os().initrd());
            optional(vm().os().kernel());
            optional(vm().placementPolicy().affinity());
            optional(vm().stateless());
            optional(authorizedKey().user().name());
            optional(vm().os().boot().devices()[COLLECTION]);
            optional(vm().initialization().cloudInit().networkConfiguration().dns().searchDomains()[COLLECTION].address());
            optional(vm().initialization().cloudInit().users()[COLLECTION].name());
            optional(vm().initialization().cloudInit().users()[COLLECTION].password());
            optional(vm().initialization().cloudInit().authorizedKeys()[COLLECTION].key());
            optional(vm().initialization().cloudInit().networkConfiguration().nics()[COLLECTION].bootProtocol());
            optional(vm().initialization().cloudInit().networkConfiguration().nics()[COLLECTION].name());
            optional(vm().initialization().cloudInit().networkConfiguration().nics()[COLLECTION].network().ip().gateway());
            optional(vm().initialization().cloudInit().networkConfiguration().nics()[COLLECTION].network().ip().netmask());
            optional(vm().initialization().cloudInit().networkConfiguration().nics()[COLLECTION].onBoot());
            optional(vm().initialization().cloudInit().networkConfiguration().dns().servers()[COLLECTION].address());
            or(optional(vm().placementPolicy().hosts()[COLLECTION].id()), optional(vm().placementPolicy().hosts()[COLLECTION].name()));
//            optional(vm().initialization().cloudInit().payloadFiles()[COLLECTION].content());
//            optional(vm().initialization().cloudInit().payloadFiles()[COLLECTION].name());
//            optional(vm().initialization().cloudInit().payloadFiles()[COLLECTION].type());
            optional(vm().initialization().nicConfigurations()[COLLECTION].bootProtocol());
            optional(vm().initialization().nicConfigurations()[COLLECTION].ip().address());
            optional(vm().initialization().nicConfigurations()[COLLECTION].ip().gateway());
            optional(vm().initialization().nicConfigurations()[COLLECTION].ip().netmask());
            optional(vm().initialization().nicConfigurations()[COLLECTION].name());
            optional(vm().initialization().nicConfigurations()[COLLECTION].onBoot());
            optional(_volatile());
        }

    }

    /**
     * This operation forces a virtual machine to power-off.
     *
     * For example:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/stop
     * ----
     *
     * The stop action does not take any action specific parameters;
     * therefore, the request body should contain an empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface Stop {
        /**
         * Indicates if the stop action should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();

        /**
         * The reason the virtual machine was stopped.
         * Optionally set by user when shutting down the virtual machine.
         *
         * @author Liran Rotenberg <lrotenbe@redhat.com>
         * @date 12 Sep 2019
         * @status added
         * @since 4.4
         */
        @In String reason();
    }

    /**
     * This operation saves the virtual machine state to disk and stops it.
     * Start a suspended virtual machine and restore the virtual machine state with the start action.
     *
     * For example:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/suspend
     * ----
     *
     * The suspend action does not take any action specific parameters;
     * therefore, the request body should contain an empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface Suspend {
        /**
         * Indicates if the suspend action should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Thaws virtual machine file systems.
     *
     * This operation thaws a virtual machine's file systems using the QEMU guest agent when taking a live snapshot of a
     * running virtual machine. Normally, this is done automatically by the manager, but this must be executed manually
     * with the API for virtual machines using OpenStack Volume (Cinder) disks.
     *
     * Example:
     *
     * [source]
     * ----
     * POST /api/vms/123/thawfilesystems
     * ----
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Daniel Erez <derez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface ThawFilesystems {
        /**
         * Indicates if the thaw file systems action should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Generates a time-sensitive authentication token for accessing a virtual machine's display.
     *
     * For example:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/ticket
     * ----
     *
     * The client-provided action optionally includes a desired ticket value and/or an expiry time in seconds.
     *
     * The response specifies the actual ticket value and expiry used.
     *
     * [source,xml]
     * ----
     * <action>
     *   <ticket>
     *     <value>abcd12345</value>
     *     <expiry>120</expiry>
     *   </ticket>
     * </action>
     * ----
     *
     * [IMPORTANT]
     * ====
     * If the virtual machine is configured to support only one graphics protocol
     * then the generated authentication token will be valid for that protocol.
     * But if the virtual machine is configured to support multiple protocols,
     * VNC and SPICE, then the authentication token will only be valid for
     * the SPICE protocol.
     *
     * In order to obtain an authentication token for a specific protocol, for
     * example for VNC, use the `ticket` method of the <<services/vm_graphics_console,
     * service>>, which manages the graphics consoles of the virtual machine, by sending
     * a request:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/graphicsconsoles/456/ticket
     * ----
     * ====
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface Ticket {
        @In @Out types.Ticket ticket();

        /**
         * Indicates if the generation of the ticket should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();

        @InputDetail
        default void inputDetail() {
            optional(ticket().value());
        }
    }

    /**
     * Restores the virtual machine to the state it had before previewing the snapshot.
     *
     * See the <<services/vm/methods/preview_snapshot, preview_snapshot>> operation for details.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com:
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    interface UndoSnapshot {
        /**
         * Indicates if the undo snapshot action should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 28 Mar 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    @Service AssignedPermissionsService permissions();
    @Service AssignedTagsService tags();
    @Service VmGraphicsConsolesService graphicsConsoles();

    /**
     * Reference to the service that can show the applicable errata available on the virtual machine.
     * This information is taken from Katello.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    @Service KatelloErrataService katelloErrata();
    @Service SnapshotsService snapshots();
    @Service VmApplicationsService applications();
    @Service VmCdromsService cdroms();
    @Service VmHostDevicesService hostDevices();
    @Service VmNicsService nics();
    @Service VmNumaNodesService numaNodes();
    @Service VmReportedDevicesService reportedDevices();

    /**
     * Reference to the service that provides information about virtual machine user sessions.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    @Service VmSessionsService sessions();

    @Service VmWatchdogsService watchdogs();

    /**
     * List of backups of this virtual machine.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 12 Dec 2018
     * @status added
     * @since 4.3
     */
    @Service
    VmBackupsService backups();

    /**
     * List of disks attached to this virtual machine.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    @Service DiskAttachmentsService diskAttachments();

    /**
     * List of scheduling labels assigned to this virtual machine.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 28 Mar 2017
     * @status updated_by_docs
     */
    @Service AssignedAffinityLabelsService affinityLabels();
}
