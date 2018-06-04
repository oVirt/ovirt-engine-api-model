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
     * The signed ticket that should be attached as an `Authentication` header in the
     * HTTPS request for the proxy server to input or output to (See the `proxy_url` attribute).
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    String signedTicket();

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
}
