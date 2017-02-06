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

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;
/**
 * A service to manage clusters.
 *
 * @author Yaniv Bronheim <ybronhei@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface ClustersService {
    /**
     * Creates a new cluster.
     *
     * This requires the `name`, `cpu.type` and `data_center` attributes. Identify the data center with either the `id`
     * or `name` attributes.
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
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(cluster().cpu().type());
            mandatory(cluster().name());
            mandatory(cluster().version().major());
            mandatory(cluster().version().minor());
            or(mandatory(cluster().dataCenter().id()), mandatory(cluster().dataCenter().name()));
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
        }
        @In @Out Cluster cluster();
    }

    interface List {
        @Out Cluster[] clusters();

        /**
         * Sets the maximum number of clusters to return. If not specified all the clusters are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned clusters.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    /**
     * Reference to the service that manages a specific cluster.
     *
     * @author Yaniv Bronheim <ybronhei@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service ClusterService cluster(String id);
}
