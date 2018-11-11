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
package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing a storage domain type.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 05 Dec 2016
 * @status added
 */
@Type
public enum StorageType {
    /**
     * iSCSI storage domain.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 05 Dec 2016
     * @status added
     */
    ISCSI,

    /**
     * Fibre-Channel storage domain.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 05 Dec 2016
     * @status added
     */
    FCP,

    /**
     * NFS storage domain.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 05 Dec 2016
     * @status added
     */
    NFS,

    /**
     * Storage domain on Local storage.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 05 Dec 2016
     * @status added
     */
    LOCALFS,

    /**
     * POSIX-FS storage domain.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 05 Dec 2016
     * @status added
     */
    POSIXFS,

    /**
     * Gluster-FS storage domain.
     * For more details on Gluster please go to https://www.gluster.org/[Gluster].
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 05 Dec 2016
     * @status added
     */
    GLUSTERFS,

    /**
     * Glance storage domain.
     * For more details on Glance please go to https://wiki.openstack.org/wiki/Glance[Glance].
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 05 Dec 2016
     * @status added
     */
    GLANCE,

    /**
     * Cinder storage domain.
     * For more details on Cinder please go to https://wiki.openstack.org/wiki/Cinder[Cinder].
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 05 Dec 2016
     * @status added
     */
    CINDER,

    /**
     * Managed block storage domain.
     * A storage domain managed using cinderlib.
     * For supported storage drivers, see https://docs.openstack.org/cinder/rocky/drivers.html[Available Drivers].
     *
     * @author Benny Zlotnik <bzlotnik@redhat.com>
     * @author Steve Goodman <sgoodman@redhat.com>
     * @date 18 Dec 2018
     * @since 4.3
     * @status updated_by_docs

     */
    MANAGED_BLOCK_STORAGE;
}
