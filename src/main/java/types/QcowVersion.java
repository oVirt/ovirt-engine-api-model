/*
Copyright (c) 2016 Red Hat, Inc.

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
 * The QCOW version specifies to the qemu which qemu version the volume supports.
 *
 * This field can be updated using the update API and will be reported only for QCOW volumes,
 * it is determined by the storage domain's version which the disk is created on.
 * Storage domains with version lower than V4 support QCOW2 version 2 volumes,
 * while V4 storage domains also support QCOW2 version 3.
 * For more information about features of the different QCOW versions, see http://wiki.qemu.org/Features/Qcow3[here].
 *
 * @author Maor Lipchuk <mlipchuk@redhat.com>
 * @date 21 Nov 2016
 * @status added
 * @since 4.1
 */
@Type
public enum QcowVersion {

    /**
     * The _Copy On Write_ default compatibility version
     * It means that every QEMU can use it.
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @date 21 Nov 2016
     * @status added
     * @since 4.1
     */
    QCOW2_V2,

    /**
     * The _Copy On Write_ compatibility version which was introduced in QEMU 1.1
     * It means that the new format is in use.
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @date 21 Nov 2016
     * @status added
     * @since 4.1
     */
    QCOW2_V3,
}
