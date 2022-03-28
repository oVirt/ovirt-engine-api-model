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

package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents the kinds of devices that a virtual machine can boot from.
 *
 * @author Shahar Havivi <shavivi@redhat.com>
 * @author Lukas Svaty <lsvaty@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public enum BootDevice {
    /**
     * Boot from CD-ROM. The CD-ROM can be chosen from the list of ISO files available in an ISO domain attached to the
     * ata center that the virtual machine belongs to.
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    CDROM,

    /**
     * Boot from the hard drive.
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    HD,

    /**
     * Boot from the network, using PXE. It is necessary to have
     * link:https://en.wikipedia.org/wiki/Preboot_Execution_Environment[PXE] configured on the network that the virtual
     * machine is connected to.
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    NETWORK;
}
