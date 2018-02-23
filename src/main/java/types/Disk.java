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
 * @author Megan Lewis <melewis@redhat.com>
 * @date 26 Apr 2017
 * @status updated_by_docs
 */
@Type
public interface Disk extends Device {

    /**
     * A human-readable name in plain text.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 02 Mar 2018
     * @status updated_by_docs
     */
    String alias();
    String imageId();

    /**
     * The virtual size of the disk, in bytes.
     *
     * This attribute is mandatory when creating a new disk.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
     */
    Integer provisionedSize();

    /**
     * The initial size of a sparse image disk created on block storage, in bytes.
     *
     * The initial size is the number of bytes a sparse disk is initially allocated with when created on block storage.
     * The initial size will be smaller than the provisioned size. If not specified the default initial size used by the
     * system will be allocated.
     *
     * @author Liron Aravot <laravot@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
     * @since 4.0.7
     */
    Integer initialSize();

    /**
     * The actual size of the disk, in bytes.
     *
     * The actual size is the number of bytes actually used by the disk. It will be smaller than the provisioned
     * size for disks that use the `cow` format.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
     */
    Integer actualSize();

    /**
     * The total size of the disk including all of its snapshots, in bytes.
     *
     * The total size is the number of bytes actually used by the disk plus the size of its snapshots.
     * It will not be populated for direct LUN and Cinder disks.
     * For disks without snapshots the total size is equal to the actual size.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 02 Mar 2018
     * @status updated_by_docs
     * @since 4.2.0
     */
    Integer totalSize();

    /**
     * The status of the disk device.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
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
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
     */
    @Deprecated
    DiskInterface _interface();

    /**
     * The underlying storage format.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
     */
    DiskFormat format();

    /**
     * The underlying QCOW version of a QCOW volume.
     * The QCOW version specifies to the qemu which qemu version the volume supports.
     * This field can be updated using the update API and will be reported only for QCOW volumes.
     * It is determined by the version of the storage domain that the disk is created on.
     * Storage domains with a version lower than V4 support QCOW2 volumes. V4 storage domains also support QCOW2v3.
     * For more information about features of the different QCOW versions, see http://wiki.qemu.org/Features/Qcow3[here].
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
     * @since 4.1
     */
    QcowVersion qcowVersion();

    /**
     * Indicates if the physical storage for the disk should not be preallocated.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
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
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
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
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
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
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
     */
    Boolean wipeAfterDelete();

    /**
     * Indicates if disk errors should cause virtual machine to be paused or if disk errors should be
     * propagated to the the guest operating system instead.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
     */
    Boolean propagateErrors();

    /**
     * Indicates if the disk is visible to the virtual machine.
     *
     * IMPORTANT: When adding a disk attachment to a virtual machine, if the server accepts requests that do not contain
     * this attribute the result is undefined. In some cases the disk will be automatically activated
     * and in other cases it will not. To avoid issues it is strongly recommended to always include the this attribute
     * with the desired value.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
     */
    Boolean active();

    /**
     * Indicates if the disk is in read-only mode.
     *
     * Since version 4.0 this attribute is not shown in the API and was moved to <<types/disk_attachment,DiskAttachment>>.
     *
     * Since version 4.1.2 of {engine-name} this attribute is deprecated, and it will be removed in the future.
     * In order to attach a disk in read only mode use the `read_only` attribute of the <<types/disk_attachment,
     * DiskAttachment>> type. For example:
     *
     * ....
     * POST /ovirt-engine/api/vms/123/diskattachments
     * ....
     *
     * [source,xml]
     * ----
     * <disk_attachment>
     *   <read_only>true</read_only>
     *   ...
     * </disk_attachment>
     * ----
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
     */
    @Deprecated
    Boolean readOnly();

    HostStorage lunStorage();
    ScsiGenericIO sgio();
    Boolean usesScsiReservation();
    DiskStorageType storageType();
    String logicalName();

    /**
     * Indicates the actual content residing on the disk.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 02 Mar 2018
     * @status updated_by_docs
     * @since 4.2.1
     */
    DiskContentType contentType();

    /**
     * A human-readable name in plain text.
     *
     * Deprecated in favor of the `alias` attribute. It always returns the same value as `alias`.
     * If both `alias` and this property are set during a write operation, the value of `alias` is used.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 02 Mar 2018
     * @status updated_by_docs
     */
    @Override
    String name();

    @Link StorageDomain storageDomain();

    /**
     * The storage domains associated with this disk.
     *
     * NOTE: Only required when the first disk is being added to a virtual machine that was not itself created from a
     * template.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
     */
    @Link StorageDomain[] storageDomains();

    @Link Quota quota();
    @Link DiskProfile diskProfile();
    @Link Snapshot snapshot();
    @Link OpenStackVolumeType openstackVolumeType();
    @Link Permission[] permissions();

    /**
     * Statistics exposed by the disk. For example:
     *
     * [source,xml]
     * ----
     * <statistics>
     *   <statistic href="/ovirt-engine/api/disks/123/statistics/456" id="456">
     *     <name>data.current.read</name>
     *     <description>Read data rate</description>
     *     <kind>gauge</kind>
     *     <type>decimal</type>
     *     <unit>bytes_per_second</unit>
     *     <values>
     *       <value>
     *         <datum>1052</datum>
     *       </value>
     *     </values>
     *     <disk href="/ovirt-engine/api/disks/123" id="123"/>
     *   </statistic>
     *   ...
     * </statistics>
     * ----
     *
     * These statistics are not directly included when the disk is retrieved, only a link. To obtain the statistics
     * follow the included link:
     *
     * ....
     * GET /ovirt-engine/api/disks/123/statistics
     * ....
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 26 Apr 2017
     * @status updated_by_docs
     */
    @Link Statistic[] statistics();
}
