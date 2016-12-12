/*
Copyright (c) 2015-2016 Red Hat, Inc.

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
 * Type representing a virtual machine template.
 * This allows a rapid instanstiation of virtual machines with common configuration and disk states.
 *
 * @author Arik Hadas <ahadas@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface Template extends VmBase {
    /**
     * The status of the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    TemplateStatus status();

    /**
     * The virtual machine configuration associated with this template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Vm vm();

    /**
     * Indicates whether this is a base version or a sub version of another template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    TemplateVersion version();

    /**
     * References to the CD-ROM devices attached to the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Cdrom[] cdroms();

    /**
     * References to the graphic consoles attached to the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link GraphicsConsole[] graphicsConsoles();

    /**
     * References to the network interfaces attached to the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Nic[] nics();

    /**
     * References to the user permissions attached to the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Permission[] permissions();

    /**
     * References to the tags attached to the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Tag[] tags();

    /**
     * References to the watchdog devices attached to the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Watchdog[] watchdogs();

    /**
     * References to the disks attached to the template.
     */
    @Link DiskAttachment[] diskAttachments();
}
