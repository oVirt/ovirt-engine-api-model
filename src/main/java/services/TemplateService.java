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

package services;

import mixins.Follow;

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.Host;
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
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 03 Mar 2018
 * @status updated_by_docs
 */
@Service
@Area("Virtualization")
public interface TemplateService {
    /**
     * Exports a template to the data center export domain.
     *
     * For example, send the following request:
     *
     * ```http
     * POST /ovirt-engine/api/templates/123/export HTTP/1.1
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <action>
     *   <storage_domain id="456"/>
     *   <exclusive>true<exclusive/>
     * </action>
     * ```
     *
     * Since version 4.2 of the engine it is also possible to export a template as a virtual appliance (OVA).
     * For example, to export template `123` as an OVA file named `myvm.ova` that is placed in the directory `/home/ovirt/` on host `myhost`:
     *
     * ```http
     * POST /ovirt-engine/api/templates/123/export HTTP/1.1
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <action>
     *   <host>
     *     <name>myhost</name>
     *   </host>
     *   <directory>/home/ovirt</directory>
     *   <filename>myvm.ova</filename>
     * </action>
     * ```
     *
     * @author Liron Aravot <laravot@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 20 Apr 2022
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
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 03 Mar 2018
         * @status updated_by_docs
         */
        @In Boolean exclusive();

        /**
         * Specifies the destination export storage domain.
         *
         * @author Liron Aravot <laravot@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 03 Mar 2018
         * @status updated_by_docs
         */
        @In StorageDomain storageDomain();

        /**
         * Exports a template to an export domain.
         *
         * @author Arik Hadas <ahadas@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 03 Mar 2018
         * @status updated_by_docs
         */
        interface ToExportDomain extends Export {
            @InputDetail
            default void inputDetail() {
                or(mandatory(storageDomain().id()), mandatory(storageDomain().name()));
                optional(exclusive());
            }
        }

        /**
         * Exports a template as an OVA file to a given path on a specified host.
         *
         * @author Arik Hadas <ahadas@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 03 Mar 2018
         * @status updated_by_docs
         * @since 4.2.3
         */
        interface ToPathOnHost extends Export {
            /**
             * The host to generate the OVA file on.
             *
             * @author Arik Hadas <ahadas@redhat.com>
             * @author Tahlia Richardson <trichard@redhat.com>
             * @date 03 Mar 2018
             * @status updated_by_docs
             * @since 4.2.3
             */
            @In Host host();

            /**
             * An absolute path of a directory on the host to generate the OVA file in.
             *
             * @author Arik Hadas <ahadas@redhat.com>
             * @author Tahlia Richardson <trichard@redhat.com>
             * @date 03 Mar 2018
             * @status updated_by_docs
             * @since 4.2.3
             */
            @In String directory();

            /**
             * The name of the OVA file.
             *
             * This is an optional parameter. If it is not specified, the name of the OVA file is determined according
             * to the name of the template. It will conform to the following pattern: "template_name.ova".
             *
             * @author Arik Hadas <ahadas@redhat.com>
             * @author Tahlia Richardson <trichard@redhat.com>
             * @date 03 Mar 2018
             * @status updated_by_docs
             * @since 4.2.3
             */
            @In String filename();

            @InputDetail
            default void inputDetail() {
                or(mandatory(host().id()), mandatory(host().name()));
                mandatory(directory());
                optional(filename());
            }
        }
    }

    /**
     * Returns the information about this template or template version.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 03 Mar 2018
     * @status updated_by_docs
     */
    interface Get extends Follow {
        /**
         * The information about the template or template version.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 03 Mar 2018
         * @status updated_by_docs
         */
        @Out Template template();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 03 Mar 2018
         * @status updated_by_docs
         */
        @In Boolean filter();
    }

    /**
     * Updates the template.
     *
     * The `name`, `description`, `type`, `memory`, `cpu`, `topology`, `os`, `high_availability`, `display`,
     * `stateless`, `usb`, and `timezone` elements can be updated after a template has been created.
     *
     * For example, to update a template so that it has 1 GiB of memory send a request like this:
     *
     * ```http
     * PUT /ovirt-engine/api/templates/123 HTTP/1.1
     * ```
     *
     * With the following request body:
     *
     * ```xml
     * <template>
     *   <memory>1073741824</memory>
     * </template>
     * ```
     *
     * The `version_name` name attribute is the only one that can be updated within the `version` attribute used for
     * template versions:
     *
     * ```xml
     * <template>
     *   <version>
     *     <version_name>mytemplate_2</version_name>
     *   </version>
     * </template>
     * ```
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 03 Mar 2018
     * @status updated_by_docs
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
            optional(template().cpu().cpuTune().vcpuPins()[COLLECTION].cpuSet());
            optional(template().cpu().cpuTune().vcpuPins()[COLLECTION].vcpu());
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
            or(optional(template().placementPolicy().hosts()[COLLECTION].id()), optional(template().placementPolicy().hosts()[COLLECTION].name()));
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
            optional(template().multiQueuesEnabled());
            optional(template().virtioScsiMultiQueuesEnabled());
            optional(template().virtioScsiMultiQueues());
            optional(template().tpmEnabled());
            optional(template().autoPinningPolicy());
        }
        @In @Out Template template();

        /**
         * Indicates if the update should be performed asynchronously.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 03 Mar 2018
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Removes a virtual machine template.
     *
     * ```http
     * DELETE /ovirt-engine/api/templates/123 HTTP/1.1
     * ```
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 03 Mar 2018
     * @status updated_by_docs
     */
    interface Remove {
        /**
         * Indicates if the removal should be performed asynchronously.
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 03 Mar 2018
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Returns a reference to the service that manages the permissions that are associated with the template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 03 Mar 2018
     * @status updated_by_docs
     */
    @Service AssignedPermissionsService permissions();

    /**
     * Returns a reference to the service that manages the tags that are associated with the template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 03 Mar 2018
     * @status updated_by_docs
     */
    @Service AssignedTagsService tags();

    /**
     * Returns a reference to the service that manages the graphical consoles that are associated with the template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 03 Mar 2018
     * @status updated_by_docs
     */
    @Service TemplateGraphicsConsolesService graphicsConsoles();

    /**
     * Returns a reference to the service that manages the CD-ROMs that are associated with the template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 03 Mar 2018
     * @status updated_by_docs
     */
    @Service TemplateCdromsService cdroms();

    /**
     * Returns a reference to the service that manages the NICs that are associated with the template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 03 Mar 2018
     * @status updated_by_docs
     */
    @Service TemplateNicsService nics();

    /**
     * Returns a reference to the service that manages the _watchdogs_ that are associated with the template.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 03 Mar 2018
     * @status updated_by_docs
     */
    @Service TemplateWatchdogsService watchdogs();

    /**
     * Returns a reference to the service that manages a specific
     * disk attachment of the template.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 03 Mar 2018
     * @status updated_by_docs
     */
    @Service TemplateDiskAttachmentsService diskAttachments();

    /**
     * Reference to the service that manages mediated devices associated with the template.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 10 Mar 2022
     * @status added
     * @since 4.5
     */
    @Service TemplateMediatedDevicesService mediatedDevices();
}
