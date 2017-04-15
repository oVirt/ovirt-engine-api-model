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
import types.Qos;

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
    interface Add {
        @In @Out Qos qos();
    }

    /**
     * Returns the list of _quality of service_ configurations available in the data center.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/datacenter/123/qoss
     * ----
     *
     * You will get response which will look like this:
     *
     * [source, xml]
     * ----
     * <qoss>
     *   <qos href="/ovirt-engine/api/datacenters/123/qoss/1" id="1">...</qos>
     *   <qos href="/ovirt-engine/api/datacenters/123/qoss/2" id="2">...</qos>
     *   <qos href="/ovirt-engine/api/datacenters/123/qoss/3" id="3">...</qos>
     * </qoss>
     * ----
     *
     * The returned list of quality of service configurations isn't guaranteed.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface List {
        @Out Qos[] qoss();

        /**
         * Sets the maximum number of QoS descriptors to return. If not specified all the descriptors are returned.
         */
        @In Integer max();
    }

    @Service QosService qos(String id);
}
