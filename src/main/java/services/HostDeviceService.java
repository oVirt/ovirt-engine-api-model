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
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.HostDevice;

/**
 * A service to access a particular device of a host.
 *
 * @author Martin Betak <mbetak@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface HostDeviceService {

    /**
     * Retrieve information about a particular host's device.
     *
     * An example of getting a host device:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/hosts/123/devices/456
     * ----
     *
     * [source,xml]
     * ----
     * <host_device href="/ovirt-engine/api/hosts/123/devices/456" id="456">
     *   <name>usb_1_9_1_1_0</name>
     *   <capability>usb</capability>
     *   <host href="/ovirt-engine/api/hosts/123" id="123"/>
     *   <parent_device href="/ovirt-engine/api/hosts/123/devices/789" id="789">
     *     <name>usb_1_9_1</name>
     *   </parent_device>
     * </host_device>
     * ----
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        @Out HostDevice device();
    }
}
