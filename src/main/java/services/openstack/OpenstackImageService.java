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

package services.openstack;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Cluster;
import types.Disk;
import types.OpenStackImage;
import types.StorageDomain;
import types.Template;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

@Service
@Area("Storage")
public interface OpenstackImageService {
    interface Get extends Follow {
        @Out OpenStackImage image();
    }

    /**
     * Imports a virtual machine from a Glance image storage domain.
     *
     * For example, to import the image with identifier `456` from the
     * storage domain with identifier `123` send a request like this:
     *
     * ```http
     * POST /ovirt-engine/api/openstackimageproviders/123/images/456/import
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <action>
     *   <storage_domain>
     *     <name>images0</name>
     *   </storage_domain>
     *   <cluster>
     *     <name>images0</name>
     *   </cluster>
     * </action>
     * ```
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Import {
        @InputDetail
        default void inputDetail() {
            or(mandatory(storageDomain().id()), mandatory(storageDomain().name()));
            optional(importAsTemplate());
            optional(template().name());
            or(optional(cluster().id()), optional(cluster().name()));
            or(optional(disk().alias()), optional(disk().name()));
        }
        @In Disk disk();

        /**
         * Indicates whether the image should be imported as a template.
         *
         * @author Daniel Erez <derez@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In Boolean importAsTemplate();

        @In StorageDomain storageDomain();
        @In Template template();

        /**
         * This parameter is mandatory in case of using `import_as_template` and indicates which cluster should be used
         * for import glance image as template.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In Cluster cluster();

        /**
         * Indicates if the import should be performed asynchronously.
         */
        @In Boolean async();
    }
}
