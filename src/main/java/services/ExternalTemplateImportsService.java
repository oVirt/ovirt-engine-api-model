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

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.ExternalTemplateImport;

/**
 * Provides capability to import external templates.
 * Currently supports OVA only.
 *
 * @author Liran Rotenberg <lrotenbe@redhat.com>
 * @date 12 May 2021
 * @status added
 * @since 4.4.7
 */
@Service
@Area("Virtualization")
public interface ExternalTemplateImportsService {

    /**
     * This operation is used to import a template from external hypervisor.
     *
     * For example import of a template OVA can be facilitated using the following request:
     *
     * ```http
     * POST /externaltemplateimports HTTP/1.1
     * ```
     *
     * With request body of type xref:types/external_template_import[ExternalTemplateImport], for example:
     *
     * ```xml
     * <external_template_import>
     *   <template>
     *     <name>my_template</name>
     *   </template>
     *   <cluster id="2b18aca2-4469-11eb-9449-482ae35a5f83" />
     *   <storage_domain id="8bb5ade5-e988-4000-8b93-dbfc6717fe50" />
     *   <url>ova:///mnt/ova/ova_template.ova</url>
     *   <host id="8bb5ade5-e988-4000-8b93-dbfc6717fe50" />
     * </external_template_import>
     * ```
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 12 May 2021
     * @status added
     * @since 4.4.7
     */
    interface Add {
        @In @Out ExternalTemplateImport _import();
    }
}
