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
import types.AffinityLabel;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * This service manages a collection of all host labels assigned to an affinity group.
 *
 * @author Andrej Krejcir <akrejcir@redhat.com>
 * @date 22 Jul 2019
 * @status added
 * @since 4.3.6
 */
@Service
@Area("SLA")
public interface AffinityGroupHostLabelsService {
    /**
     * Adds a host label to the affinity group.
     *
     * For example, to add the label `789` to the affinity group `456` of cluster `123`,
     * send a request like this:
     *
     * ....
     * POST /ovirt-engine/api/clusters/123/affinitygroups/456/hostlabels
     * ....
     *
     * With the following body:
     *
     * [source,xml]
     * ----
     * <affinity_label id="789"/>
     * ----
     *
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 22 Jul 2019
     * @status added
     * @since 4.3.6
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            or(mandatory(label().id()), mandatory(label().name()));
        }

        /**
         * The AffinityLabel object to add to the affinity group.
         *
         * @author Andrej Krejcir <akrejcir@redhat.com>
         * @date 22 Jul 2019
         * @status added
         * @since 4.3.6
         */
        @In @Out AffinityLabel label();
    }

    /**
     * List all host labels assigned to this affinity group.
     *
     * The order of the returned labels isn't guaranteed.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 22 Jul 2019
     * @status added
     * @since 4.3.6
     */
    interface List extends Follow {
        /**
         * Host labels assigned to the affinity group.
         *
         * @author Andrej Krejcir <akrejcir@redhat.com>
         * @date 22 Jul 2019
         * @status added
         * @since 4.3.6
         */
        @Out AffinityLabel[] labels();

        /**
         * Sets the maximum number of host labels to return.
         * If not specified, all the labels are returned.
         *
         * @author Andrej Krejcir <akrejcir@redhat.com>
         * @date 22 Jul 2019
         * @status added
         * @since 4.3.6
         */
        @In Integer max();
    }

    /**
     * Access the service that manages the host label assignment to this affinity group.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 22 Jul 2019
     * @status added
     * @since 4.3.6
     */
    @Service AffinityGroupHostLabelService label(String id);
}
