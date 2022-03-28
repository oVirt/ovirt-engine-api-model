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

package services.aaa;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.User;

/**
 * A service to view a domain user in the system.
 *
 * @author Ravi Nori <rnori@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface DomainUserService {
    /**
     * Gets the domain user information.
     *
     * Usage:
     *
     * ....
     * GET /ovirt-engine/api/domains/5678/users/1234
     * ....
     *
     * Will return the domain user information:
     *
     * [source,xml]
     * ----
     * <user href="/ovirt-engine/api/users/1234" id="1234">
     *   <name>admin</name>
     *   <namespace>*</namespace>
     *   <principal>admin</principal>
     *   <user_name>admin@internal-authz</user_name>
     *   <domain href="/ovirt-engine/api/domains/5678" id="5678">
     *     <name>internal-authz</name>
     *   </domain>
     *   <groups/>
     * </user>
     * ----
     *
     * @author Ravi Nori <rnori@redhat.com>
     * @date 12 Dec 2017
     * @status added
     */
    interface Get extends Follow {
        /**
         * The domain user.
         *
         * @author Ravi Nori <rnori@redhat.com>
         * @date 12 Dec 2017
         * @status added
         */
        @Out User user();
    }
}
