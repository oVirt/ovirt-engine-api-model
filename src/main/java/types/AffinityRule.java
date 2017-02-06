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
 * Generic rule definition for affinity group. Each supported resource type (virtual machine, host) is controlled
 * by a separate rule. This allows expressing of rules like: no affinity between defined virtual machines, but hard
 * affinity between defined virtual machines and virtual hosts.
 *
 * @author Martin Sivak <msivak@redhat.com>
 * @date 6 Dec 2016
 * @status added
 * @since 4.1
 */
@Type
public interface AffinityRule {
    /**
     * Specifies whether the affinity group applies positive affinity or negative affinity to the resources
     * that are controlled by this rule. This argument is mandatory if the rule is enabled and is ignored
     * when the rule is disabled.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @date 6 Dec 2016
     * @status added
     * @since 4.1
     */
    Boolean positive();

    /**
     * Specifies whether the affinity group uses hard or soft enforcement of the affinity applied to the resources
     * that are controlled by this rule. This argument is mandatory if the rule is enabled and is ignored
     * when the rule is disabled.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @date 6 Dec 2016
     * @status added
     * @since 4.1
     */
    Boolean enforcing();

    /**
     * Specifies whether the affinity group uses this rule or not.
     * This attribute is optional during creation and is considered to be `true` when it is not provided.
     * In case this attribute is not provided to the update operation, it is considered to be `true` if
     * AffinityGroup `positive` attribute is set as well.
     * The backend `enabled` value will be preserved when both `enabled` and `positive` attributes are missing.
     *
     * @author Martin Sivak <msivak@redhat.com>
     * @date 6 Dec 2016
     * @status added
     * @since 4.1
     */
    Boolean enabled();
}
