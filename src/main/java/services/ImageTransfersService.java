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
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.ImageTransfer;

/**
 * This service manages image transfers, for performing Image I/O API in oVirt.
 * Please refer to <<services/image_transfer, image transfer>> for further
 * documentation.
 *
 * @author Amit Aviram <aaviram@redhat.com>
 * @date 30 Aug 2016
 * @status added
 * @since 4.0.4
 */
@Service
@Area("Storage")
public interface ImageTransfersService {
    /**
     * Add a new image transfer. An image needs to be specified in order to make
     * a new transfer.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    interface Add {
        @In @Out ImageTransfer imageTransfer();
    }

    /**
     * Retrieves the list of image transfers that are currently
     * being performed.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    interface List {
        @Out ImageTransfer[] ImageTransfer();
    }

    /**
     * Returns a reference to the service that manages an
     * specific image transfer.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    @Service ImageTransferService imageTransfer(String id);
}
