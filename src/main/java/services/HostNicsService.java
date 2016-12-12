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
import types.HostNic;

/**
 * A service to manage the network interfaces of a host.
 *
 * @author Leon Goldberg <lgoldber@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Network")
public interface HostNicsService {

    interface List {
        @Out HostNic[] nics();

        /**
         * Sets the maximum number of NICs to return. If not specified all the NICs are returned.
         */
        @In Integer max();
    }

    /**
     * Reference to the service that manages a single network interface.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service HostNicService nic(String id);
}
