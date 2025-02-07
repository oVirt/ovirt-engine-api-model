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
import types.Filter;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

/**
 * Manages the filters used by an scheduling policy.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 15 Apr 2017
 * @status added
 */
@Service
@Area("SLA")
public interface FiltersService {
    /**
     * Add a filter to a specified user defined scheduling policy.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(filter().schedulingPolicyUnit().id());
//            optional(filter().schedulingPolicyUnit().position()); //TODO: check
        }
        @In @Out Filter filter();
    }

    /**
     * Returns the list of filters used by the scheduling policy.
     *
     * The order of the returned list of filters isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out Filter[] filters();

        /**
         * Sets the maximum number of filters to return. If not specified all the filters are returned.
         */
        @In Integer max();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    @Service FilterService filter(String id);
}
