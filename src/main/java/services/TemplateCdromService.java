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
import types.Cdrom;

/**
 * A service managing a CD-ROM device on templates.
 *
 * @author Milan Zamazal <mzamazal@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface TemplateCdromService {
    /**
     * Returns the information about this CD-ROM device.
     *
     * For example, to get information about the CD-ROM device of template `123` send a request like:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/templates/123/cdroms/
     * ----
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        /**
         * The information about the CD-ROM device.
         *
         * The information consists of `cdrom` attribute containing reference to the CD-ROM device, the template,
         * and optionally the inserted disk.
         *
         * If there is a disk inserted then the `file` attribute will contain a reference to the ISO image:
         *
         * [source,xml]
         * ----
         * <cdrom href="..." id="00000000-0000-0000-0000-000000000000">
         *   <template href="/ovirt-engine/api/templates/123" id="123"/>
         *   <file id="mycd.iso"/>
         * </cdrom>
         * ----
         *
         * If there is no disk inserted then the `file` attribute won't be reported:
         *
         * [source,xml]
         * ----
         * <cdrom href="..." id="00000000-0000-0000-0000-000000000000">
         *   <template href="/ovirt-engine/api/templates/123" id="123"/>
         * </cdrom>
         * ----
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Cdrom cdrom();
    }
}
