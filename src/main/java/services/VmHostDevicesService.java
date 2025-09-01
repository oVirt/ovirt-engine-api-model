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
import types.HostDevice;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * A service to manage host devices attached to a virtual machine.
 *
 * @author Martin Betak <mbetak@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface VmHostDevicesService {

    /**
     * Attach target device to given virtual machine.
     *
     * Example:
     *
     * ```http
     * POST /ovirt-engine/api/vms/123/hostdevices HTTP/1.1
     * ```
     *
     * With request body of type xref:types/host_device[HostDevice], for example
     *
     * ```xml
     * <host_device id="123" />
     * ```
     *
     * NOTE: A necessary precondition for a successful host device attachment is that the virtual machine must be pinned
     * to *exactly* one host. The device ID is then taken relative to this host.
     *
     * NOTE: Attachment of a PCI device that is part of a bigger IOMMU group will result in attachment of the remaining
     * devices from that IOMMU group as "placeholders". These devices are then identified using the `placeholder`
     * attribute of the xref:types/host_device[HostDevice] type set to `true`.
     *
     * In case you want attach a device that already serves as an IOMMU placeholder, simply issue an explicit Add operation
     * for it, and its `placeholder` flag will be cleared, and the device will be accessible to the virtual machine.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            or(mandatory(device().id()), mandatory(device().name()));
        }
        /**
         * The host device to be attached to given virtual machine.
         *
         * @author Martin Betak <mbetak@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out HostDevice device();
    }

    /**
     * List the host devices assigned to given virtual machine.
     *
     * The order of the returned list of devices isn't guaranteed.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        /**
         * Retrieved list of host devices attached to given virtual machine.
         *
         * @author Martin Betak <mbetak@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out HostDevice[] device();

        /**
         * Sets the maximum number of devices to return. If not specified all the devices are returned.
         */
        @In Integer max();
    }

    /**
     * Returns a reference to the service that manages a specific host device attached to given virtual machine.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service VmHostDeviceService device(String id);
}
