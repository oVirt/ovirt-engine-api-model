/*
Copyright (c) 2016 Red Hat, Inc.

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

import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.ClusterLevel;

/**
 * Provides information about a specific cluster level. See the <<services/cluster_levels,ClusterLevels>> service for
 * more information.
 */
@Service
public interface ClusterLevelService {
    /**
     * Provides the information about the capabilities of the specific cluster level managed by this service.
     *
     * For example, to find what CPU types are supported by level 3.6 you can send a request like this:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/clusterlevels/3.6
     * ----
     *
     * That will return a <<types/cluster_level, ClusterLevel>> object containing the supported CPU types, and other
     * information which describes the cluster level:
     *
     * [source,xml]
     * ----
     * <cluster_level id="3.6">
     *   <cpu_types>
     *     <cpu_type>
     *       <name>Intel Nehalem Family</name>
     *       <level>3</level>
     *       <architecture>x86_64</architecture>
     *     </cpu_type>
     *     ...
     *   </cpu_types>
     *   <permits>
     *     <permit id="1">
     *       <name>create_vm</name>
     *       <administrative>false</administrative>
     *     </permit>
     *     ...
     *   </permits>
     * </cluster_level>
     * ----
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @author Steve Goodman <sgoodman@redhat.com>
     * @date 04 Oct 2018
     * @status updated_by_docs
     */
    interface Get extends Follow {
        /**
         * Retreived cluster level.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out ClusterLevel level();
    }

    /**
     * Reference to the service that manages the collection of supported features for this cluster level.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 04 Aug 2017
     * @status added
     * @since 4.1.6
     */
    @Service ClusterFeaturesService clusterFeatures();
}
