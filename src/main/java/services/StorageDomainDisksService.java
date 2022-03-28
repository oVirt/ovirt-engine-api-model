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

/**
 * Manages the collection of disks available inside a specific storage domain.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 13 Sep 2017
 * @status updated_by_docs
 */
@Service
@Area("Storage")
public interface StorageDomainDisksService {
    /**
     * Adds or registers a disk.
     *
     * IMPORTANT: Since version 4.2 of the {engine-name} this operation is deprecated, and preserved only for backwards
     * compatibility. It will be removed in the future. To add a new disk use the <<services/disks/methods/add, add>>
     * operation of the service that manages the disks of the system. To register an unregistered disk use the
     * <<services/attached_storage_domain_disk/methods/register, register>> operation of the service that manages
     * that disk.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     */
    @Deprecated
    interface Add {
        /**
         * The disk to add or register.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        @In @Out Disk disk();

        /**
         * Indicates if a new disk should be added or if an existing unregistered disk should be registered. If the
         * value is `true` then the identifier of the disk to register needs to be provided. For example, to register
         * the disk with ID `456` send a request like this:
         *
         * ....
         * POST /ovirt-engine/api/storagedomains/123/disks?unregistered=true
         * ....
         *
         * With a request body like this:
         *
         * [source,xml]
         * ----
         * <disk id="456"/>
         * ----
         *
         * If the value is `false` then a new disk will be created in the storage domain. In that case the
         * `provisioned_size`, `format`, and `name` attributes are mandatory. For example, to create a new
         * _copy on write_ disk of 1 GiB, send a request like this:
         *
         * ....
         * POST /ovirt-engine/api/storagedomains/123/disks
         * ....
         *
         * With a request body like this:
         *
         * [source,xml]
         * ----
         * <disk>
         *   <name>mydisk</name>
         *   <format>cow</format>
         *   <provisioned_size>1073741824</provisioned_size>
         * </disk>
         * ----
         *
         * The default value is `false`.
         *
         * This parameter has been deprecated since version 4.2 of
         * the {engine-name}.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         * @Deprecated 4.2
         */
        @In Boolean unregistered();
    }

    /**
     * Retrieves the list of disks that are available in the storage domain.
     *
     * The order of the returned list of disks is not guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     */
    interface List extends Follow {
        /**
         * The list of retrieved disks.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        @Out Disk[] disks();

        /**
         * Sets the maximum number of disks to return. If not specified, all the disks are returned.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        @In Integer max();

        /**
         * Indicates whether to retrieve a list of registered or unregistered disks in the storage domain.
         * To get a list of unregistered disks in the storage domain the call should indicate the unregistered flag.
         * For example, to get a list of unregistered disks the REST API call should look like this:
         *
         * ....
         * GET /ovirt-engine/api/storagedomains/123/disks?unregistered=true
         * ....
         *
         * The default value of the unregistered flag is `false`.
         * The request only applies to storage domains that are attached.
         *
         * @author Maor Lipchuk <mlipchuk@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         * @since 4.1.1
         */
        @In Boolean unregistered();
    }

    /**
     * A reference to the service that manages a specific disk.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     */
    @Service StorageDomainDiskService disk(String id);
}
