/*
The oVirt Project - oVirt Engine Api Model

Copyright oVirt Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the Apache License, Version 2.0 is included with the program
in the file ASL2.
*/
package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents the type of the snapshot.
 *
 * @author Tomas Jelinek <tjelinek@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public enum SnapshotType {
    /**
     * Snapshot created by user.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    REGULAR,

    /**
     * Reference to the current configuration of the virtual machines.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    ACTIVE,

    /**
     * Snapshot created internally for stateless virtual machines.
     *
     * This snapshot is created when the virtual machine is started and it is restored when the virtual machine is shut
     * down.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    STATELESS,

    /**
     * The `active` snapshot will become `preview` if some snapshot is being previewed.
     *
     * In other words, this is the `active` snapshot before preview.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    PREVIEW;
}
