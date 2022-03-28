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

package services;

import annotations.Area;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Service;

/**
 * This service manages a single virtual machine to affinity group assignment.
 *
 * @author Martin Sivak <msivak@redhat.com>
 * @author Megan Lewis <melewis@redhat.com>
 * @date 31 Jan 2017
 * @status updated_by_docs
 */
@Service
@Area("SLA")
public interface AffinityGroupVmService {
    /**
     * Remove this virtual machine from the affinity group.
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
}
