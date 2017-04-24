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
import types.ExternalComputeResource;

/**
 * Manages a collection of external compute resources.
 *
 * Compute resource is a term of host external provider. The external provider also needs to know to where the
 * provisioned host needs to register. The login details of the engine is saved as a compute resource in the external
 * provider side.
 *
 * @author Yaniv Bronhaim <ybronhei@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Service
@Area("Infrastructure")
public interface ExternalComputeResourcesService {
    /**
     * Retrieves a list of external compute resources.
     *
     * For example, to retrieve the compute resources of external host provider `123`, send a request like this:
     *
     * ....
     * GET /ovirt-engine/api/externalhostproviders/123/computeresources
     * ....
     *
     * It will return a response like this:
     *
     * [source,xml]
     * ----
     * <external_compute_resources>
     *   <external_compute_resource href="/ovirt-engine/api/externalhostproviders/123/computeresources/234" id="234">
     *     <name>hostname</name>
     *     <provider>oVirt</provider>
     *     <url>https://address/api</url>
     *     <user>admin@internal</user>
     *     <external_host_provider href="/ovirt-engine/api/externalhostproviders/123" id="123"/>
     *    </external_compute_resource>
     *    ...
     * </external_compute_resources>
     * ----
     *
     * @author Yaniv Bronhaim <ybronhei@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface List {
        /**
         * List of external computer resources.
         *
         * @author Yaniv Bronhaim <ybronhei@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @Out ExternalComputeResource[] resources();

        /**
         * Sets the maximum number of resources to return. If not specified all the resources are returned.
         */
        @In Integer max();
    }

    /**
     * This service manages compute resource instance
     *
     * @author Yaniv Bronhaim <ybronhei@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Service ExternalComputeResourceService resource(String id);
}
