/*
Copyright (c) 2017 Red Hat, Inc.

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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Network;

/**
 * A service to manage data center networks.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 23 Mar 2017
 * @status updated_by_docs
 */
@Service
@Area("Network")
public interface DataCenterNetworksService {
    /**
     * Create a new network in a data center.
     *
     * Post a request like in the example below to create a new network in a data center with an ID of `123`.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/datacenters/123/networks
     * ----
     *
     * Use the following example in its body:
     *
     * [source,xml]
     * ----
     * <network>
     *   <name>mynetwork</name>
     * </network>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Mar 2017
     * @status updated_by_docs
     */
    interface Add {
        /**
         * The network object to be created in the data center.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 23 Mar 2017
         * @status updated_by_docs
         */
        @In @Out Network network();
    }

    /**
     * Lists networks in the data center.
     *
     * The order of the returned list of networks isn't guaranteed.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Mar 2017
     * @status updated_by_docs
     */
    interface List extends Follow {
        /**
         * The list of networks which are in the data center.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 23 Mar 2017
         * @status updated_by_docs
         */
        @Out Network[] networks();

        /**
         * Sets the maximum number of networks to return. If not specified, all the networks are returned.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 23 Mar 2017
         * @status updated_by_docs
         */
        @In Integer max();
    }

    /**
     * Access the data center network service that manages the data center network specified by an ID.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Mar 2017
     * @status updated_by_docs
     */
    @Service DataCenterNetworkService network(String id);
}
