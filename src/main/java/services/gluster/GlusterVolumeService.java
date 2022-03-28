/*
Copyright (c) 2015 Red Hat, Inc.

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

package services.gluster;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import services.MeasurableService;
import types.GlusterVolume;
import types.GlusterVolumeProfileDetails;
import types.Option;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * This service manages a single gluster volume.
 *
 * @author Ramesh Nachimuthu <rnachimu@redhat.com>
 * @date 12 Dec 2016
 * @status added
 *
 */
@Service
@Area("Gluster")
public interface GlusterVolumeService extends MeasurableService {
    /**
     * Get the gluster volume details.
     *
     * For example, to get details of a gluster volume with identifier `123` in cluster `456`, send a request like this:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/clusters/456/glustervolumes/123
     * ----
     *
     * This GET request will return the following output:
     *
     * [source,xml]
     * ----
     * <gluster_volume id="123">
     *  <name>data</name>
     *  <link href="/ovirt-engine/api/clusters/456/glustervolumes/123/glusterbricks" rel="glusterbricks"/>
     *  <disperse_count>0</disperse_count>
     *  <options>
     *    <option>
     *      <name>storage.owner-gid</name>
     *      <value>36</value>
     *    </option>
     *    <option>
     *      <name>performance.io-cache</name>
     *      <value>off</value>
     *    </option>
     *    <option>
     *      <name>cluster.data-self-heal-algorithm</name>
     *      <value>full</value>
     *    </option>
     *  </options>
     *  <redundancy_count>0</redundancy_count>
     *  <replica_count>3</replica_count>
     *  <status>up</status>
     *  <stripe_count>0</stripe_count>
     *  <transport_types>
     *    <transport_type>tcp</transport_type>
     *  </transport_types>
     *  <volume_type>replicate</volume_type>
     *  </gluster_volume>
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        /**
         * Representation of the gluster volume.
         *
         * @author Ramesh Nachimuthu <rnachimu@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out GlusterVolume volume();
    }

    /**
     * Get gluster volume profile statistics.
     *
     * For example, to get profile statistics for a gluster volume with identifier `123` in cluster `456`, send a
     * request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/456/glustervolumes/123/getprofilestatistics
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface GetProfileStatistics {
        /**
         * Gluster volume profiling information returned from the action.
         *
         * @author Ramesh Nachimuthu <rnachimu@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out GlusterVolumeProfileDetails details();
    }

    /**
     * Rebalance the gluster volume.
     *
     * Rebalancing a gluster volume helps to distribute the data evenly across all the bricks. After expanding or
     * shrinking a gluster volume (without migrating data), we need to rebalance the data among the bricks. In a
     * non-replicated volume, all bricks should be online to perform the rebalance operation. In a replicated volume, at
     * least one of the bricks in the replica should be online.
     *
     * For example, to rebalance a gluster volume with identifier `123` in cluster `456`, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/456/glustervolumes/123/rebalance
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Rebalance {
        @InputDetail
        default void inputDetail() {
            optional(fixLayout());
            optional(force());
        }
        /**
         * If set to true, rebalance will only fix the layout so that new data added to the volume is distributed
         * across all the hosts. But it will not migrate/rebalance the existing data. Default is `false`.
         *
         * @author Ramesh Nachimuthu <rnachimu@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In Boolean fixLayout();

        /**
         * Indicates if the rebalance should be force started. The rebalance command can be executed with the force
         * option even when the older clients are connected to the cluster. However, this could lead to a data loss
         * situation. Default is `false`.
         *
         * @author Ramesh Nachimuthu <rnachimu@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In Boolean force();

        /**
         * Indicates if the rebalance should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Removes the gluster volume.
     *
     * For example, to remove a volume with identifier `123` in cluster `456`, send a request like this:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/clusters/456/glustervolumes/123
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Resets all the options set in the gluster volume.
     *
     * For example, to reset all options in a gluster volume with identifier `123` in cluster `456`, send a request like
     * this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/456/glustervolumes/123/resetalloptions
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface ResetAllOptions {
        /**
         * Indicates if the reset should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Resets a particular option in the gluster volume.
     *
     * For example, to reset a particular option `option1` in a gluster volume with identifier `123` in cluster `456`,
     * send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/456/glustervolumes/123/resetoption
     * ----
     *
     * With the following request body:
     *
     * [source,xml]
     * ----
     * <action>
     *  <option name="option1"/>
     * </action>
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface ResetOption {
        @InputDetail
        default void inputDetail() {
            mandatory(force());
            mandatory(option().name());
        }
        @In Boolean force();

        /**
         * Option to reset.
         *
         * @author Ramesh Nachimuthu <rnachimu@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In Option option();

        /**
         * Indicates if the reset should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Sets a particular option in the gluster volume.
     *
     * For example, to set `option1` with value `value1` in a gluster volume with identifier `123` in cluster `456`,
     * send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/456/glustervolumes/123/setoption
     * ----
     *
     * With the following request body:
     *
     * [source,xml]
     * ----
     * <action>
     *  <option name="option1" value="value1"/>
     * </action>
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface SetOption {
        @InputDetail
        default void inputDetail() {
            mandatory(option().name());
            mandatory(option().value());
        }
        /**
         * Option to set.
         *
         * @author Ramesh Nachimuthu <rnachimu@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In Option option();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Starts the gluster volume.
     *
     * A Gluster Volume should be started to read/write data. For example, to start a gluster volume with identifier
     * `123` in cluster `456`, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/456/glustervolumes/123/start
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Start {
        @InputDetail
        default void inputDetail() {
            optional(force());
        }
        /**
         * Indicates if the volume should be force started. If a gluster volume is started already but few/all bricks
         * are down then force start can be used to bring all the bricks up. Default is `false`.
         *
         * @author Ramesh Nachimuthu <rnachimu@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In Boolean force();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Start profiling the gluster volume.
     *
     * For example, to start profiling a gluster volume with identifier `123` in cluster `456`, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/456/glustervolumes/123/startprofile
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface StartProfile {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Stops the gluster volume.
     *
     * Stopping a volume will make its data inaccessible.
     *
     * For example, to stop a gluster volume with identifier `123` in cluster `456`, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/456/glustervolumes/123/stop
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Stop {
        @InputDetail
        default void inputDetail() {
            optional(force());
        }
        @In Boolean force();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Stop profiling the gluster volume.
     *
     * For example, to stop profiling a gluster volume with identifier `123` in cluster `456`, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/456/glustervolumes/123/stopprofile
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface StopProfile {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Stop rebalancing the gluster volume.
     *
     * For example, to stop rebalancing a gluster volume with identifier `123` in cluster `456`, send a request like
     * this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/456/glustervolumes/123/stoprebalance
     * ----
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface StopRebalance {
        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Reference to a service managing gluster bricks.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service GlusterBricksService glusterBricks();
}
