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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representation of a cluster.
 *
 * A JSON representation of a cluster:
 *
 * [source]
 * ----
 * {
 *   "cluster" : [ {
 *     "ballooning_enabled" : "false",
 *     "cpu" : {
 *       "architecture" : "x86_64",
 *       "type" : "Intel SandyBridge Family"
 *     },
 *     "custom_scheduling_policy_properties" : {
 *       "property" : [ {
 *         "name" : "HighUtilization",
 *         "value" : "80"
 *       }, {
 *         "name" : "CpuOverCommitDurationMinutes",
 *         "value" : "2"
 *       } ]
 *     },
 *     "error_handling" : {
 *       "on_error" : "migrate"
 *     },
 *     "fencing_policy" : {
 *       "enabled" : "true",
 *       "skip_if_connectivity_broken" : {
 *         "enabled" : "false",
 *         "threshold" : "50"
 *       },
 *       "skip_if_gluster_bricks_up" : "false",
 *       "skip_if_gluster_quorum_not_met" : "false",
 *       "skip_if_sd_active" : {
 *         "enabled" : "false"
 *       }
 *     },
 *     "gluster_service" : "false",
 *     "ha_reservation" : "false",
 *     "ksm" : {
 *       "enabled" : "true",
 *       "merge_across_nodes" : "true"
 *     },
 *     "maintenance_reason_required" : "false",
 *     "memory_policy" : {
 *       "over_commit" : {
 *         "percent" : "100"
 *       },
 *       "transparent_hugepages" : {
 *         "enabled" : "true"
 *       }
 *     },
 *     "migration" : {
 *       "auto_converge" : "inherit",
 *       "bandwidth" : {
 *         "assignment_method" : "auto"
 *       },
 *       "compressed" : "inherit",
 *       "policy" : {
 *         "id" : "00000000-0000-0000-0000-000000000000"
 *       }
 *     },
 *     "optional_reason" : "false",
 *     "required_rng_sources" : {
 *       "required_rng_source" : [ "random" ]
 *     },
 *     "switch_type" : "legacy",
 *     "threads_as_cores" : "false",
 *     "trusted_service" : "false",
 *     "tunnel_migration" : "false",
 *     "version" : {
 *       "major" : "4",
 *       "minor" : "1"
 *     },
 *     "virt_service" : "true",
 *     "data_center" : {
 *       "href" : "/ovirt-engine/api/datacenters/123",
 *       "id" : "123"
 *     },
 *     "mac_pool" : {
 *       "href" : "/ovirt-engine/api/macpools/456",
 *       "id" : "456"
 *     },
 *     "scheduling_policy" : {
 *       "href" : "/ovirt-engine/api/schedulingpolicies/789",
 *       "id" : "789"
 *     },
 *     "actions" : {
 *       "link" : [ {
 *         "href" : "/ovirt-engine/api/clusters/234/resetemulatedmachine",
 *         "rel" : "resetemulatedmachine"
 *       } ]
 *     },
 *     "name" : "Default",
 *     "description" : "The default server cluster",
 *     "href" : "/ovirt-engine/api/clusters/234",
 *     "id" : "234",
 *     "link" : [ {
 *       "href" : "/ovirt-engine/api/clusters/234/permissions",
 *       "rel" : "permissions"
 *     }, {
 *       "href" : "/ovirt-engine/api/clusters/234/cpuprofiles",
 *       "rel" : "cpuprofiles"
 *     }, {
 *       "href" : "/ovirt-engine/api/clusters/234/networkfilters",
 *       "rel" : "networkfilters"
 *     }, {
 *       "href" : "/ovirt-engine/api/clusters/234/networks",
 *       "rel" : "networks"
 *     }, {
 *       "href" : "/ovirt-engine/api/clusters/234/affinitygroups",
 *       "rel" : "affinitygroups"
 *     }, {
 *       "href" : "/ovirt-engine/api/clusters/234/glusterhooks",
 *       "rel" : "glusterhooks"
 *     }, {
 *       "href" : "/ovirt-engine/api/clusters/234/glustervolumes",
 *       "rel" : "glustervolumes"
 *     }, {
 *       "href" : "/ovirt-engine/api/clusters/234/externalnetworkproviders",
 *       "rel" : "externalnetworkproviders"
 *     } ]
 *   } ]
 * }
 * ----
 *
 * @author Piotr Kliczewski <pkliczew@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 06 Oct 2017
 * @status updated_by_docs
 */
@Type
public interface Cluster extends Identified {
    Cpu cpu();
    MemoryPolicy memoryPolicy();

    /**
     * The compatibility version of the cluster.
     *
     * All hosts in this cluster must support at least this compatibility version.
     *
     * For example:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/clusters/123
     * ----
     *
     * Will respond with:
     *
     * [source,xml]
     * ----
     * <cluster>
     *   ...
     *   <version>
     *     <major>4</major>
     *     <minor>0</minor>
     *   </version>
     *   ...
     * </cluster>
     * ----
     *
     * To update the compatibility version, use:
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
     *   <version>
     *     <major>4</major>
     *     <minor>1</minor>
     *   </version>
     * </cluster>
     * ----
     *
     * In order to update the cluster compatibility version, all hosts in the cluster must support
     * the new compatibility version.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 25 Oct 2016
     * @status updated_by_docs
     */
    Version version();

