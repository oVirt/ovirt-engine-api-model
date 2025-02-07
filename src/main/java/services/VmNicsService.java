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

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Nic;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("Network")
public interface VmNicsService {
    /**
     * Adds a NIC to the virtual machine.
     *
     * The following example adds to the virtual machine `123` a network interface named `mynic` using `virtio` and the
     * NIC profile `456`.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/nics
     * ----
     *
     * [source,xml]
     * ----
     * <nic>
     *   <name>mynic</name>
     *   <interface>virtio</interface>
     *   <vnic_profile id="456"/>
     * </nic>
     * ----
     *
     * The following example sends that request using `curl`:
     *
     * [source,bash]
     * ----
     * curl \
     * --request POST \
     * --header "Version: 4" \
     * --header "Content-Type: application/xml" \
     * --header "Accept: application/xml" \
     * --user "admin@internal:mypassword" \
     * --cacert /etc/pki/ovirt-engine/ca.pem \
     * --data '
     * <nic>
     *   <name>mynic</name>
     *   <interface>virtio</interface>
     *   <vnic_profile id="456"/>
     * </nic>
     * ' \
     * https://myengine.example.com/ovirt-engine/api/vms/123/nics
     * ----
     *
     * [IMPORTANT]
     * ====
     * The hotplugging feature only supports virtual machine operating systems with hotplugging operations.
     * Example operating systems include:
     *
     * - Red Hat Enterprise Linux 6
     * - Red Hat Enterprise Linux 5
     * - Windows Server 2008 and
     * - Windows Server 2003
     * ====
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Vinzenz Feenstra <vfeenstr@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(nic().name());
//            optional(nic()._interface()); TODO: uncomment when able to handle '_'
            optional(nic().linked());
            optional(nic().mac().address());
            optional(nic().plugged());
            optional(nic().vnicProfile().id());
        }
        @In @Out Nic nic();
    }

    /**
     * Returns the list of NICs of the virtual machine.
     *
     * The order of the returned list of NICs isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out Nic[] nics();

        /**
         * Sets the maximum number of NICs to return. If not specified all the NICs are returned.
         */
        @In Integer max();
    }

    @Service VmNicService nic(String id);
}
