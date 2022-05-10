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
import types.Host;
import types.LogicalUnit;
import types.StorageDomain;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
@Service
@Area("Storage")
public interface StorageDomainService {
    /**
     * Retrieves the description of the storage domain.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 12 Apr 2018
     * @status updated_by_docs
     */
    interface Get extends Follow {
        /**
         * The description of the storage domain.
         *
         * @author Shani Leviim <sleviim@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 12 Apr 2018
         * @status updated_by_docs
         */
        @Out StorageDomain storageDomain();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        @In Boolean filter();
    }
    /**
     * Used for querying if the storage domain is already attached to a data center using
     * the is_attached boolean field, which is part of the storage server. IMPORTANT:
     * Executing this API will cause the host to disconnect from the storage domain.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     */
    interface IsAttached {
        @InputDetail
        default void inputDetail() {
            mandatory(host().id());
        }
        /**
         * Indicates the data center's host.
         *
         * @author Shani Leviim <sleviim@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 12 Apr 2018
         * @status updated_by_docs
         */
        @In Host host();
        /**
         * Indicates whether the storage domain is attached to the data center.
         *
         * @author Shani Leviim <sleviim@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 12 Apr 2018
         * @status updated_by_docs
         */
        @Out Boolean isAttached();

        /**
         * Indicates if the action should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Updates a storage domain.
     *
     * Not all of the xref:types/storage_domain[StorageDomain]'s attributes are updatable after creation. Those that can be
     * updated are: `name`, `description`, `comment`, `warning_low_space_indicator`, `critical_space_action_blocker` and
     * `wipe_after_delete.` (Note that changing the `wipe_after_delete` attribute will not change the wipe after delete
     * property of disks that already exist).
     *
     * To update the `name` and `wipe_after_delete` attributes of a storage domain with an identifier `123`, send a
     * request as follows:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/storageDomains/123
     * ----
     *
     * With a request body as follows:
     *
     * [source,xml]
     * ----
     * <storage_domain>
     *   <name>data2</name>
     *   <wipe_after_delete>true</wipe_after_delete>
     * </storage_domain>
     * ----
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     */
    interface Update {
        default void inputDetail() {
            optional(storageDomain().name());
            optional(storageDomain().storage().logicalUnits()[COLLECTION].address());
            optional(storageDomain().storage().logicalUnits()[COLLECTION].id());
            optional(storageDomain().storage().logicalUnits()[COLLECTION].port());
            optional(storageDomain().storage().logicalUnits()[COLLECTION].target());
            optional(storageDomain().storage().logicalUnits()[COLLECTION].lunMapping());
            optional(storageDomain().storage().logicalUnits()[COLLECTION].password());
            optional(storageDomain().storage().logicalUnits()[COLLECTION].paths());
            optional(storageDomain().storage().logicalUnits()[COLLECTION].portal());
            optional(storageDomain().storage().logicalUnits()[COLLECTION].productId());
            optional(storageDomain().storage().logicalUnits()[COLLECTION].serial());
            optional(storageDomain().storage().logicalUnits()[COLLECTION].username());
            optional(storageDomain().storage().logicalUnits()[COLLECTION].vendorId());
            optional(storageDomain().comment());
            optional(storageDomain().criticalSpaceActionBlocker());
            optional(storageDomain().host().id()); //DEPRECATED
            optional(storageDomain().storage().overrideLuns());
            optional(storageDomain().warningLowSpaceIndicator());
            optional(storageDomain().wipeAfterDelete());
        }

        /**
         * The updated storage domain.
         *
         * @author Shani Leviim <sleviim@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 12 Apr 2018
         * @status updated_by_docs
         */
        @In @Out StorageDomain storageDomain();

