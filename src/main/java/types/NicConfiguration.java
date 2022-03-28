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

package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * The type describes the configuration of a virtual network interface.
 *
 * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
 * @date 14 Feb 2017
 * @status added
 */
@Type
public interface NicConfiguration {
    /**
     * Network interface name.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 14 Feb 2017
     * @status added
     */
    String name();

    /**
     * IPv4 address details.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 14 Feb 2017
     * @status added
     */
    Ip ip();

    /**
     * IPv4 boot protocol.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 14 Feb 2017
     * @status added
     */
    BootProtocol bootProtocol();

    /**
     * IPv6 address details.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 14 Feb 2017
     * @status added
     */
    Ip ipv6();

    /**
     * IPv6 boot protocol.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 14 Feb 2017
     * @status added
     */
    BootProtocol ipv6BootProtocol();

    /**
     * Specifies whether the network interface should be activated on the virtual machine guest operating system boot.
     *
     * @author Yevgeny Zaspitsky <yzaspits@redhat.com>
     * @date 14 Feb 2017
     * @status added
     */
    Boolean onBoot();
}
