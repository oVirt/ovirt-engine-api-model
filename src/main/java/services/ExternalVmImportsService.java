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

package services;

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.ExternalVmImport;

/**
 * Provides capability to import external virtual machines.
 *
 * @author Martin Betak <mbetak@redhat.com>
 * @date 27 Jul 2016
 * @status added
 * @since 4.0.4
 */
@Service
@Area("Virtualization")
public interface ExternalVmImportsService {

    /**
     * This operation is used to import a virtual machine from external hypervisor, such as KVM, XEN or VMware.
     *
     * For example import of a virtual machine from VMware can be facilitated using the following request:
     *
     * [source]
     * ----
     * POST /externalvmimports
     * ----
     *
     * With request body of type xref:types-external_vm_import[ExternalVmImport], for example:
     *
     * [source,xml]
     * ----
     * <external_vm_import>
     *   <vm>
     *     <name>my_vm</name>
     *   </vm>
     *   <cluster id="360014051136c20574f743bdbd28177fd" />
     *   <storage_domain id="8bb5ade5-e988-4000-8b93-dbfc6717fe50" />
     *   <name>vm_name_as_is_in_vmware</name>
     *   <sparse>true</sparse>
     *   <username>vmware_user</username>
     *   <password>123456</password>
     *   <provider>VMWARE</provider>
     *   <url>vpx://wmware_user@vcenter-host/DataCenter/Cluster/esxi-host?no_verify=1</url>
     *   <drivers_iso id="virtio-win-1.6.7.iso" />
     * </external_vm_import>
     * ----
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @date 27 Jul 2016
     * @status added
     * @since 4.0
     */
    interface Add {
        @In @Out ExternalVmImport _import();
    }
}
