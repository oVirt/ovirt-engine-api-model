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

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Template;

/**
 * Manages the set of templates available in a storage domain.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 15 Apr 2017
 * @status added
 */
@Service
@Area("Storage")
public interface StorageDomainTemplatesService {
    /**
     * Returns the list of templates availalbe in the storage domain.
     *
     * The order of the returned list of templates isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out Template[] templates();

        /**
         * Sets the maximum number of templates to return. If not specified all the templates are returned.
         */
        @In Integer max();

        /**
         * Indicates whether to retrieve a list of registered or unregistered templates which contain disks on the storage domain.
         * To get a list of unregistered templates the call should indicate the unregistered flag.
         * For example, to get a list of unregistered templates the REST API call should look like this:
         *
         * ```http
         * GET /ovirt-engine/api/storagedomains/123/templates?unregistered=true
         * ```
         *
         * The default value of the unregisterd flag is `false`.
         * The request only apply to storage domains that are attached.
         *
         * @author Maor Lipchuk <mlipchuk@redhat.com>
         * @date 8 Mar 2017
         * @status added
         * @since 4.1.1
         */
        @In Boolean unregistered();
    }

    @Service StorageDomainTemplateService template(String id);
}
