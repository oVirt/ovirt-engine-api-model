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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Vm;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * This service manages a collection of all the virtual machines assigned to an affinity group.
 *
 * @author Martin Sivak <msivak@redhat.com>
 * @author Megan Lewis <melewis@redhat.com>
 * @date 31 Jan 2017
 * @status updated_by_docs
 */
@Service
@Area("SLA")
public interface AffinityGroupVmsService {
    /**
     * Add a virtual machine to the affinity group.
     *
     * For example, to add the virtual machine 000-000 to affinity group 123-456 send a request to:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/clusters/000-000/affinitygroups/123-456/vms
     * ----
     *
     * With the following body:
     *
     * [source,xml]
     * ----
     * <vm id="000-000"/>
     * ----
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 20 Feb 2017
     * @status updated_by_docs
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            or(mandatory(vm().id()), mandatory(vm().name()));
        }
        @In @Out Vm vm();
    }

    /**
     * List all virtual machines assigned to this affinity group.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    interface List {
        @Out Vm[] vms();

        /**
         * Sets the maximum number of virtual machines to return. If not specified, all the virtual machines are
         * returned.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 31 Jan 2017
         * @status updated_by_docs
         */
        @In Integer max();
    }

    /**
     * Access the service that manages the virtual machine assignment to this affinity group.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    @Service AffinityGroupVmService vm(String id);
}
