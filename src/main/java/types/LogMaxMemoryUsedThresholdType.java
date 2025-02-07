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
package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Describes all maximum memory threshold types supported by the system.
 *
 * @author Nori Ravi <rnori@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 10 Aug 2018
 * @since 4.3
 * @status updated_by_docs
 */
@Type
public enum LogMaxMemoryUsedThresholdType {
    /**
     * Percentage threshold type.
     *
     * When a percentage is specified, an audit log event is logged if the memory used is above the value specified
     * in `LogMaxMemoryUsedThreshold`.
     *
     * @author Nori Ravi <rnori@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 10 Aug 2018
     * @since 4.3
     * @status updated_by_docs
     */
    PERCENTAGE,

    /**
     * Absolute value threshold type.
     *
     * When an absolute value is specified, an audit log event is logged if the free memory in MB falls below the value
     * specified in `LogMaxMemoryUsedThreshold`.
     *
     *
     * @author Nori Ravi <rnori@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 10 Aug 2018
     * @since 4.3
     * @status updated_by_docs
     */
    ABSOLUTE_VALUE_IN_MB,
}
