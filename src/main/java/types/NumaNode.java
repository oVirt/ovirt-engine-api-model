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
 * Represents a physical NUMA node.
 *
 * Example XML representation:
 *
 * [source,xml]
 * ----
 * <host_numa_node href="/ovirt-engine/api/hosts/0923f1ea/numanodes/007cf1ab" id="007cf1ab">
 *   <cpu>
 *     <cores>
 *       <core>
 *         <index>0</index>
 *       </core>
 *     </cores>
 *   </cpu>
 *   <index>0</index>
 *   <memory>65536</memory>
 *   <node_distance>40 20 40 10</node_distance>
 *   <host href="/ovirt-engine/api/hosts/0923f1ea" id="0923f1ea"/>
 * </host_numa_node>
 * ----
 *
 * @author Andrej Krejcir <akrejcir@redhat.com>
 * @date 14 Sep 2016
 * @status added
 */
@Type
public interface NumaNode extends Identified {
    Integer index();

    /**
     * Memory of the NUMA node in MB.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    Integer memory();
    Cpu cpu();
    String nodeDistance();

    @Link Host host();
    @Link Statistic[] statistics();
}
