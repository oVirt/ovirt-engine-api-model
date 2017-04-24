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
 * Represents an operating system installed on the virtual machine.
 *
 * To get that information send a request like this:
 *
 * ....
 * GET /ovirt-engine/api/vms/123
 * ....
 *
 * The result will be like this:
 *
 * [source,xml]
 * ----
 * <vm href="/ovirt-engine/api/vms/123" id="123">
 * ...
 *   <guest_operating_system>
 *     <architecture>x86_64</architecture>
 *     <codename>Maipo</codename>
 *     <distribution>Red Hat Enterprise Linux Server</distribution>
 *     <family>Linux</family>
 *     <kernel>
 *       <version>
 *         <build>0</build>
 *         <full_version>3.10.0-514.10.2.el7.x86_64</full_version>
 *         <major>3</major>
 *         <minor>10</minor>
 *         <revision>514</revision>
 *       </version>
 *     </kernel>
 *     <version>
 *       <full_version>7.3</full_version>
 *       <major>7</major>
 *       <minor>3</minor>
 *     </version>
 *   </guest_operating_system>
 * </vm>
 * ----
 *
 * @author Lukas Svaty <lsvaty@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public interface GuestOperatingSystem {
    /**
     * The architecture of the operating system, such as x86_64.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String architecture();

    /**
     * Code name of the operating system, such as `Maipo`.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String codename();

    /**
     * Full name of operating system distribution.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String distribution();

    /**
     * Kernel version of the operating system.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Kernel kernel();

    /**
     * Family of operating system, such as `Linux`.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String family();

    /**
     * Version of the installed operating system.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Version version();
}
