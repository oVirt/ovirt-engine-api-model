/*
Copyright (c) 2015-2021 Red Hat, Inc.

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

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;
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
     * There are three types of disks that can be added - disk image, direct LUN and Managed Block disk.
     * https://wiki.openstack.org/wiki/Cinder[Cinder] integration has been replaced by Managed Block Storage.
     *
     * *Adding a new image disk:*
     *
     * When creating a new floating image <<types/disk,Disk>>, the API requires the `storage_domain`, `provisioned_size`
     * and `format` attributes.
     *
     * Note that block storage domains (i.e., storage domains with the <<types/storage_type, storage type>> of iSCSI or
     * FCP) don't support the combination of the raw `format` with `sparse=true`, so `sparse=false` must be stated
     * explicitly.
     *
     * To create a new floating image disk with specified `provisioned_size`, `format` and `name` on a storage domain
     * with an id `123`, send a request as follows:
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
     *     <storage_domain id="123"/>
     *   </storage_domains>
     *   <name>mydisk</name>
     *   <provisioned_size>1048576</provisioned_size>
     *   <format>cow</format>
     * </disk>
     * ----
     *
     *
     * *Adding a new direct LUN disk:*
     *
     * When adding a new floating direct LUN via the API, there are two flavors that can be used:
     *
     * . With a `host` element - in this case, the host is used for sanity checks (e.g., that the LUN is visible) and
     * to retrieve basic information about the LUN (e.g., size and serial).
     * . Without a `host` element - in this case, the operation is a database-only operation, and the storage is never
     * accessed.
     *
     * To create a new floating direct LUN disk with a `host` element with an id `123`, specified `alias`, `type` and
     * `logical_unit` with an id `456` (that has the attributes `address`, `port` and `target`),
     * send a request as follows:
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
     *   <alias>mylun</alias>
     *   <lun_storage>
     *     <host id="123"/>
     *     <type>iscsi</type>
     *     <logical_units>
     *       <logical_unit id="456">
     *         <address>10.35.10.20</address>
     *         <port>3260</port>
     *         <target>iqn.2017-01.com.myhost:444</target>
     *       </logical_unit>
     *     </logical_units>
     *   </lun_storage>
     * </disk>
     * ----
     *
     * To create a new floating direct LUN disk without using a host, remove the `host` element.
     *
     *
     * *Adding a new Cinder disk:*
     *
     * Cinder integration has been replaced by Managed Block Storage.
     * //TODO: Add an example for adding an MBS disk
     *
     * *Adding a floating disks in order to upload disk snapshots:*
     *
     * Since version 4.2 of the engine it is possible to upload disks with
     * snapshots. This request should be used to create the base image of the
     * images chain (The consecutive disk snapshots (images), should be created
     * using `disk-attachments` element when creating a snapshot).
     *
     * The disk has to be created with the same disk identifier and image identifier
     * of the uploaded image. I.e. the identifiers should be saved as part of the
     * backup process. The image identifier can be also fetched using the
     * `qemu-img info` command. For example, if the disk image is stored into
     * a file named `b7a4c6c5-443b-47c5-967f-6abc79675e8b/myimage.img`:
     *
     * [source,shell]
     * ----
     * $ qemu-img info b7a4c6c5-443b-47c5-967f-6abc79675e8b/myimage.img
     * image: b548366b-fb51-4b41-97be-733c887fe305
     * file format: qcow2
     * virtual size: 1.0G (1073741824 bytes)
     * disk size: 196K
     * cluster_size: 65536
     * backing file: ad58716a-1fe9-481f-815e-664de1df04eb
     * backing file format: raw
     * ----
     *
     * To create a disk with with the disk identifier and image identifier obtained
     * with the `qemu-img info` command shown above, send a request like this:
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
     * <disk id="b7a4c6c5-443b-47c5-967f-6abc79675e8b">
     *   <image_id>b548366b-fb51-4b41-97be-733c887fe305</image_id>
     *   <storage_domains>
     *     <storage_domain id="123"/>
     *   </storage_domains>
     *   <name>mydisk</name>
     *   <provisioned_size>1048576</provisioned_size>
     *   <format>cow</format>
     * </disk>
     * ----
     *
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Daniel Erez <derez@redhat.com>
     * @date 11 Jan 2017
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(disk().format());
            mandatory(disk()._interface());
            optional(disk().alias());
            optional(disk().bootable());
            optional(disk().description());
            optional(disk().propagateErrors());
            optional(disk().quota().id());
            optional(disk().shareable());
            optional(disk().sparse());
            optional(disk().wipeAfterDelete());
            optional(disk().id());
            optional(disk().imageId());
        }

        /**
         * Add a new disk to the storage domain with the specified size allocating space from the storage domain.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface OnStorageDomain extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(disk().provisionedSize());
                or(optional(disk().storageDomains()[COLLECTION].id()), optional(disk().storageDomains()[COLLECTION].name()));
                optional(disk().diskProfile().id());
                optional(disk().name());
                optional(disk().openstackVolumeType().name());
//                optional(disk().size());
            }
        }

        /**
         * Add a new lun disk to the storage domain.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface Lun extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(disk().lunStorage().type());
                mandatory(disk().lunStorage().logicalUnits()[COLLECTION].address());
                mandatory(disk().lunStorage().logicalUnits()[COLLECTION].id());
                mandatory(disk().lunStorage().logicalUnits()[COLLECTION].port());
                mandatory(disk().lunStorage().logicalUnits()[COLLECTION].target());
                optional(disk().sgio());
            }
        }
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
     * The order of the returned list of disks is guaranteed only if the `sortby` clause is included in the
     * `search` parameter.
     *
     * @author Aleksei Slaikovskii
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
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
