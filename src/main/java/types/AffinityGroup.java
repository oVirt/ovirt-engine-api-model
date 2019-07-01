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

/**
 * An affinity group represents a group of virtual machines with a defined relationship.
 *
 * @author Martin Sivak <msivak@redhat.com>
 * @author Megan Lewis <melewis@redhat.com>
 * @date 15 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface AffinityGroup extends Identified {
    /**
     * Specifies whether the affinity group applies positive affinity or negative affinity to virtual machines that are
     * members of that affinity group.
     *
     * WARNING: Please note that this attribute has been deprecated since version 4.1 of the engine,
     * and will be removed in the future. Use the `vms_rule` attribute from now on.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     */
    @Deprecated
    Boolean positive();

    /**
     * Specifies whether the affinity group uses hard or soft enforcement of the affinity applied to virtual machines
     * that are members of that affinity group.
     *
     * WARNING: Please note that this attribute has been deprecated since version 4.1 of the engine,
     * and will be removed in the future. Use the `vms_rule` attribute from now on.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     */
    @Deprecated
    Boolean enforcing();

    /**
     * Specifies the affinity rule applied to virtual machines that are members of this affinity group.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @date 6 Dec 2016
     * @status added
     * @since 4.1
     */
    AffinityRule vmsRule();

    /**
     * Specifies the affinity rule applied between virtual machines and hosts that are members of this affinity group.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @date 6 Dec 2016
     * @status added
     * @since 4.1
     */
    AffinityRule hostsRule();

    /**
     * Priority of the affinity group.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 21 Jun 2019
     * @status added
     * @since 4.3.6
     */
    Double priority();

    /**
     * A reference to the cluster to which the affinity group applies.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     */
    @Link
    Cluster cluster();

    /**
     * A list of all virtual machines assigned to this affinity group.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 2 Dec 2016
     * @status updated_by_docs
     */
    @Link Vm[] vms();

    /**
     * A list of all hosts assigned to this affinity group.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @date 6 Dec 2016
     * @status added
     * @since 4.1
     */
    @Link Host[] hosts();

    /**
     * A list of all virtual machine labels assigned to this affinity group.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 22 Jul 2019
     * @status added
     * @since 4.3.6
     */
    @Link AffinityLabel[] vmLabels();

    /**
     * A list of all host labels assigned to this affinity group.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 28 Jul 2019
     * @status updated_by_docs
     * @since 4.3.6
     */
    @Link AffinityLabel[] hostLabels();
}
