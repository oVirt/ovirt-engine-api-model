/*
Copyright (c) 2019 Red Hat, Inc.

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
import types.Backup;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * Lists the backups of a virtual machine.
 *
 * @author Daniel Erez <derez@redhat.com>
 * @date 12 Dec 2018
 * @status added
 * @since 4.3
 */
@Service
@Area("Storage")
public interface VmBackupsService {
    /**
     * Adds a new backup entity to a virtual machine.
     *
     * For example, to start a new incremental backup of a virtual machine
     * since checkpoint id `previous-checkpoint-uuid`, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/backups
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <backup>
     *   <from_checkpoint_id>previous-checkpoint-uuid</from_checkpoint_id>
     *   <disks>
     *       <disk id="disk-uuid" />
     *       ...
     *   </disks>
     * </backup>
     * ----
     *
     * The response body:
     *
     * [source,xml]
     * ----
     * <backup id="backup-uuid">
     *     <from_checkpoint_id>previous-checkpoint-uuid</from_checkpoint_id>
     *     <to_checkpoint_id>new-checkpoint-uuid</to_checkpoint_id>
     *     <disks>
     *         <disk id="disk-uuid" />
     *         ...
     *         ...
     *     </disks>
     *     <status>initializing</status>
     *     <creation_date>
     * </backup>
     * ----
     *
     * @author Daniel Erez <derez@redhat.com>
     * @author Rolfe Dlugy-Hegwer <rdlugyhe@redhat.com>
     * @date 12 Dec 2018
     * @status added
     * @since 4.3
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            optional(backup().fromCheckpointId());
            optional(backup().toCheckpointId());
            mandatory(backup().disks());
        }
        /**
         * The information about the virtual machine backup entity.
         *
         * @author Daniel Erez <derez@redhat.com>
         * @date 12 Dec 2018
         * @status added
         * @since 4.3
         */
        @In @Out Backup backup();

        /**
         * Indicates if the backup will fail if VM failed to freeze or not.
         *
         * If requireConsistency=True VM backup will fail in case of a
         * failure to freeze the VM.
         *
         * The REST API call should look like this:
         *
         * ....
         * POST /ovirt-engine/api/vms/123/backups?require_consistency=true
         * ....
         *
         * The default value of the requireConsistency flag is `false`.
         *
         * @author Eyal Shenitzky <eshenitz@redhat.com>
         * @date 12 Nov 2020
         * @status added
         * @since 4.4.4
         */
        @In Boolean requireConsistency();
    }

    /**
     * The list of virtual machine backups.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 12 Dec 2018
     * @status added
     * @since 4.3
     */
    interface List extends mixins.Follow {
        /**
         * The information about the virtual machine backup entities.
         *
         * [source,xml]
         * ----
         * <backups>
         *   <backup id="backup-uuid">
         *     <from_checkpoint_id>previous-checkpoint-uuid</from_checkpoint_id>
         *     <disks>
         *       <disk id="disk-uuid" />
         *       ...
         *       ...
         *     </disks>
         *     <status>initiailizing</status>
         *     <creation_date>
         *  </backup>
         * </backups>
         * ----
         *
         * @author Daniel Erez <derez@redhat.com>
         * @date 12 Dec 2018
         * @status added
         * @since 4.3
         */
        @Out Backup[] backups();

        /**
         * Sets the maximum number of virtual machine backups to return. If not specified, all the virtual machine backups are returned.
         */
        @In Integer max();
    }

    /**
     * Returns a reference to the service that manages a specific VM backup.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 12 Dec 2018
     * @status added
     * @since 4.3
     */
    @Service
    VmBackupService backup(String id);
}
