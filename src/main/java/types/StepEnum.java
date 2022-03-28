/*
Copyright (c) 2015 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing a step type.
 *
 * @author Moti Asayag <masayag@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public enum StepEnum {

    /**
     * The validation step type.
     * Used to verify the correctness of parameters and the validity of the parameters prior to the execution.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    VALIDATING,

    /**
     * The executing step type.
     * Used to track the main execution block of the job.
     * Usually it will be a parent step of several sub-steps which describe portions of the execution step.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    EXECUTING,

    /**
     * The finalizing step type.
     * Describes the post-execution steps requires to complete the `job`.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    FINALIZING,

    /**
     * The `rebalancing volume` step type.
     * Describes a step type which is part of `Gluster` flow.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    REBALANCING_VOLUME,

    /**
     * The `removing bricks` step type.
     * Describes a step type which is part of `Gluster` flow.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    REMOVING_BRICKS,

    /**
     * The unknown step type.
     * Describes a step type which its origin is unknown.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    UNKNOWN;
}
