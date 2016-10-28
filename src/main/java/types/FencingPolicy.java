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

import org.ovirt.api.metamodel.annotations.Type;

@Type
public interface FencingPolicy {
    Boolean enabled();
    SkipIfSdActive skipIfSdActive();
    SkipIfConnectivityBroken skipIfConnectivityBroken();

    /**
     * A flag indicating if fencing should be skipped if Gluster bricks are up and running in the host being fenced.
     * This flag is optional, and the default value is `false`.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 28 Oct 2016
     * @status added
     * @since 4.1
     */
    Boolean skipIfGlusterBricksUp();

    /**
     * A flag indicating if fencing should be skipped if Gluster bricks are up and running and Gluster quorum will not
     * be met without those bricks.
     * This flag is optional, and the default value is `false`.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 28 Oct 2016
     * @status added
     * @since 4.1
     */
    Boolean skipIfGlusterQuorumNotMet();
}
