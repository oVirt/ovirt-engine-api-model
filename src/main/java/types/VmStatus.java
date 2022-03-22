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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing a status of a virtual machine.
 *
 * @author Arik Hadas <ahadas@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public enum VmStatus {
    /**
     * This status is set when an invalid status is received.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    UNASSIGNED,

    /**
     * This status indicates that the virtual machine process is not running.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    DOWN,

    /**
     * This status indicates that the virtual machine process is running and the guest operating system is loaded.
     * Note that if no guest-agent is installed, this status is set after a predefined period of time, that is by
     * default 60 seconds, when running a virtual machine.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    UP,

    /**
     * This status indicates that the virtual machine process is running and the guest operating system is being loaded.
     * Note that if no guest-agent is installed, this status is set for a predefined period of time, that is by
     * default 60 seconds, when running a virtual machine.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    POWERING_UP,

    /**
     * This status indicates that the virtual machine process is running and the virtual machine is paused.
     * This may happen in two cases: when running a virtual machine is paused mode and when the virtual machine is being
     * automatically paused due to an error.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    PAUSED,

    /**
     * This status indicates that the virtual machine process is running and the virtual machine is being migrated from
     * one host to another.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    MIGRATING,

    /**
     * This status indicates that the system failed to determine the status of the virtual machine.
     * The virtual machine process may be running or not running in this status.
     * For instance, when host becomes non-responsive the virtual machines that ran on it are set with this status.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    UNKNOWN,

    /**
     * This status indicates that the hypervisor detected that the virtual machine is not responding.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    NOT_RESPONDING,

    /**
     * This status indicates that the virtual machine process is about to run.
     * This status is set when a request to run a virtual machine arrives to the host.
     * It is possible that the virtual machine process will fail to run.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    WAIT_FOR_LAUNCH,

    /**
     * This status indicates that the virtual machine process is running and the guest operating system is being
     * rebooted.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    REBOOT_IN_PROGRESS,

    /**
     * This status indicates that the virtual machine process is running and the virtual machine is being hibernated.
     * In this status, the running state of the virtual machine is being saved.
     * Note that this status does not mean that the guest operating system is being hibernated.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    SAVING_STATE,

    /**
     * This status indicates that the virtual machine process is about to run and the virtual machine is going to awake
     * from hibernation.
     * In this status, the running state of the virtual machine is being restored.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    RESTORING_STATE,

    /**
     * This status indicates that the virtual machine process is not running and a running state of the virtual machine
     * was saved.
     * This status is similar to Down, but when the VM is started in this status its saved running state is restored
     * instead of being booted using the normal procedue.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    SUSPENDED,

    /**
     * This status indicates that the virtual machine process is not running and there is some operation on the disks of
     * the virtual machine that prevents it from being started.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    IMAGE_LOCKED,

    /**
     * This status indicates that the virtual machine process is running and it is about to stop running.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    POWERING_DOWN;
}
