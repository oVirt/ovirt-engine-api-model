/*
Copyright (c) 2018 Red Hat, Inc.

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
 * The actual content residing on the disk.
 *
 * @author Tal Nisan <tnisan@redhat.com>
 * @date 27 Dec 2017
 * @status added
 */
@Type
public enum DiskContentType {
    /**
     * The disk contains data.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 27 Dec 2017
     * @status added
     * @since 4.2.1
     */
    DATA,

    /**
     * The disk is an OVF store.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 27 Dec 2017
     * @status added
     * @since 4.2.1
     */
    OVF_STORE,

    /**
     * The disk contains a memory dump from a live snapshot.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 27 Dec 2017
     * @status added
     * @since 4.2.1
     */
    MEMORY_DUMP_VOLUME,

    /**
     * The disk contains memory metadata from a live snapshot.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 27 Dec 2017
     * @status added
     * @since 4.2.1
     */
    MEMORY_METADATA_VOLUME,

    /**
     * The disk contains an ISO image to be used a CDROM device.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 27 Dec 2017
     * @status added
     * @since 4.2.1
     */
    ISO;
}
