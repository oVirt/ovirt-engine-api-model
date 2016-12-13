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

package services;

import annotations.Area;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.NetworkFilterParameter;

/**
 * This service manages a parameter for a network filter.
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @date 13 Dec 2016
 * @status added
 * @since 4.2
 */
@Service
@Area("Network")
public interface NetworkFilterParameterService {

    /**
     * Retrieves a representation of the network filter parameter.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 13 Dec 2016
     * @status added
     * @since 4.2
     */
    interface Get {

        /**
         * The representation of the network filter parameter.
         *
         * @author Dominik Holler <dholler@redhat.com>
         * @date 13 Dec 2016
         * @status added
         * @since 4.2
         */
        @Out NetworkFilterParameter parameter();
    }

    /**
     * Updates the network filter parameter.
     *
     * For example, to update the network filter parameter having with with id `123` on NIC `456` of
     * virtual machine `789` send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/vms/789/nics/456/networkfilterparameters/123
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <network_filter_parameter>
     *   <name>updatedName</name>
     *   <value>updatedValue</value>
     * </network_filter_parameter>
     * ----
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 13 Dec 2016
     * @status added
     * @since 4.2
     */
    interface Update {

        /**
         * The network filter parameter that is being updated.
         *
         * @author Dominik Holler <dholler@redhat.com>
         * @date 12 Dec 2016
         * @status added
         * @since 4.2
         */
        @In
        @Out
        NetworkFilterParameter parameter();
    }

    /**
     * Removes the filter parameter.
     *
     * For example, to remove the filter parameter with id `123` on NIC `456` of virtual machine `789`
     * send a request like this:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/vms/789/nics/456/networkfilterparameters/123
     * ----
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 13 Dec 2016
     * @status added
     * @since 4.2
     */
    interface Remove {
    }
}
