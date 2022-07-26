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

package services;

import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.ImageTransfer;
import annotations.Area;

/**
 * This service provides a mechanism to control an image transfer. The client will have
 * to create a transfer by using xref:services-image_transfers-methods-add[add]
 * of the xref:services-image_transfers[image transfers] service, stating the image to transfer
 * data to/from.
 *
 * After doing that, the transfer is managed by this service.
 *
 * *Using oVirt's Python's SDK:*
 *
 * Uploading a `disk` with id `123` (on a random host in the data center):
 *
 * [source,python]
 * ----
 * transfers_service = system_service.image_transfers_service()
 * transfer = transfers_service.add(
 *    types.ImageTransfer(
 *       disk=types.Disk(
 *          id='123'
 *       )
 *    )
 * )
 * ----
 *
 * Uploading a `disk` with id `123` on `host` id `456`:
 *
 * [source,python]
 * ----
 * transfers_service = system_service.image_transfers_service()
 * transfer = transfers_service.add(
 *    types.ImageTransfer(
 *       disk=types.Disk(
 *          id='123'
 *       ),
 *       host=types.Host(
 *          id='456'
 *      )
 *    )
 * )
 * ----
 *
 * If the user wishes to download a disk rather than upload, he/she should specify
 * `download` as the xref:types-image_transfer_direction[direction] attribute of the transfer.
 * This will grant a read permission from the image, instead of a write permission.
 *
 * E.g:
 *
 * [source,python]
 * ----
 * transfers_service = system_service.image_transfers_service()
 * transfer = transfers_service.add(
 *    types.ImageTransfer(
 *       disk=types.Disk(
 *          id='123'
 *       ),
 *       direction=types.ImageTransferDirection.DOWNLOAD
 *    )
 * )
 * ----
 *
 * Transfers have phases, which govern the flow of the upload/download.
 * A client implementing such a flow should poll/check the transfer's phase and
 * act accordingly. All the possible phases can be found in
 * xref:types-image_transfer_phase[ImageTransferPhase].
 *
 * After adding a new transfer, its phase will be xref:types-image_transfer_phase[initializing].
 * The client will have to poll on the transfer's phase until it changes.
 * When the phase becomes xref:types-image_transfer_phase[transferring],
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
 * At that stage, if the transfer's phase is xref:types-image_transfer_phase[paused_system], then the session was
 * not successfully established. One possible reason for that is that the ovirt-imageio is not running
 * in the host that was selected for transfer.
 * @author Donna DaCosta <ddacosta@redhat.com>
 * @date 26 July 2022
 * @status updated_by_docs
 * @since 4.5.1
 * The transfer can be resumed by calling xref:services-image_transfer-methods-resume[resume]
 * of the service that manages it.
 *
 * If the session was successfully established - the returned transfer entity will
 * contain the xref:types-image_transfer[transfer_url] and xref:types-image_transfer[proxy_url] attributes,
 * which the client needs to use in order to transfer the required data. The client can choose whatever
 * technique and tool for sending the HTTPS request with the image's data.
 *
 * - `transfer_url` is the address of an imageio server running on one of the hypervisors.
 * - `proxy_url` is the address of an imageio proxy server that can be used if
 *   you cannot access transfer_url.
 *
 * To transfer the image, it is recommended to use the imageio client python library.
 *
 * [source,python]
 * ----
 * from ovirt_imageio import client
 *
 * # Upload qcow2 image to virtual disk:
 * client.upload("disk.qcow2", transfer.transfer_url)
 *
 * # Download virtual disk to qcow2 image:
 * client.download(transfer.transfer_url, "disk.qcow2")
 * ----
 *
 * You can also upload and download using imageio REST API. For more info
 * on this, see imageio API documentation:
 *
 *     http://ovirt.github.io/ovirt-imageio/images.html
 *
 * When finishing the transfer, the user should call
 * xref:services-image_transfer-methods-finalize[finalize]. This will make the
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
 * xref:types-image_transfer_phase[finished_failure], and
 * the disk's status will be changed to `Illegal`. Otherwise it will be changed to
 * xref:types-image_transfer_phase[finished_success], and the disk will be ready
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
 * - use 'transfer_url' for transferring directly from/to ovirt-imageio,
 *   or, use 'proxy_url' for transferring from/to ovirt-imageio.
 *   Note: using the proxy would mitigate scenarios where there's no direct connectivity
 *   to the daemon machine, e.g. vdsm machines are on a different network than the engine.
 * @author Donna DaCosta <ddacosta@redhat.com>
 * @date 26 July 2022
 * @status updated_by_docs
 * @since 4.5.1
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
 * @author Eli Marcus <emarcus@redhat.com>
 * @date 24 Aug 2020
 * @status updated_by_docs
 * @since 4.0.4
 */
@Service
@Area("Storage")
public interface ImageTransferService {
    /**
     * Get the image transfer entity.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Billy Burmester <bburmest@redhat.com>
     * @date 12 Jun 2018
     * @status updated_by_docs
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
