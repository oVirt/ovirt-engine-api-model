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
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.NetworkLabel;

@Service
@Area("Network")
public interface NetworkLabelService {
    interface Get extends Follow {
        @Out NetworkLabel label();
    }

    /**
     * Removes a label from a logical network.
     *
     * For example, to remove the label `exemplary` from a logical network having id `123` send the following request:
     *
     * ```http
     * DELETE /ovirt-engine/api/networks/123/networklabels/exemplary HTTP/1.1
     * ```
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Dominik Holler <dholler@redhat.com>
     * @date 08 Apr 2019
     * @status updated
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
