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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.DiskAttachment;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * This service manages the attachment of a disk to a virtual machine.
 */
@Service
public interface DiskAttachmentService {
    /**
     * Returns the details of the attachment, including the bootable flag and link to the disk.
     *
     * An example of getting a disk attachment:
     *
     * ```http
     * GET /ovirt-engine/api/vms/123/diskattachments/456
     * ```
     *
     * ```xml
     * <disk_attachment href="/ovirt-engine/api/vms/123/diskattachments/456" id="456">
     *   <active>true</active>
     *   <bootable>true</bootable>
     *   <interface>virtio</interface>
     *   <disk href="/ovirt-engine/api/disks/456" id="456"/>
     *   <vm href="/ovirt-engine/api/vms/123" id="123"/>
     * </disk_attachment>
     * ```
     *
     * @author Boris Odnopozov <bodnopoz@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        @Out DiskAttachment attachment();
    }

    /**
     * Removes the disk attachment.
     *
     * This will only detach the disk from the virtual machine, but won't remove it from
     * the system, unless the `detach_only` parameter is `false`.
     *
     * An example of removing a disk attachment:
     *
     * ```http
     * DELETE /ovirt-engine/api/vms/123/diskattachments/456?detach_only=true
     * ```
     *
     * @author Boris Odnopozov <bodnopoz@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the disk should only be detached from the virtual machine, but not removed from the system.
         * The default value is `true`, which won't remove the disk from the system.
         */
        @In Boolean detachOnly();
    }

    /**
     * Update the disk attachment and the disk properties within it.
     *
     * ```xml
     * PUT /vms/{vm:id}/disksattachments/{attachment:id}
     * <disk_attachment>
     *   <bootable>true</bootable>
     *   <interface>ide</interface>
     *   <active>true</active>
     *   <disk>
     *     <name>mydisk</name>
     *     <provisioned_size>1024</provisioned_size>
     *     ...
     *   </disk>
     * </disk_attachment>
     * ```
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(diskAttachment().disk().alias());
            optional(diskAttachment().bootable());
            optional(diskAttachment().description());
            optional(diskAttachment().disk().diskProfile().id());
            optional(diskAttachment().disk().format());
//            optional(diskAttachment()._interface()); //TODO: ucomment when able to handle '_'
            optional(diskAttachment().name());
            optional(diskAttachment().disk().propagateErrors());
            optional(diskAttachment().disk().provisionedSize());
            optional(diskAttachment().disk().quota().id());
            optional(diskAttachment().disk().readOnly());
            optional(diskAttachment().disk().sgio());
            optional(diskAttachment().disk().shareable());
//            optional(diskAttachment().size());
            optional(diskAttachment().disk().sparse());
            optional(diskAttachment().usesScsiReservation());
            optional(diskAttachment().disk().wipeAfterDelete());
        }
        @In @Out DiskAttachment diskAttachment();
    }
}
