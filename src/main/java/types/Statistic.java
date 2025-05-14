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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * A generic type used for all kinds of statistics.
 *
 * Statistic contains the statistics values for various entities.
 * The following object contain statistics:
 *
 * * Disk
 * * Host
 * * HostNic
 * * NumaNode
 * * Nic
 * * Vm
 * * GlusterBrick
 * * Step
 * * GlusterVolume
 *
 * An example of a XML representation:
 *
 * ```xml
 * <statistics>
 *   <statistic id="1234" href="/ovirt-engine/api/hosts/1234/nics/1234/statistics/1234">
 *     <name>data.current.rx</name>
 *     <description>Receive data rate</description>
 *     <values type="DECIMAL">
 *       <value>
 *         <datum>0</datum>
 *       </value>
 *     </values>
 *     <type>GAUGE</type>
 *     <unit>BYTES_PER_SECOND</unit>
 *     <host_nic id="1234" href="/ovirt-engine/api/hosts/1234/nics/1234"/>
 *   </statistic>
 *   ...
 * </statistics>
 * ```
 *
 * NOTE: This statistics sub-collection is read-only.
 *
 * @author Shmuel Melamud <smelamud@redhat.com>
 * @date 14 Sep 2016
 * @status added
 */
@Type

public interface Statistic extends Identified {

    /**
     * A data set that contains `datum`.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    Value[] values();

    /**
     * The type of statistic measures.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    StatisticKind kind();

    /**
     * The data type for the statistical values that follow.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    ValueType type();

    /**
     * The unit or rate to measure of the statistical values.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    StatisticUnit unit();


    /**
     * A relationship to the containing `disk` resource.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    @Link Disk disk();

    @Link Host host();

    /**
     * A reference to the host NIC.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @date 22 Sept 2016
     * @status added
     */
    @Link HostNic hostNic();

    @Link NumaNode hostNumaNode();
    @Link Nic nic();
    @Link Vm vm();
    @Link GlusterBrick brick();
    @Link Step step();
    @Link GlusterVolume glusterVolume();
}
