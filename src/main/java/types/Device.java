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
 * A device wraps links to potential parents of a device.
 *
 * @author Marek Libra <mlibra@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface Device extends Identified {
    /**
     * Optionally references to a template the device is used by.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Template template();


    /**
     * Optionally references to an instance type the device is used by.
     *
     * @author Marek Libra <mlibra@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link InstanceType instanceType();

    /**
     * References to the virtual machines that are using this device. A device may be used by several virtual machines;
     * for example, a shared disk my be used simultaneously by two or more virtual machines.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    @Link Vm[] vms();

    /**
     * Do not use this element, use `vms` instead.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    @Deprecated
    @Link Vm vm();
}
