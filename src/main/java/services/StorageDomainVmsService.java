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

package services;

import annotations.Area;
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
 * [source]
 * ----
 * GET /ovirt-engine/api/storagedomains/123/vms
 * ----
 *
 * This will return the following response body:
 *
 * [source,xml]
 * ----
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
 * ----
 *
 * Virtual machines and templates in these collections have a similar representation to their counterparts in the
 * top-level <<types/vm, Vm>> and <<types/template, Template>> collections, except they also contain a
 * <<types/storage_domain, StorageDomain>> reference and an <<services/storage_domain_vm/methods/import, import>>
 * action.
 *
 * @author Amit Aviram <aaviram@redhat.com>
 * @date 14 Sep 2016
 * @status added
 */

@Service
@Area("Storage")
public interface StorageDomainVmsService {
    interface List {
        @Out Vm[] vm();

        /**
         * Sets the maximum number of virtual machines to return. If not specified all the virtual machines are
         * returned.
         */
        @In Integer max();
    }

    @Service StorageDomainVmService vm(String id);
}
