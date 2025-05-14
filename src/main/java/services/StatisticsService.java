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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Statistic;

@Service
@Area("Infrastructure")
public interface StatisticsService {
    /**
     * Retrieves a list of statistics.
     *
     * For example, to retrieve the statistics for virtual machine `123` send a
     * request like this:
     *
     * ```http
     * GET /ovirt-engine/api/vms/123/statistics
     * ```
     *
     * The result will be like this:
     *
     * ```xml
     * <statistics>
     *   <statistic href="/ovirt-engine/api/vms/123/statistics/456" id="456">
     *     <name>memory.installed</name>
     *     <description>Total memory configured</description>
     *     <kind>gauge</kind>
     *     <type>integer</type>
     *     <unit>bytes</unit>
     *     <values>
     *       <value>
     *         <datum>1073741824</datum>
     *       </value>
     *     </values>
     *     <vm href="/ovirt-engine/api/vms/123" id="123"/>
     *   </statistic>
     *   ...
     * </statistics>
     * ```
     *
     * Just a single part of the statistics can be retrieved by specifying its id at the end of the URI. That means:
     *
     * ```http
     * GET /ovirt-engine/api/vms/123/statistics/456
     * ```
     *
     * Outputs:
     *
     * ```xml
     * <statistic href="/ovirt-engine/api/vms/123/statistics/456" id="456">
     *   <name>memory.installed</name>
     *   <description>Total memory configured</description>
     *   <kind>gauge</kind>
     *   <type>integer</type>
     *   <unit>bytes</unit>
     *   <values>
     *     <value>
     *       <datum>1073741824</datum>
     *     </value>
     *   </values>
     *   <vm href="/ovirt-engine/api/vms/123" id="123"/>
     * </statistic>
     * ```
     *
     * The order of the returned list of statistics isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out Statistic[] statistics();

        /**
         * Sets the maximum number of statistics to return. If not specified all the statistics are returned.
         */
        @In Integer max();
    }

    @Service StatisticService statistic(String id);
}
