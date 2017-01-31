/*
Copyright (c) 2016 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Defines how the migration bandwidth is assigned.
 *
 * @author Megan Lewis <melewis@redhat.com>
 * @date 31 Jan 2017
 * @status updated_by_docs
 */
@Type
public enum MigrationBandwidthAssignmentMethod {

    /**
     * Takes the bandwidth from the Quality of Service if the Quality of Service is defined.
     * If the Quality of Service is not defined the bandwidth is taken from the detected link speed being used.
     * If nothing is detected, bandwidth falls back to the hypervisor_default value.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 21 Feb 2017
     * @status updated_by_docs
     */
    AUTO,

    /**
     * Custom defined bandwidth in Mbit/s.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    CUSTOM,

    /**
     * Takes the value as configured on the hypervisor.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    HYPERVISOR_DEFAULT
}
