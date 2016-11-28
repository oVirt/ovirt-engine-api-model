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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents a user in the system.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 28 Nov 2016
 * @status added
 */
@Type
public interface User extends Identified {

    String domainEntryId();
    String department();
    Boolean loggedIn();

    /**
     * Namespace where user resides. When using the authorization provider that stores users in the LDAP
     * (see https://github.com/oVirt/ovirt-engine-extension-aaa-ldap[here] for details) this attribute equals to naming
     * context of the LDAP. When using the built-in authorization provider that stores users in the database
     * (see https://github.com/oVirt/ovirt-engine-extension-aaa-jdbc[here] for details) this attribute is ignored.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    String namespace();
    String lastName();

    /**
     * Username of the user. The format depends on authorization provider type. In case of most LDAP providers it is
     * value of the `uid` LDAP attribute. In case of Active Directory it is the user principal name (UPN). `UPN` or
     * `uid` must be followed by authorization provider name. For example in case of LDAP using `uid` attribute it is:
     * `myuser@myextension-authz`. In case of Active Directory using `UPN` it is:
     * `myuser@mysubdomain.mydomain.com@myextension-authz`. This attribute is required parameter when adding new user.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    String userName();

    /**
     * Same as `user_name` principal has different formats based on LDAP provider. In case of most LDAP providers it is
     * value of the `uid` LDAP attribute. In case of Active Directory it is the user principal name (UPN).
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    String principal();
    String password();
    String email();

    @Link Domain domain();
    @Link Role[] roles();
    @Link Group[] groups();
    @Link Permission[] permissions();
    @Link Tag[] tags();
    @Link SshPublicKey[] sshPublicKeys();
}
