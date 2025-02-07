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
import types.Cdrom;

/**
 * Lists the CD-ROM devices of a template.
 *
 * @author Milan Zamazal <mzamazal@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface TemplateCdromsService {
    /**
     * Returns the list of CD-ROM devices of the template.
     *
     * The order of the returned list of CD-ROM devices isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        /**
         * The list of CD-ROM devices of the template.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Cdrom[] cdroms();

        /**
         * Sets the maximum number of CD-ROMs to return. If not specified all the CD-ROMs are returned.
         */
        @In Integer max();
    }

    /**
     * Returns a reference to the service that manages a specific CD-ROM device.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service TemplateCdromService cdrom(String id);
}
