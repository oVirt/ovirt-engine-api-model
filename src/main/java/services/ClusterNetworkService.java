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
 * A service to manage a specific cluster network.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 23 Mar 2017
 * @status updated_by_docs
 */
@Service
@Area("Network")
public interface ClusterNetworkService {
    /**
     * Retrieves the cluster network details.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Mar 2017
     * @status updated_by_docs
     */
    interface Get extends Follow {
        /**
         * The cluster network.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 23 Mar 2017
         * @status updated_by_docs
         */
        @Out Network network();
    }

    /**
     * Unassigns the network from a cluster.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Mar 2017
     * @status updated_by_docs
     */
    interface Remove {
    }

    /**
     * Updates the network in the cluster.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Mar 2017
     * @status updated_by_docs
     */
    interface Update {
        /**
         * The cluster network.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 23 Mar 2017
         * @status updated_by_docs
         */
        @In @Out Network network();
    }
}
