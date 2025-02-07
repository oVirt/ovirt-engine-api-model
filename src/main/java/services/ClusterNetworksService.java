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
import types.Network;

/**
 * A service to manage cluster networks.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 23 Mar 2017
 * @status updated_by_docs
 */
@Service
@Area("Network")
public interface ClusterNetworksService {
    /**
     * Assigns the network to a cluster.
     *
     * Post a request like in the example below to assign the network to a cluster:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/123/networks
     * ----
     *
     * Use the following example in its body:
     *
     * [source,xml]
     * ----
     * <network id="123" />
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Mar 2017
     * @status updated_by_docs
     */
    interface Add {
        /**
         * The network object to be assigned to the cluster.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 23 Mar 2017
         * @status updated_by_docs
         */
        @In @Out Network network();
    }

    /**
     * Lists the networks that are assigned to the cluster.
     *
     * The order of the returned clusters isn't guaranteed.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Mar 2017
     * @status updated_by_docs
     */
    interface List extends Follow {
        /**
         * The list of networks that are assigned to the cluster.
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
     * Access the cluster network service that manages the cluster network specified by an ID.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Mar 2017
     * @status updated_by_docs
     */
    @Service ClusterNetworkService network(String id);
}
