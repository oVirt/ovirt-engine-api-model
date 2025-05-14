/*
The oVirt Project - oVirt Engine Api Model

Copyright oVirt Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the Apache License, Version 2.0 is included with the program
in the file ASL2.
*/

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.NetworkFilter;

/**
 * Manages a network filter.
 *
 * ```xml
 * <network_filter id="00000019-0019-0019-0019-00000000026b">
 *   <name>example-network-filter-b</name>
 *   <version>
 *     <major>4</major>
 *     <minor>0</minor>
 *     <build>-1</build>
 *     <revision>-1</revision>
 *   </version>
 * </network_filter>
 * ```
 *
 * Please note that version is referring to the minimal support version for the specific filter.
 */
@Service
@Area("Network")
public interface NetworkFilterService {

    /**
     * Retrieves a representation of the network filter.
     */
    interface Get extends Follow {
        @Out NetworkFilter networkFilter();
    }

}
