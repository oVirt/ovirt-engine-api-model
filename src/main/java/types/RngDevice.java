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
 * Random number generator (RNG) device model.
 *
 * @author Jakub Niedermertl <jniederm@redhat.com>
 * @date 13 Dec 2016
 * @state added
 */
@Type
public interface RngDevice {

    /**
     * Determines maximum speed of consumption of bytes from random number generator device.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 13 Dec 2016
     * @state added
     */
    Rate rate();

    /**
     * Backend of the random number generator device.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 13 Dec 2016
     * @state added
     */
    RngSource source();
}
