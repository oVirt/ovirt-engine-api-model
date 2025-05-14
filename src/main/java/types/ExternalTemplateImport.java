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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Describes the parameters for the template import operation from an external system.
 * Currently supports OVA only.
 *
 * @author Liran Rotenberg <lrotenbe@redhat.com>
 * @date 12 May 2021
 * @status added
 * @since 4.4.7
 */
@Type
public interface ExternalTemplateImport {

    /**
     * The URL to be passed to the engine.
     *
     * Example:
     *
     * ```http
     * ova:///mnt/ova/ova_file.ova
     * ```
     *
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 12 May 2021
     * @status added
     * @since 4.4.7
     */
    String url();

    /**
     * The template entity used to specify a name for the newly created template.
     *
     * If a name is not specified, the source template name will be used.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 12 May 2021
     * @status added
     * @since 4.4.7
     */
    @Link Template template();

    /**
     * Specifies the target cluster for the resulting template.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 12 May 2021
     * @status added
     * @since 4.4.7
     */
    @Link Cluster cluster();

    /**
     * Specifies the target storage domain for disks.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 12 May 2021
     * @status added
     * @since 4.4.7
     */
    @Link StorageDomain storageDomain();

    /**
     * Specifies the host that the OVA file exists on.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 12 May 2021
     * @status added
     * @since 4.4.7
     */
    @Link Host host();

    /**
     * Optional. Specifies the CPU profile of the resulting template.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 12 May 2021
     * @status added
     * @since 4.4.7
     */
    @Link CpuProfile cpuProfile();

    /**
     * Optional. Specifies the quota that will be applied to the resulting template.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 12 May 2021
     * @status added
     * @since 4.4.7
     */
    @Link Quota quota();

    /**
     * Optional. Indicates if the identifiers of the imported template
     * should be regenerated.
     *
     * By default when a template is imported the identifiers
     * are preserved. This means that the same template can't
     * be imported multiple times, as that identifiers needs to be
     * unique. To allow importing the same template multiple times set
     * this parameter to `true`, as the default is `false`.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 14 Jun 2021
     * @status added
     * @since 4.4.8
     */
    Boolean clone();
}
