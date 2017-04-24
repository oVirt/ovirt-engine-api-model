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
import types.Agent;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * A service to manage fence agents for a specific host.
 *
 * @author Piotr Kliczewski <pkliczew@redhat.com>
 * @date 24 Apr 2017
 * @status added
 *
 */
@Service
@Area("Infrastructure")
public interface FenceAgentsService {
    /**
     * Add a new fencing-agent to the host.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(agent().address());
            mandatory(agent().order());
            mandatory(agent().password());
            mandatory(agent().type());
            mandatory(agent().username());
            optional(agent().port());
            optional(agent().encryptOptions());
            optional(agent().name());
        }
        @In @Out Agent agent();
    }

    /**
     * Gets list of fence agents.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/hosts/123/fenceagents
     * ----
     *
     * And here is sample response:
     *
     * [source,xml]
     * ----
     * <agents>
     *   <agent id="0">
     *     <type>apc</type>
     *     <order>1</order>
     *     <ip>192.168.1.101</ip>
     *     <user>user</user>
     *     <password>xxx</password>
     *     <port>9</port>
     *     <options>name1=value1, name2=value2</options>
     *   </agent>
     * </agents>
     * ----
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface List {

        /**
         * List of fence agent details.
         *
         * @author Piotr Kliczewski <pkliczew@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @Out Agent[] agents();

        /**
         * Sets the maximum number of agents to return. If not specified all the agents are returned.
         */
        @In Integer max();
    }

    /**
     * Reference to service that manages a specific fence agent
     * for this host.
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Service FenceAgentService agent(String id);
}
