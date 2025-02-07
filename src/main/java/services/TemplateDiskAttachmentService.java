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
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.DiskAttachment;

/**
 * This service manages the attachment of a disk to a template.
 *
 * @author Tal Nisan <tnisan@redhat.com>
 * @date 7 Jul 2016
 * @status added
 */
@Service
@Area("Storage")
public interface TemplateDiskAttachmentService {

    /**
     * Returns the details of the attachment.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 7 Jul 2016
     * @status added
     */
    interface Get extends Follow {
        @Out DiskAttachment attachment();
    }

    /**
     * Removes the disk from the template. The disk will only be removed if there are other existing copies of the
     * disk on other storage domains.
     *
     * A storage domain has to be specified to determine which of the copies should be removed (template disks can
     * have copies on multiple storage domains).
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/templates/{template:id}/diskattachments/{attachment:id}?storage_domain=072fbaa1-08f3-4a40-9f34-a5ca22dd1d74
     * ----
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 7 Jul 2016
     * @status added
     */
    interface Remove {

        /**
         * Specifies the identifier of the storage domain the image to be removed resides on.
         *
         * @author Tal Nisan <tnisan@redhat.com>
         * @date 7 Jul 2016
         * @status added
         */
        @In String storageDomain();

        @In Boolean force();
    }
}
