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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents the DNS resolver configuration.
 *
 * @author Leon Goldberg <lgoldber@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface Dns {

    /**
     * Array of hosts serving as DNS servers.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Host[] servers();

    /**
     * Array of hosts serving as search domains.
     *
     * @author Leon Goldberg <lgoldber@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Host[] searchDomains();
}
