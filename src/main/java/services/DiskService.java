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
import types.StorageDomain;

/**
 * Manages a single disk.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 4 Nov 2016
 * @status added
 */
@Service
@Area("Storage")
public interface DiskService extends MeasurableService {
    /**
     * This operation copies a disk to the specified storage domain.
     *
     * For example, copy of a disk can be facilitated using the following request:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/disks/123/copy
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <action>
     *   <storage_domain id="456"/>
     *   <disk>
     *     <name>mydisk</name>
     *   </disk>
     * </action>
     * ----
     *
     * @author Liron Aravot <laravot@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Copy {
        @In Disk disk();

        @In StorageDomain storageDomain();

        /**
         * Indicates if the copy should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    /**
     * This operation updates the disk with the appropriate parameters.
     * The only field that can be updated is `qcow_version`.
     *
     * For example, update disk can be facilitated using the following request:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/disks/123
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <disk>
     *   <qcow_version>qcow2_v3</qcow_version>
     * </disk>
     * ----
     *
     * Since the backend operation is asynchronous the disk element which will be returned
     * to the user might not be synced with the changed properties.
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @date 21 Nov 2016
     * @status added
     * @since 4.1
     */
    interface Update {
        /**
         * The update to apply to the disk.
         *
         * @author Maor Lipchuk <mlipchuk@redhat.com>
         * @date 21 Nov 2016
         * @status added
         * @since 4.1
         */
        @In Disk disk();
    }


    interface Export {
        @In StorageDomain storageDomain();

        /**
         * Indicates if the export should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    interface Get {
        @Out Disk disk();
    }

    /**
     * Moves a disk to another storage domain.
     *
     * For example, to move the disk with identifier `123` to a storage domain with identifier `456` send the following
     * request:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/disks/123/move
     * ----
     *
     * With the following request body:
     *
     * [source,xml]
     * ----
     * <action>
     *   <storage_domain id="456"/>
     * </action>
     * ----
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Move {
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

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Sparsify the disk.
     *
     * Sparsification frees space in the disk image that is not used by its
     * filesystem. As a result, the image will occupy less space on the storage.
     *
     * Currently sparsification works only on disks without snapshots. Disks
     * having derived disks are also not allowed.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 12 Sep 2016
     * @status added
     * @since 4.1
     */
    interface Sparsify {
    }

    @Service AssignedPermissionsService permissions();
}
