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

package services.externalhostproviders;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.ExternalHostProvider;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("Infrastructure")
public interface ExternalHostProvidersService {
    /**
     * Add a new external host provider to the system.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(provider().name());
            optional(provider().description());
            optional(provider().password());
            optional(provider().requiresAuthentication());
            optional(provider().url());
            optional(provider().username());
        }
        @In @Out ExternalHostProvider provider();
    }

    /**
     * Returns the list of external host providers.
     *
     * The order of the returned list of host providers isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out ExternalHostProvider[] providers();

        /**
         * Sets the maximum number of providers to return. If not specified all the providers are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned external host providers.
         */
        @In String search();

    }

    @Service ExternalHostProviderService provider(String id);
}
