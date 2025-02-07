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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.VmMediatedDevice;

/**
 * A service that manages mediated devices of a VM.
 *
 * @author Milan Zamazal <mzamazal@redhat.com>
 * @date 10 Mar 2022
 * @status added
 * @since 4.5
 */
@Service
@Area("Virtualization")
public interface VmMediatedDevicesService {

    /**
     * Add a new mediated device to the virtual machine.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 10 Mar 2022
     * @status added
     * @since 4.5
     */
    interface Add {
        @In @Out VmMediatedDevice device();
    }

    /**
     * Lists all the configured mediated devices of the virtual machine.
     *
     * The order of the returned list of mediated devices is not guaranteed.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 10 Mar 2022
     * @status added
     * @since 4.5
     */
    interface List extends Follow {

        /**
         * The list of mediated devices of the virtual machine.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 10 Mar 2022
         * @status added
         * @since 4.5
         */
        @Out VmMediatedDevice[] devices();

        /**
         * Sets the maximum number of mediated devices to return.
         * If not specified all the mediated devices are returned.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 10 Mar 2022
         * @status added
         * @since 4.5
         */
        @In Integer max();
    }

    /**
     * Returns a reference to the service that manages a mediated device of a virtual machine.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 10 Mar 2022
     * @status added
     * @since 4.5
     */
    @Service VmMediatedDeviceService device(String id);
}
