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

/**
 * The enum defines the options of the IP address assignment method to a NIC.
 */
@Type
public enum BootProtocol {
    /**
     * Stateless Address Auto-Configuration.
     * The mechanism is defined by http://tools.ietf.org/html/rfc4862[RFC 4862].
     * Please refer to https://en.wikipedia.org/wiki/IPv6_address#Stateless_address_autoconfiguration[this wikipedia article].
     *
     * NOTE: The value is valid for IPv6 addresses only.
     */
    AUTOCONF,

    /**
     * Dynamic Host Configuration Protocol.
     * Please refer to https://en.wikipedia.org/wiki/Dynamic_Host_Configuration_Protocol[this wikipedia article].
     */
    DHCP,

    /**
     * Statically (by user) defined address, mask and gateway.
     */
    STATIC,

    /**
     * No address configuration.
     */
    NONE;
}
