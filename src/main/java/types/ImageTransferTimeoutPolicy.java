/*
Copyright (c) 2021 Red Hat, Inc.

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
 * The xref:types/image_transfer[image transfer] timeout policy.
 *
 * Define how the system handles a transfer when the client is inactive
 * for inactivityTimeout seconds.
 *
 * Please refer to xref:services/image_transfer[image transfer] for further
 * documentation.
 *
 * @author Ahmad Khiet <akhiet@redhat.com>
 * @date 25 Jan 2021
 * @status added
 * @since 4.4.5
 */
@Type
public enum ImageTransferTimeoutPolicy {
    /**
     * LEGACY policy will preserve the legacy functionality which is the default.
     * The default behaviour will cancel the transfer if the direction is download,
     * and pause it if its upload.
     *
     *
     * @author Ahmad Khiet <akhiet@redhat.com>
     * @date 25 Jan 2021
     * @status added
     * @since 4.4.5
     */
    LEGACY,

    /**
     * Pause the transfer. The transfer can be resumed or canceled by the user.
     * The disk will remain locked while the transfer is paused.
     *
     * @author Ahmad Khiet <akhiet@redhat.com>
     * @date 25 Jan 2021
     * @status added
     * @since 4.4.5
     */
    PAUSE,

    /**
     * Cancel the transfer and unlock the disk. For image transfer using upload
     * direction, the disk is deleted.
     *
     * @author Ahmad Khiet <akhiet@redhat.com>
     * @date 25 Jan 2021
     * @status added
     * @since 4.4.5
     */
    CANCEL
}
