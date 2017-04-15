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

package services;

import annotations.Area;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.NetworkAttachment;

/**
 * Manages the set of network attachments of a host or host NIC.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 15 Apr 2017
 * @status added
 */
@Service
@Area("Network")
public interface NetworkAttachmentsService {
    interface Add {
        @In @Out NetworkAttachment attachment();
    }

    /**
     * Returns the list of network attachments of the host or host NIC.
     *
     * The order of the returned list of network attachments isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List {
        @Out NetworkAttachment[] attachments();

        /**
         * Sets the maximum number of attachments to return. If not specified all the attachments are returned.
         */
        @In Integer max();
    }

    @Service NetworkAttachmentService attachment(String id);
}
