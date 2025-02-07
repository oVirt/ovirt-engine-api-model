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
import types.Checkpoint;

/**
 * Lists the checkpoints of a virtual machine.
 *
 * @author Eyal Shenitzky <eshenitz@redhat.com>
 * @date 3 Jun 2020
 * @status added
 * @since 4.4
 */
@Service
@Area("Storage")
public interface VmCheckpointsService {
    /**
     * The list of virtual machine checkpoints.
     *
     * To get a list of checkpoints for a virtual machine with an id '123', send a request as follows:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/vms/123/checkpoints
     * ----
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @author Steve Goodman <sgoodman@redhat.com>
     * @date 11 Oct 2021
     * @status updated_by_docs
     * @since 4.4
     */
    interface List extends mixins.Follow {
        /**
         * The information about the virtual machine checkpoint entities.
         *
         * [source,xml]
         * ----
         * <checkpoints>
         *   <checkpoint id="checkpoint-uuid">
         *     <link href="/ovirt-engine/api/vms/vm-uuid/checkpoints/checkpoint-uuid/disks" rel="disks"/>
         *     <parent_id>parent-checkpoint-uuid</parent_id>
         *     <creation_date>xxx</creation_date>
         *     <vm href="/ovirt-engine/api/vm-uuid" id="vm-uuid"/>
         *  </checkpoint>
         * </checkpoints>
         * ----
         *
         * @author Eyal Shenitzky <eshenitz@redhat.com>
         * @date 3 Jun 2020
         * @status added
         * @since 4.4
         */
        @Out Checkpoint[] checkpoints();

        /**
         * Sets the maximum number of virtual machine checkpoints to return.
         * If not specified, all the virtual machine checkpoints are returned.
         */
        @In Integer max();
    }

    /**
     * Returns a reference to the service that manages a specific VM checkpoint.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 3 Jun 2020
     * @status added
     * @since 4.4
     */
    @Service
    VmCheckpointService checkpoint(String id);
}
