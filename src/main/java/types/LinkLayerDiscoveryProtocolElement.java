/*
Copyright (c) 2017 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents an information element received by Link Layer Discovery Protocol (LLDP).
 * IEEE 802.1AB defines type, length, value (TLV) as a "short, variable length encoding of an information element".
 * This type represents such an information element.
 *
 * The attribute `name` is a human-readable string used to describe what the value is about, and may not be unique.
 * The name is redundant, because it could be created from `type` and the optional `oui` and `subtype`.
 * The purpose of `name` is to simplify the reading of the information element.
 * The `name` of a property is exactly the same string which is used in IEEE 802.1AB chapter 8.
 *
 * Organizationally-specific information elements have the `type` of `127` and the attributes
 * `oui` and `subtype`.
 *
 * For example, the XML representation of an information element may look like this:
 *
 * [source,xml]
 * ----
 * <link_layer_discovery_protocol_element>
 *   <name>Port VLAN Id</name>
 *   <oui>32962</oui>
 *   <properties>
 *     <property>
 *       <name>vlan id</name>
 *       <value>488</value>
 *     </property>
 *     <property>
 *       <name>vlan name</name>
 *       <value>v2-0488-03-0505</value>
 *     </property>
 *   </properties>
 *   <subtype>3</subtype>
 *   <type>127</type>
 * </link_layer_discovery_protocol_element>
 * ----
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 14 Jul 2017
 * @status updated_by_docs
 * @since 4.1.5
 */
@Type
public interface LinkLayerDiscoveryProtocolElement extends Identified {
    /**
     * The type of the LinkLayerDiscoveryProtocolElement encoded as an integer.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 14 Jul 2017
     * @status updated_by_docs
     * @since 4.1.5
     */
    Integer type();

    /**
     * The organizationally-unique identifier (OUI) encoded as an integer.
     * Only available if `type` is `127`.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 14 Jul 2017
     * @status updated_by_docs
     * @since 4.1.5
     */
    Integer oui();

    /**
     * The organizationally-defined subtype encoded as an integer.
     * Only available if `type` is `127`.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 14 Jul 2017
     * @status updated_by_docs
     * @since 4.1.5
     */
    Integer subtype();

    /**
     * Represents structured data transported by the information element as a
     * list of name/value pairs.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 14 Jul 2017
     * @status updated_by_docs
     * @since 4.1.5
     */
    Property[] properties();
}
