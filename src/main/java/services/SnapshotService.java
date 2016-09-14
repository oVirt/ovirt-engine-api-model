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
import types.Disk;
import types.Snapshot;

@Service
@Area("Storage")
public interface SnapshotService {
    interface Get {
        @Out Snapshot snapshot();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Restores a virtual machine snapshot.
     *
     * For example, to restore the snapshot with identifier `456` of virtual machine with identifier `123` send a
     * request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/snapshots/456/restore
     * ----
     *
     * With an empty `action` in the body:
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
    interface Restore {
        @In Disk[] disks();
        @In Boolean restoreMemory();

        /**
         * Indicates if the restore should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service SnapshotCdromsService cdroms();
    @Service SnapshotDisksService disks();
    @Service SnapshotNicsService nics();
}
