/*
Copyright (c) 2019 Red Hat, Inc.

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

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import mixins.Follow;
import types.Host;

/**
 * This service manages a collection of all hosts assigned to an affinity group.
 *
 * @author Lucia Jelinkova <ljelinko@redhat.com>
 * @date 26 Jun 2019
 * @since 4.4
 * @status added
 */
@Service
@Area("SLA")
public interface AffinityGroupHostsService {
    /**
     * Adds a host to the affinity group.
     *
     * For example, to add the host `789` to the affinity group `456` of cluster `123`, send a request like
     * this:
     *
     * ....
     * POST /ovirt-engine/api/clusters/123/affinitygroups/456/hosts
     * ....
     *
     * With the following body:
     *
     * [source,xml]
     * ----
     * <host id="789"/>
     * ----
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @date 26 Jun 2019
     * @since 4.4
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            or(mandatory(host().id()), mandatory(host().name()));
        }
        /**
         * The host to be added to the affinity group.
         *
         * @author Lucia Jelinkova <ljelinko@redhat.com>
         * @date 26 Jun 2019
         * @since 4.4
         * @status added
         */
        @In @Out Host host();
    }

    /**
     * List all hosts assigned to this affinity group.
     *
     * The order of the returned hosts isn't guaranteed.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @date 26 Jun 2019
     * @since 4.4
     * @status added
     */
    interface List extends Follow {
        /**
         * The list of hosts assigned to this affinity group.
         *
         * @author Lucia Jelinkova <ljelinko@redhat.com>
         * @date 26 Jun 2019
         * @since 4.4
         * @status added
         */
        @Out Host[] hosts();

        /**
         * Sets the maximum number of hosts to return. If not specified, all the hosts are
         * returned.
         *
         * @author Lucia Jelinkova <ljelinko@redhat.com>
         * @date 26 Jun 2019
         * @since 4.4
         * @status added
         */
        @In Integer max();
    }

    /**
     * Access the service that manages the host assignment to this affinity group by host id or name.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @author Saif Abu Saleh <sabusale@redhat.com>
     * @date 1 Dec 2021
     * @since 4.4
     * @status updated
     */
    @Service AffinityGroupHostService host(String idOrName);
}
