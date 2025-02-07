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

package services.openstack;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import services.ExternalProviderService;
import types.OpenStackVolumeProvider;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * Openstack Volume (Cinder) integration has been replaced by Managed Block Storage.
 *
 * @author Shani Leviim <sleviim@redhat.com>
 * @date 14 Oct 2021
 * @status added
 */

@Deprecated
@Service
@Area("Storage")
public interface OpenstackVolumeProviderService extends ExternalProviderService {
    interface Get extends Follow {
        @Out OpenStackVolumeProvider provider();
    }

    /**
     * Update the specified OpenStack volume provider in the system.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(provider().authenticationUrl());
            optional(provider().description());
            optional(provider().name());
            optional(provider().password());
            optional(provider().requiresAuthentication());
            optional(provider().tenantName());
            optional(provider().username());
            or(optional(provider().dataCenter().id()), optional(provider().dataCenter().name()));
            optional(provider().properties()[COLLECTION].name());
            optional(provider().properties()[COLLECTION].value());
        }
        @In @Out OpenStackVolumeProvider provider();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the operation should succeed, and the provider removed from the database,
         * even if something fails during the operation.
         *
         * This parameter is optional, and the default value is `false`.
         *
         * @author Daniel Erez <derez@redhat.com>
         * @date 17 Aug 2017
         * @status added
         * @since 4.2.0
         */
        @In Boolean force();
    }

    @Service OpenstackVolumeTypesService volumeTypes();
    @Service OpenstackVolumeAuthenticationKeysService authenticationKeys();
}
