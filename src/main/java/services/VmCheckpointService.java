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
import mixins.Follow;
import types.Checkpoint;

/**
 * A service managing a checkpoint of a virtual machines.
 *
 * @author Eyal Shenitzky <eshenitz@redhat.com>
 * @date 3 Jun 2020
 * @status added
 * @since 4.4
 */
@Service
@Area("Storage")
public interface VmCheckpointService {
    /**
     * Returns information about the virtual machine checkpoint.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 3 Jun 2020
     * @status added
     * @since 4.4
     */
    interface Get extends Follow {
        /**
         * The information about the virtual machine checkpoint entity.
         *
         * ```xml
         *<checkpoint id="checkpoint-uuid">
         *  <link href="/ovirt-engine/api/vms/vm-uuid/checkpoints/checkpoint-uuid/disks" rel="disks"/>
         *  <parent_id>parent-checkpoint-uuid</parent_id>
         *  <creation_date>xxx</creation_date>
         *  <vm href="/ovirt-engine/api/vms/vm-uuid" id="vm-uuid"/>
         *</checkpoint>
         * ```
         *
         * @author Eyal Shenitzky <eshenitz@redhat.com>
         * @date 3 Jun 2020
         * @status added
         * @since 4.4
         */
        @Out
        Checkpoint checkpoint();
    }

    /**
     * Remove the virtual machine checkpoint entity.
     *
     * Remove the checkpoint from libvirt and the database.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 3 Jun 2020
     * @status added
     * @since 4.4
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * A reference to the service that lists the disks in checkpoint.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @author Rolfe Dlugy-Hegwer <rdlugyhe@redhat.com>
     * @date 3 Jun 2020
     * @status added
     * @since 4.4
     */
    @Service VmCheckpointDisksService disks();
}
