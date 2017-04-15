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
import types.Job;

/**
 * A service to manage jobs.
 *
 * @author Moti Asayag <masayag@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface JobsService {

    /**
     * Add an external job.
     *
     * For example, to add a job with the following request:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/jobs
     * ----
     *
     * With the following request body:
     *
     * [source,xml]
     * ----
     * <job>
     *   <description>Doing some work</description>
     *   <auto_cleared>true</auto_cleared>
     * </job>
     * ----
     *
     * The response should look like:
     *
     * [source,xml]
     * ----
     * <job href="/ovirt-engine/api/jobs/123" id="123">
     *   <actions>
     *     <link href="/ovirt-engine/api/jobs/123/clear" rel="clear"/>
     *     <link href="/ovirt-engine/api/jobs/123/end" rel="end"/>
     *   </actions>
     *   <description>Doing some work</description>
     *   <link href="/ovirt-engine/api/jobs/123/steps" rel="steps"/>
     *   <auto_cleared>true</auto_cleared>
     *   <external>true</external>
     *   <last_updated>2016-12-13T02:15:42.130+02:00</last_updated>
     *   <start_time>2016-12-13T02:15:42.130+02:00</start_time>
     *   <status>started</status>
     *   <owner href="/ovirt-engine/api/users/456" id="456"/>
     * </job>
     * ----
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {

        /**
         * Job that will be added.
         *
         * @author Moti Asayag <masayag@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Job job();
    }

    /**
     * Retrieves the representation of the jobs.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/jobs
     * ----
     *
     * You will receive response in XML like this one:
     *
     * [source,xml]
     * ----
     * <jobs>
     *   <job href="/ovirt-engine/api/jobs/123" id="123">
     *     <actions>
     *       <link href="/ovirt-engine/api/jobs/123/clear" rel="clear"/>
     *       <link href="/ovirt-engine/api/jobs/123/end" rel="end"/>
     *     </actions>
     *     <description>Adding Disk</description>
     *     <link href="/ovirt-engine/api/jobs/123/steps" rel="steps"/>
     *     <auto_cleared>true</auto_cleared>
     *     <end_time>2016-12-12T23:07:29.758+02:00</end_time>
     *     <external>false</external>
     *     <last_updated>2016-12-12T23:07:29.758+02:00</last_updated>
     *     <start_time>2016-12-12T23:07:26.593+02:00</start_time>
     *     <status>failed</status>
     *     <owner href="/ovirt-engine/api/users/456" id="456"/>
     *   </job>
     *   ...
     * </jobs>
     * ----
     *
     * The order of the returned list of jobs isn't guaranteed.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {

        /**
         * A representation of jobs.
         *
         * @author Moti Asayag <masayag@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Job[] jobs();

        /**
         * Sets the maximum number of jobs to return. If not specified all the jobs are returned.
         */
        @In Integer max();
    }

    /**
     * Reference to the job service.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service JobService job(String id);
}
