/*
Copyright (c) 2016 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * The OpenStack network provider can either be implemented by OpenStack Neutron, in which case the Neutron
 * agent is automatically installed on the hosts, or it can be an external provider implementing the
 * OpenStack API, in which case the virtual interface driver is a custom solution installed manually.
 *
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 16 Nov 2016
 * @status updated_by_docs
 */
@Type
public enum OpenStackNetworkProviderType {
    /**
     * Indicates that the provider is OpenStack Neutron.
     * The standard OpenStack Neutron agent is used as the virtual interface driver.
     *
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 16 Nov 2016
     * @status updated_by_docs
     */
    NEUTRON,

    /**
     * Indicates that the provider is an external one, implementing the OpenStack Neutron API.
     * The virtual interface driver in this case is implemented by the external provider.
     *
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 16 Nov 2016
     * @status updated_by_docs
     */
    EXTERNAL;
}
