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

@Service
@Area("Storage")
public interface StorageDomainTemplatesService {
    interface List {
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
         * ....
         * GET /ovirt-engine/api/storagedomains/123/templates?unregistered=true
         * ....
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
