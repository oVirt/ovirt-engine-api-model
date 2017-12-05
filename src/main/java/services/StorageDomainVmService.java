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
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Cluster;
import types.RegistrationConfiguration;
import types.StorageDomain;
import types.Vm;
import types.VnicProfileMapping;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

@Service
@Area("Storage")
public interface StorageDomainVmService {
    interface Get extends Follow {
        @Out Vm vm();
    }

    /**
     * Imports a virtual machine from an export storage domain.
     *
     * For example, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/storagedomains/123/vms/456/import
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <action>
     *   <storage_domain>
     *     <name>mydata</name>
     *   </storage_domain>
     *   <cluster>
     *     <name>mycluster</name>
     *   </cluster>
     * </action>
     * ----
     *
     * To import a virtual machine as a new entity add the `clone` parameter:
     *
     * [source,xml]
     * ----
     * <action>
     *   <storage_domain>
     *     <name>mydata</name>
     *   </storage_domain>
     *   <cluster>
     *     <name>mycluster</name>
     *   </cluster>
     *   <clone>true</clone>
     *   <vm>
     *     <name>myvm</name>
     *   </vm>
     * </action>
     * ----
     *
     * Include an optional `disks` parameter to choose which disks to import. For example, to import the disks
     * of the template that have the identifiers `123` and `456` send the following request body:
     *
     * [source,xml]
     * ----
     * <action>
     *   <cluster>
     *     <name>mycluster</name>
     *   </cluster>
     *   <vm>
     *     <name>myvm</name>
     *   </vm>
     *   <disks>
     *     <disk id="123"/>
     *     <disk id="456"/>
     *   </disks>
     * </action>
     * ----
     *
     * If you register an entity without specifying the cluster ID or name,
     * the cluster name from the entity's OVF will be used (unless the register request also includes the
     * cluster mapping).
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 20 Nov 2017
     * @status updated_by_docs
     */
    interface Import {
        @InputDetail
        default void inputDetail() {
            or(optional(cluster().id()), optional(cluster().name()));
            optional(clone());
            optional(exclusive());
            optional(vm().name());
            or(optional(storageDomain().id()), optional(storageDomain().name()));
            optional(vm().diskAttachments()[COLLECTION].disk().format());
            optional(vm().diskAttachments()[COLLECTION].id());
            optional(vm().diskAttachments()[COLLECTION].disk().sparse());
        }
        /**
         * Indicates if the identifiers of the imported virtual machine
         * should be regenerated.
         *
         * By default when a virtual machine is imported the identifiers
         * are preserved. This means that the same virtual machine can't
         * be imported multiple times, as that identifiers needs to be
         * unique. To allow importing the same machine multiple times set
         * this parameter to `true`, as the default is `false`.
         */
        @In Boolean clone();

        @In Cluster cluster();
        @In StorageDomain storageDomain();
        @In Vm vm();
        @In Boolean exclusive();

        /**
         * Indicates of the snapshots of the virtual machine that is imported
         * should be collapsed, so that the result will be a virtual machine
         * without snapshots.
         *
         * This parameter is optional, and if it isn't explicitly specified the
         * default value is `false`.
         */
        @In Boolean collapseSnapshots();

        /**
         * Indicates if the import should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Register {
        @In Boolean clone();
        @In Cluster cluster();
        @In Vm vm();

        /**
         * Indicates whether a virtual machine is allowed to be registered with only some of its disks.
         *
         * If this flag is `true`, the engine will not fail in the validation process if an image is not found, but
         * instead it will allow the virtual machine to be registered without the missing disks. This is mainly used
         * during registration of a virtual machine when some of the storage domains are not available. The default
         * value is `false`.
         *
         * @author Maor Lipchuk <mlipchuk@redhat.com>
         * @date 17 Nov 2016
         * @status added
         * @since 4.1
         */
        @In Boolean allowPartialImport();

        /**
         * Deprecated attribute describing mapping rules for virtual NIC profiles that will be applied during the import\register process.
         *
         * WARNING: Please note that this attribute has been deprecated since version 4.2.1 of the engine, and preserved only for backward
         * compatibility. It will be removed in the future. To specify `vnic_profile_mappings` use the `vnic_profile_mappings`
         * attribute inside the <<types/registration_configuration, RegistrationConfiguration>> type.
         *
         * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
         * @author Eitan Raviv <eraviv@redhat.com>
         * @date 26 Sep 2016
         * @status added
         * @since 4.1
         */
        @Deprecated
        @In VnicProfileMapping[] vnicProfileMappings();

        /**
         * Indicates if the problematic MAC addresses should be re-assigned during the import process by the engine.
         *
         * A MAC address would be considered as a problematic one if one of the following is true:
         *
         * - It conflicts with a MAC address that is already allocated to a virtual machine in the target environment.
         * - It's out of the range of the target MAC address pool.
         *
         * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
         * @date 26 Sep 2016
         * @status added
         * @since 4.1
         */
        @In Boolean reassignBadMacs();

        /**
         * This parameter describes how the virtual machine should be
         * registered.
         *
         * This parameter is optional. If the parameter is not specified, the virtual
         * machine will be registered with the same configuration that
         * it had in the original environment where it was created.
         *
         * @author Maor Lipchuk <mlipchuk@redhat.com>
         * @author Byron Gravenorst <bgraveno@redhat.com>
         * @date 20 Oct 2017
         * @status updated_by_docs
         * @since 4.2
         */
        @In RegistrationConfiguration registrationConfiguration();

        /**
         * Indicates if the registration should be performed asynchronously.
         */
        @In Boolean async();

        @InputDetail
        default void inputDetail() {
            or(optional(cluster().id()), optional(cluster().name()));
            optional(registrationConfiguration());
        }
    }

    /**
     * Deletes a virtual machine from an export storage domain.
     *
     * For example, to delete the virtual machine `456` from the storage domain `123`, send a request like this:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/storagedomains/123/vms/456
     * ----
     *
     * @author Liron Aravot <laravot@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service StorageDomainContentDisksService disks();

    /**
     * Returns a reference to the service that manages the disk attachments of the virtual machine.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 6 Sep 2016
     * @status added
     * @since 4.0.4
     */
    @Service StorageDomainVmDiskAttachmentsService diskAttachments();
}
