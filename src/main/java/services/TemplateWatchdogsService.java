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
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Watchdog;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

@Service
@Area("Virtualization")
public interface TemplateWatchdogsService {
    /**
     * Add a watchdog to the template identified by the given id.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(watchdog().action());
            mandatory(watchdog().model());
        }
        @In @Out Watchdog watchdog();
    }

    /**
     * Returns the list of watchdogs.
     *
     * The order of the returned list of watchdogs isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out Watchdog[] watchdogs();

        /**
         * Sets the maximum number of watchdogs to return. If not specified all the watchdogs are returned.
         */
        @In Integer max();
    }

    @Service
    TemplateWatchdogService watchdog(String id);
}
