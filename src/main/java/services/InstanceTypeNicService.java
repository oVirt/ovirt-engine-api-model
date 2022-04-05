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
import types.Nic;

@Service
@Area("Network")
public interface InstanceTypeNicService {
    /**
     * Gets network interface configuration of the instance type.
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        @Out Nic nic();
    }

    /**
     * Updates the network interface configuration of the instance type.
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Update {
        @In @Out Nic nic();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Remove the network interface from the instance type.
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
