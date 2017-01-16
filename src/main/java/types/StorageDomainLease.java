/*
Copyright (c) 2015-2017 Red Hat, Inc.

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
 * Represents a lease residing on a storage domain.
 *
 * A lease is a http://www.ovirt.org/develop/developer-guide/vdsm/sanlock[Sanlock] resource residing on a special
 * volume on the storage domain, this Sanlock resource is used to provide storage base locking.
 *
 * @author Tal Nisan <tnisan@redhat.com>
 * @date 11 Jan 2017
 * @status added
 * @since 4.1
 */
@Type
public interface StorageDomainLease {
    /**
     * Reference to the storage domain on which the lock resides on.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 16 Jan 2017
     * @status added
     * @since 4.1
     */
    @Link StorageDomain storageDomain();
}
