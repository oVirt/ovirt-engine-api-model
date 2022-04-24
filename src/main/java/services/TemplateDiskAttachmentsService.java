/*
Copyright (c) 2016 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package services;

import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.DiskAttachment;

/**
 * This service manages the set of disks attached to a template. Each attached disk is represented by a
 * xref:types/disk_attachment[DiskAttachment].
 *
 * @author Tal Nisan <tnisan@redhat.com>
 * @date 7 Jul 2016
 * @status added
 */
@Service
@Area("Storage")
public interface TemplateDiskAttachmentsService {

    /**
     * List the disks that are attached to the template.
     *
     * The order of the returned list of attachments isn't guaranteed.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 7 Jul 2016
     * @status added
     */
    interface List extends Follow {
        @Out DiskAttachment[] attachments();
    }

    /**
     * Reference to the service that manages a specific attachment.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 7 Jul 2016
     * @status added
     */
    @Service TemplateDiskAttachmentService attachment(String id);
}
