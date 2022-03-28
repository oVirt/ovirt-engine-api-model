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
import types.StorageConnection;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

/**
 * Manages the set of connections to storage servers that exist in a storage domain.
 *
 * @author Juan Hernandez <juan.henandez@redhat.com>
 * @date 15 Arp 2017
 * @status added
 */
@Service
@Area("Storage")
public interface StorageDomainServerConnectionsService {

    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(connection().id());
        }
        @In @Out StorageConnection connection();
    }

    /**
     * Returns the list of connections to storage servers that existin the storage domain.
     *
     * The order of the returned list of connections isn't guaranteed.
     *
     * @author Juan Hernandez <juan.henandez@redhat.com>
     * @date 15 Arp 2017
     * @status added
     */
    interface List extends Follow {
        @Out StorageConnection[] connections();

        /**
         * Sets the maximum number of connections to return. If not specified all the connections are returned.
         */
        @In Integer max();
    }

    @Service StorageDomainServerConnectionService connection(String id);
}
