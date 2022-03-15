/*
Copyright (c) 2015-2022 Red Hat, Inc.

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
 * The type that represents a virtual machine template.
 * Templates allow for a rapid instantiation of virtual machines with common configuration and disk states.
 *
 * @author Arik Hadas <ahadas@redhat.com>
 * @author Megan Lewis <melewis@redhat.com>
 * @date 02 Aug 2018
 * @status updated_by_docs
 */
@Type
public interface Template extends VmBase {
    /**
     * The status of the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 02 Aug 2018
     * @status updated_by_docs
     */
    TemplateStatus status();

    /**
     * The virtual machine configuration associated with this template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 02 Aug 2018
     * @status updated_by_docs
     */
    Vm vm();

    /**
     * Indicates whether this is the base version or a sub-version of another template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 02 Aug 2018
     * @status updated_by_docs
     */
    TemplateVersion version();

    /**
     * Reference to the CD-ROM devices attached to the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 02 Aug 2018
     * @status updated_by_docs
     */
    @Link Cdrom[] cdroms();

    /**
     * Reference to the graphic consoles attached to the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 02 Aug 2018
     * @status updated_by_docs
     */
    @Link GraphicsConsole[] graphicsConsoles();

    /**
     * Reference to the network interfaces attached to the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 02 Aug 2018
     * @status updated_by_docs
     */
    @Link Nic[] nics();

    /**
     * Reference to the user permissions attached to the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 02 Aug 2018
     * @status updated_by_docs
     */
    @Link Permission[] permissions();

    /**
     * Reference to the tags attached to the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 02 Aug 2018
     * @status updated_by_docs
     */
    @Link Tag[] tags();

    /**
     * Reference to the watchdog devices attached to the template.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 02 Aug 2018
     * @status updated_by_docs
     */
    @Link Watchdog[] watchdogs();

    /**
     * Reference to the disks attached to the template.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 28 Dec 2016
     * @status updated_by_docs
     */
    @Link DiskAttachment[] diskAttachments();

    /**
     * Mediated devices configuration.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @date 10 Mar 2022
     * @status added
     * @since 4.5
     */
    @Link VmMediatedDevice[] mediatedDevices();
}
