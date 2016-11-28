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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents a quota object.
 *
 * An example XML representation of a quota:
 *
 * [source,xml]
 * ----
 * <quota href="/ovirt-engine/api/datacenters/7044934e/quotas/dcad5ddc" id="dcad5ddc">
 *   <name>My Quota</name>
 *   <description>A quota for my oVirt environment</description>
 *   <cluster_hard_limit_pct>0</cluster_hard_limit_pct>
 *   <cluster_soft_limit_pct>0</cluster_soft_limit_pct>
 *   <data_center href="/ovirt-engine/api/datacenters/7044934e" id="7044934e"/>
 *   <storage_hard_limit_pct>0</storage_hard_limit_pct>
 *   <storage_soft_limit_pct>0</storage_soft_limit_pct>
 * </quota>
 * ----
 *
 * @author Andrej Krejcir <akrejcir@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 28 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface Quota extends Identified {
    DataCenter dataCenter();
    Vm[] vms();
    Disk[] disks();
    User[] users();
    Integer clusterSoftLimitPct();
    Integer clusterHardLimitPct();
    Integer storageSoftLimitPct();
    Integer storageHardLimitPct();

    @Link Permission[] permissions();
    @Link QuotaClusterLimit[] quotaClusterLimits();
    @Link QuotaStorageLimit[] quotaStorageLimits();
}
