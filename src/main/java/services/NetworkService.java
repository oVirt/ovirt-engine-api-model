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

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Network;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * A service managing a network
 *
 * @author Ori Ben Sasson <obensass@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Network")
public interface NetworkService {
    /**
     * Gets a logical network.
     *
     * For example:
     *
     * ```http
     * GET /ovirt-engine/api/networks/123 HTTP/1.1
     * ```
     *
     * Will respond:
     *
     * ```xml
     * <network href="/ovirt-engine/api/networks/123" id="123">
     *   <name>ovirtmgmt</name>
     *   <description>Default Management Network</description>
     *   <link href="/ovirt-engine/api/networks/123/permissions" rel="permissions"/>
     *   <link href="/ovirt-engine/api/networks/123/vnicprofiles" rel="vnicprofiles"/>
     *   <link href="/ovirt-engine/api/networks/123/networklabels" rel="networklabels"/>
     *   <mtu>0</mtu>
     *   <stp>false</stp>
     *   <usages>
     *     <usage>vm</usage>
     *   </usages>
     *   <data_center href="/ovirt-engine/api/datacenters/456" id="456"/>
     * </network>
     * ```
     *
     * @author Ori Ben Sasson <obensass@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Get extends Follow {
        @Out Network network();
    }

    /**
     * Updates a logical network.
     *
     * The `name`, `description`, `ip`, `vlan`, `stp` and `display` attributes can be updated.
     *
     * For example, to update the description of the logical network `123` send a request like this:
     *
     * ```http
     * PUT /ovirt-engine/api/networks/123 HTTP/1.1
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <network>
     *   <description>My updated description</description>
     * </network>
     * ```
     *
     *
     * The maximum transmission unit of a network is set using a PUT request to
     * specify the integer value of the `mtu` attribute.
     *
     * For example, to set the maximum transmission unit send a request like this:
     *
     * ```http
     * PUT /ovirt-engine/api/datacenters/123/networks/456 HTTP/1.1
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <network>
     *   <mtu>1500</mtu>
     * </network>
     * ```
     *
     * NOTE: Updating external networks is not propagated to the provider.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Dominik Holler <dholler@redhat.com>
     * @author Emma Heftman <eheftman@redhat.com>
     * @date 17 Sep 2018
     * @status updated_by_docs
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(network().comment());
            optional(network().description());
            optional(network().display());
            optional(network().ip().address());
            optional(network().ip().gateway());
            optional(network().ip().netmask());
            optional(network().mtu());
            optional(network().name());
            optional(network().stp());
            optional(network().vlan().id());
        }
        @In @Out Network network();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes a logical network, or the association of a logical network to a data center.
     *
     * For example, to remove the logical network `123` send a request like this:
     *
     * ```http
     * DELETE /ovirt-engine/api/networks/123 HTTP/1.1
     * ```
     *
     * Each network is bound exactly to one data center. So if we disassociate network with data center it has the same
     * result as if we would just remove that network. However it might be more specific to say we're removing network
     * `456` of data center `123`.
     *
     * For example, to remove the association of network `456` to data center `123` send a request like this:
     *
     * ```http
     * DELETE /ovirt-engine/api/datacenters/123/networks/456 HTTP/1.1
     * ```
     *
     * NOTE: To remove an external logical network, the network has to be removed directly from its provider by
     * link:https://developer.openstack.org/api-ref/network[OpenStack Networking API].
     * The entity representing the external network inside {product-name} is removed automatically,
     * if xref:types/open_stack_network_provider/attributes/auto_sync[`auto_sync`] is enabled for the provider,
     * otherwise the entity has to be removed using this method.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Domininik Holler <dholler@redhat.com>
     * @author Emma Heftman <eheftman@redhat.com>
     * @date 2 July 2018
     * @status updated-by-docs
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Reference to the service that manages the permissions assigned to this network.
     *
     * @author Ori Ben Sasson <obensass@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service AssignedPermissionsService permissions();

    /**
     * Reference to the service that manages the vNIC profiles assigned to this network.
     *
     * @author Ori Ben Sasson <obensass@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service AssignedVnicProfilesService vnicProfiles();

    /**
     * Reference to the service that manages the network labels assigned to this network.
     *
     * @author Ori Ben Sasson <obensass@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service NetworkLabelsService networkLabels();
}
