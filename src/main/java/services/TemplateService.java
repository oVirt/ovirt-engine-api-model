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

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.StorageDomain;
import types.Template;

@Service
@Area("Virtualization")
public interface TemplateService {
    interface Export {
        @In Boolean exclussive();
        @In StorageDomain storageDomain();
    }

    interface Get {
        @Out Template template();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    /**
     * Updates the template.
     *
     * The `name`, `description`, `type`, `memory`, `cpu`, `topology`, `os`, `high_availability`, `display`,
     * `stateless`, `usb` and `timezone` elements can be updated after a template has been created.
     *
     * For example, to update a template to so that it has 1 GiB of memory send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/templates/123
     * ----
     *
     * With the following request body:
     *
     * [source, xml]
     * ----
     * <template>
     *   <memory>1073741824</memory>
     * </template>
     * ----
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @In @Out Template template();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service AssignedPermissionsService permissions();
    @Service AssignedTagsService tags();
    @Service GraphicsConsolesService graphicsConsoles();
    @Service TemplateCdromsService cdroms();
    @Service TemplateNicsService nics();
    @Service TemplateWatchdogsService watchdogs();

    /**
     * Reference to the service that manages a specific
     * disk attachment of the template.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 7 Jul 2016
     * @status added
     */
    @Service TemplateDiskAttachmentsService diskAttachments();
}
