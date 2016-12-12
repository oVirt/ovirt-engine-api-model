/*
Copyright (c) 2015-2016 Red Hat, Inc.

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

/**
 * Manages the collection of disks available in the system.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 4 Nov 2016
 * @status added
 */
@Service
@Area("Storage")
public interface DisksService {
    /**
     * Adds a new floating disk.
     *
     * When creating a new floating <<types/disk,Disk>>, the API requires the `storage_domain`, `provisioned_size` and
     * `format` attributes.
     *
     * To create a new floating disk with specified `provisioned_size`, `format` and `name` on a storage domain with an
     * id `e9fedf39-5edc-4e0a-8628-253f1b9c5693`, send a request as follows:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/disks
     * ----
     *
     * With a request body as follows:
     *
     * [source,xml]
     * ----
     * <disk>
     *   <storage_domains>
     *     <storage_domain id="e9fedf39-5edc-4e0a-8628-253f1b9c5693"/>
     *   </storage_domains>
     *   <name>disk1</name>
     *   <provisioned_size>1048576</provisioned_size>
     *   <format>cow</format>
     * </disk>
     * ----
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        /**
         * The disk.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Disk disk();
    }

    /**
     * Get list of disks.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/disks
     * ----
     *
     * You will get a XML response which will look like this one:
     *
     * [source,xml]
     * ----
     * <disks>
     *   <disk id="123">
     *     <actions>...</actions>
     *     <name>MyDisk</name>
     *     <description>MyDisk description</description>
     *     <link href="/ovirt-engine/api/disks/123/permissions" rel="permissions"/>
     *     <link href="/ovirt-engine/api/disks/123/statistics" rel="statistics"/>
     *     <actual_size>5345845248</actual_size>
     *     <alias>MyDisk alias</alias>
     *     ...
     *     <status>ok</status>
     *     <storage_type>image</storage_type>
     *     <wipe_after_delete>false</wipe_after_delete>
     *     <disk_profile id="123"/>
     *     <quota id="123"/>
     *     <storage_domains>...</storage_domains>
     *   </disk>
     *   ...
     * </disks>
     * ----
     *
     * @author Aleksei Slaikovskii
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        /**
         * List of retrieved disks.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Disk[] disks();

        /**
         * Sets the maximum number of disks to return. If not specified all the disks are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned disks.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();
    }

    /**
     * Reference to a service managing a specific disk.
     *
     * @author Aleksei Slaikovskii
     * @date 12 Dec 2016
     * @status added
     */
    @Service DiskService disk(String id);
}
