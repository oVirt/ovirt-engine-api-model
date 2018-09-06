/*
Copyright (c) 2015 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * The underlying storage interface of disks communication with controller.
 *
 * @author Shahar Havivi <shavivi@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public enum DiskInterface {
    /**
     * Legacy controller device. Works with almost all guest operating systems, so it is good for compatibility.
     * Performance is lower than with the other alternatives.
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    IDE,

    /**
     * Para-virtualized SCSI controller device. Fast interface with the guest via direct physical storage device
     * address, using the SCSI protocol.
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    VIRTIO_SCSI,

    /**
     * Virtualization interface where just the guest's device driver knows it is running in a virtual environment.
     * Enables guests to get high performance disk operations.
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    VIRTIO,

    /**
     * Para-virtualized device supported by the IBM pSeries family of machines, using the SCSI protocol.
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    SPAPR_VSCSI,

    /**
     * SATA controller device.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 12 Sep 2018
     * @status updated_by_docs
     * @since 4.3
     */
    SATA;
}
