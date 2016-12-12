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
import types.VirtualNumaNode;

@Service
@Area("SLA")
public interface VmNumaNodeService {
    interface Get {
        @Out VirtualNumaNode node();
    }

    /**
     * Updates a virtual NUMA node.
     *
     * An example of pinning a virtual NUMA node to a physical NUMA node on the host:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/vms/123/numanodes/456
     * ----
     *
     * The request body should contain the following:
     *
     * [source,xml]
     * ----
     * <vm_numa_node>
     *   <numa_node_pins>
     *     <numa_node_pin>
     *       <index>0</index>
     *     </numa_node_pin>
     *   </numa_node_pins>
     * </vm_numa_node>
     * ----
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @In @Out VirtualNumaNode node();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes a virtual NUMA node.
     *
     * An example of removing a virtual NUMA node:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/vms/123/numanodes/456
     * ----
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
