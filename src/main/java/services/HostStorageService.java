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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.HostStorage;

/**
 * A service to manage host storages.
 *
 * @author Aleksei Slaikovskii <aslaikov@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Storage")
public interface HostStorageService {
    /**
     * Get list of storages.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/hosts/123/storage
     * ----
     *
     * The XML response you get will be like this one:
     *
     * [source,xml]
     * ----
     * <host_storages>
     *   <host_storage id="123">
     *     ...
     *   </host_storage>
     *   ...
     * </host_storages>
     * ----
     *
     * The order of the returned list of storages isn't guaranteed.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        /**
         * Retrieved list of storages.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out HostStorage[] storages();

        /**
         * Indicates if the status of the LUNs in the storage should be checked.
         * Checking the status of the LUN is an heavy weight operation and
         * this data is not always needed by the user.
         * This parameter will give the option to not perform the status check of the LUNs.
         *
         * The default is `true` for backward compatibility.
         *
         * Here an example with the LUN status :
         *
         * [source,xml]
         * ----
         * <host_storage id="123">
         *   <logical_units>
         *     <logical_unit id="123">
         *       <lun_mapping>0</lun_mapping>
         *       <paths>1</paths>
         *       <product_id>lun0</product_id>
         *       <serial>123</serial>
         *       <size>10737418240</size>
         *       <status>used</status>
         *       <vendor_id>LIO-ORG</vendor_id>
         *       <volume_group_id>123</volume_group_id>
         *     </logical_unit>
         *   </logical_units>
         *   <type>iscsi</type>
         *   <host id="123"/>
         * </host_storage>
         * ----
         *
         * Here an example without the LUN status :
         *
         * [source,xml]
         * ----
         * <host_storage id="123">
         *   <logical_units>
         *     <logical_unit id="123">
         *       <lun_mapping>0</lun_mapping>
         *       <paths>1</paths>
         *       <product_id>lun0</product_id>
         *       <serial>123</serial>
         *       <size>10737418240</size>
         *       <vendor_id>LIO-ORG</vendor_id>
         *       <volume_group_id>123</volume_group_id>
         *     </logical_unit>
         *   </logical_units>
         *   <type>iscsi</type>
         *   <host id="123"/>
         * </host_storage>
         * ----
         */
        @In Boolean reportStatus();
    }

    /**
     * Reference to a service managing the storage.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service StorageService storage(String id);
}
