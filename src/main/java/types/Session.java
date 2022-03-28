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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Describes a user session to a virtual machine.
 *
 * @author Jakub Niedermertl <jniederm@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 29 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface Session extends Identified {
    /**
     * The protocol used by the session.
     *
     * Currently not used. Intended for info about how the user is connected: through SPICE, VNC, SSH, or RDP.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 29 Nov 2016
     * @status updated_by_docs
     */
    String protocol();

    /**
     * The IP address the user is connected from.
     *
     * Currently only available for console users.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 29 Nov 2016
     * @status updated_by_docs
     */
    Ip ip();

    /**
     * Indicates if this is a console session.
     *
     * The value will be `true` for console users (SPICE or VNC), and `false` for others (such as RDP or SSH).
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 29 Nov 2016
     * @status updated_by_docs
     */
    Boolean consoleUser();

    /**
     * The user related to this session.
     *
     * If the user is a console user, this is a link to the real {product-name} user. Otherwise, only the user name is provided.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 29 Nov 2016
     * @status updated_by_docs
     */
    @Link User user();

    /**
     * A link to the virtual machine related to this session.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 29 Nov 2016
     * @status updated_by_docs
     */
    @Link Vm vm();
}
