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
import types.Template;

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
     * This requires the `name` and `vm` elements. Identify the virtual machine with the `id` `name` attributes.
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
     * @date 14 Sep 2016
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
