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
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * This service manages a vNIC profile.
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Network")
public interface VnicProfileService {

    /**
     * Retrieves details about a vNIC profile.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        @Out VnicProfile profile();
    }

    /**
     * Updates details of a vNIC profile.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Ales Musil <amusil@redhat.com>
     * @date 30 March 2021
     * @status updated
     */
    interface Update {

        @InputDetail
        default void inputDetail() {
            optional(profile().description());
            optional(profile().name());
            optional(profile().passThrough().mode());
            optional(profile().portMirroring());
            optional(profile().customProperties()[COLLECTION].name());
            optional(profile().customProperties()[COLLECTION].value());
            optional(profile().networkFilter());
            optional(profile().qos());
            optional(profile().failover());
        }
        /**
         * The vNIC profile that is being updated.
         *
         * @author Dominik Holler <dholler@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out VnicProfile profile();

        /**
         * Indicates if the update should be performed asynchronously.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 16 Dec 2016
         * @status added
         */
        @In Boolean async();
    }

    /**
     * Removes the vNIC profile.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 16 Dec 2016
         * @status added
         */
        @In Boolean async();
    }

    @Service AssignedPermissionsService permissions();
}
