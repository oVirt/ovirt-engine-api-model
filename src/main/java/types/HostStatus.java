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
 * Type representing a host status.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 01 Dec 2016
 * @status added
 */
@Type
public enum HostStatus {
    /**
     * The host is down.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    DOWN,

    /**
     * The host is in error status.
     * This will happen if we will try
     * to run a virtual machine several times and it
     * will fail.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    ERROR,

    /**
     * The host is initializing.
     * This is an intermediate step before
     * moving the host to 'up' status.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    INITIALIZING,

    /**
     * The host is being installed.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    INSTALLING,

    /**
     * The host installation failed.
     * In such cases look at the event log to understand
     * what failed the installation, and issue a re-install.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    INSTALL_FAILED,

    /**
     * The host is in maintenance status.
     * When a host is in maintenance it cannot run virtual machines.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    MAINTENANCE,

    /**
     * The host is non operational.
     * This can happen due to various reasons, such as not having
     * a connection with the storage, not supporting a mandatory network,
     * not supporting the cluster level, and more.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    NON_OPERATIONAL,

    /**
     * The host is not responsive.
     * This means that the engine is not able to communicate with the host.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    NON_RESPONSIVE,

    /**
     * The host is pending administrator approval.
     * This is relevant only for vintage ovirt-node / RHV-H.
     * This property is no longer relevant since Vintage Node is no longer supported, and has been deprecated.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @author Dana Elfassy <delfassy@redhat.com>
     * @date 19 Aug 2019
     * @status added
     */
    @Deprecated
    PENDING_APPROVAL,

    /**
     * The host is preparing for maintenance.
     * During this time the engine makes sure to live migrate
     * all the virtual machines from this host to other hosts.
     * Once all migrations have been completed the host will move
     * to 'maintenance' status.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    PREPARING_FOR_MAINTENANCE,

    /**
     * The engine cannot communicate with the host for a specific threshold
     * so it is now trying to connect before going through fencing.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    CONNECTING,

    /**
     * The host is being rebooted.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    REBOOT,

    /**
     * The host is in activation process.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    UNASSIGNED,

    /**
     * The host is up.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    UP,

    /**
     * The host operating system is now installing.
     * This status is relevant when using a Satellite/Foreman
     * provider, and issuing a bare-metal provisioning (discovered host provisioning).
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    INSTALLING_OS,

    /**
     * The host kernel has crashed and it is now going through memory dumping.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    KDUMPING;
}
