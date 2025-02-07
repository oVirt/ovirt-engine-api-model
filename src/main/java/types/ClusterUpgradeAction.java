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
 * The action type for cluster upgrade action.
 *
 * @author Ravi Nori <rnori@redhat.com>
 * @date 13 March 2019
 * @since 4.3.2
 * @status added
 */
@Type
public enum ClusterUpgradeAction {
    /**
     * The upgrade action to be passed to start the cluster upgrade process by marking the cluster's
     * upgrade_running flag to true. This should used at the beginning of the cluster upgrade process.
     *
     * @author Ravi Nori <rnori@redhat.com>
     * @date 13 March 2019
     * @since 4.3.2
     * @status added
     */
    START,

    /**
     * The upgrade action to be passed to update the cluster upgrade progress. This should be used
     * as the upgrade progresses.
     *
     * @author Scott Dickerson <sdickers@redhat.com>
     * @date 16 Mar 2022
     * @since 4.5.0
     * @status added
     */
    UPDATE_PROGRESS,

    /**
     * The upgrade action to be passed to finish the cluster upgrade process by marking the cluster's
     * upgrade_running flag to false. This should be used at the end of the cluster upgrade process.
     *
     * @author Ravi Nori <rnori@redhat.com>
     * @date 13 March 2019
     * @since 4.3.2
     * @status added
     */
    FINISH;
}
