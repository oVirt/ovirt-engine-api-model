/*
Copyright (c) 2015 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * This type represents the attributes to define Quality of service (QoS).
 *
 * For storage the `type` is xref:types/qos_type[storage], the attributes `max_throughput`, `max_read_throughput`,
 * `max_write_throughput`, `max_iops`, `max_read_iops` and `max_write_iops` are relevant.
 *
 * For resources with computing capabilities the `type` is xref:types/qos_type[cpu], the attribute `cpu_limit` is
 * relevant.
 *
 * For virtual machines networks the `type` is xref:types/qos_type[network], the attributes `inbound_average`,
 * `inbound_peak`, `inbound_burst`, `outbound_average`, `outbound_peak` and `outbound_burst` are relevant.
 *
 * For host networks the `type` is xref:types/qos_type[hostnetwork], the attributes `outbound_average_linkshare`,
 * `outbound_average_upperlimit` and `outbound_average_realtime` are relevant.
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface Qos extends Identified {

    /**
     * The kind of resources this entry can be assigned.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    QosType type();

    /**
     * Maximum permitted total throughput.
     *
     * Used to configure storage. Must not be set if `max_read_throughput` or `max_write_throughput` is set.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Integer maxThroughput();

    /**
     * Maximum permitted throughput for read operations.
     *
     * Used to configure storage. Must not be set if `max_throughput` is set.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Integer maxReadThroughput();

    /**
     * Maximum permitted throughput for write operations.
     *
     * Used to configure storage. Must not be set if `max_throughput` is set.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Integer maxWriteThroughput();

    /**
     * Maximum permitted number of input and output operations per second.
     *
     * Used to configure storage. Must not be set if `max_read_iops` or `max_write_iops` is set.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Integer maxIops();

    /**
     * Maximum permitted number of input operations per second.
     *
     * Used to configure storage. Must not be set if `max_iops` is set.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Integer maxReadIops();

    /**
     * Maximum permitted number of output operations per second.
     *
     * Used to configure storage. Must not be set if `max_iops` is set.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Integer maxWriteIops();

    /**
     * The maximum processing capability in %.
     *
     * Used to configure computing resources.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Integer cpuLimit();

    /**
     * The desired average inbound bit rate in Mbps (Megabits per sec).
     *
     * Used to configure virtual machines networks. If defined, `inbound_peak` and `inbound_burst` also has to be set.
     *
     * See link:https://libvirt.org/formatnetwork.html#elementQoS[Libvirt-QOS] for further details.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 04 Jun 2018
     * @status updated_by_docs
     */
    Integer inboundAverage();

    /**
     * The maximum inbound rate in Mbps (Megabits per sec).
     *
     * Used to configure virtual machines networks. If defined, `inbound_average` and `inbound_burst` also has to be set.
     *
     * See link:https://libvirt.org/formatnetwork.html#elementQoS[Libvirt-QOS] for further details.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 04 June 2018
     * @status updated_by_docs
     */
    Integer inboundPeak();

    /**
     * The amount of data that can be delivered in a single burst, in MB.
     *
     * Used to configure virtual machine networks. If defined, `inbound_average` and `inbound_peak` must also be set.
     *
     * See link:https://libvirt.org/formatnetwork.html#elementQoS[Libvirt-QOS] for further details.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 04 Jun 2018
     * @status updated_by_docs
     */
    Integer inboundBurst();

    /**
     * The desired average outbound bit rate in Mbps (Megabits per sec).
     *
     * Used to configure virtual machines networks. If defined, `outbound_peak` and `outbound_burst` also has to be set.
     *
     * See link:https://libvirt.org/formatnetwork.html#elementQoS[Libvirt-QOS] for further details.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 04 June 2018
     * @status updated_by_docs
     */
    Integer outboundAverage();

    /**
     * The maximum outbound rate in Mbps (Megabits per sec).
     *
     * Used to configure virtual machines networks. If defined, `outbound_average` and `outbound_burst` also has to be
     * set.
     *
     * See link:https://libvirt.org/formatnetwork.html#elementQoS[Libvirt-QOS] for further details.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 04 June 2018
     * @status updated_by_docs
     */
    Integer outboundPeak();

    /**
     * The amount of data that can be sent in a single burst, in MB.
     *
     * Used to configure virtual machine networks. If defined, `outbound_average` and `outbound_peak` must also be
     * set.
     *
     * See link:https://libvirt.org/formatnetwork.html#elementQoS[Libvirt-QOS] for further details.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 04 Jun 2018
     * @status updated_by_docs
     */
    Integer outboundBurst();

    /**
     * Weighted share.
     *
     * Used to configure host networks. Signifies how much of the logical link's capacity a specific network should be
     * allocated, relative to the other networks attached to the same logical link. The exact share depends on the sum
     * of shares of all networks on that link. By default this is a number in the range 1-100.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Integer outboundAverageLinkshare();

    /**
     * The maximum bandwidth to be used by a network in Mbps (Megabits per sec).
     *
     * Used to configure host networks. If `outboundAverageUpperlimit` and `outbound_average_realtime` are provided, the
     * `outbound_averageUpperlimit` must not be lower than the `outbound_average_realtime`.
     *
     * See link:https://libvirt.org/formatnetwork.html#elementQoS[Libvirt-QOS] for further details.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 04 June 2018
     * @status updated_by_docs
     */
    Integer outboundAverageUpperlimit();

    /**
     * The committed rate in Mbps (Megabits per sec).
     *
     * Used to configure host networks. The minimum bandwidth required by a network. The committed rate requested is not
     * guaranteed and will vary depending on the network infrastructure and the committed rate requested by other
     * networks on the same logical link.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 04 June 2018
     * @status updated_by_docs
     */
    Integer outboundAverageRealtime();

    /**
     * The data center the QoS is assiciated to.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link DataCenter dataCenter();
}
