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

@Type
public interface Ssh extends Identified {
    Integer port();

    /**
     * Fingerprint of SSH public key for a host. This field is deprecated since 4.4.5 and will be removed in the future.
     *
     * Please use publicKey instead.
     *
     * @deprecated 4.4.5
     */
    @Deprecated
    String fingerprint();

    /**
     * SSH public key of the host using SSH public key format as defined in
     * link:https://tools.ietf.org/html/rfc4253[RFC4253].
     *
     * @author Artur Socha <asocha@redhat.com>
     * @date 29 Jan 21
     * @status requires_text
     * @since 4.4.5
     */
    String publicKey();

    SshAuthenticationMethod authenticationMethod();
    User user();
}
