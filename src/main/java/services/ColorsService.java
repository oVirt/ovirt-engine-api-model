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
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Color;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

/**
 * A service to manage colors.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface ColorsService {
    /**
     * Adding a new color.
     *
     * Example of adding a color:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/colors
     * ----
     *
     * [source,xml]
     * ----
     * <color>
     *   <name>new_example_vm</name>
     *   <value>vm: name=new_example*</value>
     * </color>
     * ----
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(color().name());
            mandatory(color().red());
            mandatory(color().blue());
            mandatory(color().green());
        }
        /**
         * The added color.
         *
         * @author Oved Ourfali <oourfali@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @In @Out Color color();
    }

    /**
     * Listing all the available colors.
     *
     * Example of listing colors:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/colors
     * ----
     *
     * [source,xml]
     * ----
     * <colors>
     *   <color href="/ovirt-engine/api/colors/123" id="123">
     *     <name>database</name>
     *     <value>vm: name=database*</value>
     *   </color>
     *   <color href="/ovirt-engine/api/colors/456" id="456">
     *     <name>example</name>
     *     <value>vm: name=example*</value>
     *   </color>
     * </colors>
     * ----
     *
     * The order of the returned colors isn't guaranteed.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        /**
         * The list of available colors.
         *
         * @author Oved Ourfali <oourfali@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out Color[] colors();

        /**
         * Sets the maximum number of colors to return. If not specified all the colors are returned.
         */
        @In Integer max();
    }

    /**
     * A reference to the service managing a specific color.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service ColorsService colors(String id);
}
