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

package services.aaa;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.User;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.optional;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

/**
 * A service to manage the users in the system.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 01 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface UsersService {

    /**
     * Add user from a directory service.
     *
     * For example, to add the `myuser` user from the `myextension-authz` authorization provider send a request
     * like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/users
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <user>
     *   <user_name>myuser@myextension-authz</user_name>
     *   <domain>
     *     <name>myextension-authz</name>
     *   </domain>
     * </user>
     * ----
     *
     * In case you are working with Active Directory you have to pass user principal name (UPN) as `username`, followed
     * by authorization provider name. Due to link:https://bugzilla.redhat.com/1147900[bug 1147900] you need to provide
     * also `principal` parameter set to UPN of the user.
     *
     * For example, to add the user with UPN `myuser@mysubdomain.mydomain.com` from the `myextension-authz`
     * authorization provider send a request body like this:
     *
     * [source,xml]
     * ----
     * <user>
     *   <principal>myuser@mysubdomain.mydomain.com</principal>
     *   <user_name>myuser@mysubdomain.mydomain.com@myextension-authz</user_name>
     *   <domain>
     *     <name>myextension-authz</name>
     *   </domain>
     * </user>
     * ----
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(user().userName());
            or(mandatory(user().domain().id()), mandatory(user().domain().name()));
            optional(user().namespace());
            optional(user().principal());
        }
        @In @Out User user();
    }

    /**
     * List all the users in the system.
     *
     * Usage:
     *
     * ....
     * GET /ovirt-engine/api/users
     * ....
     *
     * Will return the list of users:
     *
     * [source,xml]
     * ----
     * <users>
     *   <user href="/ovirt-engine/api/users/1234" id="1234">
     *     <name>admin</name>
     *     <link href="/ovirt-engine/api/users/1234/sshpublickeys" rel="sshpublickeys"/>
     *     <link href="/ovirt-engine/api/users/1234/roles" rel="roles"/>
     *     <link href="/ovirt-engine/api/users/1234/permissions" rel="permissions"/>
     *     <link href="/ovirt-engine/api/users/1234/tags" rel="tags"/>
     *     <domain_entry_id>23456</domain_entry_id>
     *     <namespace>*</namespace>
     *     <principal>user1</principal>
     *     <user_name>user1@domain-authz</user_name>
     *     <domain href="/ovirt-engine/api/domains/45678" id="45678">
     *       <name>domain-authz</name>
     *     </domain>
     *   </user>
     * </users>
     * ----
     *
     * The order of the returned list of users isn't guaranteed.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @author Ravi Nori <rnori@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List extends Follow {
        /**
         * The list of users.
         *
         * @author Oved Ourfali <oourfali@redhat.com>
         * @date 01 Dec 2016
         * @status added
         */
        @Out User[] users();

        /**
         * Sets the maximum number of users to return. If not specified all the users are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned users.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();
    }

    @Service UserService user(String id);
}
