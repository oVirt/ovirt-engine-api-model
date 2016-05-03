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

    interface RefreshLuns {
        @In LogicalUnit[] logicalUnits();

        /**
         * Indicates if the refresh should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service AssignedDiskProfilesService diskProfiles();
    @Service AssignedPermissionsService permissions();
    @Service DiskSnapshotsService diskSnapshots();
    @Service DisksService disks();
    @Service FilesService files();
    @Service ImagesService images();
    @Service StorageDomainServerConnectionsService storageConnections();
    @Service StorageDomainTemplatesService templates();
    @Service StorageDomainVmsService vms();
}
