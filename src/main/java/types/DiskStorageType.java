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

@Type
public enum DiskStorageType {
    IMAGE, LUN, CINDER,

    /**
     * A storage type, used for a storage domain that was created
     * using a cinderlib driver.
     *
     * @author Benny Zlotnik <bzlotnik@redhat.com>
     * @author Steve Goodman <sgoodman@redhat.com>
     * @date 18 Dec 2018
     * @since 4.3
     * @status updated_by_docs
     */
    MANAGED_BLOCK_STORAGE;
}
