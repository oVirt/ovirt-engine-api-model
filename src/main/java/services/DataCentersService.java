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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.DataCenter;

@Service
@Area("Virtualization")
public interface DataCentersService {
    interface Add {
        @In @Out DataCenter dataCenter();
    }

    /**
     * Lists the data centers.
     *
     * The following request retrieves a representation of the data centers:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/datacenters
     * ----
     *
     * The above request performed with `curl`:
     *
     * [source,bash]
     * ----
     * curl \
     * --request GET \
     * --cacert /etc/pki/ovirt-engine/ca.pem \
     * --header "Version: 4" \
     * --header "Accept: application/xml" \
     * --user "admin@internal:mypassword" \
     * https://myengine.example.com/ovirt-engine/api/datacenters
     * ----
     *
     * This is what an example response could look like:
     *
     * [source,xml]
     * ----
     * <data_center href="/ovirt-engine/api/datacenters/123" id="123">
     *   <name>Default</name>
     *   <description>The default Data Center</description>
     *   <link href="/ovirt-engine/api/datacenters/123/networks" rel="networks"/>
     *   <link href="/ovirt-engine/api/datacenters/123/storagedomains" rel="storagedomains"/>
     *   <link href="/ovirt-engine/api/datacenters/123/permissions" rel="permissions"/>
     *   <link href="/ovirt-engine/api/datacenters/123/clusters" rel="clusters"/>
     *   <link href="/ovirt-engine/api/datacenters/123/qoss" rel="qoss"/>
     *   <link href="/ovirt-engine/api/datacenters/123/iscsibonds" rel="iscsibonds"/>
     *   <link href="/ovirt-engine/api/datacenters/123/quotas" rel="quotas"/>
     *   <local>false</local>
     *   <quota_mode>disabled</quota_mode>
     *   <status>up</status>
     *   <supported_versions>
     *     <version>
     *       <major>4</major>
     *       <minor>0</minor>
     *     </version>
     *   </supported_versions>
     *   <version>
     *     <major>4</major>
     *     <minor>0</minor>
     *   </version>
     * </data_center>
     * ----
     *
     * Note the `id` code of your `Default` data center. This code identifies this data center in relation to other
     * resources of your virtual environment.
     *
     * The data center also contains a link to the storage domains collection. The data center uses this collection to
     * attach storage domains from the storage domains main collection.
     *
     * @author Vinzenz Feenstra <vfeenstr@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface List {
        @Out DataCenter[] dataCenters();

        /**
         * Sets the maximum number of data centers to return. If not specified all the data centers are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned data centers.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    @Service DataCenterService dataCenter(String id);
}
