/*
Copyright (c) 2016 Red Hat, Inc.

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
 * The underlying storage format of disks.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 4 Nov 2016
 * @status added
 */
@Type
public enum DiskFormat {
    /**
     * The _Copy On Write_ format allows snapshots, with a small performance overhead.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    COW,

    /**
     * The raw format does not allow snapshots, but offers improved performance.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 4 Nov 2016
     * @status added
     */
    RAW;
}
