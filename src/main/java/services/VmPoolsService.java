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

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.VmPool;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * Provides read-write access to virtual machines pools.
 *
 * @author Aleksei Slaikovskii <aslaikov@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface VmPoolsService {
    /**
     * Creates a new virtual machine pool.
     *
     * A new pool requires the `name`, `cluster` and `template` attributes. Identify the cluster and template with the
     * `id` or `name` nested attributes:
     *
     * ```http
     * POST /ovirt-engine/api/vmpools
     * ```
     *
     * With the following body:
     *
     * ```xml
     * <vmpool>
     *   <name>mypool</name>
     *   <cluster id="123"/>
     *   <template id="456"/>
     * </vmpool>
     * ```
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(pool().name());
            or(mandatory(pool().cluster().id()), mandatory(pool().cluster().name()));
            or(mandatory(pool().template().id()), mandatory(pool().template().name()));
            optional(pool().comment());
            optional(pool().description());
            optional(pool().display().proxy());
            optional(pool().maxUserVms());
            optional(pool().prestartedVms());
            optional(pool().size());
            optional(pool().soundcardEnabled());
            optional(pool().type());
            optional(pool().useLatestTemplateVersion());
            optional(pool().tpmEnabled());
        }
        /**
         * Pool to add.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out VmPool pool();

        /**
         * Specifies if virtual machines created for the pool should be sealed after creation.
         *
         * If this optional parameter is provided, and its value is `true`, virtual machines created for the pool
         * will be sealed after creation. If the value is 'false', the virtual machines will not be sealed.
         * If the parameter is not provided, the virtual machines will be sealed, only if they are created from
         * a sealed template and their guest OS is not set to Windows. This parameter affects only the virtual machines
         * created when the pool is created.
         *
         * For example, to create a virtual machine pool with 5 virtual machines and to seal them, send a request
         * like this:
         *
         * ```http
         * POST /ovirt-engine/api/vmpools?seal=true
         * ```
         *
         * With the following body:
         *
         * ```xml
         * <vmpool>
         *   <name>mypool</name>
         *   <cluster id="123"/>
         *   <template id="456"/>
         *   <size>5</size>
         * </vmpool>
         * ```
         *
         * @author Shmuel Leib Melamud <smelamud@redhat.com>
         * @date 1 Mar 2022
         * @status added
         * @since 4.5
         */
        @In Boolean seal();
    }

    /**
     * Get a list of available virtual machines pools.
     *
     * ```http
     * GET /ovirt-engine/api/vmpools
     * ```
     *
     * You will receive the following response:
     *
     * ```xml
     * <vm_pools>
     *   <vm_pool id="123">
     *     ...
     *   </vm_pool>
     *   ...
     * </vm_pools>
     * ```
     *
     * The order of the returned list of pools is guaranteed only if the `sortby` clause is included in the
     * `search` parameter.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        /**
         * Retrieved pools.
         */
        @Out VmPool[] pools();

        /**
         * Sets the maximum number of pools to return. If this value is not specified, all of the pools are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned pools.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    /**
     * Reference to the service that manages a specific virtual machine pool.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service VmPoolService pool(String id);
}
