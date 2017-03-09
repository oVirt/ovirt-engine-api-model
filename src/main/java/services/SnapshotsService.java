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
import types.Snapshot;

@Service
@Area("Storage")
public interface SnapshotsService {
    /**
     * Creates a virtual machine snapshot.
     *
     * For example, to create a new snapshot for virtual machine `123` send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/snapshots
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <snapshot>
     *   <description>My snapshot</description>
     * </snapshot>
     * ----
     *
     * [IMPORTANT]
     * ====
     * When a snapshot is created the default value for the <<types/snapshot/attributes/persist_memorystate,
     * persist_memorystate>> attribute is `true`. That means that the content of the memory of the virtual
     * machine will be included in the snapshot, and it also means that the virtual machine will be paused
     * for a longer time. That can negatively affect applications that are very sensitive to timing (NTP
     * servers, for example). In those cases make sure that you set the attribute to `false`:
     *
     * [source,xml]
     * ----
     * <snapshot>
     *   <description>My snapshot</description>
     *   <persist_memorystate>false</persist_memorystate>
     * </snapshot>
     * ----
     * ====
     *
     * @author Daniel Erez <derez@redhat.com>
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 9 Mar 2017
     * @status added
     */
    interface Add {
        @In @Out Snapshot snapshot();
    }

    interface List {
        @Out Snapshot[] snapshots();

        /**
         * Sets the maximum number of snapshots to return. If not specified all the snapshots are returned.
         */
        @In Integer max();
    }

    @Service SnapshotService snapshot(String id);
}
