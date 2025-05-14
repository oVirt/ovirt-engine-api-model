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
import types.Job;

/**
 * A service to manage a job.
 *
 * @author Moti Asayag <masayag@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface JobService {

    /**
     * Set an external job execution to be cleared by the system.
     *
     * For example, to set a job with identifier `123` send the following request:
     *
     * ```http
     * POST /ovirt-engine/api/jobs/clear
     * ```
     *
     * With the following request body:
     *
     * ```xml
     * <action/>
     * ```
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Clear {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Marks an external job execution as ended.
     *
     * For example, to terminate a job with identifier `123` send the following request:
     *
     * ```http
     * POST /ovirt-engine/api/jobs/end
     * ```
     *
     * With the following request body:
     *
     * ```xml
     * <action>
     *   <force>true</force>
     *   <status>finished</status>
     * </action>
     * ```
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface End {
        /**
         * Indicates if the job should be forcibly terminated.
         *
         * @author Moti Asayag <masayag@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In Boolean force();

        /**
         * Indicates if the job should be marked as successfully finished or as failed.
         *
         * This parameter is optional, and the default value is `true`.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 16 Dec 2016
         * @status added
         * @since 4.1
         */
        @In Boolean succeeded();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Retrieves a job.
     *
     * ```http
     * GET /ovirt-engine/api/jobs/123
     * ```
     *
     * You will receive response in XML like this one:
     *
     * ```xml
     * <job href="/ovirt-engine/api/jobs/123" id="123">
     *   <actions>
     *     <link href="/ovirt-engine/api/jobs/123/clear" rel="clear"/>
     *     <link href="/ovirt-engine/api/jobs/123/end" rel="end"/>
     *   </actions>
     *   <description>Adding Disk</description>
     *   <link href="/ovirt-engine/api/jobs/123/steps" rel="steps"/>
     *   <auto_cleared>true</auto_cleared>
     *   <end_time>2016-12-12T23:07:29.758+02:00</end_time>
     *   <external>false</external>
     *   <last_updated>2016-12-12T23:07:29.758+02:00</last_updated>
     *   <start_time>2016-12-12T23:07:26.593+02:00</start_time>
     *   <status>failed</status>
     *   <owner href="/ovirt-engine/api/users/456" id="456"/>
     * </job>
     * ```
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {

        /**
         * Retrieves the representation of the job.
         *
         * @author Moti Asayag <masayag@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Job job();
    }

    /**
     * List all the steps of the job.
     *
     * The order of the returned list of steps isn't guaranteed.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service StepsService steps();
}
