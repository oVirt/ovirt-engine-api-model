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
import types.StorageConnection;

@Service
@Area("Storage")
public interface StorageServerConnectionService {
    interface Get {
        @Out StorageConnection conection();
    }

    /**
     * Updates the storage connection.
     *
     * For example, to change the address of the storage server send a request like this:
     *
     * [source,xml]
     * ----
     * PUT /ovirt-engine/api/storageconnections/123
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <storage_connection>
     *   <address>mynewnfs.example.com</address>
     *   <host>
     *     <name>myhost</name>
     *   </host>
     * </storage_connection>
     * ----
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @In @Out StorageConnection connection();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the operation should succeed regardless to the relevant storage domain's status
         * (i.e. updating is also applicable when storage domain's status is not maintenance).
         *
         * This parameter is optional, and the default value is `false`.
         *
         * @author Daniel Erez <derez@redhat.com>
         * @date 21 Sep 2016
         * @status added
         * @since 4.0.6
         */
        @In Boolean force();
    }

    /**
     * Removes a storage connection.
     *
     * A storage connection can only be deleted if neither storage domain nor LUN disks reference it. The host name or
     * id is optional; providing it disconnects (unmounts) the connection from that host.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Remove {
        /**
         * The name or identifier of the host from which the connection would be unmounted (disconnected). If not
         * provided, no host will be disconnected.
         *
         * For example, to use the host with identifier `456` to delete the storage connection with identifier `123`
         * send a request like this:
         *
         * [source]
         * ----
         * DELETE /ovirt-engine/api/storageconnections/123?host=456
         * ----
         *
         * @author Daniel Erez <derez@redhat.com>
         * @date 14 Sep 2016
         * @status added
         */
        @In String host();

        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
