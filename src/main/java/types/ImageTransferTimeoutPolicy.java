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
 * The <<types/image_transfer, image transfer>> direction for a transfer.
 *
 * Timeout Policy will define the next action to do after the image transfer time out.
 *
 * Please refer to <<services/image_transfer, image transfer>> for further
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
     * The default behaviour will cancel the transfer if the direction is download, and puase it if its upload.
     *
     *
     * @author Ahmad Khiet <akhiet@redhat.com>
     * @date 25 Jan 2021
     * @status added
     * @since 4.4.5
     */
    LEGACY,

    /**
     * After the timeout is passed the image transfer will be paused.
     *
     * @author Ahmad Khiet <akhiet@redhat.com>
     * @date 25 Jan 2021
     * @status added
     * @since 4.4.5
     */
    PAUSE,

    /**
     * After the timeout is passed the image transfer will be canceld.
     *
     * @author Ahmad Khiet <akhiet@redhat.com>
     * @date 25 Jan 2021
     * @status added
     * @since 4.4.5
     */
    CANCEL
}
