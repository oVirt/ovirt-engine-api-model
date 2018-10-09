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
 * Deprecated Agent configuration settings.
 *
 * Ignored, because the deployment of OpenStack Neutron agent is dropped since {product-name} 4.4.0.
 * The deployment of OpenStack hosts can be done by Red Hat OpenStack Platform Director or TripleO.
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 11 Jun 2019
 * @status updated
 */
@Deprecated
@Type
public interface AgentConfiguration {
    String networkMappings();
    MessageBrokerType brokerType();
    String address();
    Integer port();
    String username();
    String password();
}
