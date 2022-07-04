/*
Copyright (c) 2015 Red Hat, Inc.

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
 * Indicates the kind of data managed by a xref:types-storage_domain[storage domain].
 *
 * @author Aleksei Slaikovski <aslaikov@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public enum StorageDomainType {
    /**
     * Data domains are used to store the disks and snapshots of the virtual machines and templates in the system. In
     * addition, snapshots of the disks are also stored in data domains. Data domains cannot be shared across data
     * centers.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    DATA,

    /**
     * ISO domains store ISO files (or logical CDs) used to install and boot operating systems and applications for the
     * virtual machines. ISO domains remove the data center's need for physical media. An ISO domain can be shared
     * across different data centers.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    ISO,

    /**
     * Export domains are temporary storage repositories used to copy and move virtual machines and templates between
     * data centers and {product-name} environments. Export domains can also be used to backup virtual machines. An
     * export domain can be moved between data centers but it can only be active in one data center at a time.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    EXPORT,

    /**
     * Image domain store images that can be imported into from an external system. For example, images from an
     * OpenStack Glance image repository.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    IMAGE,

    /**
     * Volume domains store logical volumes that can be used as disks for virtual machines. For example, volumes
     * from an OpenStack Cincer block storage service.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    VOLUME,

    /**
     * Managed block storage domains are created on block storage devices. These domains are accessed and managed by cinder.
     *
     * @author Benny Zlotnik <bzlotnik@redhat.com>
     * @author Steve Goodman <sgoodman@redhat.com>
     * @since 4.3
     * @date 18 Dec 2018
     * @status updated_by_docs
     */
    MANAGED_BLOCK_STORAGE;
}
