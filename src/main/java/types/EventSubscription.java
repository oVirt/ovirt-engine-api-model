package types;

import org.ovirt.api.metamodel.annotations.Type;

@Type
public interface EventSubscription extends Identified {

    /**
     * The subscribed-for event.
     *
     * (Combined with the user, Uniquely identifies the event-subscription).
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 10 Jun 2019
     * @status added
     * @since 4.4.0
     */
    NotifiableEvent event();

    /**
     * The subscribing user.
     *
     * Combined with the event-name, uniquely identifies the event-subscription.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 10 Jun 2019
     * @status added
     * @since 4.4.0
     */
    User user();

    /**
     * The notification method: SMTP or SNMP.
     *
     * Currently only SMTP supported by API. Support for SNMP
     * will be added in the future.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 10 Jun 2019
     * @status added
     * @since 4.4.0
     */
    NotificationMethod notificationMethod();

    /**
     * The email address to which notifications should be sent.
     *
     * When not provided, notifications are sent to the user's email.
     * Only a single address per user is currently supported. If a
     * subscription with a different email address to that of existing
     * subscriptions is added, a 409 (CONFLICT) status is returned with
     * an explanation that the provided address conflicts with an existing
     * address of an event-subscription for this user.
     *
     * This field might be deprecated in the future, and notifications
     * will always be sent on the user's email address.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 10 Jun 2019
     * @status added
     * @since 4.4.0
     */
    String address();

}
