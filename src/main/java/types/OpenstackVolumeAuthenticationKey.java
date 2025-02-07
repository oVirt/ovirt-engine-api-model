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

import java.util.Date;

/**
 * Openstack Volume (Cinder) integration has been replaced by Managed Block Storage.
 *
 * @author Shani Leviim <sleviim@redhat.com>
 * @date 14 Oct 2021
 * @status added
 */

@Deprecated
@Type
public interface OpenstackVolumeAuthenticationKey extends Identified {
    String uuid();
    String value();
    OpenstackVolumeAuthenticationKeyUsageType usageType();
    Date creationDate();

    @Link OpenStackVolumeProvider openstackVolumeProvider();
}
