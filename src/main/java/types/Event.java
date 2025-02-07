/*
The oVirt Project - oVirt Engine Api Model

Copyright oVirt Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the Apache License, Version 2.0 is included with the program
in the file ASL2.
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
     * The numeric index of this event. The indexes of events are always increasing, so events with higher indexes
     * are guaranteed to be older than events with lower indexes.
     *
     * IMPORTANT: In the current implementation of the engine, the `id` attribute has the same value as this
     * `index` attribute. That is an implementation detail that the user of the API should not rely on. In the future
     * the `id` attribute may be changed to an arbitrary string, containing non numeric characters and no implicit
     * order. On the other hand this `index` attribute is guaranteed to stay as integer and ordered.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 14 May 2017
     * @status added
     * @since 4.1.3
     */
    Integer index();

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
     * Defaults is 30 seconds.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    Integer floodRate();

    /**
     * Specifies whether the event should also be written
     * to the ${hypervisor.name} log. If no host is specified
     * the event description will be written to all hosts.
     * Default is false.
     *
     * @author Eitan Raviv <eraviv@redhat.com>
     * @date 19 Aug 2019
     * @since 4.4
     * @status added
     */
    Boolean logOnHost();

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
