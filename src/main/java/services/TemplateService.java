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

import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.StorageDomain;
import types.Template;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * Manages the virtual machine template and template versions.
 *
 * @author Tomas Jelinek <tjelinek@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
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
        @InputDetail
        default void inputDetail() {
            or(mandatory(storageDomain().id()), mandatory(storageDomain().name()));
            optional(exclusive());
        }
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
        @In Boolean exclusive();

        /**
         * Specifies the destination export storage domain.
         *
         * @author Liron Aravot <laravot@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In StorageDomain storageDomain();
    }

    /**
     * Returns the information about this template or template version.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        /**
         * The information about the template or template version.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
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
     * [source,xml]
     * ----
     * <template>
     *   <memory>1073741824</memory>
     * </template>
     * ----
     *
     * The `version_name` name attribute is the only one that can be updated within the `version` attribute used for
     * template versions:
     *
     * [source,xml]
     * ----
     * <template>
     *   <version>
     *     <version_name>mytemplate_2</version_name>
     *   </version>
     * </template>
     * ----
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(template().bios().bootMenu().enabled());
            optional(template().comment());
            optional(template().console().enabled());
            optional(template().cpu().architecture());
            optional(template().cpu().topology().cores());
            optional(template().cpu().topology().sockets());
            optional(template().cpuProfile().id());
            optional(template().cpuShares());
            optional(template().customCpuModel());
            optional(template().customEmulatedMachine());
            optional(template().deleteProtected());
            optional(template().description());
            optional(template().display().allowOverride());
            optional(template().display().disconnectAction());
            optional(template().display().keyboardLayout());
            optional(template().display().monitors());
            optional(template().display().smartcardEnabled());
            optional(template().display().type());
            optional(template().domain().name());
            optional(template().highAvailability().enabled());
            optional(template().highAvailability().priority());
            optional(template().initialization().configuration().data());
            optional(template().initialization().configuration().type());
            optional(template().io().threads());
            optional(template().largeIcon().data());
            optional(template().largeIcon().id());
            optional(template().largeIcon().mediaType());
            optional(template().memory());
            optional(template().memoryPolicy().ballooning());
            optional(template().memoryPolicy().guaranteed());
            optional(template().migration().autoConverge());
            optional(template().migration().compressed());
            optional(template().migrationDowntime());
            optional(template().name());
            optional(template().origin());
            optional(template().os().cmdline());
            optional(template().os().initrd());
            optional(template().os().kernel());
            optional(template().os().type());
            optional(template().vm().placementPolicy().affinity()); //TODO: check.
            optional(template().serialNumber().policy());
            optional(template().serialNumber().value());
            optional(template().smallIcon().id());
            optional(template().soundcardEnabled());
            optional(template().startPaused());
            optional(template().stateless());
            optional(template().timeZone().name());
            optional(template().tunnelMigration());
            optional(template().type());
            optional(template().usb().enabled());
            optional(template().usb().type());
            optional(template().version().versionName());
            optional(template().virtioScsi().enabled());
            optional(template().display().copyPasteEnabled());
            optional(template().display().fileTransferEnabled());
            optional(template().display().singleQxlPci());
            optional(template().rngDevice().rate().bytes());
            optional(template().rngDevice().rate().period());
            optional(template().rngDevice().source());
            optional(template().customProperties()[COLLECTION].name());
            optional(template().customProperties()[COLLECTION].value());
            optional(template().os().boot().devices()[COLLECTION]);
            optional(template().sso().methods()[COLLECTION].id());
            optional(template().storageErrorResumeBehaviour());
        }
        @In @Out Template template();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes a virtual machine template.
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

    /**
     * Returns a reference to the service that manages the permissions that are associated with the template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service AssignedPermissionsService permissions();

    /**
     * Returns a reference to the service that manages the tags that are associated with the template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service AssignedTagsService tags();

    /**
     * Returns a reference to the service that manages the graphical consoles that are associated with the template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service TemplateGraphicsConsolesService graphicsConsoles();

    /**
     * Returns a reference to the service that manages the CDROMs that are associated with the template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service TemplateCdromsService cdroms();

    /**
     * Returns a reference to the service that manages the NICs that are associated with the template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service TemplateNicsService nics();

    /**
     * Returns a reference to the service that manages the _watchdogs_ that are associated with the template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
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
