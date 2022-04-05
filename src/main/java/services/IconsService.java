/*
Copyright (c) 2015 Red Hat, Inc.

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

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Icon;

/**
 * A service to manage icons.
 *
 * @author Aleksei Slaikovskii <aslaikov@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface IconsService {
    /**
     * Get a list of icons.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/icons
     * ----
     *
     * You will get a XML response which is similar to this one:
     *
     * [source,xml]
     * ----
     * <icons>
     *   <icon id="123">
     *     <data>...</data>
     *     <media_type>image/png</media_type>
     *   </icon>
     *   ...
     * </icons>
     * ----
     *
     * The order of the returned list of icons isn't guaranteed.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        /**
         * Retrieved list of icons.
         *
         * @author Aleksei Slaikovskii <aslaikov@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Icon[] icons();

        /**
         * Sets the maximum number of icons to return. If not specified all the icons are returned.
         */
        @In Integer max();
    }

    /**
     * Reference to the service that manages an specific icon.
     *
     * @author Aleksei Slaikovskii <aslaikov@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service IconService icon(String id);
}
