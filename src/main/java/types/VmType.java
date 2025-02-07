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
 * Type representing what the virtual machine is optimized for.
 *
 * @author Arik Hadas <ahadas@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 15 Sep 2017
 * @status updated_by_docs
 */
@Type
public enum VmType {
    /**
     * The virtual machine is intended to be used as a desktop.
     *
     * Currently, its implication is that a sound device will automatically be added to the virtual machine.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 15 Sep 2017
     * @status updated_by_docs
     */
    DESKTOP,

     /**
     * The virtual machine is intended to be used as a server.
     *
     * Currently, its implication is that a sound device will not automatically be added to the virtual machine.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 15 Sep 2017
     * @status updated_by_docs
     */
    SERVER,

    /**
     * The virtual machine is intended to be used as a high performance virtual machine.
     *
     * Currently, its implication is that the virtual machine configuration will automatically be set for running
     * with the highest possible performance, and with performance metrics as close to bare metal as possible.
     *
     * Some of the recommended configuration settings for the highest possible performance
     * cannot be set automatically; manually setting them before running the virtual machine is recommended.
     *
     * The following configuration changes are set automatically:
     *
     * - Enable headless mode.
     * - Enable serial console.
     * - Enable pass-through host CPU.
     * - Enable I/O threads.
     * - Enable I/O threads pinning and set the pinning topology.
     * - Enable the paravirtualized random number generator PCI (virtio-rng) device.
     * - Disable all USB devices.
     * - Disable the soundcard device.
     * - Disable the smartcard device.
     * - Disable the memory balloon device.
     * - Disable the watchdog device.
     * - Disable migration.
     * - Disable high availability.
     *
     * The following recommended configuration changes have to be set manually by the user:
     *
     * - Enable CPU pinning topology.
     * - Enable non-uniform memory access (NUMA) pinning topology.
     * - Enable and set huge pages configuration.
     * - Disable kernel same-page merging (KSM).
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 15 Sep 2017
     * @status updated_by_docs
     * @since 4.2
     */
    HIGH_PERFORMANCE;
}
