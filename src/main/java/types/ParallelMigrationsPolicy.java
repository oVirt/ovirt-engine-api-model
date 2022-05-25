/*
Copyright (c) 2022 Red Hat, Inc.

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
package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing parallel migration connections policy.
 *
 * @author Milan Zamazal <mzamazal@redhat.com>
 * @date 24 May 2022
 * @status added
 * @since 4.5.1
 */
@Type
public enum ParallelMigrationsPolicy {
    /**
     * Use cluster value (applicable only to VMs).
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 24 May 2022
     * @status added
     * @since 4.5.1
     */
    INHERIT,

    /**
     * Choose automatically between parallel and non-parallel connections.
     * If parallel connections are used, select their number automatically.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 24 May 2022
     * @status added
     * @since 4.5.1
     */
    AUTO,

    /**
     * Use parallel connections and select their number automatically.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 24 May 2022
     * @status added
     * @since 4.5.1
     */
    AUTO_PARALLEL,

    /**
     * Use non-parallel connections.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 24 May 2022
     * @status added
     * @since 4.5.1
     */
    DISABLED,

    /**
     * Use manually specified number of parallel connections.
     * The number of parallel connections must be set in
     * MigrationOptions.customParallelMigrations.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 24 May 2022
     * @status added
     * @since 4.5.1
     */
    CUSTOM;
}
