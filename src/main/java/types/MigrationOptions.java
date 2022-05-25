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

package types;

import org.ovirt.api.metamodel.annotations.Type;
import org.ovirt.api.metamodel.annotations.Link;

/**
 * The type for migration options.
 *
 * @author Megan Lewis <melewis@redhat.com>
 * @date 31 Jan 2017
 * @status updated_by_docs
 */
@Type
public interface MigrationOptions {
    InheritableBoolean autoConverge();
    InheritableBoolean compressed();

    /**
     * Specifies whether the migration should be encrypted or not.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @date 30 Sep 2019
     * @status added
     * @since 4.4
     */
    InheritableBoolean encrypted();

    /**
     * Specifies whether and how to use parallel migration connections.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 24 May 2022
     * @status added
     * @since 4.5.1
     */
    ParallelMigrationsPolicy parallelMigrationsPolicy();

    /**
     * Specifies how many parallel migration connections to use.
     * May be specified only when ParallelMigrationsPolicy is CUSTOM.
     * The valid range of values is 2-255.
     * The recommended range of values is 2-16.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 24 May 2022
     * @status added
     * @since 4.5.1
     */
    Integer customParallelMigrations();

    /**
     * The bandwidth that is allowed to be used by the migration.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    MigrationBandwidth bandwidth();

    /**
     * A reference to the migration policy, as defined using `engine-config`.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    @Link MigrationPolicy policy();
}
