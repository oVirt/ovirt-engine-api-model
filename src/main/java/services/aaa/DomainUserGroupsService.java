/*
Copyright (c) 2018 Red Hat, Inc.

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
import types.Group;

/**
 * A service that shows a user's group membership in the AAA extension.
 *
 * @author Ondra Machacek <omachace@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 26 Feb 2018
 * @status updated_by_docs
 * @since 4.2.3
 */
@Service
@Area("Infrastructure")
public interface DomainUserGroupsService {

    /**
     * Returns the list of groups that the user is a member of.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 26 Feb 2018
     * @status updated_by_docs
     * @since 4.2.3
     */
    interface List extends Follow {
        /**
         * The list of groups that the user is a member of.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 26 Feb 2018
         * @status updated_by_docs
         * @since 4.2.3
         */
        @Out Group[] groups();
    }
}
