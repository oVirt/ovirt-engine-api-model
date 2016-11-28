/*
Copyright (c) 2015-2016 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

@Type
public interface StorageDomain extends Identified {
    StorageDomainType type();
    StorageDomainStatus status();
    ExternalStatus externalStatus();
    Boolean master();
    HostStorage storage();
    Integer available();
    Integer used();
    Integer committed();
    StorageFormat storageFormat();

    /**
     * Serves as the default value of `wipe_after_delete` for <<types/disk, disk>>s on this
     * <<types/storage_domain, storage domain>>.
     *
     * That is, newly created disks will get their `wipe_after_delete` value from their storage domains by default.
     * Note that the configuration value `SANWipeAfterDelete` serves as the default value of block storage domains'
     * `wipe_after_delete` value.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 29 November 2016
     * @status added
     */
    Boolean wipeAfterDelete();

    /**
     * Indicates whether <<types/disk, disk>>s' blocks on block <<types/storage_domain, storage domain>>s will be
     * discarded right before they are deleted.
     *
     * If true, and a disk on this storage domain has its `wipe_after_delete` value enabled, then when the disk is
     * deleted:
     *
     * . It is first wiped.
     * . Then its blocks are discarded.
     * . Finally it is deleted.
     *
     * Note that:
     *
     * * Discard after delete will always be `false` for non block storage types.
     *
     * * Discard after delete can be set to `true` only if the storage domain
     * <<types/storage_domain/attributes/supports_discard, supports discard>>.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 29 November 2016
     * @status added
     * @since 4.1
     */
    Boolean discardAfterDelete();

    /**
     * Indicates whether a block storage domain supports discard operations.
     * A <<types/storage_domain,storage domain>> supports discard if and only
     * if all of the <<types/logical_unit, logical unit>>s that it is built
     * from support discard, i.e each logical unit's `discard_max_size` value
     * is bigger than 0.
     * This is a necessary but not a sufficient condition for a virtual
     * machine's disk that is a part of this storage domain to have its
     * `pass_discard` attribute enabled.
     * Since the engine cannot check if the underlying block device supports
     * discard for file storage domains, this attribute will not be reported
     * for them at all.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 27 September 2016
     * @status added
     * @since 4.1
     */
    Boolean supportsDiscard();

    /**
     * Indicates whether a block storage domain supports the property that
     * discard zeroes the data.
     * A <<types/storage_domain,storage domain>> supports the property that
     * discard zeroes the data if and only if all of the
     * <<types/logical_unit, logical unit>>s that it is built from support it,
     * i.e each logical unit's `discard_zeroes_data` value is true.
     * This is a necessary but not a sufficient condition for a virtual
     * machine's disk that is a part of this storage domain to have both
     * `wipe_after_delete` and `pass_discard` attributes enabled.
     * Since the engine cannot check if the underlying block device supports
     * the property that discard zeroes the data for file storage domains,
     * this attribute will not be reported for them at all.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 27 September 2016
     * @status added
     * @since 4.1
     */
    Boolean supportsDiscardZeroesData();

    Boolean _import(); // TODO: Should be an action parameter.
    Integer warningLowSpaceIndicator();
    Integer criticalSpaceActionBlocker();

    /**
     * Host is only relevant at creation time.
     */
    @Link Host host();

    /**
     * This is used to link to the data center that the storage domain is attached to. It is preserved for backwards
     * compatibility, as the storage domain may be attached to multiple data centers (if it is an ISO domain). Use
     * the `dataCenters` element instead.
     */
    @Link DataCenter dataCenter();

    /**
     * This is a set of links to the data centers that the storage domain is attached to.
     */
    @Link DataCenter[] dataCenters();
    @Link Disk[] disks();
    @Link DiskProfile[] diskProfiles();
    @Link DiskSnapshot[] diskSnapshots();
    @Link File[] files();
    @Link Image[] images();
    @Link Permission[] permissions();
    @Link StorageConnection[] storageConnections();
    @Link Template[] templates();
    @Link Vm[] vms();
}
