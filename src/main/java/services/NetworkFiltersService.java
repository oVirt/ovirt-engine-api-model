/*
Copyright (c) 2016 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.NetworkFilter;
import types.Version;

/**
 * Represents a readonly network filters sub-collection.
 *
 * The network filter enables to filter packets send to/from the VM's nic according to defined rules.
 * For more information please refer to <<services/network-filter,NetworkFilter>> service documentation
 *
 * Network filters are supported in different versions, starting from version 3.0.
 *
 * A network filter is defined for each vnic profile.
 *
 * A vnic profile is defined for a specific network.
 *
 * A network can be assigned to several different clusters. In the future, each network will be defined in
 * cluster level.
 *
 * Currently, each network is being defined at data center level. Potential network filters for each network
 * are determined by the network's data center compatibility version V.
 * V must be >= the network filter version in order to configure this network filter for a specific network.
 * Please note, that if a network is assigned to cluster with a version supporting a network filter, the filter
 * may not be available due to the data center version being smaller then the network filter's version.
 *
 * Example of listing all of the supported network filters for a specific cluster:
 *
 * [source]
 * ----
 * GET http://localhost:8080/ovirt-engine/api/clusters/{cluster:id}/networkfilters
 * ----
 *
 * Output:
 *
 * [source,xml]
 * ----
 * <network_filters>
 *   <network_filter id="00000019-0019-0019-0019-00000000026c">
 *     <name>example-network-filter-a</name>
 *     <version>
 *       <major>4</major>
 *       <minor>0</minor>
 *       <build>-1</build>
 *       <revision>-1</revision>
 *     </version>
 *   </network_filter>
 *   <network_filter id="00000019-0019-0019-0019-00000000026b">
 *     <name>example-network-filter-b</name>
 *     <version>
 *       <major>4</major>
 *       <minor>0</minor>
 *       <build>-1</build>
 *       <revision>-1</revision>
 *     </version>
 *   </network_filter>
 *   <network_filter id="00000019-0019-0019-0019-00000000026a">
 *     <name>example-network-filter-a</name>
 *     <version>
 *       <major>3</major>
 *       <minor>0</minor>
 *       <build>-1</build>
 *       <revision>-1</revision>
 *     </version>
 *   </network_filter>
 * </network_filters>
 * ----
 */
@Service
public interface NetworkFiltersService {

    /**
     * Retrieves the representations of the network filters.
     */
    interface List {
        @Out NetworkFilter[] filters();
    }

    @Service NetworkFilterService networkFilter(String id);
}
