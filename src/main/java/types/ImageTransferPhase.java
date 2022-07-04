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
 * A list of possible phases for an xref:types-image_transfer[image transfer] entity. Each of these values
 * defines a specific point in a transfer flow.
 *
 * Please refer to xref:services-image_transfer[image transfer] for more
 * information.
 *
 * @author Amit Aviram <aaviram@redhat.com>
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 15 Nov 2016
 * @status updated_by_docs
 * @since 4.0.4
 */
@Type
public enum ImageTransferPhase {
    /**
     * An unknown phase. This will only be set in cases of unpredictable errors.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    UNKNOWN,

    /**
     * The initial phase of an image transfer. It is set while the transfer session is establishing.
     * Once the session is established, the phase will be changed to `transferring`
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    INITIALIZING,

    /**
     * The phase where the transfer session is open, and the client can input or output the desired image using the preferred
     * tools.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    TRANSFERRING,

    /**
     * The phase where the transfer has been resumed by the client calling
     * xref:services-image_transfer-methods-resume[resume]. Resuming starts a new session, and after calling it,
     * the phase will be changed to `transferring`, or `paused_system` in case of a failure.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    RESUMING,

    /**
     * This phase means the session timed out, or some other error occurred
     * with this transfer; for example ovirt-imageio-daemon is not running in the selected host.
     * To resume the session, the client should call xref:services-image_transfer-methods-resume[resume]. After
     * resuming, the phase will change to `resuming`.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    PAUSED_SYSTEM,

    /**
     * This phase is a result of a pause call by the user, using
     * xref:services-image_transfer-methods-pause[pause].
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    PAUSED_USER,

    /**
     * This phase will be set as a result of the user cancelling the transfer. The cancellation can only be performed
     * in the Administration Portal.
     *
     * @deprecated To better distinguish between transfers cancelled by system and by user, this field has been marked
     * as deprecated and might be removed in the next version of the API. Use 'CANCELLED_SYSTEM' or 'CANCELLED_USER' instead.
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    @Deprecated
    CANCELLED,

    /**
     * This phase will be set when the user calls xref:services-image_transfer-methods-finalize[finalize]. Calling
     * finalize is essential to finish the transfer session, and finish using the targeted image. After finalizing,
     * the phase will be changed to `finished_success` or `finished_failure`.
     *
     * Refer to xref:services-image_transfer[image transfer] for more information.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    FINALIZING_SUCCESS,

    /**
     * This phase can only be set in the Administration Portal, and indicates that there was an error during the transfer, and it
     * is being finalized with a failure.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 17 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    FINALIZING_FAILURE,

    /**
     * Indicates that the transfer session was successfully closed, and the targeted image was verified and ready to be
     * used. After reaching this phase, the image transfer entity will be deleted.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    FINISHED_SUCCESS,

    /**
     * Indicates that the targeted image failed the verification, and cannot be used. After reaching this phase,
     * the image transfer entity will be deleted, and the targeted image will be set to illegal. System cancelling the
     * transfer will also result in this.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     * @since 4.0.4
     */
    FINISHED_FAILURE,

    /**
     * This phase will be set as a result of the system cancelling the transfer.
     *
     * @author Fedor Gavrilov <fgavrilo@redhat.com>
     * @date 14 May 2019
     * @status added
     * @since 4.3.5
     */
    CANCELLED_SYSTEM,

    /**
     * This phase will be set as a result of the user cancelling the transfer.
     *
     * @author Fedor Gavrilov <fgavrilo@redhat.com>
     * @date 14 May 2019
     * @status added
     * @since 4.3.5
     */
    CANCELLED_USER,

    /**
     * This phase indicates that the user cancelled the transfer, and necessary cleanup is being done.
     *
     * @author Fedor Gavrilov <fgavrilo@redhat.com>
     * @date 14 May 2019
     * @status added
     * @since 4.3.5
     */
    FINALIZING_CLEANUP,

    /**
     * This phase indicates that the user cancelled the transfer, and necessary cleanup is done.
     *
     * @author Fedor Gavrilov <fgavrilo@redhat.com>
     * @date 14 May 2019
     * @status added
     * @since 4.3.5
     */
    FINISHED_CLEANUP
}
