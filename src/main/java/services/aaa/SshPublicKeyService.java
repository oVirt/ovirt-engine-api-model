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

package services.aaa;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.SshPublicKey;

@Service
@Area("Infrastructure")
public interface SshPublicKeyService {
    interface Get extends Follow {
        @Out SshPublicKey key();
    }

    /**
     * Replaces the key with a new resource.
     *
     * IMPORTANT: Since version 4.4.8 of the engine this operation is deprecated, and preserved only for backwards
     * compatibility. It will be removed in the future. Instead please use DELETE followed by xref:services-ssh_public_keys-methods-add[add operation].
     *
     * @deprecated 4.4.8
     */
    @Deprecated
    interface Update {
        @In @Out SshPublicKey key();

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
