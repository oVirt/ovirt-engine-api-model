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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.NetworkAttachment;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

@Service
@Area("Network")
public interface NetworkAttachmentsService {
    /**
     * Add a new network attachment to the network interface.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            or(mandatory(attachment().network().name()), mandatory(attachment().network().id()));
            optional(attachment().properties()[COLLECTION].name());
            optional(attachment().properties()[COLLECTION].value());
            optional(attachment().ipAddressAssignments()[COLLECTION].assignmentMethod());
            optional(attachment().ipAddressAssignments()[COLLECTION].ip().address());
            optional(attachment().ipAddressAssignments()[COLLECTION].ip().gateway());
            optional(attachment().ipAddressAssignments()[COLLECTION].ip().netmask());
            optional(attachment().ipAddressAssignments()[COLLECTION].ip().version());
        }
        @In @Out NetworkAttachment attachment();
    }

    interface List {
        @Out NetworkAttachment[] attachments();

        /**
         * Sets the maximum number of attachments to return. If not specified all the attachments are returned.
         */
        @In Integer max();
    }

    @Service NetworkAttachmentService attachment(String id);
}
