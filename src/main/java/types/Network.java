/*
Copyright (c) 2015-2016 Red Hat, Inc.

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
 * Logical network.
 *
 * An example of the JSON representation of a logical network:
 *
 * [source,json]
 * ----
 * {
 *   "network" : [ {
 *     "data_center" : {
 *       "href" : "/ovirt-engine/api/datacenters/123",
 *       "id" : "123"
 *     },
 *     "stp" : "false",
 *     "mtu" : "0",
 *     "usages" : {
 *       "usage" : [ "vm" ]
 *     },
 *     "name" : "ovirtmgmt",
 *     "description" : "Management Network",
 *     "href" : "/ovirt-engine/api/networks/456",
 *     "id" : "456",
 *     "link" : [ {
 *       "href" : "/ovirt-engine/api/networks/456/permissions",
 *       "rel" : "permissions"
 *     }, {
 *       "href" : "/ovirt-engine/api/networks/456/vnicprofiles",
 *       "rel" : "vnicprofiles"
 *     }, {
 *       "href" : "/ovirt-engine/api/networks/456/labels",
 *       "rel" : "labels"
 *     } ]
 *   } ]
 * }
 * ----
 *
 * An example of the XML representation of the same logical network:
 *
 * [source,xml]
 * ----
 * <network href="/ovirt-engine/api/networks/456" id="456">
 *   <name>ovirtmgmt</name>
 *   <description>Management Network</description>
 *   <link href="/ovirt-engine/api/networks/456/permissions" rel="permissions"/>
 *   <link href="/ovirt-engine/api/networks/456/vnicprofiles" rel="vnicprofiles"/>
 *   <link href="/ovirt-engine/api/networks/456/labels" rel="labels"/>
 *   <data_center href="/ovirt-engine/api/datacenters/123" id="123"/>
 *   <stp>false</stp>
 *   <mtu>0</mtu>
 *   <usages>
 *     <usage>vm</usage>
 *   </usages>
 * </network>
 * ----
 *
 * @author Martin Mucha <mmucha@redhat.com>
 * @date 14 Sep 2016
 * @status added
 */
@Type
public interface Network extends Identified {
    Ip ip();
    Vlan vlan();
    Boolean stp();
    NetworkStatus status();
    Boolean display();
    Integer mtu();
    NetworkUsage[] usages();
    Boolean required();
    Boolean profileRequired();

    @Link DataCenter dataCenter();
    @Link Cluster cluster();
    @Link Qos qos();
    @Link NetworkLabel[] networkLabels();
    @Link Permission[] permissions();
    @Link VnicProfile[] vnicProfiles();
}
