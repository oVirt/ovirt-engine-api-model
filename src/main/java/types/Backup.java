/*
Copyright (c) 2019 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

import java.util.Date;

@Type
public interface Backup extends Identified {
    /**
     * The host that was used to start the backup.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 3 Dec 2020
     * @status added
     * @since 4.4.4
     */
    @Link Host host();

    /**
     * The checkpoint id at which to start the incremental backup.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 12 Dec 2018
     * @status added
     * @since 4.3
     */
    String fromCheckpointId();

    /**
     * The checkpoint id created by this backup operation.
     * This id can be used as the `fromCheckpointId` in the next incremental backup.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 12 Dec 2018
     * @status added
     * @since 4.3
     */
    String toCheckpointId();

    /**
     * The phase of the backup operation.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 12 Dec 2018
     * @status added
     * @since 4.3
     */
    BackupPhase phase();

    /**
     * The backup creation date.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 12 Dec 2018
     * @status added
     * @since 4.3
     */
    Date creationDate();

    /**
     * The backup modification date.
     *
     * @author Pavel Bar <pbar@redhat.com>
     * @date 28 Jul 2021
     * @status added
     * @since 4.4.8
     */
    Date modificationDate();

    /**
     * A reference to the virtual machine associated with the backup.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 12 Dec 2018
     * @status added
     * @since 4.3
     */
    @Link Vm vm();

    /**
     * A list of disks contained in the virtual machine backup.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 12 Dec 2018
     * @status added
     * @since 4.3
     */
    @Link Disk[] disks();

    /**
     * A reference to the snapshot created if the backup is using a snapshot.
     *
     * @author Benny Zlotnik
     * @date 10 Mar 2022
     * @status added
     * @since 4.5
     */
    @Link Snapshot snapshot();
}
