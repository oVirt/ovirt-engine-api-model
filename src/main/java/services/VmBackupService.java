/*
The oVirt Project - oVirt Engine Api Model

Copyright oVirt Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the Apache License, Version 2.0 is included with the program
in the file ASL2.
*/

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Backup;

/**
 * A service managing a backup of a virtual machines.
 *
 * @author Daniel Erez <derez@redhat.com>
 * @date 12 Dec 2018
 * @status added
 * @since 4.3
 */
@Service
@Area("Storage")
public interface VmBackupService {
    /**
     * Returns information about the virtual machine backup.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 12 Dec 2018
     * @status added
     * @since 4.3
     */
    interface Get extends Follow {
        /**
         * The information about the virtual machine backup entities.
         *
         * [source,xml]
         * ----
         * <backups>
         *   <backup id="backup-uuid">
         *     <from_checkpoint_id>previous-checkpoint-uuid</from_checkpoint_id>
         *     <link href="/ovirt-engine/api/vms/vm-uuid/backups/backup-uuid/disks" rel="disks"/>
         *     <status>initializing</status>
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
        @Out
        Backup backup();
    }

    /**
     * Finalize the virtual machine backup entity.
     *
     * End backup, unlock resources, and perform cleanups.
     * To finalize a virtual machine with an id '123' and a backup with an id '456'
     * send a request as follows:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/backups/456/finalize
     * ----
     *
     * With a request body as follows:
     *
     * [source,xml]
     * ----
     * <action />
     * ----
     *
     * @author Daniel Erez <derez@redhat.com>
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @author Steve Goodman <sgoodman@redhat.com>
     * @date 11 Oct 2021
     * @status updated_by_docs
     * @since 4.3
     */
    interface Finalize {}

    /**
     * A reference to the service that lists the disks in backup.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @author Rolfe Dlugy-Hegwer <rdlugyhe@redhat.com>
     * @date 12 Dec 2018
     * @status added
     * @since 4.3
     */
    @Service VmBackupDisksService disks();
}
