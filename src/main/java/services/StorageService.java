/*
Copyright (c) 2015 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.HostStorage;

@Service
@Area("Storage")
public interface StorageService {
    interface Get extends Follow {
        @Out HostStorage storage();

        /**
         * Indicates if the status of the LUNs in the storage should be checked.
         * Checking the status of the LUN is an heavy weight operation and
         * this data is not always needed by the user.
         * This parameter will give the option to not perform the status check of the LUNs.
         *
         * The default is `true` for backward compatibility.
         *
         * Here an example with the LUN status :
         *
         * [source,xml]
         * ----
         * <host_storage id="360014051136c20574f743bdbd28177fd">
         *   <logical_units>
         *     <logical_unit id="360014051136c20574f743bdbd28177fd">
         *       <lun_mapping>0</lun_mapping>
         *       <paths>1</paths>
         *       <product_id>lun0</product_id>
         *       <serial>SLIO-ORG_lun0_1136c205-74f7-43bd-bd28-177fd5ce6993</serial>
         *       <size>10737418240</size>
         *       <status>used</status>
         *       <vendor_id>LIO-ORG</vendor_id>
         *       <volume_group_id>O9Du7I-RahN-ECe1-dZ1w-nh0b-64io-MNzIBZ</volume_group_id>
         *     </logical_unit>
         *   </logical_units>
         *   <type>iscsi</type>
         *   <host id="8bb5ade5-e988-4000-8b93-dbfc6717fe50"/>
         * </host_storage>
         * ----
         *
         * Here an example without the LUN status :
         *
         * [source,xml]
         * ----
         * <host_storage id="360014051136c20574f743bdbd28177fd">
         *   <logical_units>
         *     <logical_unit id="360014051136c20574f743bdbd28177fd">
         *       <lun_mapping>0</lun_mapping>
         *       <paths>1</paths>
         *       <product_id>lun0</product_id>
         *       <serial>SLIO-ORG_lun0_1136c205-74f7-43bd-bd28-177fd5ce6993</serial>
         *       <size>10737418240</size>
         *       <vendor_id>LIO-ORG</vendor_id>
         *       <volume_group_id>O9Du7I-RahN-ECe1-dZ1w-nh0b-64io-MNzIBZ</volume_group_id>
         *     </logical_unit>
         *   </logical_units>
         *   <type>iscsi</type>
         *   <host id="8bb5ade5-e988-4000-8b93-dbfc6717fe50"/>
         * </host_storage>
         * ----
         */
        @In Boolean reportStatus();
    }
}
