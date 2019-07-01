/*
Copyright (c) 2015-2019 Red Hat, Inc.

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
import org.ovirt.api.metamodel.annotations.Service;

/**
 * This service manages a single host label assigned to an affinity group.
 *
 * @author Andrej Krejcir <akrejcir@redhat.com>
 * @date 22 Jul 2019
 * @status added
 * @since 4.3.6
 */
@Service
@Area("SLA")

public interface AffinityGroupHostLabelService {
    /**
     * Remove this label from the affinity group.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 22 Jul 2019
     * @status added
     * @since 4.3.6
     */
    interface Remove {
        /**
         * Indicates if the removal should be performed asynchronously.
         *
         * @author Andrej Krejcir <akrejcir@redhat.com>
         * @date 22 Jul 2019
         * @status added
         * @since 4.3.6
         */
        @In Boolean async();
    }
}
