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
 * Type of USB device redirection.
 *
 * @author Lukas Svaty <lsvaty@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public enum UsbType {
    /**
     * Legacy USB redirection.
     *
     * This USB type has been deprecated since version 3.6 of the engine, and has been completely removed in version
     * 4.1. It is preserved only to avoid syntax errors in existing scripts. If it is used it will be automatically
     * replaced by `native`.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Nov 2016
     * @status added
     */
    @Deprecated
    LEGACY,

    /**
     * Native USB redirection.
     *
     * Native USB redirection allows KVM/SPICE USB redirection for Linux and Windows virtual machines. Virtual (guest)
     * machines require no guest-installed agents or drivers for native USB. On Linux clients, all packages required
     * for USB redirection are provided by the `virt-viewer` package. On Windows clients, you must also install the
     * `usbdk` package.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    NATIVE;
}
