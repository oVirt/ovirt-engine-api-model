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
import types.Disk;

/**
 * Manages the set of disks available in a storage domain.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 15 Apr 2017
 * @status added
 */
@Service
@Area("Storage")
public interface StorageDomainContentDisksService {
    /**
     * Returns the list of disks available in the storage domain.
     *
     * The order of the returned list of disks is guaranteed only if the `sortby` clause is included in
     * the `search` parameter.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
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

    @Service StorageDomainContentDiskService disk(String id);
}