        /**
         * Indicates if the update should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * This operation forces the update of the `OVF_STORE`
     * of this storage domain.
     *
     * The `OVF_STORE` is a disk image that contains the metadata
     * of virtual machines and disks that reside in the
     * storage domain. This metadata is used in case the
     * domain is imported or exported to or from a different
     * data center or a different installation.
     *
     * By default the `OVF_STORE` is updated periodically
     * (set by default to 60 minutes) but users might want to force an
     * update after an important change, or when the they believe the
     * `OVF_STORE` is corrupt.
     *
     * When initiated by the user, `OVF_STORE` update will be performed whether
     * an update is needed or not.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     */
    interface UpdateOvfStore {
        /**
         * Indicates if the `OVF_STORE` update should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * This operation refreshes the LUN size.
     *
     * After increasing the size of the underlying LUN on the storage server,
     * the user can refresh the LUN size.
     * This action forces a rescan of the provided LUNs and
     * updates the database with the new size, if required.
     *
     * For example, in order to refresh the size of two LUNs send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/storageDomains/262b056b-aede-40f1-9666-b883eff59d40/refreshluns
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     *  <action>
     *    <logical_units>
     *      <logical_unit id="1IET_00010001"/>
     *      <logical_unit id="1IET_00010002"/>
     *    </logical_units>
     *  </action>
     * ----
     *
     * @author Fred Rolland <frolland@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     */
    interface RefreshLuns {
        @InputDetail
        default void inputDetail() {
            optional(logicalUnits()[COLLECTION].id());
        }
        /**
         * The LUNs that need to be refreshed.
         *
         * @author Fred Rolland <frolland@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        @In LogicalUnit[] logicalUnits();

        /**
         * Indicates if the refresh should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * This operation reduces logical units from the storage domain.
     *
     * In order to do so the data stored on the provided logical units will be moved to other logical units of the
     * storage domain and only then they will be reduced from the storage domain.
     *
     * For example, in order to reduce two logical units from a storage domain send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/storageDomains/123/reduceluns
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     *  <action>
     *    <logical_units>
     *      <logical_unit id="1IET_00010001"/>
     *      <logical_unit id="1IET_00010002"/>
     *    </logical_units>
     *  </action>
     * ----
     *
     *  Note that this operation is only applicable to block storage domains (i.e., storage domains with the
     *  xref:types/storage_type[storage type] of iSCSI or FCP).
     *
     * @author Liron Aravot <laravot@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     * @since 4.1
     */
    interface ReduceLuns {
        /**
         * The logical units that need to be reduced from the storage domain.
         *
         * @author Liron Aravot <laravot@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         * @since 4.1
         */
        @In LogicalUnit[] logicalUnits();//TODO: missing @InputDetail
    }

    /**
     * Removes the storage domain.
     *
     * Without any special parameters, the storage domain is detached from the system and removed from the database. The
     * storage domain can then be imported to the same or to a different setup, with all the data on it. If the storage is
     * not accessible the operation will fail.
     *
     * If the `destroy` parameter is `true` then the operation will always succeed, even if the storage is not
     * accessible, the failure is just ignored and the storage domain is removed from the database anyway.
     *
     * If the `format` parameter is `true` then the actual storage is formatted, and the metadata is removed from the
     * LUN or directory, so it can no longer be imported to the same or to a different setup.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     */
    interface Remove {//TODO: missing @InputDetail
        /**
         * Indicates which host should be used to remove the storage domain.
         *
         * This parameter is mandatory, except if the `destroy` parameter is included and its value is `true`, in that
         * case the `host` parameter will be ignored.
         *
         * The value should contain the name or the identifier of the host. For example, to use the host named `myhost`
         * to remove the storage domain with identifier `123` send a request like this:
         *
         * [source]
         * ----
         * DELETE /ovirt-engine/api/storageDomains/123?host=myhost
         * ----
         * @author Maor Lipchuk <mlipchuk@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        @In String host();

        /**
         * Indicates if the actual storage should be formatted, removing all the metadata from the underlying LUN or
         * directory:
         *
         * [source]
         * ----
         * DELETE /ovirt-engine/api/storageDomains/123?format=true
         * ----
         *
         * This parameter is optional, and the default value is `false`.
         *
         * @author Allon Mureinik <amureini@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        @In Boolean format();

        /**
         * Indicates if the operation should succeed, and the storage domain removed from the database, even if the
         * storage is not accessible.
         *
         * [source]
         * ----
         * DELETE /ovirt-engine/api/storageDomains/123?destroy=true
         * ----
         *
         * This parameter is optional, and the default value is `false`.
         * When the value of `destroy` is `true` the `host` parameter will be ignored.
         *
         * @author Allon Mureinik <amureini@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        // TODO: Consider renaming this to `force`, or `ignore_errors`, as that describes better what it actually means.
        @In Boolean destroy();

        /**
         * Indicates if the remove should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 13 Sep 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    @Service AssignedDiskProfilesService diskProfiles();
    @Service AssignedPermissionsService permissions();
    @Service DiskSnapshotsService diskSnapshots();

    /**
     * Reference to the service that manages the disks available in the storage domain.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     */
    @Service StorageDomainDisksService disks();

    /**
     * Returns a reference to the service that manages the files available in the storage domain.
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     */
    @Service FilesService files();
    @Service ImagesService images();

    /**
     * Returns a reference to the service that manages the storage connections.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     */
    @Service StorageDomainServerConnectionsService storageConnections();
    @Service StorageDomainTemplatesService templates();
    @Service StorageDomainVmsService vms();
}
