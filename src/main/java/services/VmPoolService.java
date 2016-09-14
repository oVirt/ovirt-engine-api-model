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
import types.VmPool;

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

    interface Get {
        @Out VmPool pool();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    interface Update {
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

    @Service AssignedPermissionsService permissions();
}
