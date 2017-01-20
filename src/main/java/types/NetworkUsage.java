/*
Copyright (c) 2016 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Type;

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

    DISPLAY,

    /**
     * The network will be used for Gluster(bricks) data traffic.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 23 Sep 2016
     * @status added
     */
    GLUSTER,

    MANAGEMENT,
    MIGRATION,
    VM;
}
