/*
Copyright (c) 2017 Red Hat, Inc.

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
 * Describes all firewall types supported by the system.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 11 Aug 2017
 * @status updated_by_docs
 * @since 4.2
 */
@Type
public enum FirewallType {
    /**
     * IPTables firewall type.
     *
     * When a cluster has the firewall type set to `iptables`, the firewalls of all hosts in the cluster will be configured using
     * `iptables`. `iptables` adds firewall rules to `/etc/sysconfig/iptables` using a special `iptables` syntax. For
     * more information, see the `iptables` manual page.
     *
     * `iptables` is deprecated in cluster version 4.2 and will be removed in a future cluster version.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @author Steve Goodman <sgoodman@redhat.com>
     * @date 26 Nov 2019
     * @status updated_by_docs
     * @since 4.2
     */
    IPTABLES,

    /**
     * FirewallD firewall type.
     *
     * When a cluster has the firewall type set to `firewalld`, the firewalls of all hosts in the cluster will be configured
     * using `firewalld`. http://www.firewalld.org[FirewallD] replaced IPTables in version 4.2. It simplifies
     * configuration using a command line program and dynamic configuration.
     *
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 11 Aug 2017
     * @status updated_by_docs
     * @since 4.2
     */
    FIREWALLD,
}
