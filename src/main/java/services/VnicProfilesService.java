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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.VnicProfile;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * This service manages the collection of all vNIC profiles.
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Network")
public interface VnicProfilesService {

    /**
     * Add a vNIC profile.
     *
     * For example to add vNIC profile `123` to network `456` send a request to:
     *
     * ```http
     * POST /ovirt-engine/api/networks/456/vnicprofiles HTTP/1.1
     * ```
     *
     * With the following body:
     *
     * ```xml
     * <vnic_profile id="123">
     *   <name>new_vNIC_name</name>
     *   <pass_through>
     *     <mode>disabled</mode>
     *   </pass_through>
     *   <port_mirroring>false</port_mirroring>
     * </vnic_profile>
     * ```
     *
     * Please note that there is a default network filter to each VNIC profile.
     * For more details of how the default network filter is calculated please refer to
     * the documentation in xref:services/network_filters[NetworkFilters].
     *
     * NOTE: The automatically created vNIC profile for the external network will be without network filter.
     *
     * The output of creating a new VNIC profile depends in the  body  arguments that were given.
     * In case no network filter was given, the default network filter will be configured. For example:
     *
     * ```xml
     * <vnic_profile href="/ovirt-engine/api/vnicprofiles/123" id="123">
     *   <name>new_vNIC_name</name>
     *   <link href="/ovirt-engine/api/vnicprofiles/123/permissions" rel="permissions"/>
     *   <pass_through>
     *     <mode>disabled</mode>
     *   </pass_through>
     *   <port_mirroring>false</port_mirroring>
     *   <network href="/ovirt-engine/api/networks/456" id="456"/>
     *   <network_filter href="/ovirt-engine/api/networkfilters/789" id="789"/>
     * </vnic_profile>
     * ```
     *
     * In case an empty network filter was given, no network filter will be configured for the specific VNIC profile
     * regardless of the VNIC profile's default network filter. For example:
     *
     * ```xml
     * <vnic_profile>
     *   <name>no_network_filter</name>
     *   <network_filter/>
     * </vnic_profile>
     * ```
     *
     * In case that a specific valid network filter id was given, the VNIC profile will be configured with the given
     * network filter regardless of the VNIC profiles's default network filter. For example:
     *
     * ```xml
     * <vnic_profile>
     *   <name>user_choice_network_filter</name>
     *   <network_filter id= "0000001b-001b-001b-001b-0000000001d5"/>
     * </vnic_profile>
     * ```
     *
     * @author Alona Kaplan <alkaplan@redhat.com>
     * @author Dominik Holler <dholler@redhat.com>
     * @author Ales Musil <amusil@redhat.com>
     * @author Billy Burmester <bburmest@redhat.com>
     * @date 30 March 2021
     * @status updated
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(profile().name());
            mandatory(profile().network().id());
            optional(profile().description());
            optional(profile().passThrough().mode());
            optional(profile().portMirroring());
            optional(profile().customProperties()[COLLECTION].name());
            optional(profile().customProperties()[COLLECTION].value());
            optional(profile().failover());
        }
        /**
         * The vNIC profile that is being added.
         *
         * @author Dominik Holler <dholler@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out VnicProfile profile();
    }

    /**
     * List all vNIC profiles.
     *
     * The order of the returned list of vNIC profiles isn't guaranteed.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Billy Burmester <bburmest@redhat.com>
     * @date 15 May 2018
     * @status updated_by_docs
     */
    interface List extends Follow {

        /**
         * The list of all vNIC profiles.
         *
         * @author Dominik Holler <dholler@redhat.com>
         * @author Billy Burmester <bburmest@redhat.com>
         * @date 15 May 2018
         * @status updated_by_docs
         */
        @Out VnicProfile[] profiles();

        /**
         * Sets the maximum number of profiles to return. If not specified all the profiles are returned.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @author Billy Burmester <bburmest@redhat.com>
         * @date 15 May 2018
         * @status updated_by_docs
         */
        @In Integer max();
    }

    @Service VnicProfileService profile(String id);
}
