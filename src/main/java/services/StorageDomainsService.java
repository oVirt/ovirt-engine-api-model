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
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.StorageDomain;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * Manages the set of storage domains in the system.
 *
 * @author Juan Hernandez <juan.hernadez@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 3 May 2018
 * @status updated_by_docs
 */
@Service
@Area("Storage")
public interface StorageDomainsService {
    /**
     * Adds a new storage domain.
     *
     * Creation of a new <<types/storage_domain,StorageDomain>> requires the `name`, `type`, `host`, and `storage`
     * attributes. Identify the `host` attribute with the `id` or `name` attributes. In {product-name} 3.6 and
     * later you can enable the wipe after delete option by default on the storage domain. To configure this, specify
     * `wipe_after_delete` in the POST request. This option can be edited after the domain is created, but doing so will
     * not change the wipe after delete property of disks that already exist.
     *
     * To add a new storage domain with specified `name`, `type`, `storage.type`, `storage.address`, and `storage.path`,
     * and using a host with an id `123`, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/storageDomains
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <storage_domain>
     *   <name>mydata</name>
     *   <type>data</type>
     *   <storage>
     *     <type>nfs</type>
     *     <address>mynfs.example.com</address>
     *     <path>/exports/mydata</path>
     *   </storage>
     *   <host>
     *     <name>myhost</name>
     *   </host>
     * </storage_domain>
     * ----
     *
     * To create a new NFS ISO storage domain send a request like this:
     *
     * [source,xml]
     * ----
     * <storage_domain>
     *   <name>myisos</name>
     *   <type>iso</type>
     *   <storage>
     *     <type>nfs</type>
     *     <address>mynfs.example.com</address>
     *     <path>/export/myisos</path>
     *   </storage>
     *   <host>
     *     <name>myhost</name>
     *   </host>
     * </storage_domain>
     * ----
     *
     * To create a new iSCSI storage domain send a request like this:
     *
     * [source,xml]
     * ----
     * <storage_domain>
     *   <name>myiscsi</name>
     *   <type>data</type>
     *   <storage>
     *     <type>iscsi</type>
     *     <logical_units>
     *       <logical_unit id="3600144f09dbd050000004eedbd340001"/>
     *       <logical_unit id="3600144f09dbd050000004eedbd340002"/>
     *     </logical_units>
     *   </storage>
     *   <host>
     *     <name>myhost</name>
     *   </host>
     * </storage_domain>
     * ----
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @author Tal Nisan <tnisan@redhat.com>
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 3 May 2018
     * @status updated_by_docs
     */
    interface Add {

        /**
         * The storage domain to add.
         *
         * @author Idan Shaby <ishaby@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 3 May 2018
         * @status updated_by_docs
         */
        @In @Out StorageDomain storageDomain();
        @InputDetail
        default void inputDetail() {
            mandatory(storageDomain().storage().type());
            mandatory(storageDomain().type());
            or(mandatory(storageDomain().host().id()), mandatory(storageDomain().host().name()));
            optional(storageDomain().comment());
            optional(storageDomain().criticalSpaceActionBlocker());
            optional(storageDomain().warningLowSpaceIndicator());
            optional(storageDomain().backup());
            optional(storageDomain().blockSize());
        }

        /**
         * Add a new storage domain to the system using a direct LUN.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 3 May 2018
         * @status updated_by_docs
         */
        interface DirectLun extends Add {
            @InputDetail
            default void inputDetail() {
                optional(storageDomain().storage().address());
                mandatory(storageDomain().storage().logicalUnits()[COLLECTION].address());
                mandatory(storageDomain().storage().logicalUnits()[COLLECTION].id());
                mandatory(storageDomain().storage().logicalUnits()[COLLECTION].port());
                mandatory(storageDomain().storage().logicalUnits()[COLLECTION].target());
                optional(storageDomain().storage().logicalUnits()[COLLECTION].lunMapping());
                optional(storageDomain().storage().logicalUnits()[COLLECTION].password());
                optional(storageDomain().storage().logicalUnits()[COLLECTION].paths());
                optional(storageDomain().storage().logicalUnits()[COLLECTION].portal());
                optional(storageDomain().storage().logicalUnits()[COLLECTION].productId());
                optional(storageDomain().storage().logicalUnits()[COLLECTION].serial());
                optional(storageDomain().storage().logicalUnits()[COLLECTION].username());
                optional(storageDomain().storage().logicalUnits()[COLLECTION].vendorId());
                optional(storageDomain().name());
                optional(storageDomain().storage().overrideLuns());
                optional(storageDomain().storageFormat());
                optional(storageDomain().wipeAfterDelete());
            }
        }

        /**
         * Import an existing block storage domain to the system using the targets already connected to the host.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 3 May 2018
         * @status updated_by_docs
         */
        interface BlockDomain extends Add {
            @InputDetail
            default void inputDetail() {
//                mandatory(storageDomain()._import());  TODO: uncomment when able to handle '_'
                optional(storageDomain().storage().address());
            }
        }

        /**
         * Add a new storage domain to the system using the storage on the given host and path.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 3 May 2018
         * @status updated_by_docs
         */
        interface ByPath extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(storageDomain().storage().address());
                mandatory(storageDomain().storage().path());
                optional(storageDomain().name());
                optional(storageDomain().storageFormat());
                optional(storageDomain().wipeAfterDelete());
            }
        }

        /**
         * Add a new storage domain to the system using the storage on the local host at the given path.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 3 May 2018
         * @status updated_by_docs
         */
        interface Local extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(storageDomain().storage().path());
                optional(storageDomain().name());
                optional(storageDomain().storageFormat());
                optional(storageDomain().wipeAfterDelete());
            }
        }

        /**
         * Add a new storage domain to the system using Gluster or POSIX FS storage.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 3 May 2018
         * @status updated_by_docs
         */
        interface GlusterOrPostfs extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(storageDomain().storage().path());
                mandatory(storageDomain().storage().vfsType());
                optional(storageDomain().name());
                optional(storageDomain().storage().address());
                optional(storageDomain().storage().mountOptions());
                optional(storageDomain().storageFormat());
                optional(storageDomain().wipeAfterDelete());
            }
        }
    }

    /**
     * Returns the list of storage domains in the system.
     *
     * The order of the returned list of storage domains is guaranteed only if the `sortby` clause is included
     * in the `search` parameter.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 3 May 2018
     * @status updated_by_docs
     */
    interface List extends Follow {

        /**
         * A list of the storage domains in the system.
         *
         * @author Idan Shaby <ishaby@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 3 May 2018
         * @status updated_by_docs
         */
        @Out StorageDomain[] storageDomains();

        /**
         * Sets the maximum number of storage domains to return. If not specified, all the storage domains are returned.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 3 May 2018
         * @status updated_by_docs
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned storage domains.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 3 May 2018
         * @status updated_by_docs
         */
        @In String search();

        /**
         * Indicates if the search should be performed taking case into account.
         * The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case, set it to `false`.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 3 May 2018
         * @status updated_by_docs
         */
        @In Boolean caseSensitive();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 3 May 2018
         * @status updated_by_docs
         */
        @In Boolean filter();
    }

    @Service StorageDomainService storageDomain(String id);
}
