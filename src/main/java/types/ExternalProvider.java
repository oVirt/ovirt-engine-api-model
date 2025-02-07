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

package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents an external provider.
 *
 * @author Mor Kalfon <mkalfon@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface ExternalProvider extends Identified {
    /**
     * Defines URL address of the external provider.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String url();

    /**
     * Defines whether provider authentication is required or not.
     *
     * If authentication is required, both `username` and `password` attributes will be used during authentication.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Boolean requiresAuthentication();

    /**
     * Defines user name to be used during authentication process.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String username();

    /**
     * Defines password for the user during the authentication process.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String password();

    /**
     * Defines the external provider authentication URL address.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String authenticationUrl();

    /**
     * Array of provider name/value properties.
     *
     * @author Mor Kalfon <mkalfon@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Property[] properties();
}
