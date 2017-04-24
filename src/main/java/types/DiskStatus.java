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
 * Current status representation for disk.
 *
 * @author Shahar Havivi <shavivi@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public enum DiskStatus {

    /**
     * Disk cannot be accessed by the virtual machine, and the user needs
     * to take action to resolve the issue.
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    ILLEGAL,

    /**
     * The disk is being used by the system, therefore it cannot be accessed
     * by virtual machines at this point. This is usually a temporary status,
     * until the disk is freed.
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    LOCKED,

    /**
     * The disk status is normal and can be accessed by the virtual machine.
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    OK;
}
