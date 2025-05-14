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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * This type represents the kind of resource the xref:types/qos[Quality of service (QoS)] can be assigned to.
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public enum QosType {
    /**
     * The xref:types/qos[Quality of service (QoS)] can be assigned to storage.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    STORAGE,

    /**
     * The xref:types/qos[Quality of service (QoS)] can be assigned to resources with computing capabilities.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    CPU,

    /**
     * The xref:types/qos[Quality of service (QoS)] can be assigned to virtual machines networks.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    NETWORK,

    /**
     * The xref:types/qos[Quality of service (QoS)] can be assigned to host networks.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    HOSTNETWORK;
}
