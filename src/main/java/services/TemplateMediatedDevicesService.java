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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.VmMediatedDevice;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

/**
 * A service that manages mediated devices of a template.
 *
 * @author Milan Zamazal <mzamazal@redhat.com>
 * @date 10 Mar 2022
 * @status added
 * @since 4.5
 */
@Service
@Area("Virtualization")
public interface TemplateMediatedDevicesService {

    /**
     * Add new mediated device to the template.
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
     * Lists all the configured mediated devices of the template.
     *
     * The order of the returned list of mediated devices isn't guaranteed.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 10 Mar 2022
     * @status added
     * @since 4.5
     */
    interface List extends Follow {

        /**
         * The list of mediated devices of the template.
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
     * Returns a reference to the service that manages a mediated device of a template.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 10 Mar 2022
     * @status added
     * @since 4.5
     */
    @Service TemplateMediatedDeviceService device(String id);
}
