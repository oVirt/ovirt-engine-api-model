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

import types.ClusterFeature;

/**
 * Represents a feature enabled for the cluster.
 *
 * @author Sahina Bose <sabose@redhat.com>
 * @date 04 Aug 2017
 * @status added
 * @since 4.1.6
 */
@Service
public interface ClusterEnabledFeatureService {
    /**
     * Provides the information about the cluster feature enabled.
     *
     * For example, to find details of the enabled feature `456` for cluster `123`, send a request like this:
     *
     * ```http
     * GET /ovirt-engine/api/clusters/123/enabledfeatures/456 HTTP/1.1
     * ```
     *
     * That will return a xref:types/cluster_feature[ClusterFeature] object containing the name:
     *
     * ```xml
     * <cluster_feature id="456">
     *   <name>libgfapi_supported</name>
     * </cluster_feature>
     * ```
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 04 Aug 2017
     * @status added
     * @since 4.1.6
     */
    interface Get extends Follow {
        /**
         * Retrieved cluster feature that's enabled.
         *
         * @author Sahina Bose <sabose@redhat.com>
         * @date 04 Aug 2017
         * @status added
         * @since 4.1.6
         */
        @Out ClusterFeature feature();
    }

    /**
     * Disables a cluster feature.
     *
     * For example, to disable the feature `456` of cluster `123` send a request like this:
     *
     * ```http
     * DELETE /ovirt-engine/api/clusters/123/enabledfeatures/456 HTTP/1.1
     * ```
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 04 Aug 2017
     * @status added
     * @since 4.1.6
     */
    interface Remove {
    }
}
