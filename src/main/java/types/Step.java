/*
Copyright (c) 2015-2016 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package types;

import java.util.Date;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents a step, which is part of `job` execution.
 * Step is used to describe and track a specific execution unit which is part of a wider sequence.
 * Some steps support reporting their progress.
 *
 * @author Moti Asayag <masayag@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface Step extends Identified {

    /**
     * The type of the step.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    StepEnum type();

    /**
     * The order of the step in current hierarchy level.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Integer number();

    /**
     * The status of the step.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    StepStatus status();

    /**
     * The start time of the step.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Date startTime();

    /**
     * The end time of the step.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Date endTime();

    /**
     * Indicates if the step is originated by an external system.
     * External steps are managed externally, by the creator of the step.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean external();

    /**
     * The external system which is referenced by the step.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    ExternalSystemType externalType();

    /**
     * References the parent step of the current step in the hierarchy.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Step parentStep();

    /**
     * References the `job` which is the top of the current step hierarchy.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Job job();
    @Link Statistic[] statistics();

    /**
     * The step progress (if reported) in percentages.
     *
     * @author Liron Aravot <laravot@redhat.com>
     * @date 21 Nov 2016
     * @status added
     * @since 4.1
     */
    Integer progress();

    /**
     * The host used for the step execution (optional).
     *
     * @author Liron Aravot <laravot@redhat.com>
     * @date 11 Jan 2017
     * @status added
     * @since 4.1
     */
    @Link Host executionHost();
}
