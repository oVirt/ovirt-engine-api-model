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
 * Describes how a host connects to a network.
 *
 * An XML representation of a network attachment on a host:
 *
 * [source,xml]
 * ----
 * <network_attachment href="/ovirt-engine/api/hosts/123/nics/456/networkattachments/789" id="789">
 *   <network href="/ovirt-engine/api/networks/234" id="234"/>
 *   <host_nic href="/ovirt-engine/api/hosts/123/nics/123" id="123"/>
 *   <in_sync>true</in_sync>
 *   <ip_address_assignments>
 *     <ip_address_assignment>
 *       <assignment_method>static</assignment_method>
 *       <ip>
 *         <address>192.168.122.39</address>
 *         <gateway>192.168.122.1</gateway>
 *         <netmask>255.255.255.0</netmask>
 *         <version>v4</version>
 *       </ip>
 *     </ip_address_assignment>
 *   </ip_address_assignments>
 *   <reported_configurations>
 *     <reported_configuration>
 *       <name>mtu</name>
 *       <expected_value>1500</expected_value>
 *       <actual_value>1500</actual_value>
 *       <in_sync>true</in_sync>
 *     </reported_configuration>
 *     <reported_configuration>
 *       <name>bridged</name>
 *       <expected_value>true</expected_value>
 *       <actual_value>true</actual_value>
 *       <in_sync>true</in_sync>
 *     </reported_configuration>
 *     ...
 *   </reported_configurations>
 * </network_attachment>
 * ----
 *
 * The network element, with either a `name` or an `id`, is required in order to attach a network
 * to a network interface card (NIC).
 *
 * For example, to attach a network to a host network interface card, send a request like this:
 *
 * [source]
 * ----
 * POST /ovirt-engine/api/hosts/123/nics/456/networkattachments
 * ----
 *
 * With a request body like this:
 *
 * [source,xml]
 * ----
 * <networkattachment>
 *   <network id="234"/>
 * </networkattachment>
 * ----
 *
 * To attach a network to a host, send a request like this:
 *
 * [source]
 * ----
 * POST /ovirt-engine/api/hosts/123/networkattachments
 * ----
 *
 * With a request body like this:
 *
 * [source,xml]
 * ----
 * <network_attachment>
 *   <network id="234"/>
 *   <host_nic id="456"/>
 * </network_attachment>
 * ----
 *
 * The `ip_address_assignments` and `properties` elements are updatable post-creation.
 *
 * For example, to update a network attachment, send a request like this:
 *
 * [source]
 * ----
 * PUT /ovirt-engine/api/hosts/123/nics/456/networkattachments/789
 * ----
 *
 * With a request body like this:
 *
 * [source,xml]
 * ----
 * <network_attachment>
 *   <ip_address_assignments>
 *     <ip_address_assignment>
 *       <assignment_method>static</assignment_method>
 *       <ip>
 *         <address>7.1.1.1</address>
 *         <gateway>7.1.1.2</gateway>
 *         <netmask>255.255.255.0</netmask>
 *         <version>v4</version>
 *       </ip>
 *     </ip_address_assignment>
 *   </ip_address_assignments>
 * </network_attachment>
 * ----
 *
 * To detach a network from the network interface card send a request like this:
 *
 * [source]
 * ----
 * DELETE /ovirt-engine/api/hosts/123/nics/456/networkattachments/789
 * ----
 *
 * IMPORTANT: Changes to network attachment configuration must be explicitly committed.
 *
 * An XML representation of a network attachment's `properties` sub-collection:
 *
 * [source, xml]
 * ----
 * <network_attachment>
 *   <properties>
 *     <property>
 *       <name>bridge_opts</name>
 *       <value>
 *         forward_delay=1500 group_fwd_mask=0x0 multicast_snooping=1
 *       </value>
 *     </property>
 *   </properties>
 *   ...
 * </network_attachment>
 * ----
 *
 * @author Marcin Mirecki <mmirecki@redhat.com>
 * @author Megan Lewis <melewis@redhat.com>
 * @date 21 Feb 2017
 * @status updated_by_docs
 */
@Type
public interface NetworkAttachment extends Identified {
    /**
     * The IP configuration of the network.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    IpAddressAssignment[] ipAddressAssignments();

    /**
     * Defines custom properties for the network configuration.
     *
     * Bridge options have the set name of bridge_opts. Separate multiple entries with a whitespace character.
     * The following keys are valid for `bridge_opts`:
     *
     * |===
     * |Name |Default value
     *
     * |`forward_delay` |`1500`
     * |`gc_timer` |`3765`
     * |`group_addr` |`1:80:c2:0:0:0`
     * |`group_fwd_mask` |`0x0`
     * |`hash_elasticity` |`4`
     * |`hash_max` |`512`
     * |`hello_time` |`200`
     * |`hello_timer` |`70`
     * |`max_age` |`2000`
     * |`multicast_last_member_count` |`2`
     * |`multicast_last_member_interval` |`100`
     * |`multicast_membership_interval` |`26000`
     * |`multicast_querier` |`0`
     * |`multicast_querier_interval` |`25500`
     * |`multicast_query_interval` |`13000`
     * |`multicast_query_response_interval` |`1000`
     * |`multicast_query_use_ifaddr` |`0`
     * |`multicast_router` |`1`
     * |`multicast_snooping` |`1`
     * |`multicast_startup_query_count` |`2`
     * |`multicast_startup_query_interval` |`3125`
     *
     * |===
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    Property[] properties();
    Boolean inSync();

    /**
     * A read-only list of configuration properties.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    ReportedConfiguration[] reportedConfigurations();

    /**
     * DNS resolver configuration will be reported when retrieving the network attachment using GET.
     * It is optional when creating a new network attachment or updating an existing one.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     * @since 4.2
     */
    DnsResolverConfiguration dnsResolverConfiguration();

    /**
     * A reference to the network that the interface is attached to.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    @Link Network network();

    /**
     * A reference to the host network interface.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    @Link HostNic hostNic();

    @Link Host host();
    @Link Qos qos();
}
