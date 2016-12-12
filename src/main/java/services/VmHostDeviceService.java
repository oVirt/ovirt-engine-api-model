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
import types.HostDevice;

/**
 * A service to manage individual host device attached to a virtual machine.
 *
 * @author Martin Betak <mbetak@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface VmHostDeviceService {

    /**
     * Retrieve information about particular host device attached to given virtual machine.
     *
     * Example:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/vms/123/hostdevices/456
     * ----
     *
     * [source,xml]
     * ----
     * <host_device href="/ovirt-engine/api/hosts/543/devices/456" id="456">
     *   <name>pci_0000_04_00_0</name>
     *   <capability>pci</capability>
     *   <iommu_group>30</iommu_group>
     *   <placeholder>true</placeholder>
     *   <product id="0x13ba">
     *     <name>GM107GL [Quadro K2200]</name>
     *   </product>
     *   <vendor id="0x10de">
     *     <name>NVIDIA Corporation</name>
     *   </vendor>
     *   <host href="/ovirt-engine/api/hosts/543" id="543"/>
     *   <parent_device href="/ovirt-engine/api/hosts/543/devices/456" id="456">
     *     <name>pci_0000_00_03_0</name>
     *   </parent_device>
     *   <vm href="/ovirt-engine/api/vms/123" id="123"/>
     * </host_device>
     * ----
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get {
        /**
         * Retrieved information about the host device attached to given virtual machine.
         *
         * @author Martin Betak <mbetak@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out HostDevice device();
    }

    /**
     * Remove the attachment of this host device from given virtual machine.
     *
     * NOTE: In case this device serves as an IOMMU placeholder, it cannot be removed (remove will result only
     * in setting its `placeholder` flag to `true`). Note that all IOMMU placeholder devices will be removed
     * automatically as soon as there will be no more non-placeholder devices (all devices from given IOMMU
     * group are detached).
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/vms/123/hostdevices/456
     * ----
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
