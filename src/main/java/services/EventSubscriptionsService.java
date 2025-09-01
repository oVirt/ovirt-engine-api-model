package services;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import mixins.Follow;
import types.EventSubscription;

/**
 * Represents a service to manage collection of event-subscription of a user.
 *
 * @author Ori Liel <oliel@redhat.com>
 * @date 17 June 2019
 * @status added
 * @since 4.4.0
 */
@Service
@Area("Infrastructure")
public interface EventSubscriptionsService {

    /**
     * Add a new event-subscription to the system.
     *
     * An event-subscription is always added in the context of a user. For example, to add new
     * event-subscription for `host_high_cpu_use` for user `123`, and have the notification
     * sent to the e-mail address: `a@b.com`, send a request like this:
     *
     * ```http
     * POST /ovirt-engine/api/users/123/eventsubscriptions HTTP/1.1
     * ```
     *
     * With a request body like this:
     *
     * ```xml
     * <event_subscription>
     *     <event>host_high_cpu_use</event>
     *     <address>a@b.com</address>
     * </event_subscription>
     * ```
     *
     * The event name will become the ID of the new event-subscription entity:
     * GET .../api/users/123/eventsubscriptions/host_high_cpu_use
     *
     * Note that no user id is provided in the request body. This is because the user-id (in this case 123)
     * is already known to the API from the context. Note also that event-subscription entity contains
     * notification-method field, but it is not provided either in the request body. This is because currently
     * it's always set to SMTP as SNMP notifications are still unsupported by the API layer.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 17 June 2019
     * @status added
     * @since 4.4.0
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(eventSubscription().event());
            // notification-method is optional. If not provided the default will be SMTP.
            optional(eventSubscription().notificationMethod());
        }

        /**
         * The added event-subscription.
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 17 June 2019
         * @status added
         * @since 4.4.0
         */
        @In @Out EventSubscription eventSubscription();
    }


    /**
     * List the event-subscriptions for the provided user.
     *
     * For example to list event-subscriptions for user `123`:
     *
     * ```http
     * GET /ovirt-engine/api/users/123/event-subscriptions HTTP/1.1
     * ```
     *
     * ```xml
     * <event-subscriptions>
     *   <event-subscription href="/ovirt-engine/api/users/123/event-subscriptions/host_install_failed">
     *     <event>host_install_failed</event>
     *     <notification_method>smtp</notification_method>
     *     <user href="/ovirt-engine/api/users/123" id="123"/>
     *     <address>a@b.com</address>
     *   </event-subscription>
     *   <event-subscription href="/ovirt-engine/api/users/123/event-subscriptions/vm_paused">
     *     <event>vm_paused</event>
     *     <notification_method>smtp</notification_method>
     *     <user href="/ovirt-engine/api/users/123" id="123"/>
     *     <address>a@b.com</address>
     *   </event-subscription>
     * </event-subscriptions>
     * ```
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 17 June 2019
     * @status added
     */
    interface List extends Follow {
        /**
         * List of the event-subscriptions for the specified user
         *
         * @author Ori Liel <oliel@redhat.com>
         * @date 17 June 2019
         * @status added
         * @since 4.4.0
         */
        @Out
        EventSubscription[] eventSubscriptions();

        /**
         * Sets the maximum number of event-subscriptions to return.
         * If not specified all the event-subscriptions are returned.
         */
        @In
        Integer max();
    }

    /**
     * Reference to the service that manages a specific event-subscription.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 17 June 2019
     * @status added
     * @since 4.4.0
     */
    @Service EventSubscriptionService eventSubscription(String id);
}
