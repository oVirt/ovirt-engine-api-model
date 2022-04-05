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

import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.Quota;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * Manages the set of quotas configured for a data center.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 15 Apr 2017
 * @status added
 */
@Service
@Area("SLA")
public interface QuotasService {

    /**
     * Creates a new quota.
     *
     * An example of creating a new quota:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/datacenters/123/quotas
     * ----
     *
     * [source,xml]
     * ----
     * <quota>
     *   <name>myquota</name>
     *   <description>My new quota for virtual machines</description>
     * </quota>
     * ----
     *
     * @author Roman Mohr <rmohr@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(quota().name());
            optional(quota().clusterHardLimitPct());
            optional(quota().clusterSoftLimitPct());
            optional(quota().description());
            optional(quota().storageHardLimitPct());
            optional(quota().storageSoftLimitPct());
        }
        @In @Out Quota quota();
    }

    /**
     * Lists quotas of a data center.
     *
     * The order of the returned list of quotas isn't guaranteed.
     *
     * @author Roman Mohr <rmohr@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface List extends Follow {
        @Out Quota[] quotas();

        /**
         * Sets the maximum number of quota descriptors to return. If not specified all the descriptors are returned.
         */
        @In Integer max();
    }

    @Service QuotaService quota(String id);
}
