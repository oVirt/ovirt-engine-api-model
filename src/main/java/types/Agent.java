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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing a fence agent.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 29 Nov 2016
 * @status added
 */
@Type
public interface Agent extends Identified {
    /**
     * Fence agent type.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    String type();

    /**
     * Fence agent address.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    String address();

    /**
     * Fence agent user name.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    String username();

    /**
     * Fence agent password.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    String password();

    /**
     * Fence agent options (comma-delimited list of
     * key-value pairs).
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    Option[] options();

    /**
     * Specifies whether the options should be encrypted.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    Boolean encryptOptions();

    /**
     * Specifies whether the agent should be used concurrently
     * or sequentially.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    Boolean concurrent();

    /**
     * The order of this agent if used with other agents.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    Integer order();

    /**
     * Fence agent port.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    Integer port();

    /**
     * Reference to the host service.
     * Each fence agent belongs to a single host.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    @Link
    Host host();
}
