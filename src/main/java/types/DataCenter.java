/*
Copyright (c) 2015-2016 Red Hat, Inc.

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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

@Type
public interface DataCenter extends Identified {
    Boolean local();
    StorageFormat storageFormat();

    /**
     * The compatibility version of the data center.
     *
     * All clusters in this data center must already be set to at least this compatibility version.
     *
     * For example:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/datacenters/123
     * ----
     *
     * Will respond:
     *
     * [source,xml]
     * ----
     * <data_center>
     *   ...
     *   <version>
     *     <major>4</major>
     *     <minor>0</minor>
     *   </version>
     *   ...
     * </data_center>
     * ----
     *
     * To update the compatibility version, use:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/datacenters/123
     * ----
     *
     * With a request body:
     *
     * [source,xml]
     * ----
     * <data_center>
     *   <version>
     *     <major>4</major>
     *     <minor>1</minor>
     *   </version>
     * </data_center>
     * ----
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    Version version();
    Version[] supportedVersions();
    DataCenterStatus status();
    QuotaModeType quotaMode();

    /**
     * Reference to the MAC pool used by this data center.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    @Link MacPool macPool();

    /**
     * Reference to storage domains attached to this data center.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    @Link StorageDomain[] storageDomains();

    /**
     * Reference to clusters inside this data center.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    @Link Cluster[] clusters();

    /**
     * Reference to networks attached to this data center.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    @Link Network[] networks();

    /**
     * Reference to permissions assigned to this data center.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    @Link Permission[] permissions();

    /**
     * Reference to quotas assigned to this data center.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    @Link Quota[] quotas();

    /**
     * Reference to quality of service used by this data center.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    @Link Qos[] qoss();

    /**
     * Reference to ISCSI bonds used by this data center.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    @Link IscsiBond[] iscsiBonds();
}
