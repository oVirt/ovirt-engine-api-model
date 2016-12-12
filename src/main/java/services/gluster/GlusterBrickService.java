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
import services.MeasurableService;
import types.GlusterBrick;

/**
 * This service manages a single gluster brick.
 *
 * @author Sahina Bose <sabose@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Gluster")
public interface GlusterBrickService extends MeasurableService {
    /**
     * Get details of a brick.
     *
     * Retrieves status details of brick from underlying gluster volume with header `All-Content` set to `true`. This is
     * the equivalent of running `gluster volume status <volumename> <brickname> detail`.
     *
     * For example, to get the details of brick `234` of gluster volume `123`, send a request like this:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/clusters/567/glustervolumes/123/glusterbricks/234
     * ----
     *
     * Which will return a response body like this:
     *
     * [source,xml]
     * ----
     * <brick id="234">
     *   <name>host1:/rhgs/data/brick1</name>
     *   <brick_dir>/rhgs/data/brick1</brick_dir>
     *   <server_id>111</server_id>
     *   <status>up</status>
     *   <device>/dev/mapper/RHGS_vg1-lv_vmaddldisks</device>
     *   <fs_name>xfs</fs_name>
     *   <gluster_clients>
     *     <gluster_client>
     *       <bytes_read>2818417648</bytes_read>
     *       <bytes_written>1384694844</bytes_written>
     *       <client_port>1011</client_port>
     *       <host_name>client2</host_name>
     *     </gluster_client>
     *   </gluster_clients>
     *   <memory_pools>
     *     <memory_pool>
     *       <name>data-server:fd_t</name>
     *       <alloc_count>1626348</alloc_count>
     *       <cold_count>1020</cold_count>
     *       <hot_count>4</hot_count>
     *       <max_alloc>23</max_alloc>
     *       <max_stdalloc>0</max_stdalloc>
     *       <padded_size>140</padded_size>
     *       <pool_misses>0</pool_misses>
     *     </memory_pool>
     *   </memory_pools>
     *   <mnt_options>rw,seclabel,noatime,nodiratime,attr2,inode64,sunit=512,swidth=2048,noquota</mnt_options>
     *   <pid>25589</pid>
     *   <port>49155</port>
     * </brick>
     * ----
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get {
        @Out GlusterBrick brick();
    }

    /**
     * Removes a brick.
     *
     * Removes a brick from the underlying gluster volume and deletes entries from database. This can be used only when
     * removing a single brick without data migration. To remove multiple bricks and with data migration, use
     * <<services/gluster_bricks/methods/migrate, migrate>> instead.
     *
     * For example, to delete brick `234` from gluster volume `123`, send a request like this:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/clusters/567/glustervolumes/123/glusterbricks/234
     * ----
     *
     * @author Sahina Bose <sabose@redhat.com>
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
     * Replaces this brick with a new one.
     *
     * IMPORTANT: This operation has been deprecated since version 3.5 of the engine and will be removed in the future.
     * Use <<services/gluster_bricks/methods/add, add brick(s)>> and
     * <<services/gluster_bricks/methods/migrate, migrate brick(s)>> instead.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Deprecated
    interface Replace {
        @In Boolean force();

        /**
         * Indicates if the replacement should be performed asynchronously.
         */
        @In Boolean async();
    }
}
