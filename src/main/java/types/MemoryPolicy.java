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
 * Logical grouping of memory-related properties of virtual machine-like entities.
 *
 * @author Jakub Niedermertl <jniederm@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 23 Nov 2017
 * @status updated_by_docs
 */
@Type
public interface MemoryPolicy {

    /**
     * The amount of memory, in bytes, that is guaranteed to not be drained by the balloon mechanism.
     *
     * The {engine-name} internally rounds this value down to whole MiB (1MiB = 2^20^ bytes).
     *
     * NOTE: It can be updated while the virtual machine is running since {product-name} 4.2 onwards, provided
     * xref:types-vm-attributes-memory[memory] is updated in the same request as well, and the virtual machine is in
     * state xref:types-vm_status-values-up[up].
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Nov 2017
     * @status updated_by_docs
     */
    Integer guaranteed();

    /**
     * Maximum virtual machine memory, in bytes.
     *
     * The user provides the value in bytes, and the {engine-name} rounds the value down to the nearest lower MiB value.
     *
     * For example, if the user enters a value of 1073741825 (1 GiB + 1 byte), then the {engine-name} will truncate
     * that value to the nearest lower MiB boundary: in this case 1073741824 (1 GiB).
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Nov 2017
     * @status updated_by_docs
     * @since 4.1
     */
    Integer max();

    Boolean ballooning();
    MemoryOverCommit overCommit();
    TransparentHugePages transparentHugePages();
}
