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

@Type
public interface DataCenter extends Identified {
    Boolean local();
    StorageFormat storageFormat();
    Version version();
    Version[] supportedVersions();
    Status status();
    QuotaModeType quotaMode();

    /**
     * Note that since version 4 of the API each cluster has its own MAC pool.
     * If you change `macPool` property on data center, all the clusters belonging
     * to this data center will be changed to use this new MAC pool. Also note,
     * that if any data center has more clusters with different MAC pools
     * assigned, `macPool` property of data center cannot be determined, and thus
     * will not be reported in that case.
     */
    @Link MacPool macPool();
}
