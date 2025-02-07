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

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.ExternalProvider;

/**
 * This service lists external providers.
 *
 * @author Dominik Holler <dholler@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 06 Oct 2017
 * @status updated_by_docs
 * @since 4.2
 */
@Service
@Area("Infrastructure")
public interface ClusterExternalProvidersService {
    /**
     * Returns the list of external providers.
     *
     * The order of the returned list of providers is not guaranteed.
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 06 Oct 2017
     * @status updated_by_docs
     * @since 4.2
     */
    interface List extends Follow {
        @Out
        ExternalProvider[] providers();
    }
}
