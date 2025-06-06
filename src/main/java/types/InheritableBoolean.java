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

@Type
/**
 * Enum representing the boolean value that can be either set, or inherited from a higher level.
 * The inheritance order is virtual machine -> cluster -> engine-config.
 *
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 17 Nov 2016
 * @status updated_by_docs
 */
public enum InheritableBoolean {
    /** Set the value to true on this level.
    *
    * @author Byron Gravenorst <bgraveno@redhat.com>
    * @date 17 Nov 2016
    * @status updated_by_docs
    */
    TRUE,
    /** Set the value to false on this level.
    *
    * @author Byron Gravenorst <bgraveno@redhat.com>
    * @date 17 Nov 2016
    * @status updated_by_docs
    */
    FALSE,
    /** Inherit the value from higher level.
    *
    * @author Byron Gravenorst <bgraveno@redhat.com>
    * @date 17 Nov 2016
    * @status updated_by_docs
    */
    INHERIT;
}
