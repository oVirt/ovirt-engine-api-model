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

import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.ImageTransfer;
import annotations.Area;

/**
 * This service provides a mechanism to control an image transfer. The client will have
 * to create a transfer by using <<services/image_transfers/methods/add, add>>
 * of the <<services/image_transfers>> service, stating the image to transfer
 * data to/from.
 *
 * After doing that, the transfer is managed by this service.
 *
 * E.g., for uploading to the disk image with id `52cb593f-837c-4633-a444-35a0a0383706`,
 * the client can use oVirt's Python's SDK as follows:
 *
 * [source,python]
 * ----
 * transfers_service = system_service.image_transfers_service()
 * transfer = transfers_service.add(
 *    types.ImageTransfer(
 *       image=types.Image(
 *          id='52cb593f-837c-4633-a444-35a0a0383706'
 *       )
 *    )
 * )
 * ----
 *
 * Transfers have phases, which govern the flow of the upload/download.
 * A client implementing such a flow should poll/check the transfer's phase and
 * act accordingly. All the possible phases can be found in
 * <<types/image_transfer_phase, ImageTransferPhase>>.
 *
 * After adding a new transfer, its phase will be <<types/image_transfer_phase, initializing>>.
 * The client will have to poll on the transfer's phase until it changes.
 * When the phase becomes <<types/image_transfer_phase, transferring>>,
 * the session is ready to start the transfer.
 *
 * For example:
 *
 * [source,python]
 * ----
 * transfer_service = transfers_service.image_transfer_service(transfer.id)
 * while transfer.phase == types.ImageTransferPhase.INITIALIZING:
 *    time.sleep(3)
 *    transfer = transfer_service.get()
 * ----
 *
 * At that stage, if the transfer's phase is <<types/image_transfer_phase, paused_system>>, then the session was
 * not successfully established. One possible reason for that is that the ovirt-imageio-daemon is not running
 * in the host that was selected for transfer.
 * The transfer can be resumed by calling <<services/image_transfer/methods/resume, resume>>
 * of the service that manages it.
 *
 * If the session was successfully established - the returned transfer entity will
 * contain the <<types/image_transfer, proxy_url>> and <<types/image_transfer, signed_ticket>> attributes,
 * which the client needs to use in order to transfer the required data. The client can choose whatever
 * technique and tool for sending the HTTPS request with the image's data.
 *
 * - `proxy_url` is the address of a proxy server to the image, to do I/O to.
 * - `signed_ticket` is the content that needs to be added to the `Authentication`
 *    header in the HTTPS request, in order to perform a trusted communication.
 *
 * For example, Python's HTTPSConnection can be used in order to perform an upload,
 * so an `upload_headers` dict is set for the upcoming upload:
 *
 * [source,python]
 * ----
 * upload_headers = {
 *    'Authorization' :  transfer.signed_ticket,
 * }
 * ----
 *
 * Using Python's `HTTPSConnection`, a new connection is established:
 *
 * [source,python]
 * ----
 * # Extract the URI, port, and path from the transfer's proxy_url.
 * url = urlparse(transfer.proxy_url)
 *
 * # Create a new instance of the connection.
 * proxy_connection = HTTPSConnection(
 *    url.hostname,
 *    url.port,
 *    context=ssl.SSLContext(ssl.PROTOCOL_SSLv23)
 * )
 * ----
 *
 * The specific content range being sent must be noted in the `Content-Range` HTTPS
 * header. This can be used in order to split the transfer into several requests for
 * a more flexible process.
 *
 * For doing that, the client will have to repeatedly extend the transfer session
 * to keep the channel open. Otherwise, the session will terminate and the transfer will
 * get into `paused_system` phase, and HTTPS requests to the server will be rejected.
 *
 * E.g., the client can iterate on chunks of the file, and send them to the
 * proxy server while asking the service to extend the session:
 *
 * [source,python]
 * ----
 * path = "/path/to/image"
 * MB_per_request = 32
 * with open(path, "rb") as disk:
 *    size = os.path.getsize(path)
 *    chunk_size = 1024*1024*MB_per_request
 *    pos = 0
 *    while (pos < size):
 *       transfer_service.extend()
 *       upload_headers['Content-Range'] = "bytes %d-%d/%d" % (pos, min(pos + chunk_size, size)-1, size)
 *       proxy_connection.request(
 *          'PUT',
 *          url.path,
 *          disk.read(chunk_size),
 *          headers=upload_headers
 *       )
 *       r = proxy_connection.getresponse()
 *       print r.status, r.reason, "Completed", "{:.0%}".format(pos/ float(size))
 *       pos += chunk_size
 * ----
 *
 * When finishing the transfer, the user should call
 * <<services/image_transfer/methods/finalize, finalize>>. This will make the
 * final adjustments and verifications for finishing the transfer process.
 *
 * For example:
 *
 * [source,python]
 * ----
 * transfer_service.finalize()
 * ----
 *
 * In case of an error, the transfer's phase will be changed to
 * <<types/image_transfer_phase, finished_failure>>, and
 * the disk's status will be changed to `Illegal`. Otherwise it will be changed to
 * <<types/image_transfer_phase, finished_success>>, and the disk will be ready
 * to be used. In both cases, the transfer entity will be removed shortly after.
 *
 * @author Amit Aviram <aaviram@redhat.com>
 * @date 30 Aug 2016
 * @status added
 * @since 4.0.4
 */
@Service
@Area("Storage")
public interface ImageTransferService {
    /**
     * Get the image transfer entity.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    interface Get {
        @Out ImageTransfer imageTransfer();
    }

    /**
     * Extend the image transfer session.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    interface Extend {}

    /**
     * Pause the image transfer session.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    interface Pause {}

    /**
     * Resume the image transfer session. The client will need to poll the transfer's phase until
     * it is different than `resuming`. For example:
     *
     * [source,python]
     * ----
     * transfer_service = transfers_service.image_transfer_service(transfer.id)
     * transfer_service.resume()
     * transfer = transfer_service.get()
     *
     * while transfer.phase == types.ImageTransferPhase.RESUMING:
     *    time.sleep(1)
     *    transfer = transfer_service.get()
     * ----
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    interface Resume {}

    /**
     * After finishing to transfer the data, finalize the transfer.
     *
     * This will make sure that the data being transferred is valid and fits the
     * image entity that was targeted in the transfer. Specifically, will verify that
     * if the image entity is a QCOW disk, the data uploaded is indeed a QCOW file,
     * and that the image doesn't have a backing file.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    interface Finalize {}
}
