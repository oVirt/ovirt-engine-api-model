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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.DiskSnapshot;

/**
 * Manages the collection of disk snapshots available in an storage domain.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 15 Apr 2017
 * @status added
 */
@Service
@Area("Storage")
public interface DiskSnapshotsService {
    /**
     * Returns the list of disk snapshots of the storage domain.
     *
     * The order of the returned list of disk snapshots isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out DiskSnapshot[] snapshots();

        /**
         * Sets the maximum number of snapshots to return. If not specified all the snapshots are returned.
         */
        @In Integer max();

        /**
         * If true return also active snapshots. If not specified active snapshots are not returned.
         *
         * @author Nir Soffer <nsoffer@redhat.com>
         * @date 26 Sep 2020
         * @status added
         * @since 4.4.3
         */
        @In Boolean includeActive();

        /**
         * If true return also template snapshots. If not specified template snapshots are not returned.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 8 Jul 2021
         * @status added
         * @since 4.4.8
         */
        @In Boolean includeTemplate();
    }

    @Service DiskSnapshotService snapshot(String id);
}
