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

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * Manages logical networks.
 *
 * The engine creates a default `ovirtmgmt` network on installation. This network acts as the management network for
 * access to hypervisor hosts. This network is associated with the `Default` cluster and is a member of the `Default`
 * data center.
 *
 * @author Ori Ben Sasson <obensass@redhat.com>
 * @date 20 Sep 2016
 * @status added
 */
@Service
@Area("Network")
public interface NetworksService {
    /**
     * Creates a new logical network, or associates an existing network with a data center.
     *
     * Creation of a new network requires the `name` and `data_center` elements.
     *
     * For example, to create a network named `mynetwork` for data center `123` send a request like this:
     *
     * ```http
     * POST /ovirt-engine/api/networks
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <network>
     *   <name>mynetwork</name>
     *   <data_center id="123"/>
     * </network>
     * ```
     *
     *
     * To associate the existing network `456` with the data center `123` send a request like this:
     *
     * ```http
     * POST /ovirt-engine/api/datacenters/123/networks
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <network>
     *   <name>ovirtmgmt</name>
     * </network>
     * ```
     *
     * To create a network named `exnetwork` on top of an external _OpenStack_ network provider `456` send a request
     * like this:
     *
     * ```http
     * POST /ovirt-engine/api/networks
     * ```
     *
     * ```xml
     * <network>
     *   <name>exnetwork</name>
     *   <external_provider id="456"/>
     *   <data_center id="123"/>
     * </network>
     * ```
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Petr Horacek <phoracek@redhat.com>
     * @date 12 Nov 2017
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            or(mandatory(network().id()), mandatory(network().name()));
            optional(network().comment());
            optional(network().description());
            optional(network().ip().address());
            optional(network().ip().gateway());
            optional(network().ip().netmask());
            optional(network().mtu());
            optional(network().profileRequired());
            optional(network().stp());
            optional(network().vlan().id());
            optional(network().usages()[COLLECTION]);
            optional(network().externalProvider().id());
            optional(network().externalProviderPhysicalNetwork().id());
            optional(network().portIsolation());
        }
        @In @Out Network network();
    }

    /**
     * List logical networks.
     *
     * For example:
     *
     * ```http
     * GET /ovirt-engine/api/networks
     * ```
     *
     * Will respond:
     *
     * ```xml
     * <networks>
     *   <network href="/ovirt-engine/api/networks/123" id="123">
     *     <name>ovirtmgmt</name>
     *     <description>Default Management Network</description>
     *     <link href="/ovirt-engine/api/networks/123/permissions" rel="permissions"/>
     *     <link href="/ovirt-engine/api/networks/123/vnicprofiles" rel="vnicprofiles"/>
     *     <link href="/ovirt-engine/api/networks/123/networklabels" rel="networklabels"/>
     *     <mtu>0</mtu>
     *     <stp>false</stp>
     *     <usages>
     *       <usage>vm</usage>
     *     </usages>
     *     <data_center href="/ovirt-engine/api/datacenters/456" id="456"/>
     *   </network>
     *   ...
     * </networks>
     * ```
     *
     * The order of the returned list of networks is guaranteed only if the `sortby` clause is included in the
     * `search` parameter.
     *
     * @author Ori Ben Sasson <obensass@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface List extends Follow {
        @Out Network[] networks();

        /**
         * Sets the maximum number of networks to return. If not specified all the networks are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned networks.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();
    }

    /**
     * Reference to the service that manages a specific network.
     *
     * @author Ori Ben Sasson <obensass@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service NetworkService network(String id);
}
