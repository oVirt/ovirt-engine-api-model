/*
Copyright (c) 2015 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

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

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import types.Snapshot;

/**
 * Manages the set of snapshots of a storage domain or virtual machine.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 15 Apr 2017
 * @status added
 */
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
     * For including only a sub-set of disks in the snapshots, add `disk_attachments` element to the
     * request body. Note that disks which are not specified in `disk_attachments` element will not be a
     * part of the snapshot. If an empty `disk_attachments` element is passed, the snapshot will include
     * only the virtual machine configuration. If no `disk_attachments` element is passed, then all
     * the disks will be included in the snapshot.
     *
     * For each disk, `image_id` element can be specified for setting the new active image id.
     * This is used in order to restore a chain of images from backup. I.e. when restoring
     * a disk with snapshots, the relevant `image_id` should be specified for each snapshot
     * (so the identifiers of the disk snapshots are identical to the backup).
     *
     * [source,xml]
     * ----
     * <snapshot>
     *   <description>My snapshot</description>
     *   <disk_attachments>
     *     <disk_attachment>
     *       <disk id="123">
     *         <image_id>456</image_id>
     *       </disk>
     *     </disk_attachment>
     *   </disk_attachments>
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
        @InputDetail
        default void inputDetail() {
            mandatory(snapshot().description());
            optional(snapshot().persistMemorystate());
            optional(snapshot().diskAttachments()[COLLECTION].id());
        }
        @In @Out Snapshot snapshot();
    }

    /**
     * Returns the list of snapshots of the storage domain or virtual machine.
     *
     * The order of the returned list of snapshots isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out Snapshot[] snapshots();

        /**
         * Sets the maximum number of snapshots to return. If not specified all the snapshots are returned.
         */
        @In Integer max();

        /**
         * Indicates if all the attributes of the virtual machine snapshot should be included in the response.
         *
         * By default the attribute `initialization.configuration.data` is excluded.
         *
         * For example, to retrieve the complete representation of the virtual machine with id `123` snapshots send a
         * request like this:
         *
         * ....
         * GET /ovirt-engine/api/vms/123/snapshots?all_content=true
         * ....
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 02 Feb 2017
         * @status added
         * @since 4.2
         */
        @In Boolean allContent();
    }

    @Service SnapshotService snapshot(String id);
}
