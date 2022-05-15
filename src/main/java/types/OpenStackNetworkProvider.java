/*
Copyright (c) 2015-2016 Red Hat, Inc.

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
     * Deprecated Agent configuration settings.
     *
     * Ignored, because the deployment of OpenStack Neutron agent is dropped since {product-name} 4.4.0.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @author Dominik Holler <dholler@redhat.com>
     * @date 11 Jun 2019
     * @status updated
     */
    @Deprecated
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

    /**
     * Indicates if the xref:types/network[networks] of this provider are automatically synchronized.
     *
     * If `true`, the networks of this provider are automatically and cyclically synchronized to {product-name} in
     * the background.
     * This means that all new networks of this provider are imported, and all discarded networks are removed
     * from all xref:types/cluster[clusters] that have this external provider as the default provider.
     * If the name of a network is changed on the provider, the change is synchronized to the network entity in
     * {product-name}. Furthermore, if a new cluster that has the provider as the default provider is added,
     * already imported networks are attached to this new cluster during synchronization.
     *
     * The automatically initiated import triggers the following steps:
     *
     * - The networks of the external provider will be imported to every xref:types/data_center[data center]
     *   in the data centers of the clusters that have that external provider as the default provider.
     *
     * - A xref:types/vnic_profile[vNIC profile] will be created for each involved data center and network.
     *
     * - The networks will be assigned to each cluster that has that external provider as the default provider.
     *
     * All users are allowed to use the new vNIC Profile.
     *
     * The default is `false` for backwards compatibility.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 04 May 2018
     * @status updated_by_docs
     * @since 4.2
     */
    Boolean autoSync();

    /**
     * Indicates whether the provider is unmanaged by {product-name}.
     *
     * If `true`, authentication and subnet control are entirely left to the external provider and are
     * unmanaged by {product-name}.
     *
     * The default is `false` for backwards compatibility.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 04 December 2017
     * @status updated_by_docs
     * @since 4.2.1
     */
    Boolean unmanaged();

    /**
     * Defines the domain name of the `username` in xref:types/external_provider[ExternalProvider] for
     * OpenStack Identity API v3.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 30 Aug 2018
     * @status updated_by_docs
     * @since 4.2.7
     */
    String userDomainName();

    /**
     * Defines the project name for OpenStack Identity API v3.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 30 Aug 2018
     * @status updated_by_docs
     * @since 4.2.7
     */
    String projectName();

    /**
     * Defines the project's domain name for OpenStack Identity API v3.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 30 Aug 2018
     * @status updated_by_docs
     * @since 4.2.7
     */
    String projectDomainName();
}
