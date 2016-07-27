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
     * This flag indicates if the disk is bootable.
     */
    Boolean bootable();

    /**
     * This flag indicates if the virtual machine passes discard commands to the storage.
     */
    // @Since("4.1")
    Boolean passDiscard();

    /**
     * This indicates the disk interface of the disk.
     */
    DiskInterface _interface();


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
