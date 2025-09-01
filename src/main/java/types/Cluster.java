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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representation of a cluster.
 *
 * A JSON representation of a cluster:
 *
 * ```json
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
 *     "firewall_type" : "iptables",
 *     "ha_reservation" : "false",
 *     "ksm" : {
 *       "enabled" : "true",
 *       "merge_across_nodes" : "true"
 *     },
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
 *       "href" : "/ovirt-engine/api/clusters/234/enabledfeatures",
 *       "rel" : "enabledfeatures"
 *     }, {
 *       "href" : "/ovirt-engine/api/clusters/234/externalnetworkproviders",
 *       "rel" : "externalnetworkproviders"
 *     } ]
 *   } ]
 * }
 * ```
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
     * ```http
     * GET /ovirt-engine/api/clusters/123 HTTP/1.1
     * ```
     *
     * Will respond with:
     *
     * ```xml
     * <cluster>
     *   ...
     *   <version>
     *     <major>4</major>
     *     <minor>0</minor>
     *   </version>
     *   ...
     * </cluster>
     * ```
     *
     * To update the compatibility version, use:
     *
     * ```http
     * PUT /ovirt-engine/api/clusters/123 HTTP/1.1
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <cluster>
     *   <version>
     *     <major>4</major>
     *     <minor>1</minor>
     *   </version>
     * </cluster>
     * ```
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

    /**
     * Enable VNC encryption. Default value for this property is false.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 6 May 2020
     * @status added
     * @since 4.4
     */
    Boolean vncEncryption();

    /**
     * Chipset and BIOS type combination.
     *
     * This value is used as default for all virtual machines in the cluster having `biosType` set to `CLUSTER_DEFAULT`.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 28 Aug 2019
     * @status added
     * @since 4.4
     */
    BiosType biosType();

    /**
     * This property has no longer any relevance and has been deprecated. Its default value is true.
     *
     * @author Dana Elfassy <delfassy@redhat.com>
     * @date 3 January 2019
     * @status added
     */
    @Deprecated
    Boolean optionalReason();

    /**
     * This property has no longer any relevance and has been deprecated. Its default value is true,
     *
     * @author Dana Elfassy <delfassy@redhat.com>
     * @date 3 January 2019
     * @status added
     */
    @Deprecated
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
     * ```http
     * PUT /ovirt-engine/api/cluster/123 HTTP/1.1
     * ```
     *
     * With request body like this:
     *
     * ```xml
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
     * ```
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     */
    FencingPolicy fencingPolicy();

    /**
     * Reference to cluster-wide configuration of migration of a running virtual machine to another host.
     *
     * NOTE: API for querying migration policy by ID returned by this method is not implemented yet. Use
     * `/ovirt-engine/api/options/MigrationPolicies` to get a list of all migration policies with their IDs.
     *
     * @author Shmuel Leib Melamud <smelamud@redhat.com>
     * @date 18 Jul 2022
     * @status added
     */
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
     * ```http
     * PUT /ovirt-engine/api/clusters/123 HTTP/1.1
     * ```
     *
     * With a request body:
     *
     * ```xml
     * <cluster>
     *   <custom_scheduling_policy_properties>
     *     <property>
     *       <name>HighUtilization</name>
     *       <value>70</value>
     *     </property>
     *   </custom_scheduling_policy_properties>
     * </cluster>
     * ```
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
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @author Steve Goodman <sgoodman@redhat.com>
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 19 Jul 2021
     * @status updated_by_docs
     * @since 4.2
     */
    FirewallType firewallType();

    /**
     * The memory consumption threshold for logging audit log events.
     *
     * For percentage, an audit log event is logged if the used memory is more that the value specified.
     * For absolute value, an audit log event is logged when the the free memory falls below the value specified in MB.
     *
     * @author Nori Ravi <rnori@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 10 Aug 2018
     * @since 4.3
     * @status updated_by_docs
     */
    Integer logMaxMemoryUsedThreshold();

    /**
     * The memory consumption threshold type for logging audit log events.
     *
     * You can choose between 'percentage' and 'absolute_value_in_mb'.
     *
     * @author Nori Ravi <rnori@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 10 Aug 2018
     * @since 4.3
     * @status updated_by_docs
     */
    LogMaxMemoryUsedThresholdType logMaxMemoryUsedThresholdType();

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
     * xref:services/clusters/methods/add[adding the cluster].
     * This value may be overwritten for individual hosts during
     * xref:services/hosts/methods/add[adding the host].
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
     * The name of the tuned profile.
     *
     * link:https://access.redhat.com/documentation/en-us/red_hat_enterprise_linux/8/html/monitoring_and_managing_system_status_and_performance/customizing-tuned-profiles_monitoring-and-managing-system-status-and-performance[Tuned] profile
     * to set on all the hosts in the cluster. This is not mandatory
     * and relevant only for clusters with Gluster service.
     *
     * @author Ramesh Nachimuthu <rnachimu@rednat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @author Ori Liel <oliel@redhat.com>
     * @date 06 Oct 2017
     * @status  updated_by_docs
     * @since 4.1
     */
    String glusterTunedProfile();

    /**
     * FIPS mode of the cluster.
     *
     * link:https://access.redhat.com/documentation/en-us/red_hat_enterprise_linux/8/html/security_hardening/using-the-system-wide-cryptographic-policies_security-hardening[FIPS] mode represents the cluster's policy towards hosts.
     * Hosts added to the cluster will be checked to fulfill the cluster's FIPS mode, making them non-operational
     * if they do not.
     * Unless a value is explicity provided, new clusters are initialized by default to `UNDEFINED`.
     * This value changes automatically to the FIPS mode of the first host added to the cluster.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 16 Dec 2020
     * @status added
     * @since 4.4.5
     */
    FipsMode fipsMode();

    /**
     * Indicates if an upgrade has been started for the cluster.
     *
     * @author Scott Dickerson <sdickers@redhat.com>
     * @date 17 Mar 2022
     * @since 4.5.0
     */
    Boolean upgradeInProgress();

    /**
     * If an upgrade is in progress, the upgrade's reported percent complete.
     *
     * @author Scott Dickerson <sdickers@redhat.com>
     * @date 17 Mar 2022
     * @since 4.5.0
     */
    Integer upgradePercentComplete();

    /**
     * The upgrade correlation identifier. Use to correlate events detailing the cluster
     * upgrade to the upgrade itself.
     *
     * @author Scott Dickerson <sdickers@redhat.com>
     * @date 17 Mar 2022
     * @since 4.5.0
     */
    String upgradeCorrelationId();
}
