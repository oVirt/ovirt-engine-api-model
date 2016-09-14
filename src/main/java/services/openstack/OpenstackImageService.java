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

package services.openstack;

import annotations.Area;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Cluster;
import types.Disk;
import types.OpenStackImage;
import types.StorageDomain;
import types.Template;

@Service
@Area("Storage")
public interface OpenstackImageService {
    interface Get {
        @Out OpenStackImage image();
    }

    /**
     * Imports a virtual machine from a Glance image storage domain.
     *
     * For example, to import the image with identifier `456` from the
     * storage domain with identifier `123` send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/openstackimageproviders/123/images/456/import
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <action>
     *   <storage_domain>
     *     <name>images0</name>
     *   </storage_domain>
     *   <cluster>
     *     <name>images0</name>
     *   </cluster>
     * </action>
     * ----
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Import {
        @In Disk disk();
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
