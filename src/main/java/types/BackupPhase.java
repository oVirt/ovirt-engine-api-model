/*
Copyright (c) 2019 Red Hat, Inc.

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

@Type
public enum BackupPhase {
    /**
     * The initial phase of the backup. It is set on entity creation.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 11 Dec 2018
     * @status added
     * @since 4.3
     */
    INITIALIZING,

    /**
     * The phase is set before invoking 'start_backup' operation in vdsm/libvirt
     * (which means that 'stop_backup' should be invoked to complete the flow).
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 11 Dec 2018
     * @status added
     * @since 4.3
     */
    STARTING,

    /**
     * The phase means that the relevant disks' backup URLs are ready to be used and downloaded
     * using image transfer.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 11 Dec 2018
     * @status added
     * @since 4.3
     */
    READY,

    /**
     * In this phase, the backup is invoking 'stop_backup' operation in order to complete
     * the backup and unlock the relevant disk.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 11 Dec 2018
     * @status added
     * @since 4.3
     */
    FINALIZING
}
