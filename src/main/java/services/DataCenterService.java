/*
Copyright (c) 2015-2020 Red Hat, Inc.

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
import types.DataCenter;
import types.StorageDomain;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * A service to manage a data center.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface DataCenterService {

    /**
     * Get a data center.
     *
     * An example of getting a data center:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/datacenters/123
     * ----
     *
     * [source,xml]
     * ----
     * <data_center href="/ovirt-engine/api/datacenters/123" id="123">
     *   <name>Default</name>
     *   <description>The default Data Center</description>
     *   <link href="/ovirt-engine/api/datacenters/123/clusters" rel="clusters"/>
     *   <link href="/ovirt-engine/api/datacenters/123/storagedomains" rel="storagedomains"/>
     *   <link href="/ovirt-engine/api/datacenters/123/permissions" rel="permissions"/>
     *   <link href="/ovirt-engine/api/datacenters/123/networks" rel="networks"/>
     *   <link href="/ovirt-engine/api/datacenters/123/quotas" rel="quotas"/>
     *   <link href="/ovirt-engine/api/datacenters/123/qoss" rel="qoss"/>
     *   <link href="/ovirt-engine/api/datacenters/123/iscsibonds" rel="iscsibonds"/>
     *   <local>false</local>
     *   <quota_mode>disabled</quota_mode>
     *   <status>up</status>
     *   <storage_format>v3</storage_format>
     *   <supported_versions>
     *     <version>
     *       <major>4</major>
     *       <minor>0</minor>
     *    </version>
     *   </supported_versions>
     *   <version>
     *     <major>4</major>
     *     <minor>0</minor>
     *   </version>
     *   <mac_pool href="/ovirt-engine/api/macpools/456" id="456"/>
     * </data_center>
     * ----
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        @Out DataCenter dataCenter();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    /**
     * Updates the data center.
     *
     * The `name`, `description`, `storage_type`, `version`, `storage_format` and `mac_pool` elements are updatable
     * post-creation. For example, to change the name and description of data center `123` send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/datacenters/123
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <data_center>
     *   <name>myupdatedname</name>
     *   <description>An updated description for the data center</description>
     * </data_center>
     * ----
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(dataCenter().comment());
            optional(dataCenter().description());
            optional(dataCenter().local());
            optional(dataCenter().macPool().id());
            optional(dataCenter().name());
            optional(dataCenter().storageFormat());
            optional(dataCenter().version().major());
            optional(dataCenter().version().minor());
        }
        /**
         * The data center that is being updated.
         *
         * @author Shmuel Melamud <smelamud@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In @Out DataCenter dataCenter();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes the data center.
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/datacenters/123
     * ----
     *
     * Without any special parameters, the storage domains attached to the data center are detached and then removed
     * from the storage. If something fails when performing this operation, for example if there is no host available to
     * remove the storage domains from the storage, the complete operation will fail.
     *
     * If the `force` parameter is `true` then the operation will always succeed, even if something fails while removing
     * one storage domain, for example. The failure is just ignored and the data center is removed from the database
     * anyway.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Aug 2016
     * @status added
     * @since 4.0.0
     */
    interface Remove {
        /**
         * Indicates if the operation should succeed, and the storage domain removed from the database, even if
         * something fails during the operation.
         *
         * This parameter is optional, and the default value is `false`.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 15 Aug 2016
         * @status added
         * @since 4.0.4
         */
        @In Boolean force();

        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Used for manually setting a storage domain in the data center as a master.
     * For example, for setting a storage domain with ID '456' as a master on a data center with ID '123',
     * send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/datacenters/123/setmaster
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <action>
     *   <storage_domain id="456"/>
     * </action>
     * ----
     *
     * The new master storage domain can be also specified by its name.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @date 17 Nov 2020
     * @status added
     * @since 4.4.4
     */
    interface SetMaster {
        @InputDetail
        default void inputDetail() {
            or(mandatory(storageDomain().id()), mandatory(storageDomain().name()));
        }
        /**
         * The new master storage domain for the data center.
         *
         * @author Shani Leviim <sleviim@redhat.com>
         * @date 17 Nov 2020
         * @status added
         * @since 4.4.4
         */
        @In StorageDomain storageDomain();

        /**
         * Indicates if the action should be performed asynchronously.
         *
         * @author Shani Leviim <sleviim@redhat.com>
         * @date 17 Nov 2020
         * @status added
         * @since 4.4.4
         */
        @In Boolean async();
    }

    /**
     * Currently, the storage pool manager (SPM) fails to
     * switch to another host if the SPM has uncleared tasks.
     * Clearing all finished tasks enables the SPM switching.
     *
     * For example, to clean all the finished tasks on a data center with ID `123` send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/datacenters/123/cleanfinishedtasks
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Pavel Bar <pbar@redhat.com>
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 10 Dec 2020
     * @status updated_by_docs
     * @since 4.4.4
     */
    interface CleanFinishedTasks {
        /**
         * Indicates if the action should be performed asynchronously.
         *
         * @author Pavel Bar <pbar@redhat.com>
         * @date 10 Dec 2020
         * @status added
         * @since 4.4.4
         */
        @In Boolean async();
    }

    /**
    * Attach and detach storage domains to and from a data center.
    *
    * For attaching a single storage domain we should use the following POST request:
    *
    * [source]
    * ----
    * POST /ovirt-engine/api/datacenters/123/storagedomains
    * ----
    *
    * With a request body like this:
    *
    * [source,xml]
    * ----
    * <storage_domain>
    *   <name>data1</name>
    * </storage_domain>
    * ----
    *
    * For detaching a single storage domain we should use the following DELETE request:
    *
    * [source]
    * ----
    * DELETE /ovirt-engine/api/datacenters/123/storagedomains/123
    * ----
    *
    * @author Maor Lipchuk <mlipchuk@redhat.com>
    * @date 14 Sep 2016
    * @status added
    */
    @Service AttachedStorageDomainsService storageDomains();
    @Service ClustersService clusters();

    /**
     * Returns a reference to the service, that manages the networks, that are associated with the data center.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 24 Oct 2016
     * @status added
     */
    @Service DataCenterNetworksService networks();

    /**
     * Reference to the permissions service.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service AssignedPermissionsService permissions();

    /**
     * Reference to the quotas service.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service QuotasService quotas();

    /**
     * Reference to the QOSs service.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service QossService qoss();

    /**
     * Reference to the iSCSI bonds service.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service IscsiBondsService iscsiBonds();
}
