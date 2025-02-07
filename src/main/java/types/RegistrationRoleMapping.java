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
 * This type describes how to map roles as part of the object registration. An object can be
 * a virtual machine, template, etc.
 *
 * A role mapping is intended to map correlating roles between the primary site
 * and the secondary site.
 * For example, there may be permissions with role `UserVmRunTimeManager` for the virtual machine that
 * is being registered.
 * Therefore we can send a mapping that will register the virtual machine in the secondary setup
 * using the `SuperUser` role instead of `UserVmRunTimeManager`
 * An example of an XML representation using this mapping:
 *
 * [source,xml]
 * ----
 * <action>
 *   <registration_configuration>
 *     <role_mappings>
 *       <registration_eole_mapping>
 *         <from>
 *           <name>SuperUser</name>
 *         </from>
 *         <to>
 *           <name>UserVmRunTimeManager</name>
 *         </to>
 *       </registration_role_mapping>
 *     </role_mappings>
 *   </registration_configuration>
 * </action>
 * ----
 *
 * @author Maor Lipchuk <mlipchuk@redhat.com>
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 20 Oct 2017
 * @status updated_by_docs
 * @since 4.2
 */
@Type
interface RegistrationRoleMapping {
    /**
     * Reference to the original role. It can be specified
     * using `name`.
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 20 Oct 2017
     * @status updated_by_docs
     * @since 4.2
     */
    @Link Role from();

    /**
     * Reference to the destination role.
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 20 Oct 2017
     * @status updated_by_docs
     * @since 4.2
     */
    @Link Role to();
}
