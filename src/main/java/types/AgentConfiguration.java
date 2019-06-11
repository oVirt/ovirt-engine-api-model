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
 * WARNING: Please note that this attribute has been deprecated since version 4.3.6 of the Engine,
 * and preserved only for backward compatibility. It will be removed in version 4.4.0.
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @date 11 Jun 2019
 * @status added
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
