/*
Copyright (c) 2019 Red Hat, Inc.

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

package services;

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;

/**
 * This service manages a single host to affinity group assignment.
 *
 * @author Lucia Jelinkova <ljelinko@redhat.com>
 * @date 26 Jun 2019
 * @since 4.4
 * @status added
 */
@Service
@Area("SLA")
public interface AffinityGroupHostService {
    /**
     * Remove host from the affinity group.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @date 26 Jun 2019
     * @since 4.4
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the removal should be performed asynchronously.
         *
         * @author Lucia Jelinkova <ljelinko@redhat.com>
         * @date 26 Jun 2019
         * @since 4.4
         * @status added
         */
        @In Boolean async();
    }
}
