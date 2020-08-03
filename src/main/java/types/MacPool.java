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
 * Represents a MAC address pool.
 *
 * Example of an XML representation of a MAC address pool:
 *
 * [source,xml]
 * ----
 * <mac_pool href="/ovirt-engine/api/macpools/123" id="123">
 *   <name>Default</name>
 *   <description>Default MAC pool</description>
 *   <allow_duplicates>false</allow_duplicates>
 *   <default_pool>true</default_pool>
 *   <ranges>
 *     <range>
 *       <from>00:1A:4A:16:01:51</from>
 *       <to>00:1A:4A:16:01:E6</to>
 *     </range>
 *   </ranges>
 * </mac_pool>
 * ----
 *
 * @author Marcin Mirecki <mmirecki@redhat.com>
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 21 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface MacPool extends Identified {
    /**
     * Defines whether duplicate MAC addresses are permitted in the pool. If not specified, defaults to `false`.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 21 Nov 2016
     * @status updated_by_docs
     */
    Boolean allowDuplicates();

    /**
     * Defines whether this is the default pool. If not specified, defaults to `false`.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 21 Nov 2016
     * @status updated_by_docs
     */
    Boolean defaultPool();

    /**
     * Defines the range of MAC addresses for the pool. Multiple ranges can be defined.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 21 Nov 2016
     * @status updated_by_docs
     */
    Range[] ranges();

    /**
     * Returns a reference to the permissions that are associated with the MacPool.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 22 September 2020
     * @since 4.4.3
     * @status added
     */
    @Link
    Permission[] permissions();
}
