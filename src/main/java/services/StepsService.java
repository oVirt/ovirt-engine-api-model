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
import types.Step;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * A service to manage steps.
 *
 * @author Moti Asayag <masayag@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface StepsService {

    /**
     * Add an external step to an existing job or to an existing step.
     *
     * For example, to add a step to `job` with identifier `123` send the
     * following request:
     *
     * ```http
     * POST /ovirt-engine/api/jobs/123/steps
     * ```
     *
     * With the following request body:
     *
     * ```xml
     * <step>
     *   <description>Validating</description>
     *   <start_time>2016-12-12T23:07:26.605+02:00</start_time>
     *   <status>started</status>
     *   <type>validating</type>
     * </step>
     * ```
     *
     * The response should look like:
     *
     * ```xml
     * <step href="/ovirt-engine/api/jobs/123/steps/456" id="456">
     *   <actions>
     *     <link href="/ovirt-engine/api/jobs/123/steps/456/end" rel="end"/>
     *   </actions>
     *   <description>Validating</description>
     *   <link href="/ovirt-engine/api/jobs/123/steps/456/statistics" rel="statistics"/>
     *   <external>true</external>
     *   <number>2</number>
     *   <start_time>2016-12-13T01:06:15.380+02:00</start_time>
     *   <status>started</status>
     *   <type>validating</type>
     *   <job href="/ovirt-engine/api/jobs/123" id="123"/>
     * </step>
     * ```
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(step().description());
            mandatory(step().type());
            optional(step().job().id());
            optional(step().parentStep().id());
        }

        /**
         * Step that will be added.
         *
         * @author Moti Asayag <masayag@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Step step();
    }

    /**
     * Retrieves the representation of the steps.
     *
     * ```http
     * GET /ovirt-engine/api/job/123/steps
     * ```
     *
     * You will receive response in XML like this one:
     *
     * ```xml
     * <steps>
     *   <step href="/ovirt-engine/api/jobs/123/steps/456" id="456">
     *     <actions>
     *       <link href="/ovirt-engine/api/jobs/123/steps/456/end" rel="end"/>
     *     </actions>
     *     <description>Validating</description>
     *     <link href="/ovirt-engine/api/jobs/123/steps/456/statistics" rel="statistics"/>
     *     <external>true</external>
     *     <number>2</number>
     *     <start_time>2016-12-13T01:06:15.380+02:00</start_time>
     *     <status>started</status>
     *     <type>validating</type>
     *     <job href="/ovirt-engine/api/jobs/123" id="123"/>
     *   </step>
     *   ...
     * </steps>
     * ```
     *
     * The order of the returned list of steps isn't guaranteed.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {

        /**
         * A representation of steps.
         *
         * @author Moti Asayag <masayag@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Step[] steps();

        /**
         * Sets the maximum number of steps to return. If not specified all the steps are returned.
         */
        @In Integer max();
    }

    /**
     * Reference to the step service.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service StepService step(String id);
}
