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
 * Network plug-in type.
 *
 * Specifies the provider driver implementation on the host.
 *
 * Since version 4.2 of the {engine-name}, this type has been deprecated in favour of the `external_plugin_type`
 * attribute of the `OpenStackNetworkProvider` type.
 *
 * @author Marcin Mirecki <mmirecki@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 6 Nov 2017
 * @status updated_by_docs
 */
@Type
@Deprecated
public enum NetworkPluginType {
    /**
     * Open vSwitch.
     *
     * Specifies that http://openvswitch.org[Open vSwitch] based driver implementation should be used for this provider.
     *
     * Since version 4.2 of the {engine-name}, this value has been deprecated. Use the string `open_vswitch` in the
     * `OpenStackNetworkProvider.external_plugin_type` attribute instead.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @date 3 Nov 2017
     * @status added
     */
    @Deprecated
    OPEN_VSWITCH;
}
