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
import types.Disk;
import types.Host;
import types.StorageDomain;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;
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
        /**
         * Description of the resulting disk. The only accepted value is the `name` attribute, which will be the name
         * used for the new disk. For example, to copy disk `123` using `myname` as the name for the new disk, send
         * a request like this:
         *
         * ....
         * POST /ovirt-engine/disks/123
         * ....
         *
         * With a request body like this:
         *
         * [source,xml]
         * ----
         * <action>
         *   <disk>
         *     <name>mydisk<name>
         *   </disk>
         *   <storage_domain id="456"/>
         * </action>
         * ----
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 4 Jan 2017
         * @status added
         */
        @InputDetail
        default void inputDetail() {
            or(mandatory(storageDomain().id()), mandatory(storageDomain().name()));
            or(optional(disk().name()), optional(disk().alias()));
        }
        @In Disk disk();

        /**
         * The storage domain where the new disk will be created. Can be specified using the `id` or `name`
         * attributes. For example, to copy a disk to the storage domain named `mydata` send a request like this:
         *
         * ....
         * POST /ovirt-engine/api/storagedomains/123/disks/789
         * ....
         *
         * With a request body like this:
         *
         * [source,xml]
         * ----
         * <action>
         *   <storage_domain>
         *     <name>mydata</name>
         *   </storage_domain>
         * </action>
         * ----
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 4 Jan 2017
         * @status added
         */
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
        @InputDetail
        default void inputDetail() {
            optional(disk().alias());
            optional(disk().bootable());
            optional(disk().description());
            optional(disk().diskProfile().id());
            optional(disk().format());
//            optional(disk()._interface()); TODO: uncomment when '_' can be handled
            optional(disk().name());
            optional(disk().propagateErrors());
            optional(disk().provisionedSize());
            optional(disk().quota().id());
            optional(disk().readOnly());
            optional(disk().sgio());
            optional(disk().shareable());
//            optional(disk().size());
            optional(disk().sparse());
            optional(disk().usesScsiReservation());
            optional(disk().wipeAfterDelete());
        }
        /**
         * The update to apply to the disk.
         *
         * @author Maor Lipchuk <mlipchuk@redhat.com>
         * @date 21 Nov 2016
         * @status added
         * @since 4.1
         */
        @In @Out Disk disk();
    }

    /**
     * Exports a disk to an export storage domain.
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
        @InputDetail
        default void inputDetail() {
            or(mandatory(storageDomain().id()), mandatory(storageDomain().name()));
        }

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

        /**
         * The storage domain where the disk will be moved to.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 4 Jan 2017
         * @status added
         */
        @InputDetail
        default void inputDetail() {
            or(mandatory(storageDomain().id()), mandatory(storageDomain().name()));
        }

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
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Jan 2017
     * @status added
     */
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

    /**
     * Refreshes a direct LUN disk with up-to-date information from the storage.
     *
     * Refreshing a direct LUN disk is useful when:
     *
     * - The LUN was added using the API without the host parameter, and therefore does not contain
     *   any information from the storage (see <<services/disks/methods/add, DisksService::add>>).
     * - New information about the LUN is available on the storage and you want to update the LUN with it.
     *
     * To refresh direct LUN disk `123` using host `456`, send the following request:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/disks/123/refreshlun
     * ----
     *
     * With the following request body:
     *
     * [source,xml]
     * ----
     * <action>
     *   <host id='456'/>
     * </action>
     * ----
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 28 June 2017
     * @status updated_by_docs
     * @since 4.2
     */
    interface RefreshLun {
        /**
         * The host that will be used to refresh the direct LUN disk.
         *
         * @author Idan Shaby <ishaby@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 28 June 2017
         * @status updated_by_docs
         * @since 4.2
         */
        @In Host host();

        @InputDetail
        default void inputDetail() {
            or(mandatory(host().id()), mandatory(host().name()));
        }
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
