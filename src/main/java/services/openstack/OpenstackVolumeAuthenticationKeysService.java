/*
Copyright (c) 2015-2021 Red Hat, Inc.

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

package services.openstack;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.OpenstackVolumeAuthenticationKey;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
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
public interface OpenstackVolumeAuthenticationKeysService {
    /**
     * Add a new authentication key to the OpenStack volume provider.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(key().usageType());
            mandatory(key().uuid());
            mandatory(key().value());
            optional(key().description());
        }
        @In @Out OpenstackVolumeAuthenticationKey key();
    }


    /**
     * Returns the list of authentication keys.
     *
     * The order of the returned list of authentication keys isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out OpenstackVolumeAuthenticationKey[] keys();

        /**
         * Sets the maximum number of keys to return. If not specified all the keys are returned.
         */
        @In Integer max();
    }

    @Service OpenstackVolumeAuthenticationKeyService key(String id);
}
