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
import types.GraphicsConsole;

@Service
@Area("Virtualization")
public interface GraphicsConsolesService {
    interface Add {
        @In @Out GraphicsConsole console();
    }

    /**
     * Lists all the configured graphics consoles of the virtual machine.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 8 Jul 2016
     * @status added
     */
    interface List {
        @Out GraphicsConsole[] consoles();

        /**
         * Sets the maximum number of consoles to return. If not specified all the consoles are returned.
         */
        @In Integer max();

        /**
         * Use the following query to obtain the current run-time configuration of the graphics consoles.
         *
         * [source]
         * ----
         * GET /ovirt-engine/api/vms/123/graphicsconsoles?current=true
         * ----
         *
         * The default value is `false`.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @date 8 Jul 2016
         * @status added
         */
        @In Boolean current();

    }

    @Service GraphicsConsoleService console(String id);
}
