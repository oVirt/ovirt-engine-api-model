/*
Copyright (c) 2015 Red Hat, Inc.

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

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Qos;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("SLA")
public interface QosService {
    /**
     * Get specified QoS in the data center.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/datacenters/123/qoss/123
     * ----
     *
     * You will get response like this one below:
     *
     * [source,xml]
     * ----
     * <qos href="/ovirt-engine/api/datacenters/123/qoss/123" id="123">
     *   <name>123</name>
     *   <description>123</description>
     *   <max_iops>1</max_iops>
     *   <max_throughput>1</max_throughput>
     *   <type>storage</type>
     *   <data_center href="/ovirt-engine/api/datacenters/123" id="123"/>
     * </qos>
     * ----
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface Get extends Follow {
        /**
         * Queried QoS object.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @Out Qos qos();
    }

    /**
     * Update the specified QoS in the dataCenter.
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/datacenters/123/qoss/123
     * ----
     *
     * For example with curl:
     *
     * [source]
     * ----
     * curl -u admin@internal:123456 -X PUT -H "content-type: application/xml" -d \
     * "<qos><name>321</name><description>321</description><max_iops>10</max_iops></qos>" \
     * https://engine/ovirt-engine/api/datacenters/123/qoss/123
     * ----
     *
     * You will receive response like this:
     *
     * [source,xml]
     * ----
     * <qos href="/ovirt-engine/api/datacenters/123/qoss/123" id="123">
     *   <name>321</name>
     *   <description>321</description>
     *   <max_iops>10</max_iops>
     *   <max_throughput>1</max_throughput>
     *   <type>storage</type>
     *   <data_center href="/ovirt-engine/api/datacenters/123" id="123"/>
     * </qos>
     * ----
     *
     * @author Ori Liel <oliel@redhat.com>
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 26 Apr 2017
     * @status updated
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
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
            optional(qos().name());
            optional(qos().outboundAverage());
            optional(qos().outboundAverageLinkshare());
            optional(qos().outboundAverageRealtime());
            optional(qos().outboundAverageUpperlimit());
            optional(qos().outboundBurst());
            optional(qos().outboundPeak());
        }

        /**
         * Updated QoS object.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @In @Out Qos qos();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Remove specified QoS from datacenter.
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/datacenters/123/qoss/123
     * ----
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
