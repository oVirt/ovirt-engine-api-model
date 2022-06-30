/*
Copyright (c) 2017 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRAN/TIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Maps an external virtual NIC profile to one that exists in the {engine-name}.
 * The target may be specified as a profile ID or a pair of profile name and network name.
 *
 * If, for example, the desired virtual NIC profile mapping includes the following lines:
 *
 * [cols="25,35,40"]
 * |===
 * |Source network name|Source network profile name|Target virtual NIC profile ID\names
 *
 * |`red`
 * |`gold`
 * |`738dd914-8ec8-4a8b-8628-34672a5d449b`
 *
 * |`<empty>` (no network name)
 * |`<empty>` (no network profile name)
 * |`892a12ec-2028-4451-80aa-ff3bf55d6bac`
 *
 * |`blue`
 * |`silver`
 * |`orange\copper`
 *
 * |`yellow`
 * |`platinum`
 * |`<empty>` (no profile)
 *
 * |`green`
 * |`bronze`
 * |
 *
 * |===
 *
 * Then the following snippet should be added to xref:types-registration_configuration[RegistrationConfiguration]
 *
 * [source,xml]
 * ----
 * <vnic_profile_mappings>
 *   <registration_vnic_profile_mapping>
 *     <from>
 *       <name>gold</name>
 *       <network>
 *         <name>red</name>
 *       </network>
 *     </from>
 *     <to id="738dd914-8ec8-4a8b-8628-34672a5d449b"/>
 *   </registration_vnic_profile_mapping>
 *   <registration_vnic_profile_mapping>
 *     <from>
 *       <name></name>
 *       <network>
 *         <name></name>
 *       </network>
 *     </from>
 *     <to id="892a12ec-2028-4451-80aa-ff3bf55d6bac"/>
 *   </registration_vnic_profile_mapping>
 *   <registration_vnic_profile_mapping>
 *     <from>
 *       <name>silver</name>
 *       <network>
 *         <name>blue</name>
 *       </network>
 *     </from>
 *     <to>
 *       <name>copper</name>
 *       <network>
 *         <name>orange</name>
 *       </network>
 *     </to>
 *   </registration_vnic_profile_mapping>
 *   <registration_vnic_profile_mapping>
 *     <from>
 *       <name>platinum</name>
 *       <network>
 *         <name>yellow</name>
 *       </network>
 *     </from>
 *     <to>
 *       <name></name>
 *       <network>
 *         <name></name>
 *       </network>
 *     </to>
 *   </registration_vnic_profile_mapping>
 *   <registration_vnic_profile_mapping>
 *     <from>
 *       <name>bronze</name>
 *       <network>
 *         <name>green</name>
 *       </network>
 *     </from>
 *   </registration_vnic_profile_mapping>
 * </vnic_profile_mappings>
 * ----
 *
 * @author Eitan Raviv <eraviv@redhat.com>
 * @date 20 Feb 2017
 * @status added
 * @since 4.1
 */
@Type
public interface RegistrationVnicProfileMapping {
    /**
     * References to the external network and the external
     * network profile. Both should be specified using their
     * `name`.
     *
     * @author Eitan Raviv <eraviv@redhat.com>
     * @date 05 Dec 2017
     * @status added
     * @since 4.2.1
     */
    @Link VnicProfile from();

    /**
     * Reference to to an existing virtual NIC profile.
     * It should be specified using its `name` or `id`.
     * Either `name` or `id` should be specified but not
     * both.
     *
     * @author Eitan Raviv <eraviv@redhat.com>
     * @date 05 Dec 2017
     * @status added
     * @since 4.2.1
     */
    @Link VnicProfile to();
}
