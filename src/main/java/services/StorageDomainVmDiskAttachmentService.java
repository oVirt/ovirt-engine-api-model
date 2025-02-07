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

import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.DiskAttachment;

/**
 * Returns the details of the disks attached to a virtual machine in the export domain.
 *
 * @author Tal Nisan <tnisan@redhat.com>
 * @date 6 Sep 2016
 * @status added
 * @since 4.0.4
 */
@Service
@Area("Storage")
public interface StorageDomainVmDiskAttachmentService {

    /**
     * Returns the details of the attachment with all its properties and a link to the disk.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 6 Sep 2016
     * @status added
     * @since 4.0.4
     */
    interface Get extends Follow {
        /**
         * The disk attachment.
         *
         * @author Tal Nisan <tnisan@redhat.com>
         * @date 6 Sep 2016
         * @status added
         * @since 4.0.4
         */
        @Out DiskAttachment attachment();
    }
}
