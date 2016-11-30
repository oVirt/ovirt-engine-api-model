/*
Copyright (c) 2015 Red Hat, Inc.

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

import java.util.Date;

/**
 * Type representing an event.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 30 Nov 2016
 * @status added
 */
@Type
public interface Event extends Identified {
    /**
     * The event code.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    Integer code();

    /**
     * The event severity.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    LogSeverity severity();

    /**
     * The event time.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    Date time();

    /**
     * The event correlation identifier. Used in order to correlate
     * several events together.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    String correlationId();

    /**
     * Free text identifying the origin of the event.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    String origin();

    /**
     * A custom event identifier.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    Integer customId();

    /**
     * Defines the flood rate.
     * This prevents flooding in case an event appeared
     * more than once in the defined rate.
     * Defauls is 30 seconds.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    Integer floodRate();

    /**
     * Free text representing custom event data.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    String customData();

    /**
     * Reference to the user service.
     * Event can be associated with a user.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    @Link User user();

    /**
     * Reference to the virtual machine service.
     * Event can be associated with a virtual machine.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    @Link Vm vm();

    /**
     * Reference to the storage domain service.
     * Event can be associated with a storage domain.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    @Link StorageDomain storageDomain();

    /**
     * Reference to the host service.
     * Event can be associated with a host.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    @Link Host host();

    /**
     * Reference to the template service.
     * Event can be associated with a template.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    @Link Template template();

    /**
     * Reference to the cluster service.
     * Event can be associated with a cluster.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    @Link Cluster cluster();

    /**
     * Reference to the data center service.
     * Event can be associated with a data center.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    @Link DataCenter dataCenter();
}
