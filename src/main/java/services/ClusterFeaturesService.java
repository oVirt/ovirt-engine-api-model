/*
Copyright (c) 2017 Red Hat, Inc.

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

import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.ClusterFeature;

/**
 * Provides information about the cluster features that are supported by a cluster level.
 *
 * @author Sahina Bose <sabose@redhat.com>
 * @date 04 Aug 2017
 * @status added
 * @since 4.1.6
 */
@Service
public interface ClusterFeaturesService {
    /**
     * Lists the cluster features supported by the cluster level.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/clusterlevels/4.1/clusterfeatures
     * ----
     *
     * This will return a list of cluster features supported by the cluster level:
     *
     * [source,xml]
     * ----
     * <cluster_features>
     *   <cluster_feature id="123">
     *      <name>test_feature</name>
     *   </cluster_feature>
     *   ...
     * </cluster_features>
     * ----
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 04 Aug 2017
     * @status added
     * @since 4.1.6
     */
    interface List extends Follow {
        /**
         * Retrieved features.
         *
         * @author Sahina Bose <sabose@redhat.com>
         * @date 04 Aug 2017
         * @status added
         * @since 4.1.6
         */
        @Out ClusterFeature[] features();
    }

    /**
     * Reference to the service that provides information about a specific feature.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 04 Aug 2017
     * @status added
     * @since 4.1.6
     */
    @Service ClusterFeatureService feature(String id);
}
