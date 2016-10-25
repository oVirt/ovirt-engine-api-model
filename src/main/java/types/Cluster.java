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
     * Will respond:
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
     * With a request body:
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
     * In order to update the cluster compatibility version, all hosts in the cluster must support the new compatibility version.
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
     * When read, it returns the implicit `random` plus additional selected RNG sources. When written, the implicit
     * `random` RNG source cannot be removed.
     *
     * [IMPORTANT]
     * ====
     * Before version 4.1 of the engine, the set of required random number generators was completely controllable by the
     * administrator; any source could be added or removed, including the `random` source. But starting with version 4.1,
     * the `random` source will always be part of the set, and can't be removed.
     * ====
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 25 Oct 2016
     * @status updated_by_docs
     */
    RngSource[] requiredRngSources();

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
     * Type of switch to be used by all networks in given cluster.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 25 Oct 2016
     * @status updated_by_docs
     */
    SwitchType switchType();

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
}
