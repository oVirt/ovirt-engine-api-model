/*
Copyright (C) 2022 Red Hat, Inc.

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
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.VmMediatedDevice;

@Service
@Area("Virtualization")
public interface TemplateMediatedDeviceService {

    /**
     * Gets mediated device configuration of the template.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 10 Mar 2022
     * @status added
     * @since 4.5
     */
    interface Get extends Follow {
        /**
         * The information about the mediated device of the template.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 10 Mar 2022
         * @status added
         * @since 4.5
         */
        @Out VmMediatedDevice device();
    }

    /**
     * Updates the information about the mediated device.
     *
     * You can update the information using `specParams` element.
     *
     * For example, to update a mediated device, send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/templates/123/mediateddevices/00000000-0000-0000-0000-000000000000
     * <vm_mediated_device>
     *   <spec_params>
     *     <property>
     *       <name>mdevType</name>
     *       <value>nvidia-11</value>
     *     </property>
     *   </spec_params>
     * </vm_mediated_device>
     * ----
     *
     * with response body:
     *
     * [source,xml]
     * ----
     * <vm_mediated_device href="/ovirt-engine/api/templates/123/mediateddevices/00000000-0000-0000-0000-000000000000" id="00000000-0000-0000-0000-000000000000">
     *   <template href="/ovirt-engine/api/templates/123" id="123"/>
     *   <spec_params>
     *     <property>
     *       <name>mdevType</name>
     *       <value>nvidia-11</value>
     *     </property>
     *   </spec_params>
     * </vm_mediated_device>
     * ----
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 10 Mar 2022
     * @status added
     * @since 4.5
     */
    interface Update {
        /**
         * The information about the mediated device.
         *
         * The request data must contain `specParams` properties.
         * The response data contains complete information about the
         * updated mediated device.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 10 Mar 2022
         * @status added
         * @since 4.5
         */
        @In @Out VmMediatedDevice devices();

        /**
         * Indicates if the update should be performed asynchronously.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 10 Mar 2022
         * @status added
         * @since 4.5
         */
        @In Boolean async();
    }

    /**
     * Remove the mediated device from the template.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 10 Mar 2022
     * @status added
     * @since 4.5
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 10 Mar 2022
         * @status added
         * @since 4.5
         */
        @In Boolean async();
    }
}
