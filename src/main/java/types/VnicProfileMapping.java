/*
Copyright (c) 2015 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

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
 * Maps an external virtual NIC profile to one that exists in the engine.
 *
 * Given the desired virtual NIC profiles mapping include the following 2 lines:
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
 * It should be expressed in the following form:
 *
 * [source,xml]
 * ----
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
 * ----
 *
 * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
 * @date 26 Sep 2016
 * @status added
 * @since 4.1
 */
@Type
public interface VnicProfileMapping {

    /**
     * Specifies the name of the external network.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 26 Sep 2016
     * @status added
     * @since 4.1
     */
    String sourceNetworkName();

    /**
     * Specifies the name of the external network profile.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 26 Sep 2016
     * @status added
     * @since 4.1
     */
    String sourceNetworkProfileName();

    /**
     * Points to an existing virtual NIC profile.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 26 Sep 2016
     * @status added
     * @since 4.1
     */
    @Link VnicProfile targetVnicProfile();
}
