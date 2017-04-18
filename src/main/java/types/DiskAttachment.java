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
 *
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 2 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface DiskAttachment extends Identified {
    /**
     * Defines whether the disk is bootable.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 2 Nov 2016
     * @status updated_by_docs
     */
    Boolean bootable();

    /**
     * Defines whether the virtual machine passes discard commands to the storage.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 2 Nov 2016
     * @status updated_by_docs
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
     * Defines whether SCSI reservation is enabled for this disk.
     *
     * Virtual machines with VIRTIO-SCSI passthrough enabled can set persistent SCSI reservations on disks. If they set
     * persistent SCSI reservations, those virtual machines cannot be migrated to a different host because they would
     * lose access to the disk, because SCSI reservations are specific to SCSI initiators, and therefore hosts. This
     * scenario cannot be automatically detected. To avoid migrating these virtual machines, the user can set this
     * attribute to `true`, to indicate the virtual machine is using SCSI reservations.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 3 Nov 2016
     * @status updated_by_docs
     * @since 4.1
     */
    Boolean usesScsiReservation();

    /**
     * Defines whether the disk is active in the virtual machine it's attached to.
     *
     * A disk attached to a virtual machine in an active status is connected to the virtual machine at run time and
     * can be used.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 3 Nov 2016
     * @status updated_by_docs
     */
    Boolean active();

    /**
     * The logical name of the virtual machine's disk, as seen from inside the virtual machine.
     *
     * The logical name of a disk is reported only when the guest agent is installed and running inside the virtual
     * machine.
     *
     * For example, if the guest operating system is Linux and the disk is connected via a VirtIO interface, the
     * logical name will be reported as `/dev/vda`:
     *
     * [source,xml]
     * ----
     * <disk_attachment>
     *   ...
     *   <logical_name>/dev/vda</logical_name>
     * </disk_attachment>
     * ----
     *
     * If the guest operating system is Windows, the logical name will be reported as `\\.\PHYSICALDRIVE0`.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 3 Nov 2016
     * @status updated_by_docs
     * @since 4.0.2
     */
    String logicalName();

    /**
     * Indicates whether the disk is connected to the virtual machine as read only.
     *
     * When adding a new disk attachment the default value is `false`.
     *
     * [source,xml]
     * ----
     * <disk_attachment>
     *   ...
     *   <read_only>true</read_only>
     * </disk_attachment>
     * ----
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 9 Apr 2017
     * @status added
     * @since 4.1.2
     */
    Boolean readOnly();

    /**
     * The reference to the disk.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 3 Nov 2016
     * @status updated_by_docs
     */
    @Link Disk disk();

    /**
     * The reference to the virtual machine.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 3 Nov 2016
     * @status updated_by_docs
     */
    @Link Vm vm();

    /**
     * The reference to the template.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 3 Nov 2016
     * @status updated_by_docs
     */
    @Link Template template();
}
