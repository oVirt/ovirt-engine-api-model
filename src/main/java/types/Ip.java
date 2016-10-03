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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents the IP configuration of a network interface.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 3 Oct 2016
 * @staus added
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
     * @date 3 Oct 2016
     * @staus added
     */
    String address();

    /**
     * The network mask.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 3 Oct 2016
     * @staus added
     */
    String netmask();

    /**
     * The address of the default gateway.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 3 Oct 2016
     * @staus added
     */
    String gateway();

    /**
     * The version of the IP protocol.
     *
     * This attribute is optional, and when it isn't given the value will be inferred from the value of the `address`
     * attribute. If that value is a valid IPv4 address, then it will be assumed that the value of this attribute is
     * `v4`. If it is a valid IPv6 address, then it will be assumed that the value of this attribute is `v6`.
     *
     * NOTE: This inference of the IP version from the value of the `address` is implemented since version 4.1 of the
     * engine, before that this attribute was mandatory.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 3 Oct 2016
     * @staus added
     */
    IpVersion version();
}
