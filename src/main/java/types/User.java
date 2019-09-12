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
     * Namespace where the user resides. When using the authorization provider that stores users in the LDAP server,
     * this attribute equals the naming context of the LDAP server.
     * See https://github.com/oVirt/ovirt-engine-extension-aaa-ldap for more information.
     * When using the built-in authorization provider that stores users in the database this attribute is ignored.
     * See https://github.com/oVirt/ovirt-engine-extension-aaa-jdbc for more information.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 29 Nov 2016
     * @status updated_by_docs
     */
    String namespace();
    String lastName();

    /**
     * The user's username. The format depends on authorization provider type. In most LDAP providers it is the
     * value of the `uid` LDAP attribute. In Active Directory it is the User Principal Name (UPN). `UPN` or
     * `uid` must be followed by the authorization provider name. For example, in the case of LDAP's `uid` attribute it is:
     * `myuser@myextension-authz`. In the case of Active Directory using `UPN` it is:
     * `myuser@mysubdomain.mydomain.com@myextension-authz`. This attribute is a required parameter when adding a new user.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 29 Nov 2016
     * @status updated_by_docs
     */
    String userName();

    /**
     * Similar to `user_name`. The format depends on the LDAP provider. With most LDAP providers it is the
     * value of the `uid` LDAP attribute. In the case of Active Directory it is the User Principal Name (UPN).
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 29 Nov 2016
     * @status updated_by_docs
     */
    String principal();
    String password();
    String email();

    @Link Domain domain();

    /**
     * A link to the roles sub-collection for user resources.
     *
     * @author Irit Goihman <igoihman@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Role[] roles();
    @Link Group[] groups();
    @Link Permission[] permissions();

    /**
     * A link to the tags sub-collection for user resources.
     *
     * @author Irit Goihman <igoihman@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Tag[] tags();
    @Link SshPublicKey[] sshPublicKeys();

    /**
     * User options allow you to save key/value properties
     * which are used to customize the settings per individual
     * user.
     *
     * @author Bohdan Iakymets <biakymet@redhat.com>
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 10 Oct 2019
     * @status updated_by_docs
     * @since 4.4
     */
    Property[] userOptions();
}
