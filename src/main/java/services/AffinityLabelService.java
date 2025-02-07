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

package services;

import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.AffinityLabel;

/**
 * The details of a single affinity label.
 *
 * @author Megan Lewis <melewis@redhat.com>
 * @date 31 Jan 2017
 * @status updated_by_docs
 */
@Service
public interface AffinityLabelService {
    /**
     * Retrieves the details of a label.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    interface Get extends Follow {
        @Out AffinityLabel label();
    }

    /**
     * Updates a label. This call will update all metadata, such as the name
     * or description.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    interface Update {
        @In @Out AffinityLabel label();
    }

    /**
     * Removes a label from the system and clears all assignments
     * of the removed label.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 20 Feb 2017
     * @status updated_by_docs
     */
    interface Remove {
    }

    /**
     * List all virtual machines with this label.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 31 Jan 2017
     * @status updated_by_docs
     */
    @Service AffinityLabelVmsService vms();

    /**
     * List all hosts with this label.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 20 Feb 2017
     * @status updated_by_docs
     */
    @Service AffinityLabelHostsService hosts();
}
