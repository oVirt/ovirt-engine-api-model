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

package services;

import annotations.Area;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.CpuProfile;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("Virtualization")
public interface CpuProfilesService {
    /**
     * Add a new cpu profile to the system.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(profile().cluster().id());
            mandatory(profile().name());
            optional(profile().description());
            optional(profile().qos().id());
        }
        @In @Out CpuProfile profile();
    }

    /**
     * Returns the list of CPU profiles of the system.
     *
     * The order of the returned list of CPU profiles isn't guranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List {
        @Out CpuProfile[] profile();

        /**
         * Sets the maximum number of profiles to return. If not specified all the profiles are returned.
         */
        @In Integer max();
    }

    @Service CpuProfileService profile(String id);
}
