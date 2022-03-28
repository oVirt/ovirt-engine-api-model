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
 * This type describes how to map affinity labels as part of the object registration. An object can be
 * a virtual machine, template, etc.
 *
 * An example of an XML representation using mapping:
 *
 * [source,xml]
 * ----
 * <action>
 *   <registration_configuration>
 *     <affinity_label_mappings>
 *      <registration_affinity_label_mapping>
 *        <from>
 *          <name>affinity_label</name>
 *        </from>
 *        <to>
 *          <name>affinity_label2</name>
 *        </to>
 *      </registration_affinity_label_mapping>
 *     </affinity_label_mappings>
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
interface RegistrationAffinityLabelMapping {
    /**
     * Reference to the original affinity label. It can be specified
     * using `name`.
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 20 Oct 2017
     * @status updated_by_docs
     * @since 4.2
     */
    @Link AffinityLabel from();

    /**
     * Reference to the destination affinity label.
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 20 Oct 2017
     * @status updated_by_docs
     * @since 4.2
     */
    @Link AffinityLabel to();
}
