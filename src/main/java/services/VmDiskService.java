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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Disk;

@Service
@Area("Storage")
public interface VmDiskService extends MeasurableService {
    interface Activate {
        /**
         * Indicates if the activation should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Deactivate {
        /**
         * Indicates if the deactivation should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Export {
        /**
         * Indicates if the export should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    interface Get extends Follow {
        @Out Disk disk();
    }

    interface Move {
        /**
         * Indicates if the move should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    interface Update {
        @In @Out Disk disk();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Detach the disk from the virtual machine.
     *
     * NOTE: In version 3 of the API this used to also remove the disk completely from the system, but starting with
     * version 4 it doesn't. If you need to remove it completely use the xref:services/disk/methods/remove[remove method of the top level disk service].
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Reduces the size of the disk image.
     *
     * Invokes _reduce_ on the logical volume (i.e. this is only applicable for block storage domains).
     * This is applicable for floating disks and disks attached to non-running virtual machines.
     * There is no need to specify the size as the optimal size is calculated automatically.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @author Emma Heftman <eheftman@redhat.com>
     * @date 3 June 2018
     * @status updated_by_docs
     * @since 4.2.5
     */
    interface Reduce {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service AssignedPermissionsService permissions();
}
