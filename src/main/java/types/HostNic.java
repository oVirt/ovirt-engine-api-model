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

@Type
public interface HostNic extends Identified {
    Mac mac();
    Ip ip();
    Ip ipv6();
    String baseInterface();
    Vlan vlan();
    Bonding bonding();
    BootProtocol bootProtocol();
    BootProtocol ipv6BootProtocol();
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
     * For a SR-IOV physical function NIC describes its virtual functions configuration.
     */
    HostNicVirtualFunctionsConfiguration virtualFunctionsConfiguration();

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
    @Link Network network();
    @Link Qos qos();

    /**
     * For a SR-IOV virtual function NIC references to its physical function NIC.
     */
    @Link HostNic physicalFunction();
}
