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

package services.externalhostproviders;

import annotations.Area;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import services.ExternalProviderService;
import types.ExternalHostProvider;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * Represents an external host provider, such as Foreman or Satellite.
 *
 * See https://www.theforeman.org/ for more details on Foreman.
 * See https://access.redhat.com/products/red-hat-satellite for more details on Red Hat Satellite.
 *
 * @author Yaniv Bronhaim <ybronhei@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Service
@Area("Infrastructure")
public interface ExternalHostProviderService extends ExternalProviderService {
    /**
     * Get external host provider information
     *
     * Host provider, Foreman or Satellite, can be set as an external provider in ovirt. To see details about specific
     * host providers attached to ovirt use this API.
     *
     * For example, to get the details of host provider `123`, send a request like this:
     *
     * ....
     * GET /ovirt-engine/api/externalhostproviders/123
     * ....
     *
     * The response will be like this:
     *
     * [source,xml]
     * ----
     * <external_host_provider href="/ovirt-engine/api/externalhostproviders/123" id="123">
     *   <name>mysatellite</name>
     *   <requires_authentication>true</requires_authentication>
     *   <url>https://mysatellite.example.com</url>
     *   <username>admin</username>
     * </external_host_provider>
     * ----
     *
     * @author Yaniv Bronhaim <ybronhei@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface Get {
        @Out ExternalHostProvider provider();
    }

    /**
     * Update the specified external host provider in the system.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(provider().description());
            optional(provider().name());
            optional(provider().password());
            optional(provider().requiresAuthentication());
            optional(provider().username());
        }
        @In @Out ExternalHostProvider provider();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service ExternalComputeResourcesService computeResources();
    @Service ExternalDiscoveredHostsService discoveredHosts();
    @Service ExternalHostGroupsService hostGroups();
    @Service ExternalHostsService hosts();
}
