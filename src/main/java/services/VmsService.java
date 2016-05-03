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

@Service
@Area("Virtualization")
public interface VmsService {
    /**
     * Creates a new virtual machine.
     *
     * The virtual machine can be created in different ways:
     *
     * - From a template. In this case the identifier or name of the template must be provided. For example, using a
     *   plain shell script and XML:
     *
     * [source,bash]
     * ----
     * #!/bin/sh -ex
     *
     * url="https://engine.example.com/ovirt-engine/api"
     * user="admin@internal"
     * password="..."
     * curl \
     * --verbose \
     * --cacert /etc/pki/ovirt-engine/ca.pem \
     * --user "${user}:${password}" \
     * --request POST \
     * --header "Content-Type: application/xml" \
     * --header "Accept: application/xml" \
     * --data '
     * <vm>
     *   <name>myvm</name>
     *   <template>
     *     <name>Blank</name>
     *   </template>
     *   <cluster>
     *     <name>mycluster</name>
     *   </cluster>
     * </vm>
     * ' \
     * "${url}/vms"
     * ----
     *
     * - From a snapshot. In this case the identifier of the snapshot has to be provided. For example, using a plain
     *   shel script and XML:
     *
     * [source,bash]
     * ----
     * #!/bin/sh -ex
     *
     * url="https://engine.example.com/ovirt-engine/api"
     * user="admin@internal"
     * password="..."
     * curl \
     * --verbose \
     * --cacert /etc/pki/ovirt-engine/ca.pem \
     * --user "${user}:${password}" \
     * --request POST \
     * --header "Content-Type: application/xml" \
     * --header "Accept: application/xml" \
     * --data '
     * <vm>
     *   <name>myvm</name>
     *   <snapshots>
     *     <snapshot id="266742a5-6a65-483c-816d-d2ce49746680"/>
     *   </snapshots>
     *   <cluster>
     *     <name>mycluster</name>
     *   </cluster>
     * </vm>
     * ' \
     * "${url}/vms"
     * ----
     *
     * When creating a virtual machine from a template or from a snapshot it is usually useful to explicitly indicate
     * in what storage domain to create the disks for the virtual machine. If the virtual machine is created from
     * a template then this is achieved passing a set of `disk` elements that indicate the mapping:
     *
     * [source,xml]
     * ----
     * <vm>
     *   ...
     *   <disks>
     *     <disk id="8d4bd566-6c86-4592-a4a7-912dbf93c298">
     *       <storage_domains>
     *         <storage_domain id="9cb6cb0a-cf1d-41c2-92ca-5a6d665649c9"/>
     *       </storage_domains>
     *     </disk>
     *   </disks>
     * </vm>
     * ----
     *
     * When the virtual machine is created from a snapshot this set of disks is sligthly different, it uses the
     * `imageId` attribute instead of `id`.
     *
     * [source,xml]
     * ----
     * <vm>
     *   ...
     *   <disks>
     *     <disk>
     *       <image_id>8d4bd566-6c86-4592-a4a7-912dbf93c298</image_id>
     *       <storage_domains>
     *         <storage_domain id="9cb6cb0a-cf1d-41c2-92ca-5a6d665649c9"/>
     *       </storage_domains>
     *     </disk>
     *   </disks>
     * </vm>
     * ----
     *
     * In all cases the name or identifier of the cluster where the virtual machine will be created is mandatory.
     *
     * This is an example of how creating a virtual machine from a snapshot with the disks in a different storage
     * domain can be done with the Python SDK:
     *
     * [source,python]
     * ----
     * # Find the VM:
     * vm = api.vms.get(name="myvm")
     *
     * # Find the snapshot:
     * snapshot = None
     * for current in vm.snapshots.list():
     *   if current.get_description() == 'mysnap':
     *     snapshot = current
     *     break
     *
     * # Find the identifiers of the disks of the snapshot, as we need them in
     * # order to explicitly indicate that we want them created in a different storage
     * # domain:
     * disk_ids = []
     * for current in snapshot.disks.list():
     *   disk_ids.append(current.get_id())
     *
     * # Find the storage domain where the disks should be created:
     * sd = api.storagedomains.get(name="yourdata")
     *
     * # Prepare the list of disks for the operation to create the snapshot,
     * # explicitly indicating for each of them the storage domain where it should be
     * # created:
     * disk_list = []
     * for disk_id in disk_ids:
     *   disk = params.Disk(
     *     image_id=disk_id,
     *     storage_domains=params.StorageDomains(
     *       storage_domain=[
     *         params.StorageDomain(
     *           id=sd.get_id(),
     *         ),
     *       ],
     *     ),
     *   )
     *   disk_list.append(disk)
     *
     * # Create the VM from the snapshot:
     * api.vms.add(
     *   params.VM(
     *     name="myclone",
     *     cluster=params.Cluster(name="mycluster"),
     *     snapshots=params.Snapshots(
     *       snapshot=[
     *         params.Snapshot(
     *           id=snapshot.get_id(),
     *         ),
     *       ],
     *     ),
     *     disks=params.Disks(
     *       disk=disk_list,
     *     ),
     *   )
     * )
     * ----
     */
    interface Add {
        @In @Out Vm vm();
    }

    interface List {
        @Out Vm[] vms();

        /**
         * A query string used to restrict the returned virtual machines.
         */
        @In String search();

        /**
         * The maximum number of results to return.
         */
        @In Integer max();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    @Service VmService vm(String id);
}
