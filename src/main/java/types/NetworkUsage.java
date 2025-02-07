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

import org.ovirt.api.metamodel.annotations.Type;
/**
 * This type indicates the purpose that the network is used for in the cluster.
 *
 * @author Sahina Bose <sabose@redhat.com>
 * @author Megan Lewis <melewis@redhat.com>
 * @date 21 Feb 2017
 * @status updated_by_docs
 */
@Type
public enum NetworkUsage {
    /**
     * The default gateway and the DNS resolver configuration of the host will be taken from this network.
     *
     * If this network is attached to the host, then the DNS resolver configuration will be taken from the
     * `dns_resolver_configuration` attribute of the network attachment. If there is no `dns_resolver_configuration`
     * attribute in this network attachment, then they will be taken from the `dns_resolver_configuration` of the
     * network itself. If `dns_resolver_configuration` attribute isn't present even there, DNS resolver configuration
     * won't be set.
     *
     * If you set this flag on a network, then the the default gateway for the host will be taken from the `gateway`
     * attribute of the `ip_address_assignment` of the network attachment.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 24 Jan 2017
     * @status added
     * @since 4.2
     */
    DEFAULT_ROUTE,

    /**
     * The network will be used for SPICE and VNC traffic.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 21 Feb 2017
     * @status updated_by_docs
     */
    DISPLAY,

    /**
     * The network will be used for Gluster (bricks) data traffic.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    GLUSTER,

    /**
     * The network will be used for communication between the {engine-name} and the nodes.
     * This is the network where the ovirtmgmt bridge will be created.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    MANAGEMENT,

    /**
     * The network will be used for virtual machine migration.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    MIGRATION,

    VM;
}
