/*
Copyright (c) 2016   Red Hat, Inc.

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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Describes how a disk is attached to a virtual machine.
 */
@Type
public interface DiskAttachment extends Identified {
    /**
     * Indicates if the disk is marked as bootable.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    Boolean bootable();

    /**
     * This flag indicates if the virtual machine passes discard commands to the storage.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 23 Jun 2016
     * @status added
     * @since 4.1
     */
    Boolean passDiscard();

    /**
     * The type of interface driver used to connect the disk device to the virtual machine.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    DiskInterface _interface();

    /**
     * This flag indicates if SCSI reservation is enabled for this disk.
     * Virtual machines that have VIRTIO-SCSI passthrough enabled can set persistent SCSI reservations on disks.
     * If they do then they can't be migrated to a different host because they would then loss access to the disk,
     * as SCSI reservations are specific to SCSI initiators, and thus hosts.
     * The system can't detect this situation automatically, so to avoid these unwanted migrations the user can tell
     * the system that the virtual machine is using SCSI reservations setting this attribute to `true`.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 19 Oct 2016
     * @status added
     * @since 4.1
     */
    Boolean usesScsiReservation();

    /**
     * This flag indicates if the disk is active in the virtual machine it's attached to.
     *
     * A disk attached to a virtual machine in an active status is connected to the virtual machine at run time and
     * can be used.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 7 Jul 2017
     * @status added
     */
    Boolean active();

    /**
     * The logical name of the virtual machine's disk, i.e the
     * name of the disk as seen from inside the virtual machine.
     * Note that the logical name of a disk is reported only
     * when the guest agent is installed and running inside the
     * virtual machine.
     *
     * For example, if the guest operating system is Linux and
     * the disk is connected via a VirtIO interface, the logical
     * name will be reported as `/dev/vda`:
     *
     * [source,xml]
     * ----
     * <disk_attachment>
     *   ...
     *   <logical_name>/dev/vda</logical_name>
     * </disk_attachment>
     * ----
     *
     * If the guest operating system is Windows, the logical
     * name will be reported as `\\.\PHYSICALDRIVE0`.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.2
     */
    String logicalName();

    /**
     * Reference to the disk.
     */
    @Link Disk disk();

    /**
     * Reference to the virtual machine.
     */
    @Link Vm vm();

    /**
     * Reference to the template.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 7 Jul 2017
     * @status added
     */
    @Link Template template();
}
