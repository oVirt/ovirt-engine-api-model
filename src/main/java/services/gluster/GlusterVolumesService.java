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

package services.gluster;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.GlusterVolume;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * This service manages a collection of gluster volumes available in a cluster.
 *
 * @author Ramesh Nachimuthu <rnachimu@redhat.com>
 * @date 12 Dec 2016
 * @status added
 *
 */
@Service
@Area("Gluster")
public interface GlusterVolumesService {
    /**
     * Creates a new gluster volume.
     *
     * The volume is created based on properties of the `volume` parameter. The properties `name`, `volume_type` and
     * `bricks` are required.
     *
     * For example, to add a volume with name `myvolume` to the cluster `123`, send the following request:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/123/glustervolumes
     * ----
     *
     * With the following request body:
     *
     * [source,xml]
     * ----
     * <gluster_volume>
     *   <name>myvolume</name>
     *   <volume_type>replicate</volume_type>
     *   <replica_count>3</replica_count>
     *   <bricks>
     *     <brick>
     *       <server_id>server1</server_id>
     *       <brick_dir>/exp1</brick_dir>
     *     </brick>
     *     <brick>
     *       <server_id>server2</server_id>
     *       <brick_dir>/exp1</brick_dir>
     *     </brick>
     *     <brick>
     *       <server_id>server3</server_id>
     *       <brick_dir>/exp1</brick_dir>
     *     </brick>
     *   <bricks>
     * </gluster_volume>
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(volume().name());
            mandatory(volume().volumeType());
            mandatory(volume().bricks()[COLLECTION].brickDir());
            mandatory(volume().bricks()[COLLECTION].serverId());
            optional(volume().replicaCount());
            optional(volume().stripeCount());
            optional(volume().options()[COLLECTION].name());
            optional(volume().options()[COLLECTION].value());
            optional(volume().transportTypes()[COLLECTION]);
        }
        /**
         * The gluster volume definition from which to create the volume is passed as input and the newly created
         * volume is returned.
         */
        @In @Out GlusterVolume volume();
    }

    /**
     * Lists all gluster volumes in the cluster.
     *
     * For example, to list all Gluster Volumes in cluster `456`, send a request like
     * this:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/clusters/456/glustervolumes
     * ----
     *
     * The order of the returned list of volumes isn't guaranteed.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        @Out GlusterVolume[] volumes();

        /**
         * Sets the maximum number of volumes to return. If not specified all the volumes are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned volumes.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();
    }

    /**
     * Reference to a service managing gluster volume.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service GlusterVolumeService volume(String id);
}
