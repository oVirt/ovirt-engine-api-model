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
import types.Cdrom;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * Manages a CDROM device of a virtual machine.
 *
 * Changing and ejecting the disk is done using always the `update` method, to change the value of the `file`
 * attribute.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 25 Aug 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface VmCdromService {
    /**
     * Returns the information about this CDROM device.
     *
     * The information consists of `cdrom` attribute containing reference to the CDROM device, the virtual machine,
     * and optionally the inserted disk.
     *
     * If there is a disk inserted then the `file` attribute will contain a reference to the ISO image:
     *
     * [source,xml]
     * ----
     * <cdrom href="..." id="00000000-0000-0000-0000-000000000000">
     *   <file id="mycd.iso"/>
     *   <vm href="/ovirt-engine/api/vms/123" id="123"/>
     * </cdrom>
     * ----
     *
     * If there is no disk inserted then the `file` attribute won't be reported:
     *
     * [source,xml]
     * ----
     * <cdrom href="..." id="00000000-0000-0000-0000-000000000000">
     *   <vm href="/ovirt-engine/api/vms/123" id="123"/>
     * </cdrom>
     * ----
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Get extends Follow {
        /**
         * The information about the CDROM device.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 25 Aug 2016
         * @status added
         */
        @Out Cdrom cdrom();

        /**
         * Indicates if the operation should return the information for the currently running virtual machine. This
         * parameter is optional, and the default value is `false`.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 25 Aug 2016
         * @status added
         */
        @In Boolean current();
    }

    /**
     * Updates the information about this CDROM device.
     *
     * It allows to change or eject the disk by changing the value of the `file` attribute.
     * For example, to insert or change the disk send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/vms/123/cdroms/00000000-0000-0000-0000-000000000000
     * ----
     *
     * The body should contain the new value for the `file` attribute:
     *
     * [source,xml]
     * ----
     * <cdrom>
     *   <file id="mycd.iso"/>
     * </cdrom>
     * ----
     *
     * The value of the `id` attribute, `mycd.iso` in this example, should correspond to a file available in an
     * attached ISO storage domain.
     *
     * To eject the disk use a `file` with an empty `id`:
     *
     * [source,xml]
     * ----
     * <cdrom>
     *   <file id=""/>
     * </cdrom>
     * ----
     *
     * By default the above operations change permanently the disk that will be visible to the virtual machine
     * after the next boot, but they do not have any effect on the currently running virtual machine. If you want
     * to change the disk that is visible to the current running virtual machine, add the `current=true` parameter.
     * For example, to eject the current disk send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/vms/123/cdroms/00000000-0000-0000-0000-000000000000?current=true
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <cdrom>
     *   <file id=""/>
     * </cdrom>
     * ----
     *
     * IMPORTANT: The changes made with the `current=true` parameter are never persisted, so they won't have any
     * effect after the virtual machine is rebooted.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(cdrom().file().id());
        }
        /**
         * The information about the CDROM device.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 25 Aug 2016
         * @status added
         */
        @In @Out Cdrom cdrom();

        /**
         * Indicates if the update should apply to the currently running virtual machine, or to the virtual machine
         * after the next boot. This parameter is optional, and the default value is `false`, which means that by
         * default the update will have effect only after the next boot.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 25 Aug 2016
         * @status added
         */
        @In Boolean current();
    }
}
