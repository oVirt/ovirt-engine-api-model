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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.MacPool;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("Network")
public interface MacPoolsService {
    /**
     * Creates a new MAC address pool.
     *
     * Creation of a MAC address pool requires values for the `name` and `ranges` attributes.
     *
     * For example, to create MAC address pool send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/macpools
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <mac_pool>
     *   <name>MACPool</name>
     *   <description>A MAC address pool</description>
     *   <allow_duplicates>true</allow_duplicates>
     *   <default_pool>false</default_pool>
     *   <ranges>
     *     <range>
     *       <from>00:1A:4A:16:01:51</from>
     *       <to>00:1A:4A:16:01:e6</to>
     *     </range>
     *   </ranges>
     * </mac_pool>
     * ----
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(pool().name());
            mandatory(pool().ranges()[COLLECTION].from());
            mandatory(pool().ranges()[COLLECTION].to());
            optional(pool().allowDuplicates());
            optional(pool().defaultPool());
            optional(pool().description());
        }
        @In @Out MacPool pool();
    }

    interface List {
        @Out MacPool[] pools();

        /**
         * Sets the maximum number of pools to return. If not specified all the pools are returned.
         */
        @In Integer max();
    }

    @Service MacPoolService macPool(String id);
}
