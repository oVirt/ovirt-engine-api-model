/*
Copyright (c) 2016 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents the DNS resolver configuration.
 *
 * @author Martin Mucha <mmucha@redhat.com>
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 4 Nov 2016
 * @status updated_by_docs
 * @since 4.1
 */
@Type
public interface DnsResolverConfiguration {
    /**
     * Array of addresses of name servers. Either IPv4 or IPv6 addresses may be specified.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 4 Nov 2016
     * @status updated_by_docs
     * @since 4.1
     */
    String[] nameServers();
}
