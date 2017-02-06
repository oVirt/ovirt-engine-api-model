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

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.Quota;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("SLA")
public interface QuotaService {

    /**
     * Retrieves a quota.
     *
     * An example of retrieving a quota:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/datacenters/123/quotas/456
     * ----
     *
     * [source,xml]
     * ----
     * <quota id="456">
     *   <name>myquota</name>
     *   <description>My new quota for virtual machines</description>
     *   <cluster_hard_limit_pct>20</cluster_hard_limit_pct>
     *   <cluster_soft_limit_pct>80</cluster_soft_limit_pct>
     *   <storage_hard_limit_pct>20</storage_hard_limit_pct>
     *   <storage_soft_limit_pct>80</storage_soft_limit_pct>
     * </quota>
     * ----
     *
     * @author Roman Mohr <rmohr@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Get {
        @Out Quota quota();
    }

    /**
     * Updates a quota.
     *
     * An example of updating a quota:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/datacenters/123/quotas/456
     * ----
     *
     * [source,xml]
     * ----
     * <quota>
     *   <cluster_hard_limit_pct>30</cluster_hard_limit_pct>
     *   <cluster_soft_limit_pct>70</cluster_soft_limit_pct>
     *   <storage_hard_limit_pct>20</storage_hard_limit_pct>
     *   <storage_soft_limit_pct>80</storage_soft_limit_pct>
     * </quota>
     * ----
     *
     * @author Roman Mohr <rmohr@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(quota().clusterHardLimitPct());
            optional(quota().clusterSoftLimitPct());
            optional(quota().description());
            optional(quota().name());
            optional(quota().storageHardLimitPct());
            optional(quota().storageSoftLimitPct());
        }
        @In @Out Quota quota();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();

    }

    /**
     * Delete a quota.
     *
     * An example of deleting a quota:
     *
     * [source]
     * ----
     * DELETE /ovirt-engine/api/datacenters/123-456/quotas/654-321
     * -0472718ab224 HTTP/1.1
     * Accept: application/xml
     * Content-type: application/xml
     * ----
     *
     * @author Roman Mohr <rmohr@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }

    @Service QuotaStorageLimitsService quotaStorageLimits();
    @Service QuotaClusterLimitsService quotaClusterLimits();
    @Service AssignedPermissionsService permissions();
}
