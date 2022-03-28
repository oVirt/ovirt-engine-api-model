/*
Copyright (c) 2016 Red Hat, Inc.

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
 * The <<types/image_transfer, image transfer>> direction for a transfer.
 *
 * When adding a new transfer, the user can choose whether the transfer will be to an image, choosing `upload`,
 * or to transfer from an image- choosing `download` as an ImageTransferDirection.
 *
 * Please refer to <<services/image_transfer, image transfer>> for further
 * documentation.
 *
 * @author Amit Aviram <aaviram@redhat.com>
 * @date 7 Nov 2016
 * @status added
 * @since 4.1
 */
@Type
public enum ImageTransferDirection {
    /**
     * The user can choose `upload` when he/she wants to stream data to an image.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 7 Nov 2016
     * @status added
     * @since 4.1
     */
    UPLOAD,

    /**
     * The user must choose `download` when he/she wants to stream data from an image.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 7 Nov 2016
     * @status added
     * @since 4.1
     */
    DOWNLOAD
}
