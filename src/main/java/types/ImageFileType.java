/*
Copyright (c) 2017 Red Hat, Inc.

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
 * Represents file types of an image.
 *
 * @author Shani Leviim <sleviim@redhat.com>
 * @date 14 Aug 2017
 * @status added
 * @since 4.2.0
 */
@Type
public enum ImageFileType {
    /**
     * The image is a `.iso` file that can be used as a CD-ROM
     * to boot and install a virtual machine.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @date 14 Aug 2017
     * @status added
     * @since 4.2.0
     */
    ISO,

    /**
     * The image is a floppy disk that can be attached to a virtual machine,
     * for example to install the VirtIO drivers in Windows.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @date 14 Aug 2017
     * @status added
     * @since 4.2.0
     */
    FLOPPY,

    /**
     * The image is a disk format that can be used as a virtual machine's disk.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @date 14 Aug 2017
     * @status added
     * @since 4.2.0
     */
    DISK;
}
