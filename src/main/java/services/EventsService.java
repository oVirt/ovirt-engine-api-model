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
import types.Event;

@Service
@Area("Infrastructure")
public interface EventsService {
    /**
     * Adds an external event to the internal audit log.
     *
     * This is intended for integration with external systems that detect or produce events relevant for the
     * administrator of the system. For example, an external monitoring tool may be able to detect that a file system
     * is full inside the guest operating system of a virtual machine. This event can be added to the internal audit
     * log sending a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/events
     * <event>
     *   <description>File system /home is full</description>
     *   <severity>alert</severity>
     *   <origin>mymonitor</origin>
     *   <custom_id>1467879754</custom_id>
     * </event>
     * ----
     *
     * Events can also be linked to specific objects. For example, the above event could be linked to the specific
     * virtual machine where it happened, using the `vm` link:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/events
     * <event>
     *   <description>File system /home is full</description>
     *   <severity>alert</severity>
     *   <origin>mymonitor</origin>
     *   <custom_id>1467879754</custom_id>
     *   <vm id="aae98225-5b73-490d-a252-899209af17e9"/>
     * </event>
     * ----
     *
     * NOTE: When using links, like the `vm` in the previous example, only the `id` attribute is accepted. The `name`
     * attribute, if provided, is simply ignored.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 7 Jul 2016
     * @status added
     */
    interface Add {
        @In @Out Event event();
    }

    interface List {
        @Out Event[] events();

        /**
         * Indicates the identifier of the the first event that should be returned. The identifiers of events are
         * strictly increasing, so when this parameter is used only the events with that identifiers equal or greater
         * than the given value will be returned. For example, the following request will return only the events
         * with identifiers greater or equal than `123`:
         *
         * [source]
         * ----
         * GET /ovirt-engine/api/events?from=123
         * ----
         *
         * This parameter is optional, and if not specified then the first event returned will be most recently
         * generated.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @date 6 Jul 2016
         * @status added
         */
        @In Integer from();

        /**
         * Sets the maximum number of events to return. If not specified all the events are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned events.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();
    }

    interface Undelete {
        /**
         * Indicates if the un-delete should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service EventService event(String id);
}
