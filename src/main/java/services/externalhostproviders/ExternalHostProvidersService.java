/*
Copyright (c) 2015 Red Hat, Inc.

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
     * Adds a new external host provider to the system.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 9 May 2018
     * @status updated_by_docs
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
     * The order of the returned list of host providers is not guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 9 May 2018
     * @status updated_by_docs
     */
    interface List extends Follow {
        @Out ExternalHostProvider[] providers();

        /**
         * Sets the maximum number of providers to return. If not specified, all the providers are returned.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 9 May 2018
         * @status updated_by_docs
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned external host providers.
         *
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 9 May 2018
         * @status updated_by_docs
         */
        @In String search();

    }

    @Service ExternalHostProviderService provider(String id);
}
