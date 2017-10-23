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

package services.openstack;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.OpenStackImageProvider;

import static org.ovirt.api.metamodel.language.ApiLanguage.COLLECTION;
import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("Storage")
public interface OpenstackImageProvidersService {
    /**
     * Add a new OpenStack image provider to the system.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(provider().name());
            optional(provider().authenticationUrl());
            optional(provider().description());
            optional(provider().password());
            optional(provider().requiresAuthentication());
            optional(provider().tenantName());
            optional(provider().url());
            optional(provider().username());
            optional(provider().properties()[COLLECTION].name());
            optional(provider().properties()[COLLECTION].value());
        }
        @In @Out OpenStackImageProvider provider();
    }

    /**
     * Returns the list of providers.
     *
     * The order of the returned list of providers isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out OpenStackImageProvider[] providers();

        /**
         * Sets the maximum number of providers to return. If not specified all the providers are returned.
         */
        @In Integer max();
    }

    @Service OpenstackImageProviderService provider(String id);
}
