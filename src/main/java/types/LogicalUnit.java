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

import org.ovirt.api.metamodel.annotations.Type;

@Type
public interface LogicalUnit {
    String id();
    String address();
    Integer port();
    String target();
    String username();
    String password();
    String portal();
    String serial();
    String vendorId();
    String productId();
    Integer lunMapping();
    Integer size();
    Integer paths();
    Integer activePaths();
    LunStatus status();
    String volumeGroupId();
    String storageDomainId();
    String diskId();

    /**
     * The maximum number of bytes that can be discarded by the logical unit's
     * underlying storage in a single operation.
     * A value of 0 means that the device does not support discard functionality.
     *
     * NOTE: This is the software limit, and not the hardware limit, as noted in the
     * link:https://www.kernel.org/doc/Documentation/block/queue-sysfs.txt[`queue-sysfs` documentation]
     * for `discard_max_bytes`.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 17 Nov 2016
     * @status updated_by_docs
     * @since 4.1
     */
    Integer discardMaxSize();

    /**
     * True, if previously discarded blocks in the logical
     * unit's underlying storage are read back as zeros.
     * For more information please see the
     * link:https://www.kernel.org/doc/Documentation/block/queue-sysfs.txt[`queue-sysfs` documentation]
     * for `discard_zeroes_data`.
     *
     * IMPORTANT: Since version 4.2.1 of the system, the support for this attribute has
     * been removed as the sysfs file, `discard_zeroes_data`, was deprecated in the kernel.
     * It is preserved for backwards compatibility, but the value will always be `false`.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 18 Dec 2017
     * @status updated_by_docs
     * @since 4.1
     */
    @Deprecated
    Boolean discardZeroesData();
}
