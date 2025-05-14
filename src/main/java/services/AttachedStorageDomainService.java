/*
The oVirt Project - oVirt Engine Api Model

Copyright oVirt Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the Apache License, Version 2.0 is included with the program
in the file ASL2.
*/

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.StorageDomain;

@Service
@Area("Storage")
public interface AttachedStorageDomainService {
    /**
     * This operation activates an attached storage domain.
     * Once the storage domain is activated it is ready for use with the data center.
     *
     * ```http
     * POST /ovirt-engine/api/datacenters/123/storagedomains/456/activate
     * ```
     *
     * The activate action does not take any action specific parameters,
     * so the request body should contain an empty `action`:
     *
     * ```xml
     * <action/>
     * ```
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Activate {
        /**
         * Indicates if the activation should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * This operation deactivates an attached storage domain.
     * Once the storage domain is deactivated it will not be used with the data center.
     * For example, to deactivate storage domain `456`, send the following request:
     *
     * ```http
     * POST /ovirt-engine/api/datacenters/123/storagedomains/456/deactivate
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <action/>
     * ```
     *
     * If the `force` parameter is `true` then the operation will succeed, even if the OVF update which takes place
     * before the deactivation of the storage domain failed. If the `force` parameter is `false` and the OVF update failed,
     * the deactivation of the storage domain will also fail.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 06 Nov 2017
     * @status updated_by_docs
     */
    interface Deactivate {
        /**
         * Indicates if the deactivation should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the operation should succeed and the storage domain should be moved to a deactivated state, even if
         * the OVF update for the storage domain failed.
         * For example, to deactivate storage domain `456` using force flag, send the following request:
         *
         * ```http
         * POST /ovirt-engine/api/datacenters/123/storagedomains/456/deactivate
         * ```
         *
         * With a request body like this:
         *
         * ```xml
         * <action>
         *   <force>true</force>
         * <action>
         * ```
         *
         * This parameter is optional, and the default value is `false`.
         *
         * @author Eyal Shenitzky<eshenitz@redhat.com>
         * @author Byron Gravenorst <bgraveno@redhat.com>
         * @date 06 Nov 2017
         * @status updated_by_docs
         * @since 4.2
         */
        @In Boolean force();
    }

    interface Get extends Follow {
        @Out StorageDomain storageDomain();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service AttachedStorageDomainDisksService disks();
}
