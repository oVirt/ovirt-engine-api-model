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
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 16 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface NumaNode extends Identified {
    Integer index();

    /**
     * Memory of the NUMA node in MB.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 16 Nov 2016
     * @status updated_by_docs
     */
    Integer memory();
    Cpu cpu();
    String nodeDistance();

    @Link Host host();

    /**
     * Each host NUMA node resource exposes a statistics sub-collection for host NUMA node specific statistics.
     *
     * An example of an XML representation:
     *
     * [source,xml]
     * ----
     * <statistics>
     *   <statistic href="/ovirt-engine/api/hosts/123/numanodes/456/statistics/789" id="789">
     *     <name>memory.total</name>
     *     <description>Total memory</description>
     *     <kind>gauge</kind>
     *     <type>integer</type>
     *     <unit>bytes</unit>
     *     <values>
     *       <value>
     *         <datum>25165824000</datum>
     *       </value>
     *     </values>
     *     <host_numa_node href="/ovirt-engine/api/hosts/123/numanodes/456" id="456" />
     *   </statistic>
     *     ...
     * </statistics>
     * ----
     *
     * NOTE: This statistics sub-collection is read-only.
     *
     * The following list shows the statistic types for a host NUMA node:
     *
     * |===
     * |Name |Description
     *
     * |`memory.total`
     * |Total memory in bytes on the NUMA node.
     *
     * |`memory.used`
     * |Memory in bytes used on the NUMA node.
     *
     * |`memory.free`
     * |Memory in bytes free on the NUMA node.
     *
     * |`cpu.current.user`
     * |Percentage of CPU usage for user slice.
     *
     * |`cpu.current.system`
     * |Percentage of CPU usage for system.
     *
     * |`cpu.current.idle`
     * |Percentage of idle CPU usage.
     * |===
     *
     * @author Artyom Lukianov <alukiano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Statistic[] statistics();
}
