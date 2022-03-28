/*
Copyright (c) 2017 Red Hat, Inc.

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

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.ExternalNetworkProviderConfiguration;

/**
 * Describes how an external network provider is provisioned by the system on the host.
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 09 Oct 2017
 * @status updated_by_docs
 * @since 4.2
 */
@Service
@Area("Network")
public interface ExternalNetworkProviderConfigurationService {
    /**
     * Returns the information about an external network provider on the host.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     * @since 4.2
     */
    interface Get extends Follow {
        @Out
        ExternalNetworkProviderConfiguration configuration();
    }
}
