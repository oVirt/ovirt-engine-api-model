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
     * The cancel migration action does not take any action specific parameters,
     * so the request body should contain an empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface CancelMigration {
        /**
         * Indicates if the migration should cancelled asynchronously.
         */
        @In Boolean async();
    }

    interface CommitSnapshot {
        /**
         * Indicates if the snapshots should be committed asynchronously.
         */
        @In Boolean async();
    }

    interface Clone {
        @In Vm vm();

        /**
         * Indicates if the clone should be performed asynchronously.
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
     * The detach action does not take any action specific parameters, so the request body should contain an
     * empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Detach {
        /**
         * Indicates if the detach should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Export a virtual machine to an export domain.
     *
     * For example to export virtual machine `123` to the export domain `myexport`, send a request like this:
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
     *     <name>myexport</name>
     *   </storage_domain>
     *   <exclusive>true</exclusive>
     *   <discard_snapshots>true</discard_snapshots>
     * </action>
     * ----
     *
     * @author Tal Nisan <tisan@redhat.com>
     * @date 19 Sep 2016
     * @status added
     */
    interface Export {
        /**
         * The `discard_snapshots` parameter is to be used when the virtual machine should be exported with all its
         * snapshots collapsed.
         *
         * @author Tal Nisan <tisan@redhat.com>
         * @date 19 Sep 2016
         * @status added
         */
        @In Boolean discardSnapshots();

        /**
         * The `exclusive` parameter is to be used when the virtual machine should be exported even if another copy of
         * it already exists in the export domain (override).
         *
         * @author Tal Nisan <tisan@redhat.com>
         * @date 19 Sep 2016
         * @status added
         */
        @In Boolean exclusive();

        @In StorageDomain storageDomain();

        /**
         * Indicates if the export should be performed asynchronously.
         */
        @In Boolean async();

        @InputDetail
        default void inputDetail() {
            optional(discardSnapshots());
            optional(exclusive());
            or(optional(storageDomain().id()), optional(storageDomain().name()));
        }
    }

    /**
     * Freeze virtual machine file systems.
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
     * @date 14 Sep 2016
     * @status added
     */
    interface FreezeFilesystems {
        /**
         * Indicates if the freeze should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Retrieves the description of the virtual machine.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 11 Oct 2016
     * @status added
     */
    interface Get {
        /**
         * Description of the virtual machine.
         *
         * @author Shmuel Melamud <smelamud@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @Out Vm vm();

        /**
         * Indicates if the returned result describes the virtual machine as it is currently running, or if describes
         * it with the modifications that have already been performed but that will have effect only when it is
         * restarted. By default the values is `false`.
         *
         * If the parameter is included in the request, but without a value, it is assumed that the value is `true`, so
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
         */
        @In Boolean nextRun();

        /**
         * Indicates if all the attributes of the virtual machine should be included in the response.
         *
         * By default the following attributes are excluded:
         *
         * - `console`
         * - `initialization.configuration.data` - The OVF document describing the virtual machine.
         * - `rng_source`
         * - `soundcard`
         * - `virtio_scsi`
         *
         * For example, to retrieve the complete representation of the virtual machine '123' send a request like this:
         *
         * ....
         * GET /ovirt-engine/api/vms/123?all_content=true
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

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
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
     * This is how an example request would look like:
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
     * @date 14 Sep 2016
     * @status added
     */
    interface Logon {
        /**
         * Indicates if the logon should be performed asynchronously.
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
     * @date 16 Feb 2017
     * @status added
     */
    interface Maintenance {
        /**
         * Indicates if global maintenance should be enabled or disabled.
         *
         * @author Andrej Krejcir <akrejcir@redhat.com>
         * @date 16 Feb 2017
         * @status added
         */

        @In Boolean maintenanceEnabled();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();

        @InputDetail
        default void inputDetail() {
            mandatory(maintenanceEnabled());
        }
    }

    /**
     * This operation migrates a virtual machine to another physical host.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/migrate
     * ----
     *
     * One can specify a specific host to migrate the virtual machine to:
     *
     * [source,xml]
     * ----
     * <action>
     *   <host id="2ab5e1da-b726-4274-bbf7-0a42b16a0fc3"/>
     * </action>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Migrate {
        /**
         * Specifies the cluster the virtual machine should migrate to. This is an optional parameter. By default, the
         * virtual machine is migrated to another host within the same cluster.
         *
         * @author Arik Hadas <ahadas@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In Cluster cluster();

        /**
         * Specifies the virtual machine should migrate although it might be defined as non migratable. This is an
         * optional parameter. By default, it is set to `false`.
         *
         * @author Arik Hadas <ahadas@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In Boolean force();

        /**
         * Specifies a specific host the virtual machine should migrate to. This is an optional parameters. By default,
         * the oVirt Engine automatically selects a default host for migration within the same cluster. If an API user
         * requires a specific host, the user can specify the host with either an `id` or `name` parameter.
         *
         * @author Arik Hadas <ahadas@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In Host host();

        /**
         * Indicates if the migration should be performed asynchronously.
         */
        @In Boolean async();

        @InputDetail
        default void inputDetail() {
            optional(cluster().id());
            optional(force());
            or(optional(host().id()), optional(host().name()));
        }
    }

    interface PreviewSnapshot {
        @In Disk[] disks();
        @In Boolean restoreMemory();
        @In Snapshot snapshot();
        @In Vm vm();

        /**
         * Indicates if the preview should be performed asynchronously.
         */
        @In Boolean async();

        @InputDetail
        default void inputDetail() {
            mandatory(snapshot().id());
            optional(restoreMemory());
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
         * Indicates if the update should be applied to the virtual machine immediately, or if it should be applied only
         * when the virtual machine is restarted. The default value is `false`, so by default changes are applied
         * immediately.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 13 Oct 2016
         * @status added
         */
        @In Boolean nextRun();

        /**
         * Indicates if the update should be performed asynchronously.
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
        }
    }

    /**
     * This operation sends a reboot request to a virtual machine.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/reboot
     * ----
     *
     * The reboot action does not take any action specific parameters, so the request body should contain an
     * empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Reboot {
        /**
         * Indicates if the reboot should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes the virtual machine, including the virtual disks attached to it.
     *
     * For example, to remove the virtual machine with identifier `123` send a request like this:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/vms/123
     * ----
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the attached virtual disks should be detached first and preserved instead of being removed.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In Boolean detachOnly();

        /**
         * Indicates if the virtual machine should be forcibly removed.
         *
         * Locked virtual machines and virtual machines with locked disk images
         * cannot be removed without this flag set to true.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 14 Sep 2016
         * @status added
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
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/shutdown
     * ----
     *
     * The shutdown action does not take any action specific parameters,
     * so the request body should contain an empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Shutdown {
        /**
         * Indicates if the shutdown should be performed asynchronously.
         */
        @In Boolean async();
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
     * @date 14 Sep 2016
     * @status added
     */
    interface Start {
        @In AuthorizedKey authorizedKey();

        /**
         * If set to `true`, start the virtual machine in paused mode. Default is `false`.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @date 14 Sep 2016
         * @status added
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
         * This will set the boot device to the CDROM only for this specific start. After the virtual machine will be
         * powered off, this definition will be reverted.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In Vm vm();

        /**
         * If set to `true`, the initialization type is set to _cloud-init_. The default value is `false`.
         * See https://cloudinit.readthedocs.io/en/latest[this] for details.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In Boolean useCloudInit();

        /**
         * If set to `true`, the initialization type is set to _Sysprep_. The default value is `false`.
         * See https://en.wikipedia.org/wiki/Sysprep[this] for details.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In Boolean useSysprep();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();

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
        }

    }

    /**
     * This operation forces a virtual machine to power-off.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/stop
     * ----
     *
     * The stop action does not take any action specific parameters,
     * so the request body should contain an empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Stop {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * This operation saves the virtual machine state to disk and stops it.
     * Start a suspended virtual machine and restore the virtual machine state with the start action.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/suspend
     * ----
     *
     * The suspend action does not take any action specific parameters,
     * so the request body should contain an empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Suspend {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Thaw virtual machine file systems.
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
     * @date 14 Sep 2016
     * @status added
     */
    interface ThawFilesystems {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Generates a time-sensitive authentication token for accessing a virtual machine's display.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/ticket
     * ----
     *
     * The client-provided action optionally includes a desired ticket value and/or an expiry time in seconds.
     *
     * In any case, the response specifies the actual ticket value and expiry used.
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
     * service>> that manages the graphics consoles of the virtual machine, sending
     * a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/graphicsconsoles/456/ticket
     * ----
     * ====
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Ticket {
        @In @Out types.Ticket ticket();

        /**
         * Indicates if the generation of the ticket should be performed asynchronously.
         */
        @In Boolean async();

        @InputDetail
        default void inputDetail() {
            optional(ticket().value());
        }
    }

    interface UndoSnapshot {
        /**
         * Indicates if the action should be performed asynchronously.
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
     * @date 12 Dec 2016
     * @status added
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
     * @date 14 Sep 2016
     * @status added
     */
    @Service VmSessionsService sessions();

    @Service VmWatchdogsService watchdogs();

    /**
     * List of disks attached to this virtual machine.
     */
    @Service DiskAttachmentsService diskAttachments();

    /**
     * List of scheduling labels assigned to this VM.
     */
    @Service AssignedAffinityLabelsService affinityLabels();
}
