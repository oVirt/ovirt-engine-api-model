/*
Copyright (c) 2016 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type which represents a format of xref:types/storage_domain[storage domain].
 *
 * @author Aleksei Slaikovskii <aslaikov@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public enum StorageFormat {
    /**
     * Version 1 of the storage domain format is applicable to NFS, iSCSI and FC storage domains.
     *
     * Each storage domain contains metadata describing its own structure, and all of the names of physical volumes that
     * are used to back virtual machine disk images. Master domains additionally contain metadata for all the domains
     * and physical volume names in the storage pool. The total size of this metadata is limited to 2 KiB, limiting the
     * number of storage domains that can be in a pool. Template and virtual machine base images are read only.
     *
     * @author Aleksei Slaikovsky <aslaikov@rehat.com>
     * @date 24 Apr 2017
     * @status added
     */
    V1,

    /**
     * Version 2 of the storage domain format is applicable to iSCSI and FC storage domains.
     *
     * All storage domain and pool metadata is stored as logical volume tags rather than written to a logical volume.
     * Metadata about virtual machine disk volumes is still stored in a logical volume on the domains. Physical volume
     * names are no longer included in the metadata. Template and virtual machine base images are read only.
     *
     * @author Aleksei Slaikovsky <aslaikov@rehat.com>
     * @date 24 Apr 2017
     * @status added
     */
    V2,

    /**
     * Version 3 of the storage domain format is applicable to NFS, POSIX, iSCSI and FC storage domains.
     *
     * All storage domain and pool metadata is stored as logical volume tags rather than written to a logical volume.
     * Metadata about virtual machine disk volumes is still stored in a logical volume on the domains. Virtual machine
     * and template base images are no longer read only. This change enables live snapshots, live storage migration,
     * and clone from snapshot. Support for Unicode metadata is added, for non-English volume names.
     *
     * @author Aleksei Slaikovsky <aslaikov@rehat.com>
     * @date 24 Apr 2017
     * @status added
     */
    V3,

    /**
     * Version 4 of the storage domain format.
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @date 14 Dec 2016
     * @status added
     * @since 4.1
     */
    V4,

    /**
     * Version 5 of the storage domain format is applicable to NFS, POSIX, and Gluster storage domains.
     *
     * Added support for 4096 bytes block sizes and variable sanlock alignments.
     *
     * @author Denis Chaplygin <dchaplyg@redhat.com>
     * @date 11 Dec 2018
     * @status added
     * @since 4.3
     */
    V5;
}
