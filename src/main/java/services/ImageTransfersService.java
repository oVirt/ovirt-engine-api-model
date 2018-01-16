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

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.ImageTransfer;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

/**
 * This service manages image transfers, for performing Image I/O API in {product-name}.
 * Please refer to <<services/image_transfer, image transfer>> for further
 * documentation.
 *
 * @author Amit Aviram <aaviram@redhat.com>
 * @author Megan Lewis <melewis@redhat.com>
 * @date 13 Sep 2017
 * @status updated_by_docs
 * @since 4.0.4
 */
@Service
@Area("Storage")
public interface ImageTransfersService {
    /**
     * Add a new image transfer. An image, disk or disk snapshot needs to be specified
     * in order to make a new transfer.
     *
     * IMPORTANT: The `image` attribute is deprecated since version 4.2 of the engine.
     * Use the `disk` or `snapshot` attributes instead.
     *
     * *Creating a new image transfer for downloading or uploading a `disk`:*
     *
     * To create an image transfer to download or upload a disk with id `123`,
     * send the following request:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/imagetransfers
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <image_transfer>
     *   <disk id="123"/>
     *   <direction>upload|download</direction>
     * </image_transfer>
     * ----
     *
     *
     * *Creating a new image transfer for downloading or uploading a `disk_snapshot`:*
     *
     * To create an image transfer to download or upload a `disk_snapshot` with id `456`,
     * send the following request:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/imagetransfers
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <image_transfer>
     *   <snapshot id="456"/>
     *   <direction>download|upload</direction>
     * </image_transfer>
     * ----
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     * @since 4.0.4
     */
    interface Add {
        @In @Out ImageTransfer imageTransfer();

        @Deprecated
        interface ForImage extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(imageTransfer().image().id());
            }
        }

        /**
         * @since 4.2.0
         */
        interface ForDisk extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(imageTransfer().disk().id());
            }
        }

        /**
         * @since 4.2.0
         */
        interface ForSnapshot extends Add {
            @InputDetail
            default void inputDetail() {
                mandatory(imageTransfer().snapshot().id());
            }
        }
    }

    /**
     * Retrieves the list of image transfers that are currently
     * being performed.
     *
     * The order of the returned list of image transfers is not guaranteed.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     * @since 4.0.4
     */
    interface List extends Follow {
        @Out ImageTransfer[] ImageTransfer();
    }

    /**
     * Returns a reference to the service that manages an
     * specific image transfer.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 13 Sep 2017
     * @status updated_by_docs
     * @since 4.0.4
     */
    @Service ImageTransferService imageTransfer(String id);
}
