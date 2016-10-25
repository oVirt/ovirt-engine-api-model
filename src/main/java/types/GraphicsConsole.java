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

@Type
public interface GraphicsConsole extends Identified {
    GraphicsType protocol();
    Integer port();
    Integer tlsPort();
    String address();

    /**
     * Contains the file which is compatible with `remote-viewer` client.
     *
     * User can use the content of this attribute to create a file, which can be passed to `remote-viewer` client to
     * connect to virtual machine graphic console.
     *
     * E.g., to fetch the content of remote viewer connection file and save it into temporary file, user can use
     * oVirt Python SDK as follows:
     *
     * [source,python]
     * ----
     * # Find the virtual machine:
     * vm = vms_service.list(search='name=myvm')[0]
     *
     * # Locate the service that manages the virtual machine, as that is where
     * # the locators are defined:
     * vm_service = vms_service.vm_service(vm.id)
     *
     * # Find the graphic console of the virtual machine:
     * graphics_console = vm_service.graphics_consoles_service().list(
     *     populate_remote_viewer_connection_file=True,
     * )[0]
     *
     * # Write the content to file "/tmp/remote_viewer_connection_file.vv"
     * path = "/tmp/remote_viewer_connection_file.vv"
     * with open(path, "w") as f:
     *     f.write(graphics_console.remote_viewer_connection_file)
     * ----
     *
     * When you create the remote viewer connection file, then you can connect to virtual machine graphic console,
     * as follows:
     *
     * [source,bash]
     * ----
     * #!/bin/sh -ex
     *
     * remote-viewer --ovirt-ca-file=/etc/pki/ovirt-engine/ca.pem /tmp/remote_viewer_connection_file.vv
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 25 Oct 2016
     * @status added
     * @since 4.1
     */
    String remoteViewerConnectionFile();

    @Link Vm vm();
    @Link Template template();
    @Link InstanceType instanceType();
}
