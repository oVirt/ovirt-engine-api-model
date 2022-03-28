/*
Copyright (c) 2015 Red Hat, Inc.

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
 * Represents OpenStack subnet type.
 *
 * @author Mor Kalfon <mkalfon@redhat.com>
 * @date 14 Dec 2016
 * @status added
 */
@Type
public interface OpenStackSubnet extends Identified {

    /**
     * Defines network CIDR.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String cidr();

    /**
     * Defines IP version.
     *
     * Values can be `v4' for IPv4 or `v6` for IPv6.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String ipVersion();

    /**
     * Defines IP gateway.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String gateway();

    /**
     * Defines a list of DNS servers.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String[] dnsServers();

    /**
     * Reference to the service managing the OpenStack network.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link OpenStackNetwork openstackNetwork();
}
