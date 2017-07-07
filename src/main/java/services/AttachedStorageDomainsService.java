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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.StorageDomain;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * Manages the storage domains attached to a data center.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 15 Apr 2017
 * @status added
 */
@Service
@Area("Storage")
public interface AttachedStorageDomainsService {
    /**
     * Attaches an existing storage domain to the data center.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Add {
        @In @Out StorageDomain storageDomain();

        @InputDetail
        default void inputDetail() {
            or(mandatory(storageDomain().id()), mandatory(storageDomain().name()));
            optional(storageDomain().backup());
        }
    }

    /**
     * Returns the list of storage domains attached to the data center.
     *
     * The order of the returned storage domains isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List {
        @Out StorageDomain[] storageDomains();

        /**
         * Sets the maximum number of storage domains to return. If not specified all the storage domains are returned.
         */
        @In Integer max();
    }

    @Service AttachedStorageDomainService storageDomain(String id);
}
