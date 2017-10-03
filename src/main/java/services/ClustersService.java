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
import types.Cluster;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;
/**
 * A service to manage clusters.
 *
 * @author Yaniv Bronheim <ybronhei@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 06 Oct 2017
 * @status updated_by_docs
 */
@Service
@Area("Virtualization")
public interface ClustersService {
    /**
     * Creates a new cluster.
     *
     * This requires the `name`, `cpu.type`, and `data_center` attributes. Identify the data center with either the `id`
     * or `name` attribute.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <cluster>
     *   <name>mycluster</name>
     *   <cpu>
     *     <type>Intel Penryn Family</type>
     *   </cpu>
     *   <data_center id="123"/>
     * </cluster>
     * ----
     *
     * To create a cluster with an external network provider to be deployed on
     * every host that is added to the cluster, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters
     * ----
     *
     * With a request body containing a reference to the desired provider:
     *
     * [source,xml]
     * ----
     * <cluster>
     *   <name>mycluster</name>
     *   <cpu>
     *     <type>Intel Penryn Family</type>
     *   </cpu>
     *   <data_center id="123"/>
     *   <external_network_providers>
     *     <external_provider id="321"/>
     *   </external_network_providers>
     * </cluster>
     * ----
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Dominik Holler <dholler@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(cluster().name());
            or(mandatory(cluster().dataCenter().id()), mandatory(cluster().dataCenter().name()));
            optional(cluster().version().major());
            optional(cluster().version().minor());
            mandatory(cluster().cpu().type());
            optional(cluster().ballooningEnabled());
            optional(cluster().comment());
            optional(cluster().cpu().architecture());
            optional(cluster().description());
            optional(cluster().display().proxy());
            optional(cluster().errorHandling().onError());
            optional(cluster().fencingPolicy().enabled());
            optional(cluster().fencingPolicy().skipIfConnectivityBroken().enabled());
            optional(cluster().fencingPolicy().skipIfConnectivityBroken().threshold());
            optional(cluster().fencingPolicy().skipIfSdActive().enabled());
            optional(cluster().glusterService());
            optional(cluster().haReservation());
            optional(cluster().ksm().enabled());
            optional(cluster().ksm().mergeAcrossNodes());
            optional(cluster().maintenanceReasonRequired());
            optional(cluster().memoryPolicy().overCommit().percent());
            optional(cluster().memoryPolicy().transparentHugePages().enabled());
            optional(cluster().threadsAsCores());
            optional(cluster().trustedService());
            optional(cluster().tunnelMigration());
            optional(cluster().virtService());
            or(optional(cluster().managementNetwork().id()), optional(cluster().managementNetwork().name()));
            or(optional(cluster().schedulingPolicy().id()), optional(cluster().schedulingPolicy().name()));
            optional(cluster().externalNetworkProviders()[COLLECTION].id());
        }
        @In @Out Cluster cluster();
    }

    /**
     * Returns the list of clusters of the system.
     *
     * The order of the returned clusters is guaranteed only if the `sortby` clause is included in the
     * `search` parameter.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    interface List {
        @Out Cluster[] clusters();

        /**
         * Sets the maximum number of clusters to return. If not specified, all the clusters are returned.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 06 Oct 2017
         * @status updated_by_docs
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned clusters.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 06 Oct 2017
         * @status updated_by_docs
         */
        @In String search();

        /**
         * Indicates if the search should be performed taking case into account.
         * The default value is `true`, which means that case is taken into account. To search
         * ignoring case, set it to `false`.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 06 Oct 2017
         * @status updated_by_docs
         */
        @In Boolean caseSensitive();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 06 Oct 2017
         * @status updated_by_docs
         */
        @In Boolean filter();
    }

    /**
     * A reference to the service that manages a specific cluster.
     *
     * @author Yaniv Bronheim <ybronhei@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    @Service ClusterService cluster(String id);
}
