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

package services;

import annotations.Area;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Permit;

/**
 * Represents a permits sub-collection of the specific role.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface PermitsService {
    /**
     * Adds a permit to the role. The permit name can be retrieved from the <<services/cluster_levels>> service.
     *
     * For example to assign a permit `create_vm` to the role with id `123` send a request like this:
     *
     * ....
     * POST /ovirt-engine/api/roles/123/permits
     * ....
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <permit>
     *   <name>create_vm</name>
     * </permit>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        /**
         * The permit to add.
         */
        @In @Out Permit permit();

    }

    /**
     * List the permits of the role.
     *
     * For example to list the permits of the role with the id `123` send a request like this:
     *
     * ....
     * GET /ovirt-engine/api/roles/123/permits
     * ....
     *
     * [source,xml]
     * ----
     * <permits>
     *   <permit href="/ovirt-engine/api/roles/123/permits/5" id="5">
     *     <name>change_vm_cd</name>
     *     <administrative>false</administrative>
     *     <role href="/ovirt-engine/api/roles/123" id="123"/>
     *   </permit>
     *   <permit href="/ovirt-engine/api/roles/123/permits/7" id="7">
     *     <name>connect_to_vm</name>
     *     <administrative>false</administrative>
     *     <role href="/ovirt-engine/api/roles/123" id="123"/>
     *   </permit>
     * </permits>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        /**
         * List of permits.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Permit[] permits();

        /**
         * Sets the maximum number of permits to return. If not specified all the permits are returned.
         */
        @In Integer max();
    }

    /**
     * Sub-resource locator method, returns individual permit resource on which the remainder of the URI is dispatched.
     */
    @Service PermitService permit(String id);
}
