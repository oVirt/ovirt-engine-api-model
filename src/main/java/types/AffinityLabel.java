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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Affinity label is a label that can influence the VM scheduling.
 * It is most often used to create a sub-cluster from available hosts.
 */
@Type
public interface AffinityLabel extends Identified {
    /**
     * List of hosts that were labeled using this scheduling label.
     */
    @Link Host[] hosts();

    /**
     * List of vms that were labeled using this scheduling label.
     */
    @Link Vm[] vms();

    /**
     * The `readOnly` property marks a label that can't be modified.
     * This is mostly the case when listing internally generated labels.
     */
    Boolean readOnly();
}
