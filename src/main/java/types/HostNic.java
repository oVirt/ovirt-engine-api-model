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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents a host NIC.
 *
 * For example, the XML representation of a host NIC looks like this:
 *
 * [source,xml]
 * ----
 * <host_nic href="/ovirt-engine/api/hosts/123/nics/456" id="456">
 *   <name>eth0</name>
 *   <boot_protocol>static</boot_protocol>
 *   <bridged>true</bridged>
 *   <custom_configuration>true</custom_configuration>
 *   <ip>
 *     <address>192.168.122.39</address>
 *     <gateway>192.168.122.1</gateway>
 *     <netmask>255.255.255.0</netmask>
 *     <version>v4</version>
 *   </ip>
 *   <ipv6>
 *     <gateway>::</gateway>
 *     <version>v6</version>
 *   </ipv6>
 *   <ipv6_boot_protocol>none</ipv6_boot_protocol>
 *   <mac>
 *     <address>52:54:00:0c:79:1d</address>
 *   </mac>
 *   <mtu>1500</mtu>
 *   <status>up</status>
 * </host_nic>
 * ----
 *
 * @author Martin Mucha <mmucha@redhat.com>
 * @date 14 Sep 2016
 * @status added
 */
@Type
public interface HostNic extends Identified {
    /**
     *
     * The MAC address of the NIC.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    Mac mac();

    Ip ip();
    Ip ipv6();
    String baseInterface();
    Vlan vlan();
    Bonding bonding();
    BootProtocol bootProtocol();
    BootProtocol ipv6BootProtocol();

    /**
     * A link to the statistics of the NIC.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    Statistic[] statistics();

    Boolean checkConnectivity();
    Integer speed();
    NicStatus status();
    Integer mtu();
    Boolean bridged();
    Boolean customConfiguration();
    Boolean overrideConfiguration();
    NetworkLabel[] networkLabels();
    Property[] properties();

    /**
     * The `ad_aggregator_id` property of a bond or bond slave, for bonds in mode 4.
     * Bond mode 4 is the 802.3ad standard, also called dynamic link aggregation -
     * https://en.wikipedia.org/wiki/Link_aggregation[Wikipedia]
     * http://www.ieee802.org/3/hssg/public/apr07/frazier_01_0407.pdf[Presentation].
     * This is only valid for bonds in mode 4, or NICs (NIC - network interface card) which are part of a bond.
     * It is not present for bonds in other modes, or NICs which are not part in a bond in mode 4.
     * The `ad_aggregator_id` property indicates which of the bond slaves are active. The value of the
     * `ad_aggregator_id` of an active slave is the same the value of the  `ad_aggregator_id` property of the bond.
     * This parameter is read only. Setting it will have no effect on the bond/NIC.
     * It is retrieved from `/sys/class/net/bondX/bonding/ad_aggregator` file for a bond, and the
     * `/sys/class/net/ensX/bonding_slave/ad_aggregator_id` file for a NIC.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @date 10 Aug 2016
     * @status added
     * @since 4.0.3
     */
    Integer adAggregatorId();

    @Link Host host();

    /**
     * A reference to the network which the interface should be connected. A blank network id is allowed.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    @Link Network network();

    @Link Qos qos();

    /**
     * For a SR-IOV virtual function NIC references to its physical function NIC.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    @Link HostNic physicalFunction();

    /**
     * For a SR-IOV physical function NIC describes its virtual functions configuration.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    HostNicVirtualFunctionsConfiguration virtualFunctionsConfiguration();
}
