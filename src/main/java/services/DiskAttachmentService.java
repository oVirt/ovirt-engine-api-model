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

package services;

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.DiskAttachment;

/**
 * This disk manages the attachment of a disk to a virtual machine.
 */
@Service
public interface DiskAttachmentService {
    /**
     * Returns the details of the attachment, including the bootable flag and link to the disk.
     */
    interface Get {
        @Out DiskAttachment attachment();
    }

    /**
     * Removes the disk attachment. This will only detach the disk from the virtual machine, but won't remove it from
     * the system, unless the `detach_only` parameter is `false`.
     */
    interface Remove {
        /**
         * Indicates if the disk should only be detached from the virtual machine, but not removed from the system.
         * The default value is `true`, which won't remove the disk from the system.
         */
        @In Boolean detachOnly();
    }
}
