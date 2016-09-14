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
    /**
     * Exports a template to the data center export domain.
     *
     * For example, the operation can be facilitated using the following request:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/templates/123/export
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <action>
     *   <storage_domain id="456"/>
     *   <exclusive>true<exclusive/>
     * </action>
     * ----
     *
     * @author Liron Aravot <laravot@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Export {
        /**
         * Indicates if the existing templates with the same name should be overwritten.
         *
         * The export action reports a failed action if a template of the same name exists in the destination domain.
         * Set this parameter to `true` to change this behavior and overwrite any existing template.
         *
         * @author Liron Aravot <laravot@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In Boolean exclussive();

        /**
         * Specifies the destination export storage domain.
         *
         * @author Liron Aravot <laravot@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
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

    /**
     * Removas a virtual machine template.
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/templates/123
     * ----
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
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
