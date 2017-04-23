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
 * Represents an external status.
 * This status is currently used for <<types/host, hosts>>
 * and <<types/storage_domain, storage domains>>, and allows an external
 * system to update status of objects it is aware of.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 23 Apr 2017
 * @status added
 */
@Type
public enum ExternalStatus {
    /**
     * Error status. There is some kind of error
     * in the relevant object.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 23 Apr 2017
     * @status added
     */
    ERROR,

    /**
     * Failure status. The relevant object is failing.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 23 Apr 2017
     * @status added
     */
    FAILURE,

    /**
     * Info status. The relevant object is in OK status,
     * but there is an information available that might be
     * relevant for the administrator.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 23 Apr 2017
     * @status added
     */
    INFO,

    /**
     * OK status. The relevant object is working well.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 23 Apr 2017
     * @status added
     */
    OK,

    /**
     * Warning status. The relevant object is working well,
     * but there is some warning that might be relevant for
     * the administrator.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 23 Apr 2017
     * @status added
     */
    WARNING;
}
