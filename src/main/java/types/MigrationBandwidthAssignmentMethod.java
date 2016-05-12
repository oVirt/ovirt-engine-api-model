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
 * Defines the method how the migration bandwidth is assigned.
 */
@Type
public enum MigrationBandwidthAssignmentMethod {

    /**
     * Takes the bandwidth from QoS if QoS defined.
     * If not, taken from detected link speed being used.
     * If nothing detected, falls back to hypervisor_default value.
     */
    AUTO,

    /**
     * Custom defined bandwidth in Mbit/s.
     */
    CUSTOM,

    /**
     * Takes the value as configured on the hypervisor.
     */
    HYPERVISOR_DEFAULT
}
