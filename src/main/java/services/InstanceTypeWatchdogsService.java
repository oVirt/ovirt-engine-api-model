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
import types.Watchdog;

@Service
@Area("Virtualization")
public interface InstanceTypeWatchdogsService {
    /**
     * Add new watchdog to the instance type.
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        @In @Out Watchdog watchdog();
    }

    /**
     * Lists all the configured watchdogs of the instance type.
     *
     * The order of the returned list of watchdogs isn't guaranteed.
     *
     * @author Sefi Litmanovich <slitmano@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        @Out Watchdog[] watchdogs();

        /**
         * Sets the maximum number of watchdogs to return. If not specified all the watchdogs are
         * returned.
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

    @Service InstanceTypeWatchdogService watchdog(String id);
}
