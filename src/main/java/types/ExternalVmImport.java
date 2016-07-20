/*
Copyright (c) 2016 Red Hat, Inc.

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

/**
 * Describes parameters of virtual machine import operation from external system.
 *
 * @author Martin Betak <mbetak@redhat.com>
 * @date 27 Jul 2016
 * @status added
 * @since 4.0.4
 */
@Type
public interface ExternalVmImport {

    /**
     * Type of external virtual machine provider.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    ExternalVmProviderType provider();

    /**
     * URL to be passed to the `virt-v2v` tool for conversion.
     *
     * Example:
     *
     * [source]
     * ----
     * vpx://wmware_user@vcenter-host/DataCenter/Cluster/esxi-host?no_verify=1
     * ----
     *
     * More examples can be found at http://libguestfs.org/virt-v2v.1.html.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    String url();

    /**
     * Username to authenticate against external hypervisor system.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    String username();

    /**
     * Password to authenticate against external hypervisor system.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0
     */
    String password();

    /**
     * Name of the virtual machine to be imported as is defined within the external system.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    String name();

    /**
     * Specifies the disk allocation policy of resulting virtual machine: `true` for sparse, `false` for preallocated.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    Boolean sparse();

    /**
     * Virtual machine entity used to specify the name of the newly created virtual machine.
     *
     * If name is not specified, the source virtual machine name will be used.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    @Link Vm vm();

    /**
     * Specifies the target cluster of the resulting virtual machine.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    @Link Cluster cluster();


    /**
     * Specifies the target storage domain for converted disks.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    @Link StorageDomain storageDomain();

    /**
     * Optionally specifies the cpu profile of the resulting virtual machine.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    @Link CpuProfile cpuProfile();

    /**
     * Optionally specifies the quota that will be applied to the resulting virtual machine.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    @Link Quota quota();

    /**
     * Optional specification of host (using host's ID) to be used for the conversion process.
     * If not specified, one is selected automatically.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    @Link Host host();

    /**
     * Optional name of ISO carrying drivers that can be used during the virt-v2v conversion process.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0.4
     */
    @Link File driversIso();
}
