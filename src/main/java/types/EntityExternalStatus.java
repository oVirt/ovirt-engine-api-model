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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing an external entity status.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 01 Dec 2016
 * @status added
 */
@Type
public enum EntityExternalStatus {
    /**
     * The external entity status is okay.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    OK,

    /**
     * There external entity status is okay but with some information that
     * might be relevant.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    INFO,

    /**
     * The external entity status is okay but with an issue that might require attention.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    WARNING,

    /**
     * The external entity status is erroneous. This might require a moderate attention.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    ERROR,

    /**
     * The external entity has an issue that causes failures. This might require immediate attention.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 01 Dec 2016
     * @status added
     */
    FAILURE;
}
