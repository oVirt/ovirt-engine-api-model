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
 * Represents OpenStack network provider type.
 *
 * @author Mor Kalfon <mkalfon@redhat.com>
 * @date 14 Dec 2016
 * @status added
 */
@Type
public interface OpenStackNetworkProvider extends OpenStackProvider {
    /**
     * Network plugin type.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    NetworkPluginType pluginType();

    /**
     * Agent configuration settings.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
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
     * @date 12 Dec 2016
     * @status added
     */
    @Link Certificate[] certificates();

    /**
     * Reference to OpenStack networks list.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link OpenStackNetwork[] networks();

    /**
     * Reference to OpenStack networks subnets list.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link OpenStackSubnet[] subnets();
}
