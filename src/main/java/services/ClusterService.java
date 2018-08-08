/*
Copyright (c) 2015-2016 Red Hat, Inc.

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
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import services.gluster.GlusterHooksService;
import services.gluster.GlusterVolumesService;
import types.Cluster;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;
 /**
  * A service to manage a specific cluster.
  *
  * @author Yaniv Bronheim <ybronhei@redhat.com>
  * @author Tahlia Richardson <trichard@redhat.com>
  * @date 06 Oct 2017
  * @status updated_by_docs
  */
@Service
@Area("Virtualization")
public interface ClusterService {
    /**
     * Gets information about the cluster.
     *
     * An example of getting a cluster:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/clusters/123
     * ----
     *
     * [source,xml]
     * ----
     * <cluster href="/ovirt-engine/api/clusters/123" id="123">
     *   <actions>
     *     <link href="/ovirt-engine/api/clusters/123/resetemulatedmachine" rel="resetemulatedmachine"/>
     *   </actions>
     *   <name>Default</name>
     *   <description>The default server cluster</description>
     *   <link href="/ovirt-engine/api/clusters/123/networks" rel="networks"/>
     *   <link href="/ovirt-engine/api/clusters/123/permissions" rel="permissions"/>
     *   <link href="/ovirt-engine/api/clusters/123/glustervolumes" rel="glustervolumes"/>
     *   <link href="/ovirt-engine/api/clusters/123/glusterhooks" rel="glusterhooks"/>
     *   <link href="/ovirt-engine/api/clusters/123/affinitygroups" rel="affinitygroups"/>
     *   <link href="/ovirt-engine/api/clusters/123/cpuprofiles" rel="cpuprofiles"/>
     *   <ballooning_enabled>false</ballooning_enabled>
     *   <cpu>
     *     <architecture>x86_64</architecture>
     *     <type>Intel Penryn Family</type>
     *   </cpu>
     *   <error_handling>
     *     <on_error>migrate</on_error>
     *   </error_handling>
     *   <fencing_policy>
     *     <enabled>true</enabled>
     *     <skip_if_connectivity_broken>
     *       <enabled>false</enabled>
     *       <threshold>50</threshold>
     *     </skip_if_connectivity_broken>
     *     <skip_if_sd_active>
     *       <enabled>false</enabled>
     *     </skip_if_sd_active>
     *   </fencing_policy>
     *   <gluster_service>false</gluster_service>
     *   <ha_reservation>false</ha_reservation>
     *   <ksm>
     *     <enabled>true</enabled>
     *     <merge_across_nodes>true</merge_across_nodes>
     *   </ksm>
     *   <maintenance_reason_required>false</maintenance_reason_required>
     *   <memory_policy>
     *     <over_commit>
     *       <percent>100</percent>
     *     </over_commit>
     *     <transparent_hugepages>
     *       <enabled>true</enabled>
     *     </transparent_hugepages>
     *   </memory_policy>
     *   <migration>
     *     <auto_converge>inherit</auto_converge>
     *     <bandwidth>
     *       <assignment_method>auto</assignment_method>
     *     </bandwidth>
     *     <compressed>inherit</compressed>
     *   </migration>
     *   <optional_reason>false</optional_reason>
     *   <required_rng_sources>
     *     <required_rng_source>random</required_rng_source>
     *   </required_rng_sources>
     *   <scheduling_policy href="/ovirt-engine/api/schedulingpolicies/456" id="456"/>
     *   <threads_as_cores>false</threads_as_cores>
     *   <trusted_service>false</trusted_service>
     *   <tunnel_migration>false</tunnel_migration>
     *   <version>
     *     <major>4</major>
     *     <minor>0</minor>
     *   </version>
     *   <virt_service>true</virt_service>
     *   <data_center href="/ovirt-engine/api/datacenters/111" id="111"/>
     * </cluster>
     * ----
     *
     * @author Yaniv Bronhaim <ybronhei@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    interface Get extends Follow {
        @Out Cluster cluster();

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
     * Updates information about the cluster.
     *
     * Only the specified fields are updated; others remain unchanged.
     *
     * For example, to update the cluster's CPU:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/clusters/123
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <cluster>
     *   <cpu>
     *     <type>Intel Haswell-noTSX Family</type>
     *   </cpu>
     * </cluster>
     * ----
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(cluster().ballooningEnabled());
            optional(cluster().cpu().architecture());
            optional(cluster().cpu().type());
            optional(cluster().description());
            optional(cluster().display().proxy());
            optional(cluster().errorHandling().onError());
            optional(cluster().glusterService());
            optional(cluster().haReservation());
            optional(cluster().ksm().enabled());
            optional(cluster().memoryPolicy().overCommit().percent());
            optional(cluster().memoryPolicy().transparentHugePages().enabled());
            optional(cluster().name());
            optional(cluster().threadsAsCores());
            optional(cluster().trustedService());
            optional(cluster().tunnelMigration());
            optional(cluster().version().major());
            optional(cluster().version().minor());
            optional(cluster().virtService());
            or(optional(cluster().schedulingPolicy().id()), optional(cluster().schedulingPolicy().name()));
        }
        @In @Out Cluster cluster();

        /**
         * Indicates if the update should be performed asynchronously.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 06 Oct 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Removes the cluster from the system.
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/clusters/00000000-0000-0000-0000-000000000000
     * ----
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 06 Oct 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    interface ResetEmulatedMachine {
        /**
         * Indicates if the reset should be performed asynchronously.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 06 Oct 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

     /**
      * Synchronizes all networks on the cluster.
      *
      * [source]
      * ----
      * POST /ovirt-engine/api/clusters/123/syncallnetworks
      * ----
      *
      * With a request body like this:
      *
      * [source,xml]
      * ----
      * <action/>
      * ----
      *
      * @author Eitan Raviv <eraviv@redhat.com>
      * @author Avital Pinnick <apinnick@redhat.com>
      * @date 17 April 2018
      * @since 4.2.6
      * @status updated_by_docs
      */
     interface SyncAllNetworks {
         /**
          * Indicates if the action should be performed asynchronously.
          *
          * @author Eitan Raviv <eraviv@redhat.com>
          * @author Avital Pinnick <apinnick@redhat.com>
          * @date 17 April 2018
          * @status updated_by_docs
          */
         @In Boolean async();
     }

