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

import types.NetworkFilterParameter;

/**
 * This service manages a collection of parameters for network filters.
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @date 13 Dec 2016
 * @status added
 * @since 4.2
 */
@Service
@Area("Network")
public interface NicNetworkFilterParametersService {

    /**
     * Retrieves the representations of the network filter parameters.
     *
     * The order of the returned list of network filters isn't guaranteed.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 13 Dec 2016
     * @status added
     * @since 4.2
     */
    interface List extends Follow {

        /**
         * The list of the network filter parameters.
         *
         * @author Dominik Holler <dholler@redhat.com>
         * @date 13 Dec 2016
         * @status added
         * @since 4.2
         */
        @Out NetworkFilterParameter[] parameters();
    }

    /**
     * Add a network filter parameter.
     *
     * For example, to add the parameter for the network filter on NIC `456` of
     * virtual machine `789` send a request like this:
     *
     * ```http
     * POST /ovirt-engine/api/vms/789/nics/456/networkfilterparameters HTTP/1.1
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <network_filter_parameter>
     *   <name>IP</name>
     *   <value>10.0.1.2</value>
     * </network_filter_parameter>
     * ```
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 13 Dec 2016
     * @status added
     * @since 4.2
     */
    interface Add {

        /**
         * The network filter parameter that is being added.
         *
         * @author Dominik Holler <dholler@redhat.com>
         * @date 12 Dec 2016
         * @status added
         * @since 4.2
         */
        @In
        @Out NetworkFilterParameter parameter();
    }

    /**
     * Reference to the service that manages a specific network filter parameter.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     * @since 4.2
     */
    @Service NicNetworkFilterParameterService parameter(String id);
}
