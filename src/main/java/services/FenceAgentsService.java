/*
The oVirt Project - oVirt Engine Api Model

Copyright oVirt Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the Apache License, Version 2.0 is included with the program
in the file ASL2.
*/

package services;

import annotations.Area;
import mixins.Follow;
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
 */
@Service
@Area("Infrastructure")
public interface FenceAgentsService {
    /**
     * Add a new fencing-agent to the host.
     *
     * [source]
     *
     * ----
     * POST /ovirt-engine/api/hosts/123/fenceagents
     *
     * You should consult the /usr/sbin/fence_<agent_name> manual page for
     * the legal parameters to [name1=value1, name2=value2,...] in the options field.
     * If any parameter in options appears by name that means that it is mandatory.
     * For example in <options>slot=7[,name1=value1, name2=value2,...]</options>
     * slot is mandatory.
     * ----
     *
     * apc, bladecenter, wti fencing agent/s sample request:
     *
     * [source,xml]
     *
     *   <agent>
     *     <type>apc</type>
     *     <order>1</order>
     *     <ip>192.168.1.101</ip>
     *     <user>user</user>
     *     <password>xxx</password>
     *     <port>9</port>
     *     <options>slot=7[,name1=value1, name2=value2,...]</options>
     *   </agent>
     *
     * apc_snmp, hpblade, ilo, ilo2, ilo_ssh, redfish, rsa fencing agent/s sample request:
     *
     * [source,xml]
     *
     *   <agent>
     *     <type>apc_snmp</type>
     *     <order>1</order>
     *     <ip>192.168.1.101</ip>
     *     <user>user</user>
     *     <password>xxx</password>
     *     <port>9</port>
     *     <options>[name1=value1, name2=value2,...]</options>
     *   </agent>
     *
     *
     * cisco_ucs, drac5, eps fencing agent/s sample request:
     *
     * [source,xml]
     *
     *   <agent>
     *     <type>cisco_ucs</type>
     *     <order>1</order>
     *     <ip>192.168.1.101</ip>
     *     <user>user</user>
     *     <password>xxx</password>
     *     <options>slot=7[,name1=value1, name2=value2,...]</options>
     *   </agent>
     *
     * drac7, ilo3, ilo4, ipmilan, rsb fencing agent/s sample request:
     *
     * [source,xml]
     *
     *   <agent>
     *     <type>drac7</type>
     *     <order>1</order>
     *     <ip>192.168.1.101</ip>
     *     <user>user</user>
     *     <password>xxx</password>
     *     <options>[name1=value1, name2=value2,...]</options>
     *   </agent>
     * @author Ori Liel <oliel@redhat.com>
     * @author Eli Mesika <emesika@redhat.com>
     * @date 26 Nov 2019
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
     * Returns the list of fencing agents configured for the host.
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
     * The order of the returned list of fencing agents isn't guaranteed.
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface List extends Follow {

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
