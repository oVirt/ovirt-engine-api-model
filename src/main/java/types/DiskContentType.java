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
    ISO,

    /**
     * The disk contains the Hosted Engine VM disk.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 17 Mar 2019
     * @status added
     * @since 4.3.3
     */
    HOSTED_ENGINE,

    /**
     * The disk contains the Hosted Engine Sanlock disk.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 17 Mar 2019
     * @status added
     * @since 4.3.3
     */
    HOSTED_ENGINE_SANLOCK,

    /**
     * The disk contains the Hosted Engine metadata disk.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 17 Mar 2019
     * @status added
     * @since 4.3.3
     */
    HOSTED_ENGINE_METADATA,

    /**
     * The disk contains the Hosted Engine configuration disk.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 17 Mar 2019
     * @status added
     * @since 4.3.3
     */
    HOSTED_ENGINE_CONFIGURATION,

    /**
     * The disk contains protected VM backup data.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 26 Apr 2021
     * @status added
     * @since 4.4.6
     */
    BACKUP_SCRATCH;
}
