/*
Copyright (c) 2022 Red Hat, Inc.

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
 * VM mediated device is a fake device specifying properties of vGPU mediated devices.
 * It is not an actual device, it just serves as a specification how to configure a part
 * of a host device.
 *
 * @author Milan Zamazal <mzamazal@redhat.com>
 * @date 10 Mar 2022
 * @status added
 * @since 4.5
 */
@Type
public interface VmMediatedDevice extends Device {
    /**
     * Properties of the device.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 10 Mar 2022
     * @status added
     * @since 4.5
     */
    Property[] specParams();
}
