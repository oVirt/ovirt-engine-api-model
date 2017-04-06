/*
Copyright (c) 2015-2017 Red Hat, Inc.

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
import types.Template;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * This service manages the virtual machine templates available in the system.
 *
 * @author Tomas Jelinek <tjelinek@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface TemplatesService {
    /**
     * Creates a new template.
     *
     * This requires the `name` and `vm` elements. To identify the virtual machine use the `vm.id` or `vm.name`
     * attributes. For example, to create a template from virtual machine with identifier `123` send a request
     * like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/templates
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <template>
     *   <name>mytemplate</name>
     *   <vm id="123"/>
     * </template>
     * ----
     *
     * The disks of the template can be customized, making some of their characteristics different to the disks of the
     * original virtual machine. To do so use the `vm.disk_attachments` attribute, specifying the identifier of the disk
     * of the original virtual machine and the characteristics that you want to change. For example, if the original
     * virtual machine has a disk with identifier `456`, and, for that disk, you want to change the format to
     * <<types/disk_format, _Copy On Write_>> and make the disk <<types/disk, sparse>>, send a request body
     * like this:
     *
     * [source,xml]
     * ----
     * <template>
     *   <name>mytemplate</name>
     *   <vm id="123">
     *     <disk_attachments>
     *       <disk_attachment>
     *         <disk id="456">
     *           <format>cow</format>
     *           <sparse>true</sparse>
     *         </disk>
     *       </disk_attachment>
     *     </disk_attachments>
     *   </vm>
     * </template>
     * ----
     *
     * The template can be created as a sub version of an existing template.This requires the `name` and `vm` attributes
     * for the new template, and the `base_template` and `version_name` attributes for the new template version. The
     * `base_template` and `version_name` attributes must be specified within a `version` section enclosed in the
     * `template` section. Identify the virtual machine with the `id` or `name` attributes.
     *
     * [source,xml]
     * ----
     * <template>
     *   <name>mytemplate</name>
     *   <vm id="123"/>
     *   <version>
     *     <base_template id="456"/>
     *     <version_name>mytemplate_001</version_name>
     *   </version>
     * </template>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 6 Apr 2017
     * @status added
     */
    interface Add {
        /**
         * The information about the template or template version.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Template template();

        /**
         * Specifies if the permissions of the virtual machine should be copied to the template.
         *
         * If this optional parameter is provided, and its values is `true` then the permissions of the virtual machine
         * (only the direct ones, not the inherited ones) will be copied to the created template. For example, to create
         * a template from the `myvm` virtual machine copying its permissions, send a request like this:
         *
         * [source]
         * ----
         * POST /ovirt-engine/api/templates?clone_permissions=true
         * ----
         *
         * With a request body like this:
         *
         * [source,xml]
         * ----
         * <template>
         *   <name>mytemplate<name>
         *   <vm>
         *     <name>myvm<name>
         *   </vm>
         * </template>
         * ----
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 16 Aug 2016
         * @status added
         * @since 4.0.0
         */
        @In Boolean clonePermissions();

        /**
         * Seal the template.
         *
         * If this optional parameter is provided and its value is `true`,
         * then the template is sealed after creation.
         *
         * Sealing erases all host-specific configuration from the filesystem:
         * SSH keys, UDEV rules, MAC addresses, system ID, hostname etc.,
         * thus making easy to use the template to create multiple virtual
         * machines without manual intervention.
         *
         * Currently sealing is supported only for Linux OS.
         *
         * @author Shmuel Melamud <smelamud@redhat.com>
         * @date 7 Mar 2017
         * @status added
         * @since 4.1.2
         */
        @In Boolean seal();

        @InputDetail
        default void inputDetail() {
            mandatory(template().name());
            or(mandatory(template().vm().id()), mandatory(template().vm().name()));
            optional(template().bios().bootMenu().enabled());
            optional(template().cluster().id());
            optional(template().cluster().name());
            optional(template().comment());
            optional(template().console().enabled());
            optional(template().cpu().architecture());
            optional(template().cpu().topology().cores());
            optional(template().cpu().topology().sockets());
            optional(template().cpu().topology().threads());
            optional(template().cpuProfile().id());
            optional(template().cpuShares());
            optional(template().customCpuModel());
            optional(template().customEmulatedMachine());
            optional(template().deleteProtected());
            optional(template().description());
            optional(template().display().allowOverride());
            optional(template().display().copyPasteEnabled());
            optional(template().display().disconnectAction());
            optional(template().display().fileTransferEnabled());
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
            optional(template().origin());
            optional(template().os().cmdline());
            optional(template().os().initrd());
            optional(template().os().kernel());
            optional(template().os().type());
            optional(template().vm().placementPolicy().affinity()); //TODO: check
            optional(template().serialNumber().policy());
            optional(template().serialNumber().value());
            optional(template().smallIcon().id());
            optional(template().soundcardEnabled());
            optional(template().startPaused());
            optional(template().stateless());
            optional(template().storageDomain().id());
            optional(template().timeZone().name());
            optional(template().tunnelMigration());
            optional(template().type());
            optional(template().usb().enabled());
            optional(template().usb().type());
            optional(template().version().baseTemplate().id());
            optional(template().version().versionName());
            optional(template().virtioScsi().enabled());
            optional(template().display().singleQxlPci());
            optional(template().rngDevice().rate().bytes());
            optional(template().rngDevice().rate().period());
            optional(template().rngDevice().source());
            optional(template().vm().diskAttachments()[COLLECTION].name());
            optional(template().vm().diskAttachments()[COLLECTION].description());
            optional(template().vm().diskAttachments()[COLLECTION].id());
            // optional(template().vm().diskAttachments()[COLLECTION].storageDomain--collection()); //TODO: check
            optional(template().customProperties()[COLLECTION].name());
            optional(template().customProperties()[COLLECTION].value());
            optional(template().os().boot().devices()[COLLECTION]);
            optional(template().cpu().cpuTune().vcpuPins()[COLLECTION].cpuSet());
            optional(template().cpu().cpuTune().vcpuPins()[COLLECTION].vcpu());
            optional(template().sso().methods()[COLLECTION].id());
        }
    }

    /**
     * Returns the list of virtual machine templates.
     *
     * For example:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/templates
     * ----
     *
     * Will return the list of virtual machines and virtual machine templates.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        /**
         * The list of virtual machine templates.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Template[] templates();

        /**
         * Sets the maximum number of templates to return. If not specified all the templates are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned templates.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    /**
     * Returns a reference to the service that manages a specific virtual machine template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service TemplateService template(String id);
}
