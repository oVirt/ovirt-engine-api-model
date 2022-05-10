/*
Copyright (c) 2017 Red Hat, Inc.

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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Disk;
import types.StorageDomain;

/**
 * Manages a single disk available in a storage domain.
 *
 * IMPORTANT: Since version 4.2 of the engine this service is intended only to list disks available in the storage
 * domain, and to register unregistered disks. All the other operations, like copying a disk, moving a disk, etc, have
 * been deprecated and will be removed in the future. To perform those operations
 * use the xref:services/disks[service that manages all the disks of the system]
 * or the xref:services/disk[service that manages a specific disk].
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 4 Nov 2016
 * @status added
 */
@Service
@Area("Storage")
public interface StorageDomainDiskService extends MeasurableService {
    /**
     * Copies a disk to the specified storage domain.
     *
     * IMPORTANT: Since version 4.2 of the engine this operation is deprecated, and preserved only for backwards
     * compatibility. It will be removed in the future. To copy a disk use the xref:services/disk/methods/copy[copy]
     * operation of the service that manages that disk.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Jan 2017
     * @status added
     */
    @Deprecated
    interface Copy {
        /**
         * Description of the resulting disk.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 4 Jan 2017
         * @status added
         */
        @In Disk disk();

        /**
         * The storage domain where the new disk will be created.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 4 Jan 2017
         * @status added
         */
        @In StorageDomain storageDomain();
    }

    /**
     * Updates the disk.
     *
     * IMPORTANT: Since version 4.2 of the engine this operation is deprecated, and preserved only for backwards
     * compatibility. It will be removed in the future. To update a disk use the
     * xref:services/disk/methods/update[update] operation of the service that manages that disk.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Jan 2017
     * @status added
     */
    interface Update {
        /**
         * The update to apply to the disk.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 4 Jan 2017
         * @status added
         * @since 4.1
         */
        @In @Out Disk disk();
    }

    /**
     * Exports a disk to an export storage domain.
     *
     * IMPORTANT: Since version 4.2 of the engine this operation is deprecated, and preserved only for backwards
     * compatibility. It will be removed in the future. To export a disk use the xref:services/disk/methods/export[export]
     * operation of the service that manages that disk.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Jan 2017
     * @status added
     */
    interface Export {
        /**
         * The export storage domain where the disk should be exported to.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 4 Jan 2017
         * @status added
         */
        @In StorageDomain storageDomain();
    }

    /**
     * Retrieves the description of the disk.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Jan 2017
     * @status added
     */
    interface Get extends Follow {
        /**
         * The description of the disk.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 4 Jan 2017
         * @status added
         */
        @Out Disk disk();
    }

    /**
     * Moves a disk to another storage domain.
     *
     * IMPORTANT: Since version 4.2 of the engine this operation is deprecated, and preserved only for backwards
     * compatibility. It will be removed in the future. To move a disk use the xref:services/disk/methods/move[move]
     * operation of the service that manages that disk.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Jan 2017
     * @status added
     */
    interface Move {
        /**
         * The storage domain where the disk will be moved to.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 4 Jan 2017
         * @status added
         */
        @In StorageDomain storageDomain();

        /**
         * Indicates if the move should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    /**
     * Removes a disk.
     *
     * IMPORTANT: Since version 4.2 of the engine this operation is deprecated, and preserved only for backwards
     * compatibility. It will be removed in the future. To remove a disk use the xref:services/disk/methods/remove[remove]
     * operation of the service that manages that disk.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Jan 2017
     * @status added
     */
    interface Remove {
    }

    /**
     * Sparsify the disk.
     *
     * IMPORTANT: Since version 4.2 of the engine this operation is deprecated, and preserved only for backwards
     * compatibility. It will be removed in the future. To remove a disk use the xref:services/disk/methods/remove[remove]
     * operation of the service that manages that disk.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Jan 2017
     * @status added
     * @since 4.1
     */
    interface Sparsify {
    }

    /**
     * Reduces the size of the disk image.
     *
     * Invokes _reduce_ on the logical volume (i.e. this is only applicable for block storage domains).
     * This is applicable for floating disks and disks attached to non-running virtual machines.
     * There is no need to specify the size as the optimal size is calculated automatically.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @author Emma Heftman <eheftman@redhat.com>
     * @date 3 June 2018
     * @status updated_by_docs
     * @since 4.2.5
     */
    interface Reduce {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Reference to the service that manages the permissions assigned to the disk.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Jan 2017
     * @status added
     */
    @Service AssignedPermissionsService permissions();
}
