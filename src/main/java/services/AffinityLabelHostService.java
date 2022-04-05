/*
Copyright (c) 2016 Red Hat, Inc.

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

import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.Host;

/**
 * This service represents a host that has a specific
 * label when accessed through the affinitylabels/hosts
 * subcollection.
 */
@Service
public interface AffinityLabelHostService {
    /**
     * Remove a label from a host.
     */
    interface Remove {
    }

    /**
     * Retrieves details about a host that has this label assigned.
     */
    interface Get extends Follow {
        @Out Host host();
    }
}
