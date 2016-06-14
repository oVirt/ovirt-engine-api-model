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
     */
    interface Get {
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
    @Service GroupsService groups();
    @Service HostsService hosts();
    @Service IconsService icons();
    @Service InstanceTypesService instanceTypes();
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
    @Service EngineKatelloErrataService katelloErrata();
    @Service SystemPermissionsService permissions();
    @Service TagsService tags();
    @Service TemplatesService templates();
    @Service UsersService users();
    @Service VmPoolsService vmPools();
    @Service VmsService vms();
    @Service VnicProfilesService vnicProfiles();
}
