package services;

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.EventSubscription;

/**
 * A service to manage a specific event-subscription in the system.
 *
 * @author Ori Liel <oliel@redhat.com>
 * @date 17 June 2019
 * @status added
 * @since 4.4.0
 */
@Service
@Area("Infrastructure")
public interface EventSubscriptionService {

    /**
     * Gets the information about the event-subscription.
     *
     * For example to retrieve the information about the subscription of user '123' to
     * the event 'vm_console_detected':
     *
     * ```http
     * GET /ovirt-engine/api/users/123/vm_console_detected HTTP/1.1
     * ```
     *
     * ```xml
     * <event-subscription href="/ovirt-engine/api/users/123/event-subscriptions/vm_console_detected">
     *   <event>vm_console_detected</event>
     *   <notification_method>smtp</notification_method>
     *   <user href="/ovirt-engine/api/users/123" id="123"/>
     *   <address>a@b.com</address>
     * </event-subscription>
     * ```
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 17 June 2019
     * @status added
     * @since 4.4.0
     */
    interface Get {
        /**
         * The event-subscription.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 17 June 2019
         * @status added
         * @since 4.4.0
         */
        @Out
        EventSubscription eventSubscription();
    }

    /**
     * Removes the event-subscription from the system.
     *
     * For example to remove user 123's subscription to `vm_console_detected` event:
     *
     * ```http
     * DELETE /ovirt-engine/api/users/123/vm_console_detected HTTP/1.1
     * ```
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 17 June 2019
     * @status added
     * @since 4.4.0
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 17 June 2019
         * @status added
         * @since 4.4.0
         */
        @In Boolean async();
    }
}
