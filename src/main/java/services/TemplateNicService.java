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

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Nic;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("Network")
public interface TemplateNicService {
    interface Get extends Follow {
        @Out Nic nic();
    }

    /**
     * Update the specified network interface card attached to the template.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(nic()._interface());
            optional(nic().linked());
            optional(nic().mac().address());
            optional(nic().name());
            optional(nic().plugged());
            optional(nic().vnicProfile().id());
        }
        @In @Out Nic nic();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
