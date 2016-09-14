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
import types.Network;

@Service
@Area("Network")
public interface NetworkService {
    interface Get {
        @Out Network network();
    }

    /**
     * Updates a logical network.
     *
     * The `name`, `description`, `ip`, `vlan`, `stp` and `display` attributes can be updated.
     * ⁠
     * For example, to update the description of the logcial network `123` send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/networks/123
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <network>
     *   <description>My updated description</description>
     * </network>
     * ----
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @In @Out Network network();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes a logical network.
     *
     * For example, to remove the logical network `123` send a request like this:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/networks/123
     * ----
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service AssignedPermissionsService permissions();
    @Service AssignedVnicProfilesService vnicProfiles();
    @Service NetworkLabelsService networkLabels();
}
