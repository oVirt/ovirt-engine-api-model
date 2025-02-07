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
import types.SchedulingPolicyUnit;

/**
 * Manages the set of scheduling policy units available in the system.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 15 Apr 2017
 * @status added
 */
@Service
@Area("SLA")
public interface SchedulingPolicyUnitsService {
    /**
     * Returns the list of scheduling policy units available in the system.
     *
     * The order of the returned list of scheduling policy units isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out SchedulingPolicyUnit[] units();

        /**
         * Sets the maximum number of policy units to return. If not specified all the policy units are returned.
         */
        @In Integer max();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    @Service SchedulingPolicyUnitService unit(String id);
}
