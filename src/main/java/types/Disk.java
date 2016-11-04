/*
Copyright (c) 2015-2016 Red Hat, Inc.

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
 * Represents a virtual disk device.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 4 Nov 2016
 * @status added
 */
@Type
public interface Disk extends Device {
    String alias();
    String imageId();

    /**
     * The virtual size of the disk, in bytes.
     *
     * This attribute is mandatory when creating a new disk.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    Integer provisionedSize();

    /**
     * The actual size of the disk, in bytes.
     *
     * The actual size is the number of bytes actually used by the disk, and it will be smaller than the provisioned
     * size for disks that use the `cow` format.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    Integer actualSize();

    /**
     * The status of the disk device.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    DiskStatus status();

    /**
     * The type of interface driver used to connect the disk device to the virtual machine.
     *
     * IMPORTANT: This attribute only makes sense for disks that are actually connected to virtual machines, and in
     * version 4 of the API it has been moved to the <<types/disk_attachment, DiskAttachment>> type. It is preserved
     * here only for backwards compatibility, and it will be removed in the future.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    @Deprecated
    DiskInterface _interface();

    /**
     * The underlying storage format.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    DiskFormat format();

    /**
     * Indicates if the physical storage for the disk should not be preallocated.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    Boolean sparse();

    /**
     * Indicates if the disk is marked as bootable.
     *
     * IMPORTANT: This attribute only makes sense for disks that are actually connected to virtual machines, and in
     * version 4 of the API it has been moved to the <<types/disk_attachment, DiskAttachment>> type. It is preserved
     * here only for backwards compatibility, and it will be removed in the future.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    @Deprecated
    Boolean bootable();

    /**
     * Indicates if the disk can be attached to multiple virtual machines.
     *
     * IMPORTANT: When a disk is attached to multiple virtual machines it is the responsibility of the guest
     * operating systems of those virtual machines to coordinate access to it, to avoid corruption of the data, for
     * example using a shared file system like https://www.gluster.org[GlusterFS] or
     * http://www.sourceware.org/cluster/gfs[GFS].
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    Boolean shareable();

    /**
     * Indicates if the disk's blocks will be read back as zeros after it is deleted:
     *
     * - On block storage, the disk will be zeroed and only then deleted.
     *
     * - On file storage, since the file system already guarantees that previously removed blocks are read back as
     * zeros, the disk will be deleted immediately.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 6 Nov 2016
     * @status added
     */
    Boolean wipeAfterDelete();

    /**
     * Indicates if disk errors should not cause virtual machine to be paused and, instead, disk errors should be
     * propagated to the the guest operating system.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    Boolean propagateErrors();

    Boolean active();

    /**
     * Indicates if the disk is in read-only mode.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    Boolean readOnly();

    HostStorage lunStorage();
    ScsiGenericIO sgio();
    Boolean usesScsiReservation();
    DiskStorageType storageType();
    String logicalName();

    @Link StorageDomain storageDomain();

    /**
     * The storage domains associated with this disk.
     *
     * NOTE: Only required when the first disk is being added to a virtual machine that was not itself created from a
     * template.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    @Link StorageDomain[] storageDomains();

    @Link Quota quota();
    @Link DiskProfile diskProfile();
    @Link Snapshot snapshot();
    @Link OpenStackVolumeType openstackVolumeType();
    @Link Permission[] permissions();
    @Link Statistic[] statistics();
}
