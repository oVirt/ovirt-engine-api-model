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
import types.Host;

/**
 * A service that manages hosts.
 *
 * @author Yaniv Bronheim <ybronhei@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface HostsService {
    /**
     * Creates a new host.
     *
     * The host is created based on the attributes of the `host` parameter. The `name`, `address` and `root_password`
     * properties are required.
     *
     * For example, to add a host send the following request:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/hosts
     * ----
     *
     * With the following request body:
     *
     * [source,xml]
     * ----
     * <host>
     *   <name>myhost</name>
     *   <address>myhost.example.com</address>
     *   <root_password>myrootpassword</root_password>
     * </host>
     * ----
     *
     * NOTE: The `root_password` element is only included in the client-provided initial representation and is not
     * exposed in the representations returned from subsequent requests.
     *
     * To add a hosted engine host, use the optional `deploy_hosted_engine` parameter:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/hosts?deploy_hosted_engine=true
     * ----
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Roy Golan <rgolan@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        /**
         * The host definition from which to create the new host is passed as parameter, and the newly created host
         * is returned.
         */
        @In @Out Host host();

        /**
         * When set to `true` it means this host should deploy also hosted engine components. Missing value is treated
         * as `true` i.e deploy. Omitting this parameter means `false` and will perform no operation in hosted engine
         * area.
         */
        @In Boolean deployHostedEngine();

        /**
         * When set to `true` it means this host should un-deploy hosted engine components and this host will not
         * function as part of the High Availability cluster. Missing value is treated as `true` i.e un-deploy.
         * Omitting this parameter means `false` and will perform no operation in hosted engine area.
         */
        @In Boolean undeployHostedEngine();
    }

    /**
     * Get a list of all available hosts.
     *
     * For example, to list the hosts send the following request:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/hosts
     *
     * The response body will be something like this:
     *
     * [source,xml]
     * ----
     * <hosts>
     *   <host href="/ovirt-engine/api/hosts/123" id="123">
     *     ...
     *   </host>
     *   <host href="/ovirt-engine/api/hosts/456" id="456">
     *     ...
     *   </host>
     *   ...
     * </host>
     * ----
     *
     * @author Yaniv Bronhaim <ybronhei@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        @Out Host[] hosts();

        /**
         * Sets the maximum number of hosts to return. If not specified all the hosts are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned hosts.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    /**
     * A Reference to service managing a specific host.
     *
     * @author Yaniv Bronheim <ybronhei@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service HostService host(String id);
}
