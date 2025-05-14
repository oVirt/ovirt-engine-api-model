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
 * A service to manage a step.
 *
 * @author Moti Asayag <masayag@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface StepService extends MeasurableService {

    /**
     * Marks an external step execution as ended.
     *
     * For example, to terminate a step with identifier `456` which belongs to a `job` with identifier `123` send the
     * following request:
     *
     * ```http
     * POST /ovirt-engine/api/jobs/123/steps/456/end
     * ```
     *
     * With the following request body:
     *
     * ```xml
     * <action>
     *   <force>true</force>
     *   <succeeded>true</succeeded>
     * </action>
     * ```xml
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface End {
        @InputDetail
        default void inputDetail() {
            mandatory(succeeded());
            optional(force());
        }

        /**
         * Indicates if the step should be forcibly terminated.
         *
         * @author Moti Asayag <masayag@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In Boolean force();

        /**
         * Indicates if the step should be marked as successfully finished or as failed.
         *
         * This parameter is optional, and the default value is `true`.
         *
         * @author Moti Asayag <masayag@redhat.com>
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 16 Dec 2016
         * @status added
         */
        @In Boolean succeeded();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Retrieves a step.
     *
     * ```http
     * GET /ovirt-engine/api/jobs/123/steps/456
     * ```
     *
     * You will receive response in XML like this one:
     *
     * ```xml
     * <step href="/ovirt-engine/api/jobs/123/steps/456" id="456">
     *   <actions>
     *     <link href="/ovirt-engine/api/jobs/123/steps/456/end" rel="end"/>
     *   </actions>
     *   <description>Validating</description>
     *   <end_time>2016-12-12T23:07:26.627+02:00</end_time>
     *   <external>false</external>
     *   <number>0</number>
     *   <start_time>2016-12-12T23:07:26.605+02:00</start_time>
     *   <status>finished</status>
     *   <type>validating</type>
     *   <job href="/ovirt-engine/api/jobs/123" id="123"/>
     * </step>
     * ```
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {

        /**
         * Retrieves the representation of the step.
         *
         * @author Moti Asayag <masayag@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Step step();
    }
}
