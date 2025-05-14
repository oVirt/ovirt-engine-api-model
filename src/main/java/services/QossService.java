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
import types.Qos;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * Manages the set of _quality of service_ configurations available in a data center.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 15 Apr 2017
 * @status added
 */
@Service
@Area("SLA")
public interface QossService {
    /**
     * Add a new QoS to the dataCenter.
     *
     * ```http
     * POST /ovirt-engine/api/datacenters/123/qoss
     * ```
     *
     * The response will look as follows:
     *
     * ```xml
     * <qos href="/ovirt-engine/api/datacenters/123/qoss/123" id="123">
     *   <name>123</name>
     *   <description>123</description>
     *   <max_iops>10</max_iops>
     *   <type>storage</type>
     *   <data_center href="/ovirt-engine/api/datacenters/123" id="123"/>
     * </qos>
     * ```
     *
     * @author Ori Liel <oliel@redhat.com>
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 26 Apr 2017
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(qos().name());
            mandatory(qos().type());
            optional(qos().cpuLimit());
            optional(qos().description());
            optional(qos().inboundAverage());
            optional(qos().inboundBurst());
            optional(qos().inboundPeak());
            optional(qos().maxIops());
            optional(qos().maxReadIops());
            optional(qos().maxReadThroughput());
            optional(qos().maxThroughput());
            optional(qos().maxWriteIops());
            optional(qos().maxWriteThroughput());
            optional(qos().outboundAverage());
            optional(qos().outboundAverageLinkshare());
            optional(qos().outboundAverageRealtime());
            optional(qos().outboundAverageUpperlimit());
            optional(qos().outboundBurst());
            optional(qos().outboundPeak());
        }

        /**
         * Added QoS object.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @In @Out Qos qos();
    }

    /**
     * Returns the list of _quality of service_ configurations available in the data center.
     *
     * ```http
     * GET /ovirt-engine/api/datacenter/123/qoss
     * ```
     *
     * You will get response which will look like this:
     *
     * ```xml
     * <qoss>
     *   <qos href="/ovirt-engine/api/datacenters/123/qoss/1" id="1">...</qos>
     *   <qos href="/ovirt-engine/api/datacenters/123/qoss/2" id="2">...</qos>
     *   <qos href="/ovirt-engine/api/datacenters/123/qoss/3" id="3">...</qos>
     * </qoss>
     * ```
     *
     * The returned list of quality of service configurations isn't guaranteed.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface List extends Follow {
        /**
         * List of queried QoS objects.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @Out Qos[] qoss();

        /**
         * Sets the maximum number of QoS descriptors to return. If not specified all the descriptors are returned.
         */
        @In Integer max();
    }

    /**
     * A reference to a service managing a specific QoS.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 25 Apr 2017
     * @status added
     */
    @Service QosService qos(String id);
}
