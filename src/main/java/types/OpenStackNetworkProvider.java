/*
Copyright (c) 2015-2016 Red Hat, Inc.

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

/*
 * Represents an OpenStack network provider type.
 *
 * @author Mor Kalfon <mkalfon@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 6 Nov 2017
 * @status updated_by_docs
 */
@Type
public interface OpenStackNetworkProvider extends OpenStackProvider {
    /**
     * Network plug-in type.
     *
     * Since version 4.2 of the {engine-name}, this attribute has been deprecated in favour of `external_plugin_type`.
     * This attribute is only valid for providers of type `open_vswitch`, and will only be returned when
     * the value of the `external_plugin_type` attribute value is equal to `open_vswitch`.
     *
     * If both `plugin_type` and `external_plugin_type` are specified during an update, the value of `plugin_type` will
     * be ignored.
     *
     * For external providers this value will not be shown and will be ignored during update requests.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 6 Nov 2017
     * @status updated_by_docs
     */
    @Deprecated
    NetworkPluginType pluginType();

    /**
     * Network plug-in type.
     *
     * This attribute allows you to choose the correct provider driver on the host when an external NIC is added or
     * modified. If automated installation of the driver is supported (only available for some predefined
     * implementations, for example `ovirt-provider-ovn`), this attribute will also allow the system to decide which
     * driver implementation to install on newly added hosts.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 6 Nov 2017
     * @status updated_by_docs
     * @since 4.2
     */
    String externalPluginType();

    /**
     * Agent configuration settings.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 6 Nov 2017
     * @status updated_by_docs
     */
    AgentConfiguration agentConfiguration();

    /**
     * Indicates whether the provider is read-only.
     *
     * A read-only provider does not allow adding, modifying, or deleting of networks or subnets. Port-related
     * operations are allowed, as they are required for the provisioning of virtual NICs.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 16 Nov 2016
     * @status updated_by_docs
     */
    Boolean readOnly();

    /**
     * The type of provider.
     *
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 16 Nov 2016
     * @status updated_by_docs
     */
    OpenStackNetworkProviderType type();

    /**
     * Reference to the certificates list.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 6 Nov 2017
     * @status updated_by_docs
     */
    @Link Certificate[] certificates();

    /**
     * Reference to the OpenStack networks list.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 6 Nov 2017
     * @status updated_by_docs
     */
    @Link OpenStackNetwork[] networks();

    /**
     * Reference to the OpenStack networks subnets list.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 6 Nov 2017
     * @status updated_by_docs
     */
    @Link OpenStackSubnet[] subnets();
}
