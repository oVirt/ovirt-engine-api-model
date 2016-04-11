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

package services;

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.AffinityLabel;

/**
 * Single affinity label details.
 */
@Service
public interface AffinityLabelService {
    /**
     * Retrieves details about a label.
     */
    interface Get {
        @Out AffinityLabel label();
    }

    /**
     * Updates a label.
     *
     * This call will update all metadata like name
     * or description.
     */
    interface Update {
        @In @Out AffinityLabel label();
    }

    /**
     * Removes a label from system and clears all assignments
     * of the removed label.
     */
    interface Remove {
    }

    /**
     * List all VMs with this label.
     */
    @Service AffinityLabelVmsService vms();

    /**
     * List all Hosts with this label.
     */
    @Service AffinityLabelHostsService hosts();
}
