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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.ExternalHostGroup;

/**
 * This service manages hostgroups.
 *
 * @author Yaniv Bronhaim <ybronhei@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Service
@Area("Infrastructure")
public interface ExternalHostGroupsService {
    /**
     * Get host groups list from external host provider.
     *
     * Host group is a term of host providers - the host group includes provision details. This API returns all possible
     * hostgroups exposed by the external provider.
     *
     * For example, to get the details of all host groups of provider `123`, send a request like this:
     *
     * ....
     * GET /ovirt-engine/api/externalhostproviders/123/hostgroups
     * ....
     *
     * The response will be like this:
     *
     * [source,xml]
     * ----
     * <external_host_groups>
     *   <external_host_group href="/ovirt-engine/api/externalhostproviders/123/hostgroups/234" id="234">
     *     <name>rhel7</name>
     *     <architecture_name>x86_64</architecture_name>
     *     <domain_name>example.com</domain_name>
     *     <operating_system_name>RedHat 7.3</operating_system_name>
     *     <subnet_name>sat0</subnet_name>
     *     <external_host_provider href="/ovirt-engine/api/externalhostproviders/123" id="123"/>
     *   </external_host_group>
     *   ...
     * </external_host_groups>
     * ----
     *
     * The order of the returned list of host groups isn't guaranteed.
     *
     * @author Yaniv Bronhaim <ybronhei@redhat.com>
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface List {
        /**
         * List of all hostgroups available for external host provider
         *
         * @author Yaniv Bronhaim <ybronhei@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @Out ExternalHostGroup[] groups();

        /**
         * Sets the maximum number of groups to return. If not specified all the groups are returned.
         */
        @In Integer max();
    }

    /**
     * This service manages hostgroup instance.
     *
     * @author Yaniv Bronhaim <ybronhei@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Service ExternalHostGroupService group(String id);
}
