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
package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing the deallocation policy of virtual machines in a virtual machines pool.
 *
 * @author Arik Hadas <ahadas@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public enum VmPoolType {
    /**
     * This policy indicates that virtual machines in the pool are automcatically deallocated by the system.
     *
     * With this policy, when a virtual machine that is part of the pool and is assigned to a user is shut-down, it is
     * detached from the user, its state is restored to the pool's default state, and the virtual machine returns to
     * pool (i.e., the virtual machine can then be assigned to another user).
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    AUTOMATIC,

    /**
     * This policy indicates that virtual machines in the pool are deallocated manually by the administrator.
     *
     * With this policy, a virtual machine that is part of the pool remains assigned to its user and preserves its state
     * on shut-down. In order to return the virtual machine back to the pool, the administrator needs to deallocate it
     * explicitly by removing the user's permissions on that virtual machine.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    MANUAL;
}
