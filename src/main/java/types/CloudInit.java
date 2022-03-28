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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Deprecated type to specify _cloud-init_ configuration.
 *
 * This type has been deprecated and replaced by alternative attributes inside the
 * <<types/initialization, Initialization>> type. See the
 * <<types/initialization/attributes/cloud_init, cloud_init>> attribute documentation for details.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 25 Jul 2017
 * @status updated_by_docs
 */
@Deprecated
@Type
public interface CloudInit {
    Host host();
    AuthorizedKey[] authorizedKeys();
    NetworkConfiguration networkConfiguration();
    Boolean regenerateSshKeys();
    String timezone();
    User[] users();
    File[] files();
}
