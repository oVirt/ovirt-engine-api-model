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
import types.QuotaClusterLimit;

@Service
@Area("SLA")
public interface QuotaClusterLimitsService {
    /**
     * Add a cluster limit to a specified Quota.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
//            mandatory(limit().cluster().id());//TODO: check
        }
        @In @Out QuotaClusterLimit limit();
    }

    interface List {
        @Out QuotaClusterLimit[] limits();

        /**
         * Sets the maximum number of limits to return. If not specified all the limits are returned.
         */
        @In Integer max();
    }

    @Service QuotaClusterLimitService limit(String id);
}
