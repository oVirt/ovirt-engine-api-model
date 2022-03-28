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
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.QuotaStorageLimit;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

/**
 * Manages the set of storage limits configured for a quota.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 5 Sep 2017
 * @status updated_by_docs
 */
@Service
@Area("SLA")
public interface QuotaStorageLimitsService {
    /**
     * Adds a storage limit to a specified quota.
     *
     * To create a 100GiB storage limit for all storage domains in a data center, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/datacenters/123/quotas/456/quotastoragelimits
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <quota_storage_limit>
     *   <limit>100</limit>
     * </quota_storage_limit>
     * ----
     *
     * To create a 50GiB storage limit for a storage domain with the ID `000`, send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/datacenters/123/quotas/456/quotastoragelimits
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <quota_storage_limit>
     *   <limit>50</limit>
     *   <storage_domain id="000"/>
     * </quota_storage_limit>
     * ----
     *
     * @author Ori Liel <oliel@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 5 Sep 2017
     * @status updated_by_docs
     */
    interface Add {
        @In @Out QuotaStorageLimit limit();
    }

    /**
     * Returns the list of storage limits configured for the quota.
     *
     * The order of the returned list of storage limits is not guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 5 Sep 2017
     * @status updated_by_docs
     */
    interface List extends Follow {
        @Out QuotaStorageLimit[] limits();

        /**
         * Sets the maximum number of limits to return. If not specified, all the limits are returned.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 5 Sep 2017
         * @status updated_by_docs
         */
        @In Integer max();
    }

    @Service QuotaStorageLimitService limit(String id);
}
