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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import services.externalhostproviders.KatelloErrataService;
import types.Cluster;
import types.Disk;
import types.Host;
import types.Snapshot;
import types.StorageDomain;
import types.Vm;

@Service
@Area("Virtualization")
public interface VmService extends MeasurableService {
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
    }

    interface Detach {
        /**
         * Indicates if the detach should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Export {
        @In Boolean discardSnapshots();
        @In Boolean exclusive();
        @In StorageDomain storageDomain();

        /**
         * Indicates if the export should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface FreezeFilesystems {
        /**
         * Indicates if the freeze should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Retrieves the description of the virtual machine.
     *
     * Note that some elements of the description of the virtual machine won't be returned unless the `All-Content`
     * header is present in the request and has the value `true`. The elements that aren't currently returned are
     * the following:
     *
     * - `console`
     * - `initialization.configuration.data` - The OVF document describing the virtual machine.
     * - `rng_source`
     * - `soundcard`
     * - `virtio_scsi`
     *
     * With the Python SDK the `All-Content` header can be set using the `all_content` parameter of the `get`
     * method:
     *
     * [source,python]
     * ----
     * api.vms.get(name="myvm", all_content=True)
     * ----
     *
     * Note that the reason for not including these elements is performance: they are seldom used and they require
     * additional queries in the server. So try to use the `All-Content` header only when it is really needed.
     */
    interface Get {
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
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    interface Logon {
        /**
         * Indicates if the logon should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Maintenance {
        @In Boolean maintenanceEnabled();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Migrate {
        @In Cluster cluster();
        @In Boolean force();
        @In Host host();

        /**
         * Indicates if the migration should be performed asynchronously.
         */
        @In Boolean async();
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
    }

    interface Update {
        @In @Out Vm vm();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Reboot {
        /**
         * Indicates if the reboot should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
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

    interface Start {
        @In Boolean pause();
        @In Vm vm();
        @In Boolean useCloudInit();
        @In Boolean useSysprep();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
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

    interface ThawFilesystems {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Ticket {
        @In @Out Ticket ticket();

        /**
         * Indicates if the generation of the ticket should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface UndoSnapshot {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service AssignedPermissionsService permissions();
    @Service AssignedTagsService tags();
    @Service GraphicsConsolesService graphicsConsoles();
    @Service KatelloErrataService katelloErrata();
    @Service SnapshotsService snapshots();
    @Service VmApplicationsService applications();
    @Service VmCdromsService cdroms();
    @Service VmHostDevicesService hostDevices();
    @Service VmNicsService nics();
    @Service VmNumaNodesService numaNodes();
    @Service VmReportedDevicesService reportedDevices();
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
