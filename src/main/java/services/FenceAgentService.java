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
import types.Agent;

/**
 * A service to manage fence agent for a specific host.
 *
 * @author Piotr Kliczewski <pkliczew@redhat.com>
 * @date 24 Apr 2017
 * @status added
 *
 */
@Service
@Area("Infrastructure")
public interface FenceAgentService {

    /**
     * Gets details of this fence agent.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/hosts/123/fenceagents/0
     * ----
     *
     * And here is sample response:
     *
     * [source,xml]
     * ----
     * <agent id="0">
     *   <type>apc</type>
     *   <order>1</order>
     *   <ip>192.168.1.101</ip>
     *   <user>user</user>
     *   <password>xxx</password>
     *   <port>9</port>
     *   <options>name1=value1, name2=value2</options>
     * </agent>
     * ----
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @date 24 Apr 2017
     * @status added
     *
     */
    interface Get {

        /**
         * Fence agent details.
         *
         * @author Piotr Kliczewski <pkliczew@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @Out Agent agent();
    }

    interface Update {
        /**
         * Fence agent details.
         *
         * @author Piotr Kliczewski <pkliczew@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @In @Out Agent agent();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes a fence agent for a specific host.
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/hosts/123/fenceagents/0
     * ----
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
