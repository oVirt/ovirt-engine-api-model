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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents the virtual NUMA node.
 *
 * An example XML representation:
 *
 * [source,xml]
 * ----
 * <vm_numa_node href="/ovirt-engine/api/vms/123/numanodes/456" id="456">
 *   <cpu>
 *     <cores>
 *       <core>
 *         <index>0</index>
 *       </core>
 *     </cores>
 *   </cpu>
 *   <index>0</index>
 *   <memory>1024</memory>
 *   <numa_node_pins>
 *     <numa_node_pin>
 *       <index>0</index>
 *     </numa_node_pin>
 *   </numa_node_pins>
 *   <vm href="/ovirt-engine/api/vms/123" id="123" />
 * </vm_numa_node>
 * ----
 *
 * @author Andrej Krejcir <akrejcir@redhat.com>
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 30 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface VirtualNumaNode extends NumaNode {
    NumaNodePin[] numaNodePins();

    @Link Vm vm();
}
