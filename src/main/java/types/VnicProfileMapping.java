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
 * Deprecated type that maps an external virtual NIC profile to one that exists in the {engine-name}.
 *
 * If, for example, the desired virtual NIC profile's mapping includes the following two lines:
 *
 * [cols="25,35,40"]
 * |===
 * |Source network name|Source network profile name|Target virtual NIC profile ID
 *
 * |`red`
 * |`gold`
 * |`738dd914-8ec8-4a8b-8628-34672a5d449b`
 *
 * |`blue`
 * |`silver`
 * |`892a12ec-2028-4451-80aa-ff3bf55d6bac`
 *
 * |===
 *
 * The following form is deprecated since 4.2.1 and will be removed in the future:
 *
 * ```xml
 * <vnic_profile_mappings>
 *   <vnic_profile_mapping>
 *     <source_network_name>red</source_network_name>
 *     <source_network_profile_name>gold</source_network_profile_name>
 *     <target_vnic_profile id="738dd914-8ec8-4a8b-8628-34672a5d449b"/>
 *   </vnic_profile_mapping>
 *   <vnic_profile_mapping>
 *     <source_network_name>blue</source_network_name>
 *     <source_network_profile_name>silver</source_network_profile_name>
 *     <target_vnic_profile id="892a12ec-2028-4451-80aa-ff3bf55d6bac"/>
 *   </vnic_profile_mapping>
 * </vnic_profile_mappings>
 * ```
 *
 *
 * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
 * @author Eitan Raviv <eraviv@redhat.com>
 * @author Megan Lewis <melewis@redhat.com>
 * @date 20 Feb 2017
 * @status added
 * @since 4.1
 */
@Type
@Deprecated
public interface VnicProfileMapping {

    /**
     * Deprecated attribute describing the name of the external network.
     *
     * WARNING: Please note that this attribute has been deprecated since version 4.2.1 of the engine,
     * and preserved only for backward compatibility. It will be removed in the future.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status added
     * @since 4.1
     */
    @Deprecated
    String sourceNetworkName();

    /**
     * Deprecated attribute describing the name of the external network profile.
     *
     * WARNING: Please note that this attribute has been deprecated since version 4.2.1 of the engine,
     * and preserved only for backward compatibility. It will be removed in the future.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status added
     * @since 4.1
     */
    @Deprecated
    String sourceNetworkProfileName();

    /**
     * Deprecated attribute describing an existing virtual NIC profile.
     *
     * WARNING: Please note that this attribute has been deprecated since version 4.2.1 of the engine,
     * and preserved only for backward compatibility. It will be removed in the future.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status added
     * @since 4.1
     */
    @Deprecated
    @Link VnicProfile targetVnicProfile();
}
