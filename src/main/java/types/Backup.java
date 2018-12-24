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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

import java.util.Date;

@Type
public interface Backup extends Identified {
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
}
