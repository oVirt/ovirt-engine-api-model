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

/**
 * Network filters filter packets sent to and from the virtual machine's NIC according to defined rules.
 *
 * There are several types of network filters supported based on libvirt.
 * For more details about the different network filters see https://libvirt.org/firewall.html[here].
 *
 * In addition to libvirt's network filters, there are two additional network filters:
 * The first is called `vdsm-no-mac-spoofing` and is composed of `no-mac-spoofing` and `no-arp-mac-spoofing`.
 * The second is called `ovirt-no-filter` and is used when no network filter is to be defined for the virtual machine's NIC.
 * The `ovirt-no-filter` network filter is only used for internal implementation, and
 * does not exist on the NICs.
 *
 * This is a example of the XML representation:
 *
 * [source,xml]
 * ----
 * <network_filter id="00000019-0019-0019-0019-00000000026c">
 *   <name>example-filter</name>
 *   <version>
 *     <major>4</major>
 *     <minor>0</minor>
 *     <build>-1</build>
 *     <revision>-1</revision>
 *   </version>
 * </network_filter>
 * ----
 *
 * If any part of the version is not present, it is represented by -1.
 *
 * @author Megan Lewis <melewis@redhat.com>
 * @date 21 Feb 2017
 * @status updated_by_docs
 */
@Type
public interface NetworkFilter extends Identified {
    /**
     * The minimum supported version of a specific NetworkFilter. This is the version that the NetworkFilter was first introduced in.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 21 Feb 2017
     * @status updated_by_docs
     */
    Version version();
}
