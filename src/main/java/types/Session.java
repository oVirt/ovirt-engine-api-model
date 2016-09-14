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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Describes user session to a virtual machine.
 *
 * @author Jakub Niedermertl <jniederm@redhat.com>
 * @date 14 Sep 2016
 * @status added
 */
@Type
public interface Session extends Identified {
    /**
     * Protocol used by the session.
     *
     * Currently not used, intended for info about how is user connected: SPICE, VNC, SSH, RDP.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    String protocol();

    /**
     * IP address user is connected from.
     *
     * Currently only available for console users.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    Ip ip();

    /**
     * Indicates if this is a console session.
     *
     * The value will be `true` for console users: SPICE or VNC, `false` for others: e.g. RDP, SSH.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    Boolean consoleUser();

    /**
     * User related to this session.
     *
     * If user is a console user, it is a link to real oVirt user. Otherwise only username is provided.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    @Link User user();

    /**
     * Link to virtual machine related to this session.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    @Link Vm vm();
}
