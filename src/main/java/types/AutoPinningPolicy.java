/*
Copyright (c) 2020 Red Hat, Inc.
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
 * Type representing what the CPU and NUMA pinning policy is.
 *
 * @author Liran Rotenberg <lrotenbe@redhat.com>
 * @date 1 Sep 2020
 * @status added
 * @since 4.4
 */
@Type
public enum AutoPinningPolicy {
    /**
     * The CPU and NUMA pinning won't be calculated.
     *
     * Currently, its implication is that the CPU and NUMA pinning won't be calculated to the current virtual
     * machine configuration. By default the VM topology set with 1 Socket, 1 Core and 1 Thread.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 1 Sep 2020
     * @status added
     * @since 4.4
     */
    DISABLED,

    /**
     * The CPU and NUMA pinning will be configured by the virtual machine current state.
     *
     * Currently, its implication is that the CPU and NUMA pinning will use the provided virtual machine CPU topology.
     * Without given CPU topology it will use the engine defaults (the VM topology set with 1 Socket, 1 Core and
     * 1 Thread).
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 1 Sep 2020
     * @status added
     * @since 4.4
     */
    EXISTING,

    /**
     * The CPU and NUMA pinning will be configured by the dedicated host.
     *
     * Currently, its implication is that the CPU and NUMA pinning will use the dedicated host CPU topology.
     * The virtual machine configuration will automatically be set to fit the host to get the highest possible
     * performance.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 1 Sep 2020
     * @status added
     * @since 4.4
     */
    ADJUST;
}

