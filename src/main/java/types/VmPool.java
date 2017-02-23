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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

@Type
public interface VmPool extends Identified {
    Integer size();
    Integer prestartedVms();
    Integer maxUserVms();
    Display display();
    RngDevice rngDevice();
    Boolean soundcardEnabled();
    VmPoolType type();
    Boolean useLatestTemplateVersion();

    /**
     * Virtual machine pool's stateful flag.
     *
     * Virtual machines from a stateful virtual machine pool are always started in stateful mode
     * (stateless snapshot is not created). The state of the virtual machine is preserved
     * even when the virtual machine is passed to a different user.
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

    @Link Cluster cluster();
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
    @Link Vm vm();
    @Link Permission[] permissions();
}
