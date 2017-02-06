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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Qos;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("SLA")
public interface QossService {
    /**
     * Add a new QoS to the dataCenter.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
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
        @In @Out Qos qos();
    }

    interface List {
        @Out Qos[] qoss();

        /**
         * Sets the maximum number of QoS descriptors to return. If not specified all the descriptors are returned.
         */
        @In Integer max();
    }

    @Service QosService qos(String id);
}
