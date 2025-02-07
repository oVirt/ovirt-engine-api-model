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

/**
 * Type representing a chipset and a BIOS type combination.
 *
 * @author Shmuel Melamud <smelamud@redhat.com>
 * @date 21 Jun 2018
 * @status added
 * @since 4.3
 */
@Type
public enum BiosType {
    /**
     * Use the cluster-wide default.
     *
     * This value cannot be used for cluster.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 28 Aug 2019
     * @status added
     * @since 4.4
     */
    CLUSTER_DEFAULT,

    /**
     * i440fx chipset with SeaBIOS.
     *
     * For non-x86 architectures this is the only non-default value allowed.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 21 Jun 2018
     * @status added
     * @since 4.3
     */
    I440FX_SEA_BIOS,

    /**
     * q35 chipset with SeaBIOS.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 21 Jun 2018
     * @status added
     * @since 4.3
     */
    Q35_SEA_BIOS,

    /**
     * q35 chipset with OVMF (UEFI) BIOS.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 21 Jun 2018
     * @status added
     * @since 4.3
     */
    Q35_OVMF,

    /**
     * q35 chipset with OVMF (UEFI) BIOS with SecureBoot enabled.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 21 Jun 2018
     * @status added
     * @since 4.3
     */
    Q35_SECURE_BOOT;
}
