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
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.VmPool;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * A service to manage a virtual machines pool.
 *
 * @author Aleksei Slaikovskii <aslaikov@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface VmPoolService {
    /**
     * This operation allocates a virtual machine in the virtual machine pool.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vmpools/123/allocatevm
     * ----
     *
     * The allocate virtual machine action does not take any action specific parameters, so the request body should
     * contain an empty `action`:
     *
     * [source,xml]
     * ----
     * <action/>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface AllocateVm {
        /**
         * Indicates if the allocation should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Get the virtual machine pool.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/vmpools/123
     * ----
     *
     * You will get a XML response like that one:
     * [source,xml]
     * ----
     * <vm_pool id="123">
     *   <actions>...</actions>
     *   <name>MyVmPool</name>
     *   <description>MyVmPool description</description>
     *   <link href="/ovirt-engine/api/vmpools/123/permissions" rel="permissions"/>
     *   <max_user_vms>1</max_user_vms>
     *   <prestarted_vms>0</prestarted_vms>
     *   <size>100</size>
     *   <stateful>false</stateful>
     *   <type>automatic</type>
     *   <use_latest_template_version>false</use_latest_template_version>
     *   <cluster id="123"/>
     *   <template id="123"/>
     *   <vm id="123">...</vm>
     *   ...
     * </vm_pool>
     * ----
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        /**
         * Retrieved virtual machines pool.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out VmPool pool();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    /**
     * Update the virtual machine pool.
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/vmpools/123
     * ----
     *
     * The `name`, `description`, `size`, `prestarted_vms` and `max_user_vms`
     * attributes can be updated after the virtual machine pool has been
     * created.
     *
     * [source,xml]
     * ----
     * <vmpool>
     *   <name>VM_Pool_B</name>
     *   <description>Virtual Machine Pool B</description>
     *   <size>3</size>
     *   <prestarted_vms>1</size>
     *   <max_user_vms>2</size>
     * </vmpool>
     * ----
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(pool().comment());
            optional(pool().description());
            optional(pool().display().proxy());
            optional(pool().maxUserVms());
            optional(pool().name());
            optional(pool().prestartedVms());
            optional(pool().size());
            optional(pool().type());
            optional(pool().useLatestTemplateVersion());
            or(optional(pool().cluster().id()), optional(pool().cluster().name()));
            or(optional(pool().template().id()), optional(pool().template().name()));
        }
        /**
         * The virtual machine pool that is being updated.
         *
         * @author Shmuel Melamud <smelamud@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In @Out VmPool pool();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes a virtual machine pool.
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/vmpools/123
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Reference to a service managing the virtual machine pool assigned permissions.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service AssignedPermissionsService permissions();
}
