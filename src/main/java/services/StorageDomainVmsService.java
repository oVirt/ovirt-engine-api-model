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

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Vm;

/**
 * Lists the virtual machines of an export storage domain.
 *
 * For example, to retrieve the virtual machines that are available in the storage domain with identifier `123` send the
 * following request:
 *
 * ```http
 * GET /ovirt-engine/api/storagedomains/123/vms
 * ```
 *
 * This will return the following response body:
 *
 * ```xml
 * <vms>
 *   <vm id="456" href="/api/storagedomains/123/vms/456">
 *     <name>vm1</name>
 *     ...
 *     <storage_domain id="123" href="/api/storagedomains/123"/>
 *     <actions>
 *       <link rel="import" href="/api/storagedomains/123/vms/456/import"/>
 *     </actions>
 *   </vm>
 * </vms>
 * ```
 *
 * Virtual machines and templates in these collections have a similar representation to their counterparts in the
 * top-level xref:types/vm[Vm] and xref:types/template[Template] collections, except they also contain a
 * xref:types/storage_domain[StorageDomain] reference and an xref:services/storage_domain_vm/methods/import[import]
 * action.
 *
 * @author Amit Aviram <aaviram@redhat.com>
 * @date 14 Sep 2016
 * @status added
 */
@Service
@Area("Storage")
public interface StorageDomainVmsService {
    /**
     * Returns the list of virtual machines of the export storage domain.
     *
     * The order of the returned list of virtual machines isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out Vm[] vm();

        /**
         * Sets the maximum number of virtual machines to return. If not specified all the virtual machines are
         * returned.
         */
        @In Integer max();

        /**
         * Indicates whether to retrieve a list of registered or unregistered virtual machines which
         * contain disks on the storage domain.
         * To get a list of unregistered virtual machines the call should indicate the unregistered flag.
         * For example, to get a list of unregistered virtual machines the REST API call should look like this:
         *
         * ```http
         * GET /ovirt-engine/api/storagedomains/123/vms?unregistered=true
         * ```
         *
         * The default value of the unregisterd flag is `false`.
         * The request only apply to storage domains that are attached.
         *
         * @author Maor Lipchuk <mlipchuk@redhat.com>
         * @date 8 Mar 2017
         * @status added
         * @since 4.1.1
         */
        @In Boolean unregistered();
    }

    @Service StorageDomainVmService vm(String id);
}
