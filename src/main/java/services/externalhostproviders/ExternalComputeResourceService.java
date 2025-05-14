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
import types.ExternalComputeResource;

/**
 * Manages a single external compute resource.
 *
 * Compute resource is a term of host external provider. The external provider also needs to know to where the
 * provisioned host needs to register. The login details of the engine are saved as a compute resource  in the external
 * provider side.
 *
 * @author Yaniv Bronhaim <ybronhei@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Service
@Area("Infrastructure")
public interface ExternalComputeResourceService {
    /**
     * Retrieves external compute resource details.
     *
     * For example, to get the details of compute resource `234` of provider `123`, send a request like this:
     *
     * ```http
     * GET /ovirt-engine/api/externalhostproviders/123/computeresources/234
     * ```
     *
     * It will return a response like this:
     *
     * ```xml
     * <external_compute_resource href="/ovirt-engine/api/externalhostproviders/123/computeresources/234" id="234">
     *   <name>hostname</name>
     *   <provider>oVirt</provider>
     *   <url>https://hostname/api</url>
     *   <user>admin@internal</user>
     *   <external_host_provider href="/ovirt-engine/api/externalhostproviders/123" id="123"/>
     * </external_compute_resource>
     * ```
     *
     * @author Yaniv Bronhaim <ybronhei@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface Get extends Follow {
        /**
         * External compute resource information
         *
         * @author Yaniv Bronhaim <ybronhei@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @Out ExternalComputeResource resource();
    }
}
