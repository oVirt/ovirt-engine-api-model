/*
Copyright (c) 2015-2016 Red Hat, Inc.

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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

import java.util.Date;

/**
 * Represents a job, which monitors execution of a flow in the system.
 * A job can contain multiple steps in a hierarchic structure.
 * The steps can be processed in parallel, depends on the implementation of the flow.
 *
 * @author Moti Asayag <masayag@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface Job extends Identified {

    /**
     * The status of the job.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    JobStatus status();

    /**
     * The start time of the job.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Date startTime();

    /**
     * The end time of the job.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Date endTime();

    /**
     * The last update date of the job.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Date lastUpdated();

    /**
     * Indicates if the job is originated by an external system.
     * External jobs are managed externally, by the creator of the job.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean external();

    /**
     * Indicates if the job should be cleared automatically after it was completed by the system.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean autoCleared();

    /**
     * The user who is the owner of the job.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link User owner();

    /**
     * The steps of the job.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Step[] steps();
}
