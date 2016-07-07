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
import types.VnicProfile;

/**
 *
 * Since 4.0 it is possible to have a customized network filter to each VNIC profile.
 * Please note that there is a default network filter to each VNIC profile.
 * For more details of how the default network filter is calculated please refer to
 * the documentation in <<services/network_filters,NetworkFilters>>.
 *
 * The basic POST command of adding a new VNIC profile is as follows:
 *
 * [source]
 * ----
 * http://{engine_ip_address}:8080/ovirt-engine/api/networks/{network_id}/vnicprofiles
 * ----
 *
 * The output of creating a new VNIC profile depends in the  body  arguments that were given.
 * In case no network filter was given, the default network filter will be configured. For example:
 *
 * [source,xml]
 * ----
 * <vnic_profile>
 *   <name>use_default_network_filter</name>
 *   <network id="00000000-0000-0000-0000-000000000009"/>
 * </vnic_profile>
 * ----
 *
 * In case an empty network filter was given, no network filter will be configured for the specific VNIC profile
 * regardless of the VNIC profile's default network filter. For example:
 *
 * [source,xml]
 * ----
 * <vnic_profile>
 *   <name>no_network_filter</name>
 *   <network id="00000000-0000-0000-0000-000000000009"/>
 *   <network_filter/>
 * </vnic_profile>
 * ----
 *
 * In case that a specific valid network filter id was given, the VNIC profile will be configured with the given
 * network filter regardless of the VNIC profiles's default network filter. For example:
 *
 * [source,xml]
 * ----
 * <vnic_profile>
 *   <name>user_choice_network_filter</name>
 *   <network id="00000000-0000-0000-0000-000000000009"/>
 *   <network_filter id= "0000001b-001b-001b-001b-0000000001d5"/>
 * </vnic_profile>
 * ----
 */
@Service
@Area("Network")
public interface VnicProfileService {
    interface Get {
        @Out VnicProfile profile();
    }

    interface Update {
        @In @Out VnicProfile profile();

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
    }

    @Service AssignedPermissionsService permissions();
}