     /**
     * A reference to the service that manages affinity groups.
     *
     * @author Yaniv Bronheim <ybronhei@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    @Service AffinityGroupsService affinityGroups();

    /**
     * A reference to the service that manages assigned CPU profiles for the cluster.
     *
     * @author Yaniv Bronheim <ybronhei@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    @Service AssignedCpuProfilesService cpuProfiles();

    /**
     * A reference to the service that manages assigned networks for the cluster.
     *
     * @author Yaniv Bronheim <ybronhei@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    @Service ClusterNetworksService networks();

    /**
     * A reference to permissions.
     *
     * @author Yaniv Bronheim <ybronhei@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    @Service AssignedPermissionsService permissions();

    /**
     * A reference to the service that manages the Gluster hooks for the cluster.
     *
     * @author Yaniv Bronheim <ybronhei@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    @Service GlusterHooksService glusterHooks();

    /**
     * A reference to the service that manages Gluster volumes for the cluster.
     *
     * @author Yaniv Bronheim <ybronhei@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    @Service GlusterVolumesService glusterVolumes();

    /**
     * A sub-collection with all the supported network filters for the cluster.
     *
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    @Service NetworkFiltersService networkFilters();

    /**
     * A reference to the service that manages the collection of enabled features for the cluster.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     * @since 4.1.6
     */
    @Service ClusterEnabledFeaturesService enabledFeatures();

     /**
      * A reference to the service that manages the collection of external network providers.
      *
      * @author Dominik Holler <dholler@redhat.com>
      * @author Tahlia Richardson <trichard@redhat.com>
      * @date 06 Oct 2017
      * @status updated_by_docs
      * @since 4.2
      */
     @Service
     ClusterExternalProvidersService externalNetworkProviders();
}
