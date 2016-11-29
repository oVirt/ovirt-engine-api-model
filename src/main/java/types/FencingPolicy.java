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

/**
 * Type representing a cluster fencing policy.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 29 Nov 2016
 * @status added
 */
@Type
public interface FencingPolicy {
    /**
     * Enable or disable fencing on this cluster.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    Boolean enabled();

    /**
     * If enabled, we will skip fencing in case the host
     * maintains its lease in the storage. It means that
     * if the host still has storage access then it won't
     * get fenced.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    SkipIfSdActive skipIfSdActive();

    /**
     * If enabled, we will not fence a host
     * in case more than a configurable percentage
     * of hosts in the cluster lost connectivity as well.
     * This comes to prevent fencing _storm_ in cases
     * where there is a global networking issue in the
     * cluster.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
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
