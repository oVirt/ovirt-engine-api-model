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

import mixins.Follow;
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
 * E.g., for uploading/downloading a disk with id `52cb593f-837c-4633-a444-35a0a0383706`:
 *
 * *Using oVirt's Python's SDK:*
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
 * If the user wishes to download a disk rather than upload, he/she should specify
 * `download` as the <<types/image_transfer_direction, direction>> attribute of the transfer.
 * This will grant a read permission from the image, instead of a write permission.
 *
 * E.g:
 *
 * [source,python]
 * ----
 * transfers_service = system_service.image_transfers_service()
 * transfer = transfers_service.add(
 *    types.ImageTransfer(
 *       image=types.Image(
 *          id='52cb593f-837c-4633-a444-35a0a0383706'
 *       ),
 *       direction=types.ImageTransferDirection.DOWNLOAD
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
 * For example, Python's HTTPSConnection can be used in order to perform a transfer,
 * so an `transfer_headers` dict is set for the upcoming transfer:
 *
 * [source,python]
 * ----
 * transfer_headers = {
 *    'Authorization' :  transfer.signed_ticket,
 * }
 * ----
 *
 * Using Python's `HTTPSConnection`, a new connection is established:
 *
 * [source,python]
 * ----
 * # Extract the URI, port, and path from the transfer's proxy_url.
 * url = urlparse.urlparse(transfer.proxy_url)
 *
 * # Create a new instance of the connection.
 * proxy_connection = HTTPSConnection(
 *    url.hostname,
 *    url.port,
 *    context=ssl.SSLContext(ssl.PROTOCOL_SSLv23)
 * )
 * ----
 *
 * For upload, the specific content range being sent must be noted in the `Content-Range` HTTPS
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
 *       transfer_headers['Content-Range'] = "bytes %d-%d/%d" % (pos, min(pos + chunk_size, size)-1, size)
 *       proxy_connection.request(
 *          'PUT',
 *          url.path,
 *          disk.read(chunk_size),
 *          headers=transfer_headers
 *       )
 *       r = proxy_connection.getresponse()
 *       print r.status, r.reason, "Completed", "{:.0%}".format(pos/ float(size))
 *       pos += chunk_size
 * ----
 *
 * Similarly, for a download transfer, a `Range` header must be sent, making the download process
 * more easily managed by downloading the disk in chunks.
 *
 * E.g., the client will again iterate on chunks of the disk image, but this time he/she will download
 * it to a local file, rather than uploading its own file to the image:
 *
 * [source,python]
 * ----
 * output_file = "/home/user/downloaded_image"
 * MiB_per_request = 32
 * chunk_size = 1024*1024*MiB_per_request
 * total = disk_size
 *
 * with open(output_file, "wb") as disk:
 *    pos = 0
 *    while pos < total:
 *       transfer_service.extend()
 *       transfer_headers['Range'] = "bytes=%d-%d" %  (pos, min(total, pos + chunk_size) - 1)
 *       proxy_connection.request('GET', proxy_url.path, headers=transfer_headers)
 *       r = proxy_connection.getresponse()
 *       disk.write(r.read())
 *       print "Completed", "{:.0%}".format(pos/ float(total))
 *       pos += chunk_size
 *
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
 *
 * *Using HTTP and cURL calls:*
 *
 * - For upload, create a new disk first:
 * * Specify 'initial_size' and 'provisioned_size' in bytes.
 * * 'initial_size' must be bigger or the same as the size of the uploaded data.
 *
 * [source]
 * ----
 * POST /ovirt-engine/api/disks
 * ----
 *
 * With a request body as follows:
 *
 * [source,xml]
 * ----
 * <disk>
 *   <storage_domains>
 *     <storage_domain id="123"/>
 *   </storage_domains>
 *   <alias>mydisk</alias>
 *   <initial_size>1073741824</initial_size>
 *   <provisioned_size>1073741824</provisioned_size>
 *   <format>raw</format>
 * </disk>
 * ----
 *
 *
 * - Create a new image transfer for downloading/uploading a `disk` with id `456`:
 *
 *
 * [source]
 * ----
 * POST /ovirt-engine/api/imagetransfers
 * ----
 *
 * With a request body as follows:
 *
 * [source,xml]
 * ----
 * <image_transfer>
 *   <disk id="456"/>
 *   <direction>upload|download</direction>
 * </image_transfer>
 * ----
 *
 * Will respond:
 *
 * [source,xml]
 * ----
 * <image_transfer id="123">
 *   <direction>download|upload</direction>
 *   <phase>initializing|transferring</phase>
 *   <proxy_url>https://proxy_fqdn:54323/images/41c732d4-2210-4e7b-9e5c-4e2805baadbb</proxy_url>
 *   <transfer_url>https://daemon_fqdn:54322/images/41c732d4-2210-4e7b-9e5c-4e2805baadbb</transfer_url>
 *   ...
 * </image_transfer>
 * ----
 *
 * Note: If the phase is 'initializing', poll the `image_transfer` till its phase changes to 'transferring'.
 *
 * - Use the 'transfer_url' or 'proxy_url' to invoke a curl command:
 * - use 'transfer_url' for transferring directly from/to ovirt-imageio-daemon,
 *   or, use 'proxy_url' for transferring from/to ovirt-imageio-proxy.
 *   Note: using the proxy would mitigate scenarios where there's no direct connectivity
 *   to the daemon machine, e.g. vdsm machines are on a different network than the engine.
 *
 * -- Download:
 *
 * [source,shell]
 * ----
 * $ curl --cacert /etc/pki/ovirt-engine/ca.pem https://daemon_fqdn:54322/images/41c732d4-2210-4e7b-9e5c-4e2805baadbb -o <output_file>
 * ----
 *
 * -- Upload:
 *
 * [source,shell]
 * ----
 * $ curl --cacert /etc/pki/ovirt-engine/ca.pem --upload-file <file_to_upload> -X PUT https://daemon_fqdn:54322/images/41c732d4-2210-4e7b-9e5c-4e2805baadbb
 * ----
 *
 * - Finalize the image transfer by invoking the action:
 *
 * [source]
 * ----
 * POST /ovirt-engine/api/imagetransfers/123/finalize
 * ----
 *
 * With a request body as follows:
 *
 * [source,xml]
 * ----
 * <action />
 * ----
 *
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
    interface Get extends Follow {
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
     * Cancel the image transfer session. This terminates the transfer operation and removes the partial image.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @author Billy Burmester <bburmest@redhat.com>
     * @date 28 Mar 2018
     * @status update_by_docs
     * @since 4.2.3
     */
    interface Cancel {}

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
