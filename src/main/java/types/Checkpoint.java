/*
Copyright (c) 2020 Red Hat, Inc.

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

import java.util.Date;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

@Type
public interface Checkpoint extends Identified {
    /**
     * The parent checkpoint id.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 3 Jun 2020
     * @status added
     * @since 4.4
     */
    String parentId();

    /**
     * The checkpoint creation date.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 3 Jun 2020
     * @status added
     * @since 4.4
     */
    Date creationDate();

    /**
     * The state of the checkpoint.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 24 May 2021
     * @status added
     * @since 4.4.7
     */
    CheckpointState state();

    /**
     * A reference to the virtual machine associated with the checkpoint.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 3 Jun 2020
     * @status added
     * @since 4.4
     */
    @Link Vm vm();

    /**
     * A list of disks contained in the backup checkpoint.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 3 Jun 2020
     * @status added
     * @since 4.4
     */
    @Link Disk[] disks();
}
