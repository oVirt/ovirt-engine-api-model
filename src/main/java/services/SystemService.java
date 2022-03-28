/*
Copyright (c) 2015-2016 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Root;
import org.ovirt.api.metamodel.annotations.Service;
import services.aaa.DomainsService;
import services.aaa.GroupsService;
import services.aaa.UsersService;
import services.externalhostproviders.EngineKatelloErrataService;
import services.externalhostproviders.ExternalHostProvidersService;
import services.openstack.OpenstackImageProvidersService;
import services.openstack.OpenstackNetworkProvidersService;
import services.openstack.OpenstackVolumeProvidersService;
import types.Api;

@Root
@Area("Infrastructure")
public interface SystemService {

    /**
     * Returns basic information describing the API, like the product name, the version number and a summary of the
     * number of relevant objects.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api
     * ----
     *
     * We get following response:
     *
     * [source,xml]
     * ----
     * <api>
     *   <link rel="capabilities" href="/api/capabilities"/>
     *   <link rel="clusters" href="/api/clusters"/>
     *   <link rel="clusters/search" href="/api/clusters?search={query}"/>
     *   <link rel="datacenters" href="/api/datacenters"/>
     *   <link rel="datacenters/search" href="/api/datacenters?search={query}"/>
     *   <link rel="events" href="/api/events"/>
     *   <link rel="events/search" href="/api/events?search={query}"/>
     *   <link rel="hosts" href="/api/hosts"/>
     *   <link rel="hosts/search" href="/api/hosts?search={query}"/>
     *   <link rel="networks" href="/api/networks"/>
     *   <link rel="roles" href="/api/roles"/>
     *   <link rel="storagedomains" href="/api/storagedomains"/>
     *   <link rel="storagedomains/search" href="/api/storagedomains?search={query}"/>
     *   <link rel="tags" href="/api/tags"/>
     *   <link rel="templates" href="/api/templates"/>
     *   <link rel="templates/search" href="/api/templates?search={query}"/>
     *   <link rel="users" href="/api/users"/>
     *   <link rel="groups" href="/api/groups"/>
     *   <link rel="domains" href="/api/domains"/>
     *   <link rel="vmpools" href="/api/vmpools"/>
     *   <link rel="vmpools/search" href="/api/vmpools?search={query}"/>
     *   <link rel="vms" href="/api/vms"/>
     *   <link rel="vms/search" href="/api/vms?search={query}"/>
     *   <product_info>
     *     <name>oVirt Engine</name>
     *     <vendor>ovirt.org</vendor>
     *     <version>
     *       <build>4</build>
     *       <full_version>4.0.4</full_version>
     *       <major>4</major>
     *       <minor>0</minor>
     *       <revision>0</revision>
     *     </version>
     *   </product_info>
     *   <special_objects>
     *     <blank_template href="/ovirt-engine/api/templates/00000000-0000-0000-0000-000000000000" id="00000000-0000-0000-0000-000000000000"/>
     *     <root_tag href="/ovirt-engine/api/tags/00000000-0000-0000-0000-000000000000" id="00000000-0000-0000-0000-000000000000"/>
     *   </special_objects>
     *   <summary>
     *     <hosts>
     *       <active>0</active>
     *       <total>0</total>
     *     </hosts>
     *     <storage_domains>
     *       <active>0</active>
     *       <total>1</total>
     *     </storage_domains>
     *     <users>
     *       <active>1</active>
     *       <total>1</total>
     *     </users>
     *     <vms>
     *       <active>0</active>
     *       <total>0</total>
     *     </vms>
     *   </summary>
     *   <time>2016-09-14T12:00:48.132+02:00</time>
     * </api>
     * ----
     *
     * The entry point provides a user with links to the collections in a
     * virtualization environment. The `rel` attribute of each collection link
     * provides a reference point for each link.
     *
     * The entry point also contains other data such as `product_info`,
     * `special_objects` and `summary`.
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Get extends Follow {
        @Out Api api();
    }

    interface ReloadConfigurations {
        /**
         * Indicates if the reload should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Reference to the service that provides information about the cluster levels supported by the system.
     */
    @Service ClusterLevelsService clusterLevels();

    @Service BookmarksService bookmarks();
    @Service ClustersService clusters();
    @Service CpuProfilesService cpuProfiles();
    @Service DataCentersService dataCenters();
    @Service DiskProfilesService diskProfiles();
    @Service DisksService disks();
    @Service DomainsService domains();
    @Service EventsService events();
    @Service ExternalHostProvidersService externalHostProviders();

    /**
     * Reference to service facilitating import of external virtual machines.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    @Service ExternalVmImportsService externalVmImports();

    /**
     * Reference to service facilitating import of external templates.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 12 May 2021
     * @status added
     * @since 4.4.7
     */
    @Service ExternalTemplateImportsService externalTemplateImports();

    @Service GroupsService groups();
    @Service HostsService hosts();
    @Service IconsService icons();
    @Service InstanceTypesService instanceTypes();

    /**
     * List all the jobs monitored by the engine.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service JobsService jobs();

    /**
     * List all known affinity labels.
     */
    @Service AffinityLabelsService affinityLabels();
    @Service MacPoolsService macPools();

    /**
     * Network filters will enhance the admin ability to manage the network packets traffic from/to the participated
     * VMs.
     */
    @Service NetworkFiltersService networkFilters();
    @Service NetworksService networks();
    @Service OpenstackImageProvidersService openstackImageProviders();
    @Service OpenstackNetworkProvidersService openstackNetworkProviders();
    @Service OpenstackVolumeProvidersService openstackVolumeProviders();
    @Service OperatingSystemsService operatingSystems();
    @Service RolesService roles();
    @Service SchedulingPoliciesService schedulingPolicies();
    @Service SchedulingPolicyUnitsService schedulingPolicyUnits();
    @Service StorageDomainsService storageDomains();
    @Service StorageServerConnectionsService storageConnections();

    /**
     * List the available Katello errata assigned to the engine.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service EngineKatelloErrataService katelloErrata();
    @Service SystemPermissionsService permissions();
    @Service TagsService tags();
    @Service TemplatesService templates();
    @Service UsersService users();
    @Service VmPoolsService vmPools();
    @Service VmsService vms();
    @Service VnicProfilesService vnicProfiles();

    /**
     * List of all image transfers being performed for image I/O in oVirt.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    @Service ImageTransfersService imageTransfers();

    /**
     * Reference to the service that provides values of configuration options of the system.
     *
     * @author Miroslava Voglova <mvoglova@redhat.com>
     * @date 18 Sep 2017
     * @status added
     * @since 4.2
     */
    @Service SystemOptionsService options();
}
