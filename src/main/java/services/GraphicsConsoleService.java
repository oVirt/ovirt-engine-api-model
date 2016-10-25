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
public interface GraphicsConsoleService {

    /**
     * Gets the configuration of the graphics console.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 8 Jul 2016
     * @status added
     */
    interface Get {
        @Out GraphicsConsole console();

        /**
         * Use the following query to obtain the current run-time configuration of the graphics console.
         *
         * [source]
         * ----
         * GET /ovit-engine/api/vms/{vm:id}/graphicsconsoles/{console:id}?current=true
         * ----
         *
         * The default value is `false`.
         *
         * @author Tomas Jelinek <tjelinek@redhat.com>
         * @date 8 Jul 2016
         * @status added
         */
        @In Boolean current();

        /**
         * Specify if the API should return the attribute `remote_viewer_connection_file`, which contains the content
         * of the file which is compatible with `remote-viewer` client. For more information about this attribute
         * please take a look <<types/graphics_console/attributes/remote_viewer_connection_file, here>>.
         *
         * Use the following request to obtain the `remote_viewer_connection_file` attribute of the graphics console.
         * Note that the attribute `remote_viewer_connection_file` is generated only if the virtual machine is running.
         *
         * [source]
         * ----
         * GET /ovit-engine/api/vms/123/graphicsconsoles/456?populate_remote_viewer_connection_file=true
         * ----
         *
         * The default value is `false`.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 25 Oct 2016
         * @status added
         * @since 4.1
         */
        @In Boolean populateRemoteViewerConnectionFile();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
