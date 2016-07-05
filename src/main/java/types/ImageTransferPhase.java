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
 * A list of possible phases for an <<types/image_transfer, image transfer>> entity. Each of these values
 * defines a specific point in a transfer flow.
 *
 * Please refer to <<services/image_transfer, image transfer>> for further
 * documentation.
 *
 * @author Amit Aviram <aaviram@redhat.com>
 * @date 30 Aug 2016
 * @status added
 * @since 4.0.4
 */
@Type
public enum ImageTransferPhase {
    /**
     * An unknown phase, will only be set in cases of unpredictable errors.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    UNKNOWN,

    /**
     * The initial phase of an added image transfer. It is set as long as the transfer session is establishing.
     * Once the session is established, the phase will be changed to `transferring`
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    INITIALIZING,

    /**
     * The phase where the transfer session is open, and the client can do I/O to the desired image using its preferred
     * tools.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    TRANSFERRING,

    /**
     * The phase where the transfer had been resumed by the client calling
     * <<services/image_transfer/methods/resume, resume>>. Resuming will start a new session, and after calling it,
     * the phase should be changed to `transferring`, or `paused_system` in case of a failure.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    RESUMING,

    /**
     * This phase stands for a situation in which the session was timed out, or if some other error occurred
     * with this transfer, e.g, ovirt-imageio-daemon is not running in the selected host for transferring. For trying
     * to resume the session, the client should call <<services/image_transfer/methods/resume, resume>>. After
     * resuming, the phase will change to `resuming`
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    PAUSED_SYSTEM,

    /**
     * This phase will be set as a result of an active pause call by the user, using
     * <<services/image_transfer/methods/pause, pause>>.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    PAUSED_USER,

    /**
     * This phase will be set as a result of the user actively cancelling the transfer, which can only be performed
     * via the webadmin.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    CANCELLED,

    /**
     * This phase will be set when the user calls <<services/image_transfer/methods/finalize, finalize>>. Calling
     * finalize is essential in order to finish the transfer session and using the targeted image. After finalizing,
     * the phase will be changed to `finished_success` or `finished_failure`.
     *
     * Please refer to <<services/image_transfer, image transfer>> for further documentation.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    FINALIZING_SUCCESS,

    /**
     * This phase can only be set by oVirt webadmin, and states that there was some error during the transfer and it
     * is being finalized with failure.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    FINALIZING_FAILURE,

    /**
     * States that the transfer session was successfully closed, and the targeted image was verified and ready to be
     * used. After reaching this phase, the image transfer entity will soon be deleted.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    FINISHED_SUCCESS,

    /**
     * States that the targeted image failed the verification, and cannot be used. After reaching this phase,
     * the image transfer entity will soon be deleted, and the targeted image will be set to illegal.
     *
     * @author Amit Aviram <aaviram@redhat.com>
     * @date 30 Aug 2016
     * @status added
     * @since 4.0.4
     */
    FINISHED_FAILURE;
}
