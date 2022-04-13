/*
Copyright (c) 2021 Red Hat, Inc.

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
package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing the CPU and NUMA pinning policy.
 *
 * @author Liran Rotenberg <lrotenbe@redhat.com>
 * @author Eli Marcus <emarcus@redhat.com>
 * @date 14 Dec 2021
 * @status updated_by_docs
 * @since 4.5
 */
@Type
public enum CpuPinningPolicy {
    /**
     * The CPU pinning won't be configured.
     *
     * Currently, this means that the CPU pinning won't be configured to the current virtual
     * machine configuration. By default, the VM topology is set with 1 Socket, 1 Core and 1 Thread.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 14 Dec 2021
     * @status updated_by_docs
     * @since 4.5
     */
    NONE,

    /**
     * The CPU pinning will be manually configured.
     *
     * Currently, this means that the CPU pinning will be manually configured to the current virtual
     * machine configuration. The VM needs to be pinned to at least one host. The Pinning is provided within
     * the CPU configuration, using CpuTune.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 14 Dec 2021
     * @status updated_by_docs
     * @since 4.5
     */
    MANUAL,

    /**
     * The CPU and NUMA pinning will be configured by the dedicated host.
     *
     * The CPU and NUMA pinning will use the dedicated host CPU topology.
     * The virtual machine configuration will automatically be set to fit the host to get the highest possible
     * performance.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 13 Dec 2021
     * @status added
     * @since 4.5
     */
    RESIZE_AND_PIN_NUMA,

    /**
     * The CPU pinning will be automatically calculated by the engine when a vm starts and it will be dropped when the vm stops.
     *
     * The pinning is exclusive, that means that no other VM can use the pinned physical CPU.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @date 3 Mar 2021
     * @status added
     * @since 4.5
     */
    DEDICATED,

    /**
     * The CPU pinning will be automatically calculated by the engine when a vm starts, and it will be dropped when the vm stops.
     *
     * The pinning is exclusive, each virtual thread will get an exclusive physical core. That means that no other VM can use the pinned physical CPU.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 13 Apr 2021
     * @status added
     * @since 4.5.1
     */
    ISOLATE_THREADS;
}

