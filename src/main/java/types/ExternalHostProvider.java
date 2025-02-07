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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents an external host provider,
 * such as Foreman or Satellite.
 *
 * See link:https://www.theforeman.org/[Foreman documentation] for more details.
 * See link:https://access.redhat.com/products/red-hat-satellite[Satellite documentation]
 * for more details on Red Hat Satellite.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public interface ExternalHostProvider extends ExternalProvider {

    /**
     * A reference to the certificates the engine supports
     * for this provider.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link Certificate[] certificates();

    /**
     * A reference to the compute resource as represented in the host provider.
     * Each host provider optionally has the engine defined as a compute resource,
     * which allows to create virtual machines in the engine. This compute resource
     * details are used in the Bare-Metal provisioning use-case, in order to
     * deploy the hypervisor.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link ExternalComputeResource[] computeResources();

    /**
     * A reference to the discovered hosts in the host provider.
     * Discovered hosts are hosts that were not provisioned yet.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link ExternalDiscoveredHost[] discoveredHosts();

    /**
     * A reference to the host groups in the host provider.
     * Host group contains different properties that the host
     * provider applies on all hosts that are member of this group.
     * Such as installed software, system definitions, passwords and more.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link ExternalHostGroup[] hostGroups();

    /**
     * A reference to the hosts provisioned by the host provider.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link Host[] hosts();
}
