package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing the notification method for an event
 * subscription. Currently only SMTP is supported by the API
 * In the future support for SNMP notifications may be added.
 *
 * @author Ori Liel <oliel@redhat.com>
 * @date 17 June 2019
 * @status added
 * @since 4.4.0
 */
@Type
public enum NotificationMethod {

    /**
     * Notification by e-mail.
     *
     * Event-subscriptions with SMTP notification method will
     * contain an email address in the address field.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 17 June 2019
     * @status added
     * @since 4.4.0
     */
    SMTP,

    /**
     * Notification by SNMP.
     *
     * Event-subscriptions with SNMP notification method will
     * contain an SNMP address in the address field.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 17 June 2019
     * @status added
     * @since 4.4.0
     */

    SNMP;
}
