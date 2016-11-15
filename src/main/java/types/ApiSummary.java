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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * A summary containing the total number of relevant objects, such as virtual machines, hosts, and storage domains.
 *
 * @author Megan Lewis <melewis@redhat.com>
 * @date 15 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface ApiSummary {
    /**
     * The summary of virtual machines.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     */
    ApiSummaryItem vms();

    /**
     * The summary of hosts.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     */
    ApiSummaryItem hosts();

    /**
     * The summary of users.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     */
    ApiSummaryItem users();

    /**
     * The summary of storage domains.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     */
    ApiSummaryItem storageDomains();
}
