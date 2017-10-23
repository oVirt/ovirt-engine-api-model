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

package services.aaa;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.SshPublicKey;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

@Service
@Area("Infrastructure")
public interface SshPublicKeysService {
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(key().content());
        }
        @In @Out SshPublicKey key();
    }

    /**
     * Returns a list of SSH public keys of the user.
     *
     * For example, to retrieve the list of SSH keys of user with identifier `123`,
     * send a request like this:
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/users/123/sshpublickeys
     * ----
     *
     * The result will be the following XML document:
     *
     * [source,xml]
     * ----
     * <ssh_public_keys>
     *   <ssh_public_key href="/ovirt-engine/api/users/123/sshpublickeys/456" id="456">
     *     <content>ssh-rsa ...</content>
     *     <user href="/ovirt-engine/api/users/123" id="123"/>
     *   </ssh_public_key>
     * </ssh_public_keys>
     * ----
     *
     * Or the following JSON object
     *
     * [source,json]
     * ----
     * {
     *   "ssh_public_key": [
     *     {
     *       "content": "ssh-rsa ...",
     *       "user": {
     *         "href": "/ovirt-engine/api/users/123",
     *         "id": "123"
     *       },
     *       "href": "/ovirt-engine/api/users/123/sshpublickeys/456",
     *       "id": "456"
     *     }
     *   ]
     * }
     * ----
     *
     * The order of the returned list of keys is not guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Bohdan Iakymet <biakymet@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 25 Jul 2017
     * @status updated_by_docs
     */
    interface List extends Follow {
        @Out SshPublicKey[] keys();

        /**
         * Sets the maximum number of keys to return. If not specified all the keys are returned.
         *
         * @author Megan Lewis <melewis@redhat.com>
         * @date 25 Jul 2017
         * @status updated_by_docs
         */
        @In Integer max();
    }

    @Service SshPublicKeyService key(String id);
}
