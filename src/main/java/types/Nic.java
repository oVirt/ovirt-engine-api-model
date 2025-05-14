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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents a virtual machine NIC.
 *
 * For example, the XML representation of a NIC will look like this:
 *
 * ```xml
 * <nic href="/ovirt-engine/api/vms/123/nics/456" id="456">
 *   <name>nic1</name>
 *   <vm href="/ovirt-engine/api/vms/123" id="123"/>
 *   <interface>virtio</interface>
 *   <linked>true</linked>
 *   <mac>
 *     <address>02:00:00:00:00:00</address>
 *   </mac>
 *   <plugged>true</plugged>
 *   <vnic_profile href="/ovirt-engine/api/vnicprofiles/789" id="789"/>
 * </nic>
 * ```
 *
 * @author Martin Mucha <mmucha@redhat.com>
 * @author Megan Lewis <melewis@redhat.com>
 * @date 21 Feb 2017
 * @status updated_by_docs
 */
@Type
public interface Nic extends Device {
    /**
     * Defines if the NIC is linked to the virtual machine.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    Boolean linked();

    /**
     * The type of driver used for the NIC.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    NicInterface _interface();

    /**
     * The MAC address of the interface.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    Mac mac();

    /**
     * Defines if the NIC is plugged in to the virtual machine.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    Boolean plugged();

    /**
     * Defines if the NIC configuration on the virtual machine is
     * synced with the configuration represented by engine.
     *
     * @author Eitan Raviv <eraviv@redhat.com>
     * @date 6 Apr 2021
     * @status updated
     */
    Boolean synced();

    /**
     * Defines how an IP address is assigned to the NIC.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    BootProtocol bootProtocol();

    /**
     * Defines if the network interface should be activated upon operation system startup.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Boolean onBoot();

    /**
     * A reference to the network that the interface should be connected to. A blank network ID is allowed.
     *
     * Usage of this element for creating or updating a NIC is deprecated; use `vnic_profile` instead. It is preserved
     * because it is still in use by the `initialization` element, as a holder for IP addresses and other network
     * details.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 21 Feb 2017
     * @status updated_by_docs
     */
    @Link Network network();

    /**
     * A link to the statistics for the NIC.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 1 Feb 2017
     * @status updated_by_docs
     */
    @Link Statistic[] statistics();

    /**
     * A link to an associated virtual network interface profile.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link VnicProfile vnicProfile();

    /**
     * A link to a collection of network labels that are associated with the host NIC.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link NetworkLabel[] networkLabels();

    /**
     * A link to a collection of network attachments that are associated with the host NIC.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link NetworkAttachment[] networkAttachments();

    /**
     * A link to a collection of network labels that are allowed to be attached to the virtual functions
     * of an SR-IOV NIC.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link NetworkLabel[] virtualFunctionAllowedLabels();

    /**
     * A link to a collection of networks that are allowed to be attached to the virtual functions
     * of an SR-IOV NIC.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link Network[] virtualFunctionAllowedNetworks();

    /**
     * A link to a collection of reported devices that are associated with the virtual network interface.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link ReportedDevice[] reportedDevices();

    /**
     * A link to the network filter parameters.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 21 Feb 2017
     * @status updated_by_docs
     * @since 4.2
     */
    @Link NetworkFilterParameter[] networkFilterParameters();

}
