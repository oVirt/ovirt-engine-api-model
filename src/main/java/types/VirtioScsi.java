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
 * Type representing the support of virtio-SCSI.
 * If it supported we use virtio driver for SCSI guest device.
 *
 * @author Shahar Havivi <shavivi@redhat.com>
 * @date 12 Dec 2016
 * @status added
 *
 */
@Type
public interface VirtioScsi {
    /**
     * Enable Virtio SCSI support.
     *
     * @author Shahar Havivi <shavivi@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean enabled();
}
