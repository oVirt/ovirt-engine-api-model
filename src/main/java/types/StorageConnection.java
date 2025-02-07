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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents a storage server connection.
 *
 * Example XML representation:
 *
 * [source,xml]
 * ----
 * <storage_connection id="123">
 *   <address>mynfs.example.com</address>
 *   <type>nfs</type>
 *   <path>/exports/mydata</path>
 * </storage_connection>
 * ----
 *
 * @author Daniel Erez <derez@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 29 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface StorageConnection extends Identified {
    // Common to all types of storage connections:
    /**
     * A storage server connection's address.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    String address();

    /**
     * A storage server connection's type.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    StorageType type();

    // For NFS:
    /**
     * The path of an NFS storage server connection.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    String path();

    /**
     * The mount options of an NFS storage server connection.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    String mountOptions();

    /**
     * The VFS type of an NFS storage server connection.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    String vfsType();

    /**
     * The NFS version of an NFS storage server connection.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    NfsVersion nfsVersion();

    /**
     * The NFS timeo value of an NFS storage server connection.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    Integer nfsTimeo();

    /**
     * The NFS retrans value of an NFS storage server connection.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    Integer nfsRetrans();

    // For iSCSI:
    /**
     * The port of an iSCSI storage server connection.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    Integer port();

    /**
     * The target of an iSCSI storage server connection.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    String target();

    /**
     * The user name of an iSCSI storage server connection.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    String username();

    /**
     * The password of an iSCSI storage server connection.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    String password();

    /**
     * The portal of an iSCSI storage server connection.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 17 May 2018
     * @status updated_by_docs
     */
    String portal();

    /**
     * Link to the gluster volume, used by that storage domain.
     *
     * @author Denis Chaplygin <dchaplyg@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 19 Jun 2018
     * @status updated_by_docs
     */
    @Link GlusterVolume glusterVolume();

    // Link to the host:
    @Link Host host();
}
