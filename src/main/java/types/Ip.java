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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents the IP configuration of a network interface.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 21 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface Ip {
    /**
     * The text representation of the IP address.
     *
     * For example, an IPv4 address will be represented as follows:
     *
     * [source,xml]
     * ----
     * <ip>
     *   <address>192.168.0.1</address>
     *   ...
     * </ip>
     * ----
     *
     * An IPv6 address will be represented as follows:
     *
     * [source,xml]
     * ----
     * <ip>
     *   <address>2620:52:0:20f0:4216:7eff:feaa:1b50</address>
     *   ...
     * </ip>
     * ----
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 21 Nov 2016
     * @status updated_by_docs
     */
    String address();

    /**
     * The network mask.
     *
     * For IPv6 addresses the value is an integer in the range of 0-128, which represents the subnet prefix.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 21 Nov 2016
     * @status updated_by_docs
     */
    String netmask();

    /**
     * The address of the default gateway.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 21 Nov 2016
     * @status updated_by_docs
     */
    String gateway();

    /**
     * The version of the IP protocol.
     *
     * NOTE: From version 4.1 of the Manager this attribute will be optional, and when a value is not provided, it will
     * be inferred from the value of the `address` attribute.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 21 Nov 2016
     * @status updated_by_docs
     */
    IpVersion version();
}
