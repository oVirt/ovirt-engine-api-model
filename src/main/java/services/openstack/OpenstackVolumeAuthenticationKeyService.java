/*
Copyright (c) 2015-2021 Red Hat, Inc.

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
import types.OpenstackVolumeAuthenticationKey;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

/**
 * Openstack Volume (Cinder) integration has been replaced by Managed Block Storage.
 *
 * @author Shani Leviim <sleviim@redhat.com>
 * @date 14 Oct 2021
 * @status added
 */

@Deprecated
@Service
@Area("Storage")
public interface OpenstackVolumeAuthenticationKeyService {
    interface Get extends Follow {
        @Out OpenstackVolumeAuthenticationKey key();
    }

    /**
     * Update the specified authentication key.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(key().description());
            optional(key().usageType());
            optional(key().value());
        }
        @In @Out OpenstackVolumeAuthenticationKey key();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
