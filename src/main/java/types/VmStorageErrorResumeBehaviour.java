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
package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * If the storage, on which this virtual machine has some disks gets
 * unresponsive, the virtual machine gets paused.
 *
 * This are the possible options, what should happen with the virtual machine
 * in the moment the storage gets available again.
 *
 * @author Tomas Jelinek <tjelinek@redhat.com>
 * @date 5 Oct 2017
 * @status added
 * @since 4.2
 */
@Type
public enum VmStorageErrorResumeBehaviour {

   /**
     * Do nothing with the virtual machine.
     *
     * Useful if there is a custom failover implemented and the user does not
     * want the virtual machine to get resumed.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 10 Oct 2017
     * @status added
     * @since 4.2
     */
    LEAVE_PAUSED,

   /**
     * The virtual machine gets resumed automatically in the moment the storage is available
     * again.
     *
     * This is the only behavior available before 4.2.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 10 Oct 2017
     * @status added
     * @since 4.2
     */
    AUTO_RESUME,

    /**
     * The virtual machine will be killed after a timeout (configurable on the hypervisor).
     *
     * This is the only option supported for highly available virtual machines
     * with leases. The reason is that the highly available virtual machine is
     * restarted using the infrastructure and any kind of resume risks
     * split brains.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 10 Oct 2017
     * @status added
     * @since 4.2
     */
    KILL;
}
