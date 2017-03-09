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
 * Parameter for the <<types/network_filter,network filter>>.
 *
 * See https://libvirt.org/formatnwfilter.html#nwfconceptsvars[Libvirt-Filters] for further details.
 * This is a example of the XML representation:
 *
 * [source,xml]
 * ----
 * <network_filter_parameter id="123">
 *   <name>IP</name>
 *   <value>10.0.1.2</value>
 * </network_filter_parameter>
 * ----
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @date 28 Nov 2016
 * @status added
 * @since 4.2
 */
@Type
public interface NetworkFilterParameter extends Identified {

    /**
     * Represents the value of the parameter.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 28 Nov 2016
     * @status added
     * @since 4.2
     */
    String value();

    /**
     * The virtual machine NIC the parameter is assiciated to.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 09 Mar 2017
     * @status added
     * @since 4.2
     */
    @Link
    Nic nic();
}
