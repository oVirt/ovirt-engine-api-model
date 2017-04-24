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
 * Represents a host provisioned by a host
 * provider (such as Foreman/Satellite).
 *
 * See https://www.theforeman.org/ for more details on Foreman.
 * See https://access.redhat.com/products/red-hat-satellite
 * for more details on Red Hat Satellite.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public interface ExternalHost extends Identified {
    /**
     * The address of the host, either IP address
     * of FQDN (Fully Qualified Domain Name).
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String address();

    /**
     * A reference to the external host provider
     * that the host is managed by.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link ExternalHostProvider externalHostProvider();
}
