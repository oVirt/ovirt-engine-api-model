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
    LunStatus status();
    String volumeGroupId();
    String storageDomainId();
    String diskId();

    /**
     * The maximum number of bytes that can be discarded by the logical unit's
     * underlying storage in a single operation.
     * A value of 0 means that the device does not support discard functionality.
     * Note that this is the software limit, and not the hardware limit, as noted in the
     * https://www.kernel.org/doc/Documentation/block/queue-sysfs.txt[documentation] of
     * `queue-sysfs` for `discard_max_bytes`.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 18 September 2016
     * @status added
     * @since 4.1
     */
    Integer discardMaxSize();

    /**
     * True if and only if previously discarded blocks in the logical
     * unit's underlying storage are read back as zeros from it.
     * For more information please see the
     * https://www.kernel.org/doc/Documentation/block/queue-sysfs.txt[documentation]
     * of `queue-sysfs` for `discard_zeroes_data`.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 18 September 2016
     * @status added
     * @since 4.1
     */
    Boolean discardZeroesData();
}
