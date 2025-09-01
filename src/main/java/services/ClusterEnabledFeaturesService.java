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

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.ClusterFeature;

/**
 * Provides information about the additional features that are enabled for this cluster.
 * The features that are enabled are the available features for the cluster level
 *
 * @author Sahina Bose <sabose@redhat.com>
 * @date 04 Aug 2017
 * @status added
 * @since 4.1.6
 */
@Service
public interface ClusterEnabledFeaturesService {
    /**
     * Lists the additional features enabled for the cluster.
     *
     * For example, to get the features enabled for cluster `123` send a request like this:
     *
     * ```http
     * GET /ovirt-engine/api/clusters/123/enabledfeatures HTTP/1.1
     * ```
     *
     * This will return a list of features:
     *
     * ```xml
     * <enabled_features>
     *   <cluster_feature id="123">
     *      <name>test_feature</name>
     *   </cluster_feature>
     *   ...
     * </enabled_features>
     * ```
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
     * Enable an additional feature for a cluster.
     *
     * For example, to enable a feature `456` on cluster `123`, send a request like this:
     *
     * ```http
     * POST /ovirt-engine/api/clusters/123/enabledfeatures HTTP/1.1
     * ```
     *
     * The request body should look like this:
     *
     * ```xml
     * <cluster_feature id="456"/>
     * ```
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 04 Aug 2017
     * @status added
     * @since 4.1.6
     */
    interface Add {
        @In @Out ClusterFeature feature();

        @InputDetail
        default void inputDetail() {
            mandatory(feature().id());
        }
    }

    /**
     * A reference to the service that provides information about a specific
     * feature enabled for the cluster.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 04 Aug 2017
     * @status added
     * @since 4.1.6
     */
    @Service ClusterEnabledFeatureService feature(String id);
}
