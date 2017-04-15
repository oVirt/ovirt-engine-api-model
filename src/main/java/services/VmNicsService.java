/*
Copyright (c) 2015 Red Hat, Inc.

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

package services;

import annotations.Area;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Nic;

@Service
@Area("Network")
public interface VmNicsService {
    /**
     * Adds a NIC to the virtual machine.
     *
     * The following example adds a network interface named `mynic` using `virtio` and the `ovirtmgmt` network to the
     * virtual machine.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/nics
     * ----
     *
     * [source,xml]
     * ----
     * <nic>
     *   <interface>virtio</interface>
     *   <name>mynic</name>
     *   <network>
     *     <name>ovirtmgmt</name>
     *   </network>
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
     *   <network>
     *     <name>ovirtmgmt</name>
     *   </network>
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
    interface List {
        @Out Nic[] nics();

        /**
         * Sets the maximum number of NICs to return. If not specified all the NICs are returned.
         */
        @In Integer max();
    }

    @Service VmNicService nic(String id);
}
