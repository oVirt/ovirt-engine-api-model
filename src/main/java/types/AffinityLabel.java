/*
Copyright (c) 2016 Red Hat, Inc.

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
 * The affinity label can influence virtual machine scheduling.
 * It is most frequently used to create a sub-cluster from the available hosts.
 *
 * @author Megan Lewis <melewis@redhat.com>
 * @date 2 Dec 2016
 * @status updated_by_docs
 */
@Type
public interface AffinityLabel extends Identified {
    /**
     * A list of hosts that were labeled using this scheduling label.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016 
     * @status updated_by_docs
     */
    @Link Host[] hosts();

    /**
     * A list of virtual machines that were labeled using this scheduling label.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     */
    @Link Vm[] vms();

    /**
     * The `read_only` property marks a label that can not be modified.
     * This is usually the case when listing internally-generated labels.
     *
     * @author Megan Lewis <melewis@redhat.com> 
     * @date 15 Nov 2016
     * @status updated_by_docs
     */
    Boolean readOnly();

    /**
     * This property enables the legacy behavior for labels.
     * If `true`, the label acts also as a positive enforcing VM-to-host affinity group.
     *
     * This parameter is only used for clusters with compatibility version 4.3 or lower.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @date 22 Jul 2019
     * @status added
     * @since 4.3.6
     */
    Boolean hasImplicitAffinityGroup();
}
