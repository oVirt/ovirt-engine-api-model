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
    RngSource[] requiredRngSources();
    FencingPolicy fencingPolicy();
    MigrationOptions migration();

    /**
     * Custom scheduling policy properties of the cluster.
     * These optional properties override the properties of the
     * scheduling policy specified by the `scheduling_policy` link
     * and apply only for this specific cluster.
     *
     * For example, to update the custom properties of the cluster
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
     * Update operations using `custom_scheduling_policy_properties` attribute
     * will not update the the properties of the scheduling policy specified by
     * the `scheduling_policy` link,
     * they will only be reflected on this specific cluster.
     *
     * @author Yanir Quinn <yquinn@redhat.com>
     * @date 29 Aug 2016
     * @status added
     * @since 4.0.6
     */
    Property[] customSchedulingPolicyProperties();

    /**
     * Type of switch which will be used by all networks in given cluster.
     */
    SwitchType switchType();

    /**
     * Reference to the scheduling policy used by default by
     * this cluster.
     *
     * NOTE: The scheduling policy properties are taken by
     * default from the referenced scheduling policy, but
     * they are overridden by the properties specified in
     * the `custom_scheduling_policy_properties` attribute
     * for this cluster.
     *
     * @author Yanir Quinn <yquinn@redhat.com>
     * @date 29 Aug 2016
     * @status added
     */
    @Link SchedulingPolicy schedulingPolicy();

    @Link DataCenter dataCenter();
    @Link Network managementNetwork();

    /**
     * A reference to the MAC pool used by this cluster.
     *
     * NOTE: This will be available only with version 4.0.1 or newer of the
     * engine.
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
