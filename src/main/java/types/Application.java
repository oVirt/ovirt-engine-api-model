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
 * Represents an application installed on a virtual machine. Applications are reported by the guest agent, if you
 * deploy one on the virtual machine operating system.
 *
 * To get that information send a request like this:
 *
 * ....
 * GET /ovirt-engine/api/vms/123/applications/456
 * ....
 *
 * The result will be like this:
 *
 * [source,xml]
 * ----
 * <application href="/ovirt-engine/api/vms/123/applications/456" id="456">
 *   <name>application-test-1.0.0-0.el7</name>
 *   <vm href="/ovirt-engine/api/vms/123" id="123"/>
 * </application>
 * ----
 *
 * @author Lukas Svaty <lsvaty@redhat.com>
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public interface Application extends Identified {
    /**
     * A reference to the virtual machine the application is installed on.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Link Vm vm();
}
