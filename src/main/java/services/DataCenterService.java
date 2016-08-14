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
import types.DataCenter;

@Service
@Area("Virtualization")
public interface DataCenterService {
    interface Get {
        @Out DataCenter dataCenter();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    interface Update {
        @In @Out DataCenter dataCenter();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes the data center.
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

    @Service AttachedStorageDomainsService storageDomains();
    @Service ClustersService clusters();
    @Service NetworksService networks();
    @Service AssignedPermissionsService permissions();
    @Service QuotasService quotas();
    @Service QossService qoss();
    @Service IscsiBondsService iscsiBonds();
}
