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
 * Represents hardware information of host.
 *
 * To get that information send a request like this:
 *
 * ```http
 * GET /ovirt-engine/api/hosts/123
 * ```
 *
 * The result will be like this:
 *
 * ```xml
 * <host href="/ovirt-engine/api/hosts/123" id="123">
 *   ...
 *   <hardware_information>
 *     <family>Red Hat Enterprise Linux</family>
 *     <manufacturer>Red Hat</manufacturer>
 *     <product_name>RHEV Hypervisor</product_name>
 *     <serial_number>01234567-89AB-CDEF-0123-456789ABCDEF</serial_number>
 *     <supported_rng_sources>
 *       <supported_rng_source>random</supported_rng_source>
 *     </supported_rng_sources>
 *     <uuid>12345678-9ABC-DEF0-1234-56789ABCDEF0</uuid>
 *     <version>1.2-34.5.el7ev</version>
 *   </hardware_information>
 *   ...
 * </application>
 * ```
 *
 * @author Lukas Svaty <lsvaty@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public interface HardwareInformation {
    /**
     * Manufacturer of the host's machine and hardware vendor.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String manufacturer();

    /**
     * Unique name for each of the manufacturer.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String version();

    /**
     * Unique ID for host's chassis.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String serialNumber();

    /**
     * Host's product name (for example `RHEV Hypervisor`).
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String productName();

    /**
     * Unique ID for each host.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String uuid();

    /**
     * Type of host's CPU.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String family();

    /**
     * Supported sources of random number generator.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    RngSource[] supportedRngSources();
}
