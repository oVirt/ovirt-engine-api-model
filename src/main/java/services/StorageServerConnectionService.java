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

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.StorageConnection;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("Storage")
public interface StorageServerConnectionService {
    interface Get extends Follow {
        // TODO: need a correction of the typo
        @Out StorageConnection conection();
    }

    /**
     * Updates the storage connection.
     *
     * For example, to change the address of an NFS storage server, send a request like this:
     *
     * ```http
     * PUT /ovirt-engine/api/storageconnections/123
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <storage_connection>
     *   <address>mynewnfs.example.com</address>
     * </storage_connection>
     * ```
     *
     * To change the connection of an iSCSI storage server, send a request like this:
     *
     * ```http
     * PUT /ovirt-engine/api/storageconnections/123
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <storage_connection>
     *   <port>3260</port>
     *   <target>iqn.2017-01.com.myhost:444</target>
     * </storage_connection>
     * ```
     *
     * @author Daniel Erez <derez@redhat.com>
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Billy Burmester <bburmest@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    interface Update {
        /**
         * Update the specified iSCSI storage connection in the system.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @author Billy Burmester <bburmest@redhat.com>
         * @date 17 May 2018
         * @status updated_by_docs
         */
        interface Iscsi extends Update {
            @InputDetail
            default void inputDetail() {
                optional(connection().password());
                optional(connection().port());
                optional(connection().target());
                optional(connection().username());
            }
        }

        /**
         * Update the specified NFS storage connection in the system.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @author Billy Burmester <bburmest@redhat.com>
         * @date 17 May 2018
         * @status updated_by_docs
         */
        interface Nfs extends Update {
            @InputDetail
            default void inputDetail() {
                optional(connection().address());
                optional(connection().nfsRetrans());
                optional(connection().nfsTimeo());
                optional(connection().nfsVersion());
                optional(connection().path());
            }
        }

        /**
         * Update the specified VFS storage connection in the system.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @author Billy Burmester <bburmest@redhat.com>
         * @date 17 May 2018
         * @status updated_by_docs
         */
        interface Vfs extends Update {
            @InputDetail
            default void inputDetail() {
                optional(connection().address());
                optional(connection().mountOptions());
                optional(connection().path());
                optional(connection().vfsType());
            }
        }

        /**
         * Update the specified Glusterfs storage connection in the system.
         *
         * @author Denis Chaplygin <dchaplyg@redhat.com>
         * @author Byron Gravenorst <bgraveno@redhat.com>
         * @date 19 Jun 2018
         * @status updated_by_docs
         */
        interface Glusterfs extends Update {
            @InputDetail
            default void inputDetail() {
                optional(connection().path());
                optional(connection().vfsType());
                optional(connection().address());
                optional(connection().mountOptions());
                optional(connection().glusterVolume());
            }
        }
        /**
         * Update the specified local storage connection in the system.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface Local extends Update {
            @InputDetail
            default void inputDetail() {
                optional(connection().path());
            }
        }
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
         * ```http
         * DELETE /ovirt-engine/api/storageconnections/123?host=456
         * ```
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
