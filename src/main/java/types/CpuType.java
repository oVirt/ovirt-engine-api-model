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
 * Describes a supported CPU type.
 *
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 28 Oct 2016
 * @status updated_by_docs
 */
@Type
public interface CpuType {
    /**
     * The name of the CPU type, for example `Intel Nehalem Family`.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @author Steve Goodman <sgoodman@redhat.com>
     * @date 04 Oct 2018
     * @status updated_by_docs
     */
    String name();

    /**
     * The level of the CPU type.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 28 Oct 2016
     * @status updated_by_docs
     */
    Integer level();

    /**
     * The architecture of the CPU.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 28 Oct 2016
     * @status updated_by_docs
     */
    Architecture architecture();
}
