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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * This type contains information regarding an image transfer being performed.
 *
 * @author Amit Aviram <aaviram@redhat.com>
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 15 Nov 2016
 * @status updated_by_docs
 * @since 4.0.4
 */
@Type
public interface ImageTransfer extends Identified {

    /**
     * The URL of the proxy server that the user inputs or outputs to. This attribute is
     * available only if the image transfer is in the <<types/image_transfer_phase, transferring>>
     * phase. See `phase` for details.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    String proxyUrl();

    /**
     * The URL of the daemon server that the user can input or output to directly.
     *
     * This is as an alternative to the `proxy_url`. I.e. if the client has access to the host machine, it could bypass
     * the proxy and transfer directly to the host, potentially improving the throughput performance. This attribute is
     * available only if the image transfer is in the <<types/image_transfer_phase, transferring>>
     * phase. See `phase` for details.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 13 Nov 2017
     * @status added
     * @since 4.2.0
     */
    String transferUrl();

    /**
     * The current phase of the image transfer in progress. Each transfer needs a managed
     * session, which must be opened for the user to input or output an image.
     * Please refer to <<services/image_transfer, image transfer>> for further
     * documentation.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    ImageTransferPhase phase();

    /**
     * The direction indicates whether the transfer is sending image data (`upload`) or
     * receiving image data (`download`).
     *
     * If a direction is not set during an addition of a new transfer,
     * The default direction for the transfer will be `upload`.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.1
     */
    ImageTransferDirection direction();

    /**
     * Indicates whether there's at least one active session for this transfer, i,e
     * there's at least one live transfer session between the client and the daemon.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 8 Nov 2017
     * @status added
     * @since 4.2
     */
    Boolean active();

    /**
     * Indicates the amount of transferred bytes.
     *
     * @author Idan Shaby <ishaby@redhat.com>
     * @date 8 Nov 2017
     * @status added
     * @since 4.2
     */
    Integer transferred();

    /**
     * The timeout in seconds of client inactivity, after which the transfer is aborted by the {engine-name}.
     * To disable the inactivity timeout specify '0'. If not specified, the value is defaulted to the
     * `engine-config` value: TransferImageClientInactivityTimeoutInSeconds.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @author Avital Pinnick <apinnick@redhat.com>
     * @date 12 Apr 2018
     * @status updated_by_docs
     * @since 4.2.3
     */
    Integer inactivityTimeout();

    /**
     * Timeout policy describes the action to be made after the inactivity timeout is passed.
     * In case the timeout policy is ImageTransferTimeoutPolicy.CANCEL, the system will cancel the transfer and unlock the disk.
     * ImageTransferTimeoutPolicy.LEGACY will perform the expected legacy behaviour, which will cancel the transfer if the direction
     * is download, and puase it if its upload.
     * In case the transfer policy is ImageTransferTimeoutPolicy.PAUSE, then, the transfer will be paused after the a timeout.
     *
     * @author Ahmad Khiet <akhiet@redhat.com>
     * @date 21 Jan 2021
     * @status added
     * @since 4.4.5
     */
    ImageTransferTimeoutPolicy timeoutPolicy();

    /**
     * The image which is targeted for input or output.
     *
     * IMPORTANT: This attribute is deprecated since version
     * 4.2 of the engine. Use the `disk` or `snapshot`
     * attributes instead.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    @Deprecated
    @Link Image image();

    /**
     * The disk which is targeted for input or output.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 15 Aug 2017
     * @since 4.2.0
     * @status added
     */
    @Link Disk disk();

    /**
     * The disk snapshot which is targeted for input or output.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 15 Aug 2017
     * @status added
     * @since 4.2.0
     */
    @Link DiskSnapshot snapshot();

    /**
     * The host which will be used to write to the image which is targeted for input or output.
     * If not specified, an active host will be randomly selected from the data center.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    @Link Host host();

    /**
     * The backup associated with the image transfer.
     * Specify when initiating an image transfer for a disk that is part of a backup.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 14 Jan 2019
     * @status added
     * @since 4.3
     */
    @Link Backup backup();

    /**
     * The format of the data sent during upload or received during download.
     * If not specified, defaults to disk's format.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 14 Jan 2019
     * @status added
     * @since 4.3
     */
    DiskFormat format();

    /**
     * Download only the specified image instead of the entire image chain.
     *
     * If true, when using format="raw" and direction="download", the transfer
     * includes data only from the specified disk snapshot, and unallocated
     * areas are reported as holes.  By default, the transfer includes data
     * from all disk snapshots.
     *
     * When specifying a disk snapshot, the transfer includes only data for the
     * specified disk snapshot. When specifying a disk, the transfer includes
     * only data from the active disk snaphost.
     *
     * This parameter has no effect when not using format="raw" or for
     * direction="upload".
     *
     * Example: Downloading a single snapshot:
     *
     * [source,xml]
     * ----
     * <image_transfer>
     *   <snapshot id="2fb24fa2-a5db-446b-b733-4654661cd56d"/>
     *   <direction>download</direction>
     *   <format>raw</format>
     *   <shallow>true</shallow>
     * </image_transfer>
     * ----
     *
     * To download the active snapshot disk image (which is not accessible
     * as a disk snapshot), specify the disk:
     *
     * [source,xml]
     * ----
     * <image_transfer>
     *   <disk id="ff6be46d-ef5d-41d6-835c-4a68e8956b00"/>
     *   <direction>download</direction>
     *   <format>raw</format>
     *   <shallow>true</shallow>
     * </image_transfer>
     * ----
     *
     * In both cases you can now download a qcow2 image using imageio client:
     *
     * [source,python]
     * ----
     * from ovirt_imageio import client
     *
     * client.download(
     *   transfer.transfer_url,
     *   "51275e7d-42e9-491f-9d65-b9211c897eac",
     *   backing_file="07c0ccac-0845-4665-9097-d0a3b16cf43b",
     *   backing_format="qcow2")
     * ----
     *
     * @author Nir Soffer <nsoffer@redhat.com>
     * @date 15 Sep 2020
     * @status added
     * @since 4.4.3
     */
    Boolean shallow();
}
