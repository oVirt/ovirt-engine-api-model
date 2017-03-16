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
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Network;

/**
 * A service to manage a specific data center network.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 23 Mar 2017
 * @status updated_by_docs
 */
@Service
@Area("Network")
public interface DataCenterNetworkService {
    /**
     * Retrieves the data center network details.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Mar 2017
     * @status updated_by_docs
     */
    interface Get {
        /**
         * The data center network.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 23 Mar 2017
         * @status updated_by_docs
         */
        @Out Network network();
    }

    /**
     * Removes the network.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Mar 2017
     * @status updated_by_docs
     */
    interface Remove {
    }

    /**
     * Updates the network in the data center.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 23 Mar 2017
     * @status updated_by_docs
     */
    interface Update {
        /**
         * The data center network.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 23 Mar 2017
         * @status updated_by_docs
         */
        @In @Out Network network();
    }
}
