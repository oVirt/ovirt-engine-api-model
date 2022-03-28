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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing a virtual machines pool.
 *
 * @author Arik Hadas <ahadas@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public interface VmPool extends Identified {
    /**
     * The number of virtual machines in the pool.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Integer size();

    /**
     * The system attempts to prestart the specified number of virtual machines from the pool.
     *
     * These virtual machines are started without being attached to any user. That way, users can acquire virtual
     * machines from the pool faster.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Integer prestartedVms();

    /**
     * The maximum number of virtual machines in the pool that could be assigned to a particular user.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Integer maxUserVms();

    /**
     * The display settings configured for virtual machines in the pool.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Display display();

    /**
     * The random number generator device configured for virtual machines in the pool.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    RngDevice rngDevice();

    /**
     * Indicates if sound card should be configured for virtual machines in the pool.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Boolean soundcardEnabled();

    /**
     * The deallocation policy of virtual machines in the pool.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    VmPoolType type();

    /**
     * Indicates if virtual machines in the pool are updated to newer versions of the template the pool is based on.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Boolean useLatestTemplateVersion();

    /**
     * Virtual machine pool's stateful flag.
     *
     * Virtual machines from a stateful virtual machine pool are always started in stateful mode (stateless snapshot is
     * not created). The state of the virtual machine is preserved even when the virtual machine is passed to a
     * different user.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 8 Dec 2016
     * @status updated_by_docs
     */
    Boolean stateful();

    /**
     * Indicates if the pool should automatically distribute the disks of the virtual machines across the multiple
     * storage domains where the template is copied.
     *
     * When the template used by the pool is present in multiple storage domains, the disks of the virtual machines of
     * the pool will be created in one of those storage domains. By default, or when the value of this attribute is
     * `false`, that storage domain is selected when the pool is created, and all virtual machines will use the same. If
     * this attribute is `true`, then, when a virtual machine is added to the pool, the storage domain that has more
     * free space is selected.
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 23 Feb 2017
     * @since 4.1.2
     * @status added
     */
    Boolean autoStorageSelect();

    /**
     * If `true`, a TPM device is added to the virtual machine. By default the value is `false`.
     * This property is only visible when fetching if "All-Content=true" header is set.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 21 Feb 2021
     * @status added
     * @since 4.4.5
     */
    Boolean tpmEnabled();

    /**
     * Reference to the cluster the pool resides in.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link Cluster cluster();

    /**
     * Reference to the template the pool is based on.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link Template template();

    /**
     * Reference to the instance type on which this pool is based. It can be
     * set only on pool creation and cannot be edited.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 8 Dec 2016
     * @status updated_by_docs
     */
    @Link InstanceType instanceType();

    /**
     * Reference to an arbitrary virtual machine that is part of the pool.
     *
     * Note that this virtual machine may not be based to the latest version of
     * the pool's template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link Vm vm();

    /**
     * Permissions set for this virtual machine pool.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link Permission[] permissions();
}
