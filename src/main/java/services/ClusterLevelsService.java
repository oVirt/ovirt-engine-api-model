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

import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.ClusterLevel;

/**
 * Provides information about the capabilities of different cluster levels supported by the engine. Version 4.0 of the
 * engine supports levels 4.0 and 3.6. Each of these levels support different sets of CPU types, for example. This
 * service provides that information.
 *
 * @author Aleksei Slaikovskii <aslaikov@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
public interface ClusterLevelsService {
    /**
     * Lists the cluster levels supported by the system.
     *
     * ```http
     * GET /ovirt-engine/api/clusterlevels
     * ```
     *
     * This will return a list of available cluster levels.
     *
     * ```xml
     * <cluster_levels>
     *   <cluster_level id="4.0">
     *      ...
     *   </cluster_level>
     *   ...
     * </cluster_levels>
     * ```
     *
     * The order of the returned cluster levels isn't guaranteed.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        /**
         * Retrieved cluster levels.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out ClusterLevel[] levels();
    }

    /**
     * Reference to the service that provides information about an specific cluster level.
     */
    @Service ClusterLevelService level(String id);
}
