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
import types.Host;
import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

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
     * The host is created based on the attributes of the `host` parameter. The `name`, `address`, and `root_password`
     * properties are required.
     *
     * For example, to add a host, send the following request:
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
     * IMPORTANT: Since version 4.1.2 of the engine, when a host is newly added, the host's firewall
     * definitions are overridden by default.
     *
     * To add a hosted engine host, use the optional `deploy_hosted_engine` parameter:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/hosts?deploy_hosted_engine=true
     * ----
     *
     * If the cluster has a default external network provider that is supported for automatic deployment,
     * the external network provider is deployed when adding the host.
     * Only external network providers for OVN are supported for the automatic deployment.
     * To deploy an external network provider other than the one defined in the clusters, overwrite the external
     * network provider when adding hosts, by sending the following request:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/hosts
     * ----
     *
     * With a request body that contains a reference to the desired provider in the
     * `external_network_provider_configuration`:
     *
     * [source,xml]
     * ----
     * <host>
     *   <name>myhost</name>
     *   <address>myhost.example.com</address>
     *   <root_password>123456</root_password>
     *   <external_network_provider_configurations>
     *     <external_network_provider_configuration>
     *       <external_network_provider name="ovirt-provider-ovn"/>
     *     </external_network_provider_configuration>
     *   </external_network_provider_configurations>
     * </host>
     * ----
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Roy Golan <rgolan@redhat.com>
     * @author Dominik Holler <dholler@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 06 May 2018
     * @status updated_by_docs
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(host().address());
            mandatory(host().name());
            or(mandatory(host().cluster().id()), mandatory(host().cluster().name()));
            optional(host().comment());
            optional(host().display().address());
            optional(host().overrideIptables());
            optional(host().port());
            optional(host().powerManagement().automaticPmEnabled());
            optional(host().powerManagement().enabled());
            optional(host().powerManagement().kdumpDetection());
            optional(host().protocol());
            optional(host().spm().priority());
            optional(host().vgpuPlacement());
            optional(host().powerManagement().pmProxies()[COLLECTION].type());
            or(optional(host().externalNetworkProviderConfigurations()[COLLECTION].externalNetworkProvider().id()),
                optional(host().externalNetworkProviderConfigurations()[COLLECTION].externalNetworkProvider().name()));
        }

        /**
         * The host definition with which the new host is created is passed as a parameter, and the newly created host
         * is returned.
         */
        @In @Out Host host();

        /**
         * When set to `true`, this host deploys the hosted engine components. A missing value is treated
         * as `true`, i.e., deploy the hosted engine components. Omitting this parameter equals `false`, and
         * the host performs no operation in the hosted engine area.
         */
        @In Boolean deployHostedEngine();

        /**
         * When set to `true`, this host un-deploys the hosted engine components and does not
         * function as part of the High Availability cluster. A missing value is treated as `true`, i.e., un-deploy.
         * Omitting this parameter equals `false` and the host performs no operation in the hosted engine area.
         */
        @In Boolean undeployHostedEngine();

        /**
         * When set to `true`, this host will be activated after its installation completes. When set to `false`
         * the host will remain in `maintenance` status after its installation. Absence of this parameter will be
         * interpreted as `true`, since the desired default behavior is activating the host after install.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 10 Dec 2018
         * @since 4.3
         * @status added
         */
        @In Boolean activate();

        /**
         * Indicates if the host should be rebooted after successful installation. The default value is `true`.
         *
         * @author Dana Elfassy <delfassy@redhat.com>
         * @date 3 Feb 2021
         */
        @In Boolean reboot();

        /**
         * Add a new host to the system providing the host root password. This has been deprecated and provided for backwards compatibility.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @author Avital Pinnick <apinnick@redhat.com>
         * @date 06 May 2018
         * @status updated_by_docs
         */
        interface UsingRootPassword extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(host().rootPassword()); //DEPRECATED
            }
        }

        /**
         * Add a new host to the system providing the ssh password, fingerprint or public key.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 18 Jan 2017
         * @status added
         */
        interface UsingSsh extends Add {
            @InputDetail
            default void inputDetail() {
                optional(host().ssh().authenticationMethod());
                optional(host().ssh().fingerprint());
                optional(host().ssh().publicKey());
                optional(host().ssh().port());
                optional(host().ssh().user().password());
                optional(host().ssh().user().userName());
            }
        }
    }

    /**
     * Get a list of all available hosts.
     *
     * For example, to list the hosts send the following request:
     *
     * ....
     * GET /ovirt-engine/api/hosts
     * ....
     *
     * The response body will be similar to this:
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
     * The order of the returned list of hosts is guaranteed only if the `sortby` clause is included in
     * the `search` parameter.
     *
     * @author Yaniv Bronhaim <ybronhei@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 06 May 2018
     * @status updated_by_docs
     */
    interface List extends Follow {
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

        /**
         * Indicates if all of the attributes of the hosts should be included in the response.
         *
         * By default the following host attributes are excluded:
         *
         * - `hosted_engine`
         *
         * For example, to retrieve the complete representation of the hosts:
         *
         * ....
         * GET /ovirt-engine/api/hosts?all_content=true
         * ....
         *
         * NOTE: These attributes are not included by default because retrieving them impacts performance. They are
         * seldom used and require additional queries to the database. Use this parameter with caution and only when
         * specifically required.
         *
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 26 Apr 2017
         * @status updated_by_docs
         */
        @In Boolean allContent();

        /**
         * Accepts a comma-separated list of virtual machine IDs and returns the hosts
         * that these virtual machines can be migrated to.
         *
         * For example, to retrieve the list of hosts to which the virtual machine with ID 123 and
         * the virtual machine with ID 456 can be migrated to, send the following request:
         *
         * ....
         * GET /ovirt-engine/api/hosts?migration_target_of=123,456
         * ....
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @author Avital Pinnick <apinnick@redhat.com>
         * @date 06 May 2018
         * @status updated_by_docs
         * @since 4.3
         */
        @In String migrationTargetOf();

        /**
         * This parameter can be used with `migration_target_of`
         * to get valid migration targets for the listed virtual machines
         * and all other virtual machines that are in positive enforcing
         * affinity with the listed virtual machines.
         *
         * This is useful in case the virtual machines will be migrated
         * together with others in positive affinity groups.
         *
         * The default value is `false`.
         *
         * ....
         * GET /ovirt-engine/api/hosts?migration_target_of=123,456&check_vms_in_affinity_closure=true
         * ....
         *
         * @author Andrej Krejcir <akrejcir@redhat.com>
         * @date 15 Apr 2019
         * @status added
         * @since 4.4
         */
        @In Boolean checkVmsInAffinityClosure();
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
