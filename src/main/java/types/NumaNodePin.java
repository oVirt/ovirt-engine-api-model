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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents the pinning of a virtual NUMA node to a physical NUMA node.
 *
 * @author Andrej Krejcir <akrejcir@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 16 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface NumaNodePin {
    /**
     * Deprecated. Has no function.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 16 Nov 2016
     * @status updated_by_docs
     */
    @Deprecated
    NumaNode hostNumaNode();

    /**
     * Deprecated. Should always be `true`.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 16 Nov 2016
     * @status updated_by_docs
     */
    @Deprecated
    Boolean pinned();

    /**
     * The index of a physical NUMA node to which the virtual NUMA node is pinned.
     *
     * @author Andrej Krejcir <akrejcir@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 16 Nov 2016
     * @status updated_by_docs
     */
    Integer index();
}
