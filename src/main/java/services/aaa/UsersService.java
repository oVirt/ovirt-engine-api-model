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
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.User;

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
     * by authorization provider name. Due to https://bugzilla.redhat.com/1147900[bug 1147900] you need to provide
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
        @In @Out User user();
    }

    interface List {
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
