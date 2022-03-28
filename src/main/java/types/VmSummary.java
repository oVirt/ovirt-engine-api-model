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
 * Type containing information related to virtual machines on a particular host.
 *
 * @author Arik Hadas <ahadas@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public interface VmSummary {
    /**
     * The number of virtual machines active on the host.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Integer active();

    /**
     * The number of virtual machines migrating to or from the host.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Integer migrating();

    /**
     * The number of virtual machines present on the host.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Integer total();
}
