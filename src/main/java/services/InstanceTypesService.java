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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.InstanceType;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("Virtualization")
public interface InstanceTypesService {
    /**
     * Creates a new instance type.
     *
     * This requires only a name attribute and can include all hardware configurations of the
     * virtual machine.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/instancetypes
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <instance_type>
     *   <name>myinstancetype</name>
     * </template>
     * ----
     *
     * Creating an instance type with all hardware configurations with a request body like this:
     *
     * [source,xml]
     * ----
     * <instance_type>
     *   <name>myinstancetype</name>
     *   <console>
     *     <enabled>true</enabled>
     *   </console>
     *   <cpu>
     *     <topology>
     *       <cores>2</cores>
     *       <sockets>2</sockets>
     *       <threads>1</threads>
     *     </topology>
     *   </cpu>
     *   <custom_cpu_model>AMD Opteron_G2</custom_cpu_model>
     *   <custom_emulated_machine>q35</custom_emulated_machine>
     *   <display>
     *     <monitors>1</monitors>
     *     <single_qxl_pci>true</single_qxl_pci>
     *     <smartcard_enabled>true</smartcard_enabled>
     *     <type>spice</type>
     *   </display>
     *   <high_availability>
     *     <enabled>true</enabled>
     *     <priority>1</priority>
     *   </high_availability>
     *   <io>
     *     <threads>2</threads>
     *   </io>
     *   <memory>4294967296</memory>
     *   <memory_policy>
     *     <ballooning>true</ballooning>
     *     <guaranteed>268435456</guaranteed>
     *   </memory_policy>
     *   <migration>
     *     <auto_converge>inherit</auto_converge>
     *     <compressed>inherit</compressed>
     *     <policy id="00000000-0000-0000-0000-000000000000"/>
     *   </migration>
     *   <migration_downtime>2</migration_downtime>
     *   <os>
     *     <boot>
     *       <devices>
     *         <device>hd</device>
     *       </devices>
     *     </boot>
     *   </os>
     *   <rng_device>
     *     <rate>
     *       <bytes>200</bytes>
     *       <period>2</period>
     *     </rate>
     *     <source>urandom</source>
     *   </rng_device>
     *   <soundcard_enabled>true</soundcard_enabled>
     *   <usb>
     *     <enabled>true</enabled>
     *     <type>native</type>
     *   </usb>
     *   <virtio_scsi>
     *     <enabled>true</enabled>
     *   </virtio_scsi>
     * </instance_type>
     * ----
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(instanceType().name());
            optional(instanceType().console().enabled());
            optional(instanceType().cpu().architecture());
            optional(instanceType().cpu().topology().cores());
            optional(instanceType().cpu().topology().sockets());
            optional(instanceType().cpu().topology().threads());
            optional(instanceType().cpuShares());
            optional(instanceType().customCpuModel());
            optional(instanceType().customEmulatedMachine());
            optional(instanceType().description());
            optional(instanceType().display().monitors());
            optional(instanceType().display().smartcardEnabled());
            optional(instanceType().display().type());
            optional(instanceType().highAvailability().enabled());
            optional(instanceType().highAvailability().priority());
            optional(instanceType().io().threads());
            optional(instanceType().memory());
            optional(instanceType().memoryPolicy().ballooning());
            optional(instanceType().memoryPolicy().guaranteed());
            optional(instanceType().migrationDowntime());
            optional(instanceType().origin());
            optional(instanceType().soundcardEnabled());
            optional(instanceType().usb().enabled());
            optional(instanceType().usb().type());
            optional(instanceType().virtioScsi().enabled());
            optional(instanceType().storageErrorResumeBehaviour());
//            optional(instanceType().singleQxlPci());  //TODO: check
//            optional(instanceType().os()[COLLECTION].dev());  //TODO: check
        }
        @In @Out InstanceType instanceType();
    }

    /**
     * Lists all existing instance types in the system.
     *
     * The order of the returned list of instance types isn't guaranteed.
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        @Out InstanceType[] instanceType();

        /**
         * Sets the maximum number of instance types to return. If not specified all the instance
         * types are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned templates.
         *
         * @author Sefi Litmanovich <slitmano@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed
         * taking case into account. The default value is `true`, which means that case is taken
         * into account. If you want to search ignoring case set it to `false`.
         *
         * @author Sefi Litmanovich <slitmano@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In Boolean caseSensitive();
    }

    @Service InstanceTypeService instanceType(String id);
}
