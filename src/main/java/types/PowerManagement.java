/*
Copyright (c) 2015-2016 Red Hat, Inc.

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

@Type
public interface PowerManagement {

    /**
     * Indicates whether power management configuration is enabled or disabled.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    Boolean enabled();

    /**
     * The host name or IP address of the host.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    String address();

    /**
     * A valid user name for power management.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    String username();

    /**
     * A valid, robust password for power management.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    String password();

    /**
     * Fencing options for the selected type= specified with the option name="" and value="" strings.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    Option[] options();

    /**
     * Determines the power status of the host.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    PowerManagementStatus status();

    /**
     * Determines the power management proxy.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    PmProxy[] pmProxies();

    /**
     * Specifies fence agent options when multiple fences are used.
     *
     * Use the order sub-element to prioritize the fence agents. Agents are run sequentially according to their
     * order until the fence action succeeds. When two or more fence agents have the same order,
     * they are run concurrently. Other sub-elements include type, ip, user, password, and options.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    Agent[] agents();

    /**
     * Toggles the automated power control of the host in order to save energy.
     * When set to true, the host will be automatically powered down if the cluster's load is low,
     * and powered on again when required. This is set to true when a host is created, unless disabled by the user.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    Boolean automaticPmEnabled();

    /**
     * Toggles whether to determine if kdump is running on the host before it is shut down.
     * When set to `true`, the host will not shut down during a kdump process.
     * This is set to `true` when a host has power management enabled, unless disabled by the user.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    Boolean kdumpDetection();

    /**
     * Fencing device code.
     *
     * A list of valid fencing device codes are available in the `capabilities` collection.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    // TODO: (deprecated) remove 'type' post 4.0
    String type();
}
