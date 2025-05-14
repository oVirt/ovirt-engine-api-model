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

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import mixins.Follow;
import types.AffinityGroup;

/**
 * This service manages a single affinity group.
 *
 * @author Martin Sivak <msivak@redhat.com>
 * @author Megan Lewis <melewis@redhat.com>
 * @date 20 Feb 2017
 * @status updated_by_docs
 */
@Service
@Area("SLA")
public interface AffinityGroupService {
    /**
     * Retrieve the affinity group details.
     *
     * ```xml
     * <affinity_group id="00000000-0000-0000-0000-000000000000">
     *   <name>AF_GROUP_001</name>
     *   <cluster id="00000000-0000-0000-0000-000000000000"/>
     *   <positive>true</positive>
     *   <enforcing>true</enforcing>
     * </affinity_group>
     * ```
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    interface Get extends Follow {
        /**
         * The affinity group.
         *
         * @author Martin Sivak <msivak@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 31 Jan 2017
         * @status updated_by_docs
         */
        @Out AffinityGroup group();
    }

    /**
     * Update the affinity group.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(group().enforcing());
            optional(group().name());
            optional(group().positive());
        }
        /**
         * The affinity group.
         *
         * @author Martin Sivak <msivak@redhat.com>
         * @author Megan Lewis <melewis@redhat.com>
         * @date 31 Jan 2017
         * @status updated_by_docs
         */
        @In @Out AffinityGroup group();

        /**
         * Indicates if the update should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 31 Jan 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Remove the affinity group.
     *
     * ```http
     * DELETE /ovirt-engine/api/clusters/000-000/affinitygroups/123-456
     * ```
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    interface Remove {
        /**
         * Indicates if the removal should be performed asynchronously.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 20 Feb 2017
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    /**
     * Returns a reference to the service that manages the
     * list of all virtual machines attached to this affinity
     * group.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    @Service AffinityGroupVmsService vms();

    /**
     * Returns a reference to the service that manages the
     * list of all hosts attached to this affinity
     * group.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @date 26 Jun 2019
     * @since 4.4
     * @status added
     */
    @Service AffinityGroupHostsService hosts();

    /**
     * Returns a reference to the service that manages the
     * list of all virtual machine labels attached to this affinity
     * group.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 22 Jul 2019
     * @status added
     * @since 4.3.6
     */
    @Service AffinityGroupVmLabelsService vmLabels();

    /**
     * Returns a reference to the service that manages the
     * list of all host labels attached to this affinity
     * group.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 22 Jul 2019
     * @status added
     * @since 4.3.6
     */
    @Service AffinityGroupHostLabelsService hostLabels();
}
