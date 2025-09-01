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

package services.externalhostproviders;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.ExternalHostGroup;

/**
 * This service manages a single host group information.
 *
 * Host group is a term of host provider - the host group includes provision details that are applied to new discovered
 * host. Information such as subnet, operating system, domain, etc.
 *
 * @author Yaniv Bronhaim <ybronhei@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Service
@Area("Infrastructure")
public interface ExternalHostGroupService {
    /**
     * Get host group information.
     *
     * For example, to get the details of hostgroup `234` of provider `123`, send a request like this:
     *
     * ```http
     * GET /ovirt-engine/api/externalhostproviders/123/hostgroups/234 HTTP/1.1
     * ```
     *
     * It will return a response like this:
     *
     * ```xml
     * <external_host_group href="/ovirt-engine/api/externalhostproviders/123/hostgroups/234" id="234">
     *   <name>rhel7</name>
     *   <architecture_name>x86_64</architecture_name>
     *   <domain_name>s.com</domain_name>
     *   <operating_system_name>RedHat 7.3</operating_system_name>
     *   <subnet_name>sat0</subnet_name>
     *   <external_host_provider href="/ovirt-engine/api/externalhostproviders/123" id="123"/>
     * </external_host_group>
     * ```
     *
     * @author Yaniv Bronhaim <ybronhei@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface Get extends Follow {
        /**
         * Host group information.
         *
         * @author Yaniv Bronhaim <ybronhei@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @Out ExternalHostGroup group();
    }
}
