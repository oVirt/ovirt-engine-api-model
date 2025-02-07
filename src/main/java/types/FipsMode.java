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
 * Representation of the FIPS mode to the cluster.
 *
 * @author Liran Rotenberg <lrotenbe@redhat.com>
 * @date 16 Dec 2020
 * @status added
 * @since 4.4
 */
@Type
public enum FipsMode {
    /**
     * The FIPS mode is not yet evaluated.
     *
     * Currently, its implication is that the FIPS mode is undetermined. Once a host is added, this value
     * will switch according to the host settings.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 16 Dec 2020
     * @status added
     * @since 4.4.5
     */
    UNDEFINED,

    /**
     * The FIPS mode is disabled.
     *
     * Its implication is that the FIPS mode is disabled and the hosts within should be with FIPS mode
     * disabled, otherwise they would be non-operational.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 16 Dec 2020
     * @status added
     * @since 4.4.5
     */
    DISABLED,

    /**
     * The FIPS mode is enabled.
     *
     * Its implication is that the FIPS mode is enabled and the hosts within should be with FIPS mode
     * enabled, otherwise they should be non-operational.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 16 Dec 2020
     * @status added
     * @since 4.4.5
     */
    ENABLED;
}
