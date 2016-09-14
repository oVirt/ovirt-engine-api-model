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
import types.MacPool;

@Service
@Area("Network")
public interface MacPoolService {
    interface Get {
        @Out MacPool pool();
    }

    /**
     * Updates a MAC address pool.
     *
     * The `name`, `description`, `allow_duplicates`, and `ranges` attributes can be updated.
     *
     * For example, to update the MAC address pool of id `123` send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/macpools/123
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <mac_pool>
     *   <name>UpdatedMACPool</name>
     *   <description>An updated MAC address pool</description>
     *   <allow_duplicates>false</allow_duplicates>
     *   <ranges>
     *     <range>
     *       <from>00:1A:4A:16:01:51</from>
     *       <to>00:1A:4A:16:01:e6</to>
     *     </range>
     *     <range>
     *       <from>02:1A:4A:01:00:00</from>
     *       <to>02:1A:4A:FF:FF:FF</to>
     *     </range>
     *   </ranges>
     * </mac_pool>
     * ----
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @In @Out MacPool pool();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes a MAC address pool.
     *
     * For example, to remove the MAC address pool having id `123` send a request like this:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/macpools/123
     * ----
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
