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
import types.StorageDomain;

@Service
@Area("Storage")
public interface StorageDomainsService {

    /**
     * Adds a new storage domain.
     *
     * Creation of a new <<types/storage_domain,StorageDomain>> requires the `name`, `type`, `host` and `storage`
     * attributes. Identify the `host` attribute with the `id` or `name` attributes. In oVirt 3.6 and later you can
     * enable the wipe after delete option by default on the storage domain. To configure this, specify
     * `wipe_after_delete` in the POST request. This option can be edited after the domain is created, but doing so will
     * not change the wipe after delete property of disks that already exist.
     *
     * To add a new storage domain with specified `name`, `type`, `storage.type`, `storage.address` and `storage.path`
     * and by using a host with an id `123`, send a request as follows:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/storagedomains
     * ----
     *
     * With a request body as follows:
     *
     * [source,xml]
     * ----
     * <storage_domain>
     *   <name>data1</name>
     *   <type>data</type>
     *   <host id="123"/>
     *   <storage>
     *     <type>nfs</type>
     *     <address>172.31.0.6</address>
     *     <path>/exports/ovirt/images/0</path>
     *   </storage>
     * </storage_domain>
     * ----
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        @In @Out StorageDomain storageDomain();
    }

    interface List {
        @Out StorageDomain[] storageDomains();

        /**
         * Sets the maximum number of storage domains to return. If not specified all the storage domains are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned storage domains.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    @Service StorageDomainService storageDomain(String id);
}
