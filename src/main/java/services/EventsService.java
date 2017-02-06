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
import types.Event;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * A service to manage events in the system.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
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
        @InputDetail
        default void inputDetail() {
            mandatory(event().customId());
            mandatory(event().description());
            mandatory(event().origin());
            mandatory(event().severity());
            optional(event().cluster().id());
            optional(event().dataCenter().id());
            optional(event().floodRate());
            optional(event().host().externalStatus());
            optional(event().host().id());
            optional(event().storageDomain().externalStatus());
            optional(event().storageDomain().id());
            optional(event().template().id());
            optional(event().user().id());
            optional(event().vm().id());
        }
        @In @Out Event event();
    }

    /**
     * Get list of events.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/events
     * ----
     *
     * To the above request we get following response:
     *
     * [source,xml]
     * ----
     * <events>
     *   <event href="/ovirt-engine/api/events/2" id="2">
     *     <description>User admin@internal-authz logged out.</description>
     *     <code>31</code>
     *     <correlation_id>1e892ea9</correlation_id>
     *     <custom_id>-1</custom_id>
     *     <flood_rate>30</flood_rate>
     *     <origin>oVirt</origin>
     *     <severity>normal</severity>
     *     <time>2016-09-14T12:14:34.541+02:00</time>
     *     <user href="/ovirt-engine/api/users/57d91d48-00da-0137-0138-000000000244" id="57d91d48-00da-0137-0138-000000000244"/>
     *   </event>
     *   <event href="/ovirt-engine/api/events/1" id="1">
     *     <description>User admin logged in.</description>
     *     <code>30</code>
     *     <correlation_id>1fbd81f4</correlation_id>
     *     <custom_id>-1</custom_id>
     *     <flood_rate>30</flood_rate>
     *     <origin>oVirt</origin>
     *     <severity>normal</severity>
     *     <time>2016-09-14T11:54:35.229+02:00</time>
     *     <user href="/ovirt-engine/api/users/57d91d48-00da-0137-0138-000000000244" id="57d91d48-00da-0137-0138-000000000244"/>
     *   </event>
     * </events>
     * ----
     *
     * The following events occur:
     *
     * * id="1" - The API logs in the admin user account.
     * * id="2" - The API logs out of the admin user account.
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @date 14 Sep 2016
     * @status added
     *
     */
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
         * The events service provides search queries similar to other resource services.
         *
         * We can search by providing specific severity.
         *
         * [source]
         * ----
         * GET /ovirt-engine/api/events?search=severity%3Dnormal
         * ----
         *
         * To the above request we get a list of events which severity is equal to `normal`:
         *
         * [source,xml]
         * ----
         * <events>
         *   <event href="/ovirt-engine/api/events/2" id="2">
         *     <description>User admin@internal-authz logged out.</description>
         *     <code>31</code>
         *     <correlation_id>1fbd81f4</correlation_id>
         *     <custom_id>-1</custom_id>
         *     <flood_rate>30</flood_rate>
         *     <origin>oVirt</origin>
         *     <severity>normal</severity>
         *     <time>2016-09-14T11:54:35.229+02:00</time>
         *     <user href="/ovirt-engine/api/users/57d91d48-00da-0137-0138-000000000244" id="57d91d48-00da-0137-0138-000000000244"/>
         *   </event>
         *   <event href="/ovirt-engine/api/events/1" id="1">
         *     <description>Affinity Rules Enforcement Manager started.</description>
         *     <code>10780</code>
         *     <custom_id>-1</custom_id>
         *     <flood_rate>30</flood_rate>
         *     <origin>oVirt</origin>
         *     <severity>normal</severity>
         *     <time>2016-09-14T11:52:18.861+02:00</time>
         *   </event>
         * </events>
         * ----
         *
         * A virtualization environment generates a large amount of events after
         * a period of time. However, the API only displays a default number of
         * events for one search query. To display more than the default, the API
         * separates results into pages with the page command in a search query.
         * The following search query tells the API to paginate results using a
         * page value in combination with the sortby clause:
         *
         * [source]
         * ----
         * sortby time asc page 1
         * ----
         *
         * Below example paginates event resources. The URL-encoded request is:
         *
         * [source]
         * ----
         * GET /ovirt-engine/api/events?search=sortby%20time%20asc%20page%201
         * ----
         *
         * Increase the page value to view the next page of results.
         *
         * [source]
         * ----
         * GET /ovirt-engine/api/events?search=sortby%20time%20asc%20page%202
         * ----
         *
         * @author Piotr Kliczewski <pkliczew@redhat.com>
         * @date 14 Sep 2016
         * @status added
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

    /**
     * Reference to the service that manages a specific event.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service EventService event(String id);
}
