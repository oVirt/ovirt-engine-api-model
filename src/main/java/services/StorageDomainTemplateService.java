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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Cluster;
import types.RegistrationConfiguration;
import types.StorageDomain;
import types.Template;
import types.Vm;
import types.VnicProfileMapping;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

@Service
@Area("Storage")
public interface StorageDomainTemplateService {
    interface Get extends Follow {
        @Out Template template();
    }

    /**
     * Action to import a template from an export storage domain.
     *
     * For example, to import the template `456` from the storage domain `123` send the following request:
     *
     * ```http
     * POST /ovirt-engine/api/storagedomains/123/templates/456/import
     * ```
     *
     * With the following request body:
     *
     * ```xml
     * <action>
     *   <storage_domain>
     *     <name>myexport</name>
     *   </storage_domain>
     *   <cluster>
     *     <name>mycluster</name>
     *   </cluster>
     * </action>
     * ```
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
            optional(template().name());
            or(optional(storageDomain().id()), optional(storageDomain().name()));
            optional(vm().diskAttachments()[COLLECTION].id());
        }
        /**
         * Use the optional `clone` parameter to generate new UUIDs for the imported template and its entities.
         *
         * You can import a template with the `clone` parameter set to `false` when importing a template
         * from an export domain, with templates that were exported by a different {product-name} environment.
         *
         * @author Amit Aviram <aaviram@redhat.com>
         * @author Byron Gravenorst <bgraveno@redhat.com>
         * @date 20 Nov 2017
         * @status updated_by_docs
         */
        @In Boolean clone();
        @In Cluster cluster();
        @In Boolean exclusive();
        @In StorageDomain storageDomain();
        @In Template template();
        @In Vm vm();

        /**
         * Indicates if the import should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Register the Template means importing the Template from the data domain by inserting the configuration of the
     * Template and disks into the database without the copy process.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 20 Nov 2017
     * @status updated_by_docs
     */
    interface Register {
        @InputDetail
        default void inputDetail() {
            or(optional(cluster().id()), optional(cluster().name()));
            optional(clone());
            optional(exclusive());
            optional(template().name());
            optional(registrationConfiguration());
        }
        @In Boolean clone();
        @In Cluster cluster();
        @In Boolean exclusive();
        @In Template template();

        /**
         * Indicates whether a template is allowed to be registered with only some of its disks.
         *
         * If this flag is `true`, the system will not fail in the validation process if an image is not found, but
         * instead it will allow the template to be registered without the missing disks. This is mainly used during
         * registration of a template when some of the storage domains are not available. The default value is `false`.
         *
         * @author Maor Lipchuk <mlipchuk@redhat.com>
         * @author Byron Gravenorst <bgraveno@redhat.com>
         * @date 20 Nov 2017
         * @status updated_by_docs
         * @since 4.1
         */
        @In Boolean allowPartialImport();

        /**
         * Deprecated attribute describing mapping rules for virtual NIC profiles that will be applied during the import\register process.
         *
         * WARNING: Please note that this attribute has been deprecated since version 4.2.1 of the engine, and preserved only for backward
         * compatibility. It will be removed in the future. To specify `vnic_profile_mappings` use the `vnic_profile_mappings`
         * attribute inside the xref:types/registration_configuration[RegistrationConfiguration] type.
         *
         * @author Maor Lipchuk <mlipchuk@redhat.com>
         * @author Eitan Raviv <eraviv@redhat.com>
         * @date 23 Oct 2017
         * @status added
         * @since 4.2
         */
        @Deprecated
        @In VnicProfileMapping[] vnicProfileMappings();

        /**
         *
         * This parameter describes how the template should be
         * registered.
         *
         * This parameter is optional. If the parameter is not specified, the template
         * will be registered with the same configuration that
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
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service StorageDomainContentDisksService disks();
}
