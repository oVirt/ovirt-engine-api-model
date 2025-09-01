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
import types.Icon;

/**
 * A service to manage an icon (read-only).
 *
 * @author Aleksei Slaikovskii <aslaikov@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface IconService {
    /**
     * Get an icon.
     *
     * ```http
     * GET /ovirt-engine/api/icons/123 HTTP/1.1
     * ```
     *
     * You will get a XML response like this one:
     *
     * ```xml
     * <icon id="123">
     *   <data>Some binary data here</data>
     *   <media_type>image/png</media_type>
     * </icon>
     * ```
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        /**
         * Retrieved icon.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Icon icon();
    }
}
