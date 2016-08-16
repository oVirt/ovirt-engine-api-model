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
@Area("Virtualization")
public interface TemplatesService {
    interface Add {
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
    }

    interface List {
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

    @Service TemplateService template(String id);
}
