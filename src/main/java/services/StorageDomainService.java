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
import types.Host;
import types.LogicalUnit;
import types.StorageDomain;

@Service
@Area("Storage")
public interface StorageDomainService {
    interface Get {
        @Out StorageDomain storageDomain();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    interface IsAttached {
        @In Host host();
        @Out Boolean isAttached();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Updates a storage domain.
     *
     * Not all of the <<types/storage_domain,StorageDomain>>'s attributes are updatable post-creation. Those that can be
     * updated are: `name`, `description`, `comment`, `warning_low_space_indicator`, `critical_space_action_blocker` and
     * `wipe_after_delete` (note that changing the `wipe_after_delete` attribute will not change the wipe after delete
     * property of disks that already exist).
     *
     * To update the `name` and `wipe_after_delete` attributes of a storage domain with an identifier `123`, send a
     * request as follows:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/storagedomains/123
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
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @In @Out StorageDomain storageDomain();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * This operation forces the update of the `OVF_STORE`
     * of this storage domain.
     *
     * The `OVF_STORE` is a disk image that contains the meta-data
     * of virtual machines and disks that reside in the
     * storage domain. This meta-data is used in case the
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
     */
    interface UpdateOvfStore {
        /**
         * Indicates if the `OVF_STORE` update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * This operation refreshes the LUN size.
     *
     * After increasing the size of the underlying LUN on the storage server,
     * the user can refresh the LUN size.
     * This action forces a rescan of the provided LUNs and
     * updates the database with the new size if required.
     *
     * For example, in order to refresh the size of two LUNs send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/storagedomains/262b056b-aede-40f1-9666-b883eff59d40/refreshluns
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
     * @date 13 Sep 2016
     * @status added
     */
    interface RefreshLuns {
        /**
         * The LUNs that need to be refreshed.
         *
         * @author Fred Rolland <frolland@redhat.com>
         * @date 13 Sep 2016
         * @status added
         */
        @In LogicalUnit[] logicalUnits();

        /**
         * Indicates if the refresh should be performed asynchronously.
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
     * POST /ovirt-engine/api/storagedomains/123/reduceluns
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
     *  <<types/storage_type, storage type> of iSCSI or FCP).
     *
     * @author Liron Aravot <laravot@redhat.com>
     * @date 30 Nov 2016
     * @status added
     * @since 4.1
     */
    interface ReduceLuns {
        /**
         * The logical units that needs to be reduced from the storage domain.
         *
         * @author Liron Aravot <laravot@redhat.com>
         * @date 30 Nov 2016
         * @status added
         * @since 4.1
         */
        @In LogicalUnit[] logicalUnits();
    }

    /**
     * Removes the storage domain.
     *
     * Without any special parameters, the storage domain is detached from the system and removed from the database. The
     * storage domain can then be imported to the same or different setup, with all the data on it. If the storage isn't
     * accessible the operation will fail.
     *
     * If the `destroy` parameter is `true` then the operation will always succeed, even if the storage isn't
     * accessible, the failure is just ignored and the storage domain is removed from the database anyway.
     *
     * If the `format` parameter is `true` then the actual storage is formatted, and the metadata is removed from the
     * LUN or directory, so it can no longer be imported to the same or a different setup.
     */
    interface Remove {
        /**
         * Indicates what host should be used to remove the storage domain.
         *
         * This parameter is mandatory, and it can contain the name or the identifier of the host. For example, to use
         * the host named `myhost` to remove the storage domain with identifier `123` send a request like this:
         *
         * [source]
         * ----
         * DELETE /ovirt-engine/api/storagedomains/123?host=myhost
         * ----
         */
        @In String host();

        /**
         * Indicates if the actual storage should be formatted, removing all the metadata from the underlying LUN or
         * directory:
         *
         * [source]
         * ----
         * DELETE /ovirt-engine/api/storagedomains/123?format=true
         * ----
         *
         * This parameter is optional, and the default value is `false`.
         *
         * @author Allon Mureinik <amureini@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In Boolean format();

        /**
         * Indicates if the operation should succeed, and the storage domain removed from the database, even if the
         * storage isn't accessible.
         *
         * [source]
         * ----
         * DELETE /ovirt-engine/api/storagedomains/123?destroy=true
         * ----
         *
         * This parameter is optional, and the default value is `false`.
         *
         * @author Allon Mureinik <amureini@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        // TODO: Consider renaming this to `force`, or `ignore_errors`, as that describes better what it actually means.
        @In Boolean destroy();

        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service AssignedDiskProfilesService diskProfiles();
    @Service AssignedPermissionsService permissions();
    @Service DiskSnapshotsService diskSnapshots();
    @Service DisksService disks();

    /**
     * Returns a reference to the service that manages the files available in the storage domain.
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    @Service FilesService files();
    @Service ImagesService images();

    /**
     * Returns a reference to the service that manages the storage connections.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    @Service StorageDomainServerConnectionsService storageConnections();
    @Service StorageDomainTemplatesService templates();
    @Service StorageDomainVmsService vms();
}