    Version[] supportedVersions();
    ErrorHandling errorHandling();
    Boolean virtService();
    Boolean glusterService();
    Boolean threadsAsCores();
    Boolean tunnelMigration();
    Boolean trustedService();
    Boolean haReservation();
    Boolean optionalReason();
    Boolean maintenanceReasonRequired();
    Boolean ballooningEnabled();
    Display display();
    Ksm ksm();
    SerialNumber serialNumber();

    /**
     * Set of random number generator (RNG) sources required from each host in the cluster.
     *
     * When read, it returns the implicit `urandom` (for cluster version 4.1 and higher) or `random` (for cluster
     * version 4.0 and lower) plus additional selected RNG sources. When written, the implicit `urandom` and `random`
     * RNG sources cannot be removed.
     *
     * [IMPORTANT]
     * ====
     * Before version 4.1 of the engine, the set of required random number generators was completely controllable by the
     * administrator; any source could be added or removed, including the `random` source. But starting with version 4.1,
     * the `urandom` and `random` sources will always be part of the set, and can't be removed.
     * ====
     *
     * [IMPORTANT]
     * ====
     * Engine version 4.1 introduces a new RNG source `urandom` that replaces `random` RNG source in clusters with
     * compatibility version 4.1 or higher.
     * ====
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 26 Oct 2016
     * @status added
     */
    RngSource[] requiredRngSources();

    /**
     * A custom fencing policy can be defined for a cluster.
     *
     * For example:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/cluster/123
     * ----
     *
     * With request body like this:
     *
     * [source,xml]
     * ----
     * <cluster>
     *   <fencing_policy>
     *     <enabled>true</enabled>
     *     <skip_if_sd_active>
     *       <enabled>false</enabled>
     *     </skip_if_sd_active>
     *     <skip_if_connectivity_broken>
     *       <enabled>false</enabled>
     *       <threshold>50</threshold>
     *     </skip_if_connectivity_broken>
     *   </fencing_policy>
     * </cluster>
     * ----
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    FencingPolicy fencingPolicy();
    MigrationOptions migration();

    /**
     * Custom scheduling policy properties of the cluster.
     * These optional properties override the properties of the
     * scheduling policy specified by the `scheduling_policy` link,
     * and apply only for this specific cluster.
     *
     * For example, to update the custom properties of the cluster,
     * send a request:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/clusters/123
     * ----
     *
     * With a request body:
     *
     * [source,xml]
     * ----
     * <cluster>
     *   <custom_scheduling_policy_properties>
     *     <property>
     *       <name>HighUtilization</name>
     *       <value>70</value>
     *     </property>
     *   </custom_scheduling_policy_properties>
     * </cluster>
     * ----
     *
     * Update operations using the `custom_scheduling_policy_properties` attribute
     * will not update the the properties of the scheduling policy specified by
     * the `scheduling_policy` link,
     * they will only be reflected on this specific cluster.
     *
     * @author Yanir Quinn <yquinn@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 25 Oct 2016
     * @status updated_by_docs
     * @since 4.0.6
     */
    Property[] customSchedulingPolicyProperties();

    /**
     * The type of switch to be used by all networks in given cluster.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 25 Oct 2016
     * @status updated_by_docs
     */
    SwitchType switchType();

    /**
     * The type of firewall to be used on hosts in this cluster.
     *
     * Up to version 4.1, it was always `iptables`. Since version 4.2, you can choose between `iptables` and `firewalld`.
     * For clusters with a compatibility version of 4.2 and higher, the default firewall type is `firewalld`.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 11 Aug 2017
     * @status updated_by_docs
     * @since 4.2
     */
    FirewallType firewallType();

    /**
     * Custom features that are enabled for the cluster.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     * @since 4.1.6
     */
    @Link ClusterFeature[] enabledFeatures();

    /**
     * Reference to the default scheduling policy used by
     * this cluster.
     *
     * NOTE: The scheduling policy properties are taken by
     * default from the referenced scheduling policy, but
     * they are overridden by the properties specified in
     * the `custom_scheduling_policy_properties` attribute
     * for this cluster.
     *
     * @author Yanir Quinn <yquinn@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 25 Oct 2016
     * @status updated_by_docs
     */
    @Link SchedulingPolicy schedulingPolicy();

    @Link DataCenter dataCenter();
    @Link Network managementNetwork();

    /**
     * A reference to the external network provider available in the cluster.
     *
     * If the automatic deployment of the external network provider is supported,
     * the networks of the referenced network provider are available on every host
     * in the cluster.
     * External network providers of a cluster can only be set during
     * <<services/clusters/methods/add,adding the cluster>>.
     * This value may be overwritten for individual hosts during
     * <<services/hosts/methods/add,adding the host>>.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     * @since 4.2
     */
    @Link ExternalProvider[] externalNetworkProviders();

    /**
     * A reference to the MAC pool used by this cluster.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 25 Oct 2016
     * @status updated_by_docs
     * @since 4.1
     */
    @Link MacPool macPool();

    @Link AffinityGroup[] affinityGroups();
    @Link CpuProfile[] cpuProfiles();
    @Link GlusterHook[] glusterHooks();
    @Link GlusterVolume[] glusterVolumes();
    @Link NetworkFilter[] networkFilters();
    @Link Network[] networks();
    @Link Permission[] permissions();

    /**
     * The name of the https://fedorahosted.org/tuned[tuned] profile
     * to set on all the hosts in the cluster. This is not mandatory
     * and relevant only for clusters with Gluster service.
     *
     * @author Ramesh Nachimuthu <rnachimu@rednat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     * @since 4.1
     */
    String glusterTunedProfile();
}
