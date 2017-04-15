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
public interface VmNumaNodesService {
    /**
     * Creates a new virtual NUMA node for the virtual machine.
     *
     * An example of creating a NUMA node:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/c7ecd2dc/numanodes
     * Accept: application/xml
     * Content-type: application/xml
     * ----
     * The request body can contain the following:
     * [source,xml]
     * ----
     * <vm_numa_node>
     *   <cpu>
     *     <cores>
     *       <core>
     *         <index>0</index>
     *       </core>
     *     </cores>
     *   </cpu>
     *   <index>0</index>
     *   <memory>1024</memory>
     * </vm_numa_node>
     * ----
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        @In @Out VirtualNumaNode node();
    }

    /**
     * Lists virtual NUMA nodes of a virtual machine.
     *
     * The order of the returned list of NUMA nodes isn't guaranteed.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface List {
        @Out VirtualNumaNode[] nodes();

        /**
         * Sets the maximum number of nodes to return. If not specified all the nodes are returned.
         */
        @In Integer max();
    }

    @Service VmNumaNodeService node(String id);
}
