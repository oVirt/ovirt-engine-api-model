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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Watchdog;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * A service managing a watchdog on virtual machines.
 *
 * @author Milan Zamazal <mzamazal@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface VmWatchdogService {
    /**
     * Returns the information about the watchdog.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
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
        @Out Watchdog watchdog();
    }

    /**
     * Updates the information about the watchdog.
     *
     * You can update the information using `action` and `model` elements.
     *
     * For example, to update a watchdog, send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/vms/123/watchdogs
     * <watchdog>
     *   <action>reset</action>
     * </watchdog>
     * ----
     *
     * with response body:
     *
     * [source,xml]
     * ----
     * <watchdog href="/ovirt-engine/api/vms/123/watchdogs/00000000-0000-0000-0000-000000000000" id="00000000-0000-0000-0000-000000000000">
     *   <vm href="/ovirt-engine/api/vms/123" id="123"/>
     *   <action>reset</action>
     *   <model>i6300esb</model>
     * </watchdog>
     * ----
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(watchdog().action());
            optional(watchdog().model());
        }
        /**
         * The information about the watchdog.
         *
         * The request data must contain at least one of `model` and `action`
         * elements. The response data contains complete information about the
         * updated watchdog.
         *
         * @author Milan Zamazal <mzamazal@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Watchdog watchdog();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes the watchdog from the virtual machine.
     *
     * For example, to remove a watchdog from a virtual machine, send a request like this:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/vms/123/watchdogs/00000000-0000-0000-0000-000000000000
     * ----
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
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
