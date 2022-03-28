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
 * Represents a graphic console configuration.
 *
 * @author Sharon Gratch <sgratch@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public interface Display {
    /**
     * The graphic console protocol type.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    DisplayType type();

    /**
     * The IP address of the guest to connect the graphic console client to.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String address();

    /**
     * The port address on the guest to connect the graphic console client to.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Integer port();

    /**
     * The secured port address on the guest, in case of using TLS, to connect the graphic console client to.
     * If TLS isn't enabled then it won't be reported.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Integer securePort();

    /**
     * The number of monitors opened for this graphic console.
     * This option is only available for the SPICE console type.
     * Possible values are 1, 2 or 4.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Integer monitors();

    /**
     * The engine now sets it automatically according to the operating system. Therefore, it has been deprecated
     * since 4.4.5.
     * Indicates if to use one PCI slot for each monitor or to use a single PCI channel for all multiple monitors.
     * This option is only available for the SPICE console type and only for connecting a guest Linux based OS.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 12 Jan 2021
     * @status updated
     */
    @Deprecated
    Boolean singleQxlPci();

    /**
     * Indicates if to override the display address per host.
     * Relevant only for the `Host.display` attribute.
     * If set, the graphical console address of a virtual machine will be overridden by the host specified display address.
     * if not set, the graphical console address of a virtual machine will not be overridden.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Boolean allowOverride();

    /**
     * The TLS certificate in case of a TLS connection.
     * If TLS isn't enabled then it won't be reported.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Certificate certificate();

    /**
     * Indicates if to use smart card authentication.
     * This option is only available for the SPICE console type.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Boolean smartcardEnabled();

    /**
     * The keyboard layout to use with this graphic console.
     * This option is only available for the VNC console type.
     * If no keyboard is enabled then it won't be reported.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String keyboardLayout();

    /**
     * The proxy IP which will be used by the graphic console client to connect to the guest.
     * It is useful when the client is outside the guest's network.
     * This option is only available for the SPICE console type.
     * This proxy can be set in global configuration, cluster level, virtual machine pool level or disabled
     * per virtual machine.
     * If the proxy is set in any of this mentioned places and not disabled for the virtual machine, it will be
     * returned by this method.
     * If the proxy is not set, nothing will be reported.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String proxy();

    /**
     * Indicates if a user is able to drag and drop files from an external host into the graphic console.
     * This option is only available for the SPICE console type.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Boolean fileTransferEnabled();

    /**
     * Indicates whether a user is able to copy and paste content from an external host into the graphic console.
     * This option is only available for the SPICE console type.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Boolean copyPasteEnabled();

    /**
     * Returns the action that will take place when the graphic console is disconnected.
     * The options are:
     *
     * none:: No action is taken.
     * lock_screen:: Locks the currently active user session.
     * logout:: Logs out the currently active user session.
     * reboot:: Initiates a graceful virtual machine reboot.
     * shutdown:: Initiates a graceful virtual machine shutdown.
     *
     * This option is only available for the SPICE console type.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    // TODO: Make this an enum.
    String disconnectAction();

    /**
     * Delay (in minutes) before the graphic console disconnect action is carried out.
     * This option is only available for Shutdown disconnect action.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 1 Mar 2022
     * @status added
     * @since 4.5
     */
    Integer disconnectActionDelay();
}
