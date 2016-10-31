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
 * A bonded interface is represented as a <<types/host_nic, HostNic>> object
 * containing the `bonding` and `slaves` attributes.
 *
 * For example, the XML representation of a bonded host NIC looks like this:
 *
 * [source,xml]
 * ----
 * <host_nic href="/ovirt-engine/api/hosts/123/nics/456" id="456">
 *   <name>bond0</name>
 *   <mac address="00:00:00:00:00:00"/>
 *   <ip>
 *     <address>192.168.122.39</address>
 *     <gateway>192.168.122.1</gateway>
 *     <netmask>255.255.255.0</netmask>
 *     <version>v4</version>
 *   </ip>
 *   <boot_protocol>dhcp</boot_protocol>
 *   <bonding>
 *     <options>
 *       <option>
 *         <name>mode</name>
 *         <value>4</value>
 *         <type>Dynamic link aggregation (802.3ad)</type>
 *       </option>
 *       <option>
 *         <name>miimon</name>
 *         <value>100</value>
 *       </option>
 *     </options>
 *     <slaves>
 *       <host_nic id="123"/>
 *       <host_nic id="456"/>
 *     </slaves>
 *   </bonding>
 *   <mtu>1500</mtu>
 *   <bridged>true</bridged>
 *   <custom_configuration>false</custom_configuration>
 * </host_nic>
 * ----
 *
 * @author Martin Mucha <mmucha@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 31 Oct 2016
 * @status updated_by_docs
 */
@Type
public interface HostNic extends Identified {
    /**
     *
     * The MAC address of the NIC.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    Mac mac();

    /**
     * The IPv4 address of the NIC.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Ip ip();

    /**
     * The IPv6 address of the NIC.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Ip ipv6();

    /**
     * The base interface of the NIC.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String baseInterface();

    Vlan vlan();

    /**
     * The bonding parameters of the NIC.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Bonding bonding();

    /**
     * The IPv4 boot protocol configuration of the NIC.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    BootProtocol bootProtocol();

    /**
     * The IPv6 boot protocol configuration of the NIC.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    BootProtocol ipv6BootProtocol();

    /**
     * A link to the statistics of the NIC.
     *
     * The data types for HostNic statistical values:
     *
     * * data.current.rx - The rate in bytes per second of data received.
     * * data.current.tx - The rate in bytes per second of data transmitted.
     * * data.total.rx - Total received data.
     * * data.total.tx - Total transmitted data.
     * * errors.total.rx - Total errors from receiving data.
     * * errors.total.tx - Total errors from transmitting data.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    Statistic[] statistics();

    Boolean checkConnectivity();
    Integer speed();
    NicStatus status();

    /**
     * The maximum transmission unit for the interface.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @date 22 Sept 2016
     * @status added
     */
    Integer mtu();

    /**
     * Defines the bridged network status. Set to `true` for a bridged network
     * and `false` for a bridgeless network.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @date 22 Sept 2016
     * @status added
     */
    Boolean bridged();

    Boolean customConfiguration();
    Boolean overrideConfiguration();

    /**
     * The labels that are applied to this NIC.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @date 22 Sept 2016
     * @status added
     */
    NetworkLabel[] networkLabels();

    Property[] properties();

    /**
     * The `ad_aggregator_id` property of a bond or bond slave, for bonds in mode 4.
     * Bond mode 4 is the 802.3ad standard, also called dynamic link aggregation.
     * (See https://en.wikipedia.org/wiki/Link_aggregation[Wikipedia] and
     * http://www.ieee802.org/3/hssg/public/apr07/frazier_01_0407.pdf[Presentation] for more information).
     * This is only valid for bonds in mode 4, or NICs which are part of a bond.
     * It is not present for bonds in other modes, or NICs which are not part of a bond in mode 4.
     * The `ad_aggregator_id` property indicates which of the bond slaves are active. The value of the
     * `ad_aggregator_id` of an active slave is the same as the value of the `ad_aggregator_id` property of the bond.
     * This parameter is read only. Setting it will have no effect on the bond/NIC.
     * It is retrieved from the `/sys/class/net/bondX/bonding/ad_aggregator` file for a bond, and the
     * `/sys/class/net/ensX/bonding_slave/ad_aggregator_id` file for a NIC.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     * @since 4.0.3
     */
    Integer adAggregatorId();

    @Link Host host();

    /**
     * A reference to the network to which the interface should be connected. A blank network ID is allowed.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    @Link Network network();

    /**
     * A link to the quality-of-service configuration of the interface.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Qos qos();

    /**
     * A reference to the physical function NIC of a SR-IOV virtual function NIC.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    @Link HostNic physicalFunction();

    /**
     * Describes the virtual functions configuration of a physical function NIC.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    HostNicVirtualFunctionsConfiguration virtualFunctionsConfiguration();
}
