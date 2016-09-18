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
import types.IscsiBond;

@Service
@Area("Storage")
public interface IscsiBondsService {

    /**
     * Create a new iSCSI bond on a data center.
     *
     * For example, to create a new iSCSI bond on data center `123` using storage connections `456` and `789`, send a
     * request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/datacenters/123/iscsibonds
     * ----
     *
     * The request body should look like this:
     *
     * [source,xml]
     * ----
     * <iscsi_bond>
     *   <name>mybond</name>
     *   <storage_connections>
     *     <storage_connection id="456"/>
     *     <storage_connection id="789"/>
     *   </storage_connections>
     *   <networks>
     *     <network id="abc"/>
     *   </networks>
     * </iscsi_bond>
     * ----
     *
     * @author Maor Lipchuk <mlipchuk@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        @In @Out IscsiBond bond();
    }

    interface List {
        @Out IscsiBond[] bonds();

        /**
         * Sets the maximum number of bonds to return. If not specified all the bonds are returned.
         */
        @In Integer max();
    }

    @Service IscsiBondService iscsiBond(String id);
}
