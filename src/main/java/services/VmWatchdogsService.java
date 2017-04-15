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
import types.Watchdog;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
/**
 * Lists the watchdogs of a virtual machine.
 *
 * @author Milan Zamazal <mzamazal@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface VmWatchdogsService {
    /**
     * Adds new watchdog to the virtual machine.
     *
     * For example, to add a watchdog to a virtual machine, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/vms/123/watchdogs
     * <watchdog>
     *   <action>poweroff</action>
     *   <model>i6300esb</model>
     * </watchdog>
     * ----
     *
     * with response body:
     *
     * [source,xml]
     * ----
     * <watchdog href="/ovirt-engine/api/vms/123/watchdogs/00000000-0000-0000-0000-000000000000" id="00000000-0000-0000-0000-000000000000">
     *   <vm href="/ovirt-engine/api/vms/123" id="123"/>
     *   <action>poweroff</action>
     *   <model>i6300esb</model>
     * </watchdog>
     * ----
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(watchdog().action());
            mandatory(watchdog().model());
        }
        /**
         * The information about the watchdog.
         *
         * The request data must contain `model` element (such as `i6300esb`) and `action` element
         * (one of `none`, `reset`, `poweroff`, `dump`, `pause`). The response data additionally
         * contains references to the added watchdog and to the virtual machine.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Watchdog watchdog();
    }

    /**
     * The list of watchdogs of the virtual machine.
     *
     * The order of the returned list of watchdogs isn't guaranteed.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        /**
         * The information about the watchdog.
         *
         * The information consists of `model` element, `action` element and the reference to the
         * virtual machine. It may look like this:
         *
         * [source,xml]
         * ----
         * <watchdogs>
         *   <watchdog href="/ovirt-engine/api/vms/123/watchdogs/00000000-0000-0000-0000-000000000000" id="00000000-0000-0000-0000-000000000000">
         *     <vm href="/ovirt-engine/api/vms/123" id="123"/>
         *     <action>poweroff</action>
         *     <model>i6300esb</model>
         *   </watchdog>
         * </watchdogs>
         * ----
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Watchdog[] watchdogs();

        /**
         * Sets the maximum number of watchdogs to return. If not specified all the watchdogs are returned.
         */
        @In Integer max();
    }

    /**
     * Returns a reference to the service that manages a specific watchdog.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service VmWatchdogService watchdog(String id);
}
