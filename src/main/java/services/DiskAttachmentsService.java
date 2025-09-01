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

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;


/**
 * This service manages the set of disks attached to a virtual machine. Each attached disk is represented by a
 * xref:types/disk_attachment[DiskAttachment], containing the bootable flag, the disk interface and the reference to
 * the disk.
 */
@Service
public interface DiskAttachmentsService {
    /**
     * Adds a new disk attachment to the virtual machine. The `attachment` parameter can contain just a reference, if
     * the disk already exists:
     *
     * ```xml
     * <disk_attachment>
     *   <bootable>true</bootable>
     *   <pass_discard>true</pass_discard>
     *   <interface>ide</interface>
     *   <active>true</active>
     *   <disk id="123"/>
     * </disk_attachment>
     * ```
     *
     * Or it can contain the complete representation of the disk, if the disk doesn't exist yet:
     *
     * ```xml
     * <disk_attachment>
     *   <bootable>true</bootable>
     *   <pass_discard>true</pass_discard>
     *   <interface>ide</interface>
     *   <active>true</active>
     *   <disk>
     *     <name>mydisk</name>
     *     <provisioned_size>1024</provisioned_size>
     *     ...
     *   </disk>
     * </disk_attachment>
     * ```
     *
     * In this case the disk will be created and then attached to the virtual machine.
     *
     * In both cases, use the following URL for a virtual machine with an id `345`:
     *
     * ```http
     * POST /ovirt-engine/api/vms/345/diskattachments HTTP/1.1
     * ```
     *
     * IMPORTANT: The server accepts requests that do not contain the `active` attribute, but the effect is
     * undefined. In some cases the disk will be automatically activated and in other cases it won't. To
     * avoid issues it is strongly recommended to always include the `active` attribute with the desired
     * value.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 15 Sep 2016
     * @status added
     */
    interface Add {
        interface Signature1 extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(attachment().bootable());
                mandatory(attachment()._interface());
                mandatory(attachment().passDiscard());
            }
        }

        interface ProvidingDiskId extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(attachment().disk().id());
            }
        }

        /**
         * The disk attachment to add to the virtual machine.
         *
         * @author Idan Shaby <ishaby@redhat.com>
         * @author Billy Burmester <bburmest@redhat.com>
         * @date 11 May 2018
         * @status updated_by_docs
         */
        @In @Out DiskAttachment attachment();
    }

    /**
     * List the disk that are attached to the virtual machine.
     *
     * The order of the returned list of disks attachments isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Billy Burmester <bburmest@redhat.com>
     * @date 11 May 2018
     * @status updated_by_docs
     */
    interface List extends Follow {

        /**
         * A list of disk attachments that are attached to the virtual machine.
         *
         * @author Idan Shaby <ishaby@redhat.com>
         * @author Billy Burmester <bburmest@redhat.com>
         * @date 11 May 2018
         * @status updated_by_docs
         */
        @Out DiskAttachment[] attachments();
    }

    /**
     * Reference to the service that manages a specific attachment.
     */
    @Service DiskAttachmentService attachment(String id);
}
