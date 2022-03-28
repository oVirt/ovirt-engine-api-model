/*
Copyright (c) 2017 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * This type describes how to map LUNs as part of the object registration. An object can be
 * a virtual machine, template, etc.
 *
 * An external LUN disk is an entity which does not reside on a storage domain.
 * It must be specified because it doesn't need to exist in the
 * environment where the object is registered.
 * An example of an XML representation using this mapping:
 *
 * [source,xml]
 * ----
 * <action>
 *   <registration_configuration>
 *     <lun_mappings>
 *       <registration_lun_mapping>
 *     <lun_mappings>
 *      <registration_lun_mapping>
 *        <from id="111">
 *        </from>
 *        <to id="222">
 *          <alias>weTestLun</alias>
 *          <lun_storage>
 *            <type>iscsi</type>
 *            <logical_units>
 *               <logical_unit id="36001405fb1ddb4b91e44078f1fffcfef">
 *                  <address>44.33.11.22</address>
 *                  <port>3260</port>
 *                  <portal>1</portal>
 *                  <target>iqn.2017-11.com.name.redhat:444</target>
 *               </logical_unit>
 *            </logical_units>
 *          </lun_storage>
 *        </to>
 *      </registration_lun_mapping>
 *     </lun_mappings>
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
interface RegistrationLunMapping {
    /**
     * Reference to the original LUN. This must be specified using the `id` attribute.
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 20 Oct 2017
     * @status updated_by_docs
     * @since 4.2
     */
    @Link Disk from();

    /**
     * Reference to the LUN which is to be added to the virtual machine.
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 20 Oct 2017
     * @status updated_by_docs
     * @since 4.2
     */
    @Link Disk to();
}
