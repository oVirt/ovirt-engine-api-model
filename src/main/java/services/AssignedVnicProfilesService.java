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

@Service
@Area("Network")
public interface AssignedVnicProfilesService {
    /**
     * Add a new virtual network interface card profile for the network.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Add {
        @In @Out VnicProfile profile();
        @InputDetail
        default void inputDetail() {
            mandatory(profile().name());
            optional(profile().description());
            optional(profile().passThrough().mode());
            optional(profile().portMirroring());
            optional(profile().customProperties()[COLLECTION].name());
            optional(profile().customProperties()[COLLECTION].value());
        }
    }

    /**
     * Returns the list of VNIC profiles assifned to the network.
     *
     * The order of the returned VNIC profiles isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out VnicProfile[] profiles();

        /**
         * Sets the maximum number of profiles to return. If not specified all the profiles are returned.
         */
        @In Integer max();
    }

    @Service AssignedVnicProfileService profile(String id);
}
