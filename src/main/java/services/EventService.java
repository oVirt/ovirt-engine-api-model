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
import types.Event;

/**
 * A service to manage an event in the system.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface EventService {
    /**
     * Get an event.
     *
     * An example of getting an event:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/events/123
     * ----
     *
     * [source,xml]
     * ----
     * <event href="/ovirt-engine/api/events/123" id="123">
     *   <description>Host example.com was added by admin@internal-authz.</description>
     *   <code>42</code>
     *   <correlation_id>135</correlation_id>
     *   <custom_id>-1</custom_id>
     *   <flood_rate>30</flood_rate>
     *   <origin>oVirt</origin>
     *   <severity>normal</severity>
     *   <time>2016-12-11T11:13:44.654+02:00</time>
     *   <cluster href="/ovirt-engine/api/clusters/456" id="456"/>
     *   <host href="/ovirt-engine/api/hosts/789" id="789"/>
     *   <user href="/ovirt-engine/api/users/987" id="987"/>
     * </event>
     * ----
     *
     * Note that the number of fields changes according to the information that resides on the event.
     * For example, for storage domain related events you will get the storage domain reference,
     * as well as the reference for the data center this storage domain resides in.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        @Out Event event();
    }

    /**
     * Removes an event from internal audit log.
     *
     * An event can be removed by sending following request
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/events/123
     * ----
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
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
