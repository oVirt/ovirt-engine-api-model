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
import types.Application;

/**
 * A service that provides information about applications installed in a virtual machine.
 *
 * @author Milan Zamazal <mzamazal@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface VmApplicationsService {
    /**
     * Returns a list of applications installed in the virtual machine.
     *
     * The order of the returned list of applications isn't guaranteed.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        /**
         * A list of applications installed in the virtual machine.
         *
         * For example, a request like this:
         *
         * ```http
         * GET /ovirt-engine/api/vms/123/applications/
         * ```
         *
         * May return a list like this:
         *
         * ```xml
         * <applications>
         *   <application href="/ovirt-engine/api/vms/123/applications/456" id="456">
         *     <name>kernel-3.10.0-327.36.1.el7</name>
         *     <vm href="/ovirt-engine/api/vms/123" id="123"/>
         *   </application>
         *   <application href="/ovirt-engine/api/vms/123/applications/789" id="789">
         *     <name>ovirt-guest-agent-common-1.0.12-3.el7</name>
         *     <vm href="/ovirt-engine/api/vms/123" id="123"/>
         *   </application>
         * </applications>
         * ```
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Application[] applications();

        /**
         * Sets the maximum number of applications to return. If not specified all the applications are returned.
         */
        @In Integer max();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    /**
     * Returns a reference to the service that provides information about a specific application.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service VmApplicationService application(String id);
}
