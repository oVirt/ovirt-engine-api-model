/*
Copyright (c) 2015 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.InstanceType;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("Virtualization")
public interface InstanceTypeService {

    /**
     * Get a specific instance type and it's attributes.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/instancetypes/123
     * ----
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        @Out InstanceType instanceType();
    }

    /**
     * Update a specific instance type and it's attributes.
     *
     * All the attributes are editable after creation.
     * If a virtual machine was created using an instance type X and some configuration in instance
     * type X was updated, the virtual machine's configuration will be updated automatically by the
     * engine.
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/instancetypes/123
     * ----
     *
     * For example, to update the memory of instance type `123` to 1 GiB and set the cpu topology
     * to 2 sockets and 1 core, send a request like this:
     *
     * [source, xml]
     * ----
     *
     * <instance_type>
     *   <memory>1073741824</memory>
     *   <cpu>
     *     <topology>
     *       <cores>1</cores>
     *       <sockets>2</sockets>
     *       <threads>1</threads>
     *     </topology>
     *   </cpu>
     * </instance_type>
     * ----
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(instanceType().storageErrorResumeBehaviour());
        }

        @In @Out InstanceType instanceType();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes a specific instance type from the system.
     *
     * If a virtual machine was created using an instance type X after removal of the instance type
     * the virtual machine's instance type will be set to `custom`.
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/instancetypes/123
     * ----
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         *
         * @author Sefi Litmanovich <slitmano@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In Boolean async();
    }

    /**
     * Reference to the service that manages the NICs that are attached to this instance type.
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service InstanceTypeNicsService nics();

    /**
     * Reference to the service that manages the watchdogs that are attached to this instance type.
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service InstanceTypeWatchdogsService watchdogs();

    /**
     * Reference to the service that manages the graphic consoles that are attached to this
     * instance type.
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service InstanceTypeGraphicsConsolesService graphicsConsoles();
}
