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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Describes the parameters for the virtual machine import operation from an external system.
 *
 * @author Martin Betak <mbetak@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 27 Oct 2016
 * @status updated_by_docs
 * @since 4.0.4
 */
@Type
public interface ExternalVmImport {

    /**
     * The type of external virtual machine provider.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    ExternalVmProviderType provider();

    /**
     * The URL to be passed to the `virt-v2v` tool for conversion.
     *
     * Example:
     *
     * ```http
     * vpx://wmware_user@vcenter-host/DataCenter/Cluster/esxi-host?no_verify=1
     * ```
     *
     * More examples can be found at http://libguestfs.org/virt-v2v.1.html.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    String url();

    /**
     * The username to authenticate against the external hypervisor system.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    String username();

    /**
     * The password to authenticate against the external hypervisor system.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2016
     * @status updated_by_docs
     * @since 4.0
     */
    String password();

    /**
     * The name of the virtual machine to be imported, as is defined within the external system.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    String name();

    /**
     * Optional. Specifies the disk allocation policy of the resulting virtual machine: `true` for sparse, `false` for
     * preallocated.
     *
     * If not specified:
     * - When importing an OVA that was produced by oVirt, it will be determined according to the configuration of the
     *   disk within the OVF.
     * - Otherwise, it will be set to true.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @author Saif Abu Saleh <sabusale@redhat.com>
     * @date 30 Nov 2021
     * @status updated
     * @since 4.0.4
     */
    Boolean sparse();

    /**
     * The virtual machine entity used to specify a name for the newly created virtual machine.
     *
     * If a name is not specified, the source virtual machine name will be used.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    @Link Vm vm();

    /**
     * Specifies the target cluster for the resulting virtual machine.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    @Link Cluster cluster();


    /**
     * Specifies the target storage domain for converted disks.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    @Link StorageDomain storageDomain();

    /**
     * Optional. Specifies the CPU profile of the resulting virtual machine.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    @Link CpuProfile cpuProfile();

    /**
     * Optional. Specifies the quota that will be applied to the resulting virtual machine.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    @Link Quota quota();

    /**
     * Optional. Specifies the host (using host's ID) to be used for the conversion process.
     * If not specified, one is selected automatically.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    @Link Host host();

    /**
     * Optional. The name of the ISO containing drivers that can be used during the `virt-v2v` conversion process.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    @Link File driversIso();
}
