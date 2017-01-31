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

package services;

import annotations.Area;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
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
     * [source,xml]
     * ----
     * <affinity_group id="00000000-0000-0000-0000-000000000000">
     *   <name>AF_GROUP_001</name>
     *   <cluster id="00000000-0000-0000-0000-000000000000"/>
     *   <positive>true</positive>
     *   <enforcing>true</enforcing>
     * </affinity_group>
     * ----
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    interface Get {
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
     * [source]
     * ----
     * DELETE /ovirt-engine/api/clusters/000-000/affinitygroups/123-456
     * ----
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
}
