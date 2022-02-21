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

package types;

import org.ovirt.api.metamodel.annotations.Type;

@Type
public interface Initialization {
    Configuration configuration();

    /**
     * Deprecated attribute to specify _cloud-init_ configuration.
     *
     * This attribute, and the <<types/cloud_init, CloudInit>> type have been deprecated and will be
     * removed in the future.  To specify the _cloud-init_ configuration, use the attributes inside
     * the <<types/initialization, Initialization>> type. The mapping between the attributes
     * of these two types are as follows:
     *
     * |===
     * |CloudInit |Initialization
     *
     * |`authorized_keys` |`authorized_ssh_keys`
     * |`dns.search_domains` |`dns_search`
     * |`dns.servers` |`dns_servers`
     * |`files` |`custom_script`
     * |`host` |`host_name`
     * |`network_configuration.nics` |`nic_configurations`
     * |`regenerate_ssh_keys` |`regenerate_ssh_keys`
     * |`timezone` |`timezone`
     * |`users` |`user_name` & `root_password`
     *
     * |===
     *
     * For more details on how to use _cloud-init_ see the examples in
     * https://github.com/oVirt/ovirt-engine-sdk/blob/master/sdk/examples/start_vm_with_cloud_init.py[Python] and
     * https://github.com/oVirt/ovirt-engine-sdk-java/blob/master/sdk/src/test/java/org/ovirt/engine/sdk4/examples/StartVmWithCloudInit.java[Java].
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @author Donna DaCosta <ddacosta@redhat.com>
     * @date 16 Feb 2022
     * @status updated_by_docs
     */
    @Deprecated
    CloudInit cloudInit();

    String hostName();
    String domain();
    String timezone();
    String authorizedSshKeys();
    Boolean regenerateSshKeys();
    Boolean regenerateIds();
    String dnsServers();
    String dnsSearch();
    NicConfiguration[] nicConfigurations();
    String windowsLicenseKey();
    String rootPassword();
    String customScript();
    String inputLocale();
    String uiLanguage();
    String systemLocale();
    String userLocale();
    String userName();
    String activeDirectoryOu();
    String orgName();

    /**
     * Attribute specifying the cloud-init protocol to
     * use for formatting the cloud-init network parameters.
     * If omitted, a default value is used, as described in
     * the <<types/cloud_init_network_protocol, CloudInitNetworkProtocol>>
     *
     *
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Steve Goodman <sgoodman@redhat.com>
     * @date 03 Dec 2018
     * @since 4.2.8
     * @status updated_by_docs
     */
    CloudInitNetworkProtocol cloudInitNetworkProtocol();
}
