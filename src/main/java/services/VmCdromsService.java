/*
Copyright (c) 2015-2016 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

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
import types.Cdrom;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
/**
 * Manages the CDROM devices of a virtual machine.
 *
 * Currently virtual machines have exactly one CDROM device. No new devices can be added, and the existing one can't
 * be removed, thus there are no `add` or `remove` methods. Changing and ejecting CDROM disks is done with the
 * xref:services/vm_cdrom/methods/update[update] method of the xref:services/vm_cdrom[service] that manages the
 * CDROM device.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 25 Aug 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface VmCdromsService {

    /**
     * Add a cdrom to a virtual machine identified by the given id.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Add{

        /**
         * Add a cdrom to a virtual machine identified by the given id and attach a file to the cdrom.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        @InputDetail
        default void inputDetail() {
            mandatory(cdrom().file().id());
        }
        @In @Out Cdrom cdrom();
    }
    /**
     * Returns the list of CDROM devices of the virtual machine.
     *
     * The order of the returned list of CD-ROM devices isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 25 Aug 2016
     * @status added
     */
    interface List extends Follow {
        /**
         * The list of CDROM devices of the virtual machine.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 25 Aug 2016
         * @status added
         */
        @Out Cdrom[] cdroms();

        /**
         * Sets the maximum number of CDROMs to return. If not specified all the CDROMs are returned.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 25 Aug 2016
         * @status added
         */
        @In Integer max();
    }

    /**
     * Returns a reference to the service that manages a specific CDROM device.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 25 Aug 2016
     * @status added
     */
    @Service VmCdromService cdrom(String id);
}
