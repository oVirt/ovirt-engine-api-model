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

import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.ClusterLevel;

/**
 * Provides information about the capabilities of different cluster levels supported by the engine. Version 4.0 of the
 * engine supports levels 4.0 and 3.6. Each of these levels support different sets of CPU types, for example. This
 * service provides that information. For example, to find what CPU types are supported by level 3.6 the you can send
 * a request like this:
 *
 * [source]
 * ----
 * GET /ovirt-engine/api/clusterlevels/3.6
 * ----
 *
 * That will return a <<types/cluster_level, ClusterLevel>> object containing the supported CPU types, and other
 * information describing the cluster level:
 *
 * [source,xml]
 * ----
 * <cluster_level id="3.6" href="/ovirt-engine/api/clusterlevel/3.6">
 *   <cpu_types>
 *     <cpu_type>
 *       <name>Intel Conroe Family</name>
 *       <level>3</level>
 *       <architecture>x86_64</architecture>
 *     </cpu_type>
 *     ...
 *   </cpu_types>
 *   ...
 * </cluster_level>
 * ----
 */
@Service
public interface ClusterLevelsService {
    /**
     * Lists the cluster levels supported by the system.
     */
    interface List {
        @Out ClusterLevel[] levels();
    }

    /**
     * Reference to the service that provides information about an specific cluster level.
     */
    @Service ClusterLevelService level(String id);
}
