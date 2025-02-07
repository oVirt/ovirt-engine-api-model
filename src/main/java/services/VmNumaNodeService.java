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

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.VirtualNumaNode;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("SLA")
public interface VmNumaNodeService {
    interface Get extends Follow {
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
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 23 Nov 2020
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(node().index());
            optional(node().memory());
            optional(node().cpu().cores()[COLLECTION].index());
            optional(node().numaNodePins()[COLLECTION].index());
            optional(node().numaTuneMode());
        }
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
     * NOTE: It's required to remove the numa nodes from the highest index
     * first.
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
