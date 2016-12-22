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

package services.gluster;

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.GlusterBrick;

/**
 * This service manages the gluster bricks in a gluster volume
 *
 * @author Sahina Bose <sabose@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Gluster")
public interface GlusterBricksService {
    /**
     * Activate the bricks post data migration of remove brick operation.
     *
     * Used to activate brick(s) once the data migration from bricks is complete but user no longer wishes to remove
     * bricks. The bricks that were previously marked for removal will now be used as normal bricks.
     *
     * For example, to retain the bricks that on glustervolume `123` from which data was migrated, send a request like
     * this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/567/glustervolumes/123/glusterbricks/activate
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <action>
     *   <bricks>
     *     <brick>
     *       <name>host1:/rhgs/brick1</name>
     *     </brick>
     *   </bricks>
     * </action>
     * ----
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Activate {
        /**
         * The list of bricks that need to be re-activated.
         *
         * @author Sahina Bose <sabose@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In GlusterBrick[] bricks();

        /**
         * Indicates if the activation should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Adds a list of bricks to gluster volume.
     *
     * Used to expand a gluster volume by adding bricks. For replicated volume types, the parameter `replica_count`
     * needs to be passed. In case the replica count is being increased, then the number of bricks needs to be
     * equivalent to the number of replica sets.
     *
     * For example, to add bricks to gluster volume `123`, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/567/glustervolumes/123/glusterbricks
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <bricks>
     *   <brick>
     *     <server_id>111</server_id>
     *     <brick_dir>/export/data/brick3</brick_dir>
     *   </brick>
     * </bricks>
     * ----
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        /**
         * The list of bricks to be added to the volume
         *
         * @author Sahina Bose <sabose@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out GlusterBrick[] bricks();

        /**
         * Replica count of volume post add operation.
         *
         * @author Sahina Bose <sabose@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In Integer replicaCount();

        /**
         * Stripe count of volume post add operation.
         *
         * @author Sahina Bose <sabose@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In Integer stripeCount();
    }

    /**
     * Lists the bricks of a gluster volume.
     *
     * For example, to list bricks of gluster volume `123`, send a request like this:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/clusters/567/glustervolumes/123/glusterbricks
     * ----
     *
     * Provides an output as below:
     *
     * [source,xml]
     * ----
     * <bricks>
     *   <brick id="234">
     *     <name>host1:/rhgs/data/brick1</name>
     *     <brick_dir>/rhgs/data/brick1</brick_dir>
     *     <server_id>111</server_id>
     *     <status>up</status>
     *   </brick>
     *   <brick id="233">
     *     <name>host2:/rhgs/data/brick1</name>
     *     <brick_dir>/rhgs/data/brick1</brick_dir>
     *     <server_id>222</server_id>
     *     <status>up</status>
     *   </brick>
     * </bricks>
     * ----
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        @Out GlusterBrick[] bricks();

        /**
         * Sets the maximum number of bricks to return. If not specified all the bricks are returned.
         */
        @In Integer max();
    }

    /**
     * Start migration of data prior to removing bricks.
     *
     * Removing bricks is a two-step process, where the data on bricks to be removed, is first migrated to remaining
     * bricks. Once migration is completed the removal of bricks is confirmed via the API
     * <<services/gluster_bricks/methods/remove, remove>>. If at any point, the action needs to be cancelled
     * <<services/gluster_bricks/methods/stop_migrate, stopmigrate>> has to be called.
     *
     * For instance, to delete a brick from a gluster volume with id `123`, send a request:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/567/glustervolumes/123/glusterbricks/migrate
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <action>
     *   <bricks>
     *     <brick>
     *       <name>host1:/rhgs/brick1</name>
     *     </brick>
     *   </bricks>
     * </action>
     * ----
     *
     * The migration process can be tracked from the job id returned from the API using
     * <<services/job/methods/get, job>> and steps in job using <<services/step/methods/get, step>>
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Migrate {
        /**
         * List of bricks for which data migration needs to be started.
         *
         * @author Sahina Bose <sabose@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In GlusterBrick[] bricks();

        /**
         * Indicates if the migration should be performed asynchronously.
         */
        @In Boolean async();

    }

    /**
     * Removes bricks from gluster volume.
     *
     * The recommended way to remove bricks without data loss is to first migrate the data using
     * <<services/gluster_bricks/methods/stop_migrate, stopmigrate>> and then removing them. If migrate was not called on
     * bricks prior to remove, the bricks are removed without data migration which may lead to data loss.
     *
     * For example, to delete the bricks from gluster volume `123`, send a request like this:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/clusters/567/glustervolumes/123/glusterbricks
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <bricks>
     *   <brick>
     *     <name>host:brick_directory</name>
     *   </brick>
     * </bricks>
     * ----
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Remove {
        /**
         * The list of bricks to be removed
         */
        @In GlusterBrick[] bricks();

        /**
         * Replica count of volume post add operation.
         *
         * @author Sahina Bose <sabose@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In Integer replicaCount();

        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Stops migration of data from bricks for a remove brick operation.
     *
     * To cancel data migration that was started as part of the 2-step remove brick process in case the user wishes to
     * continue using the bricks. The bricks that were marked for removal will function as normal bricks post this
     * operation.
     *
     * For example, to stop migration of data from the bricks of gluster volume `123`, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/567/glustervolumes/123/glusterbricks/stopmigrate
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <bricks>
     *   <brick>
     *     <name>host:brick_directory</name>
     *   </brick>
     * </bricks>
     * ----
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface StopMigrate {
        /**
         * List of bricks for which data migration needs to be stopped. This list should match the arguments passed to
         * <<services/gluster_bricks/methods/migrate, migrate>>.
         *
         * @author Sahina Bose <sabose@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In GlusterBrick[] bricks();

        /**
         * Indicates if the action should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * Returns a reference to the service managing a single gluster brick.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service GlusterBrick brick(String id);
}
