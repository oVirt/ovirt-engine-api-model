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
 * A service to manage a specific permit of the role.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface PermitService {

    /**
     * Gets the information about the permit of the role.
     *
     * For example to retrieve the information about the permit with the id `456` of the role with the id `123`
     * send a request like this:
     *
     * ....
     * GET /ovirt-engine/api/roles/123/permits/456
     * ....
     *
     * [source,xml]
     * ----
     * <permit href="/ovirt-engine/api/roles/123/permits/456" id="456">
     *   <name>change_vm_cd</name>
     *   <administrative>false</administrative>
     *   <role href="/ovirt-engine/api/roles/123" id="123"/>
     * </permit>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get {
        /**
         * The permit of the role.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Permit permit();
    }

    /**
     * Removes the permit from the role.
     *
     * For example to remove the permit with id `456` from the role with id `123` send a request like this:
     *
     * ....
     * DELETE /ovirt-engine/api/roles/123/permits/456
     * ....
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
