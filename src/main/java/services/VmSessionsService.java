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
import types.Session;

/**
 * Provides information about virtual machine user sessions.
 *
 * @author Jakub Niedermertl <jniederm@redhat.com>
 * @date 14 Sep 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface VmSessionsService {

    /**
     * Lists all user sessions for this virtual machine.
     *
     * For example, to retrieve the session information for virtual machine `123` send a request like this:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/vms/123/sessions
     * ----
     *
     * The response body will contain something like this:
     *
     * [source,xml]
     * ----
     * <sessions>
     *   <session href="/ovirt-engine/api/vms/123/sessions/456" id="456">
     *     <console_user>true</console_user>
     *     <ip>
     *       <address>192.168.122.1</address>
     *     </ip>
     *     <user href="/ovirt-engine/api/users/789" id="789"/>
     *     <vm href="/ovirt-engine/api/vms/123" id="123"/>
     *   </session>
     *   ...
     * </sessions>
     * ----
     *
     * The order of the returned list of sessions isn't guaranteed.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface List extends Follow {
        @Out Session[] sessions();

        /**
         * Sets the maximum number of sessions to return. If not specified all the sessions are returned.
         */
        @In Integer max();
    }

    /**
     * Reference to the service that manages a specific session.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    @Service VmSessionService session(String id);
}
