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
import types.Nic;

@Service
@Area("Network")
public interface InstanceTypeNicsService {
    /**
     * Add new network interface to the instance type.
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        @In @Out Nic nic();
    }

    /**
     * Lists all the configured network interface of the instance type.
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        @Out Nic[] nics();

        /**
         * Sets the maximum number of NICs to return. If not specified all the NICs are returned.
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
    }

    @Service InstanceTypeNicService nic(String id);
}
