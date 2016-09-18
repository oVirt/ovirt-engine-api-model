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
import types.IscsiBond;

@Service
@Area("Storage")
public interface IscsiBondService {
    interface Get {
        @Out IscsiBond bond();
    }

    /**
     * Updates an iSCSI bond.
     *
     * Updating of an iSCSI bond can be done on the `name` and the `description` attributes only. For example, to
     * update the iSCSI bond `456` of data center `123`, send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/datacenters/123/iscsibonds/1234
     * ----
     *
     * The request body should look like this:
     *
     * [source,xml]
     * ----
     * <iscsi_bond>
     *    <name>mybond</name>
     *    <description>My iSCSI bond</description>
     * </iscsi_bond>
     * ----
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @In @Out IscsiBond bond();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes of an exisitng iSCSI bond.
     *
     * For example, to remove the iSCSI bond `456` send a request like this:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/datacenters/123/iscsibonds/456
     * ----
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service NetworksService networks();
    @Service StorageServerConnectionsService storageServerConnections();
}
