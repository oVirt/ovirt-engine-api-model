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
import types.Application;

/**
 * A service that provides information about an application installed in a virtual machine.
 *
 * @author Milan Zamazal <mzamazal@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface VmApplicationService {
    /**
     * Returns the information about the application.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get {
        /**
         * The information about the application.
         *
         * The information consists of `name` attribute containing the name of the application
         * (which is an arbitrary string that may also contain additional information such as
         * version) and `vm` attribute identifying the virtual machine.
         *
         * For example, a request like this:
         *
         * [source]
         * ----
         * GET /ovirt-engine/api/vms/123/applications/789
         * ----
         *
         * May return information like this:
         *
         * [source,xml]
         * ----
         * <application href="/ovirt-engine/api/vms/123/applications/789" id="789">
         *   <name>ovirt-guest-agent-common-1.0.12-3.el7</name>
         *   <vm href="/ovirt-engine/api/vms/123" id="123"/>
         * </application>
         * ----
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Application application();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }
}
