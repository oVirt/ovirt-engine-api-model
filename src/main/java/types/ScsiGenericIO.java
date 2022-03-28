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
 * When a direct LUN disk is using SCSI passthrough the privileged I/O policy is determined by this enum.
 *
 * @author Tal Nisan <tnisan@redhat.com>
 * @date 5 Jun 2019
 * @status added
 */
@Type
public enum ScsiGenericIO {

    /**
     * Disallow privileged SCSI I/O.
     *
     * @date 5 Jun 2019
     * @status added
     */
    FILTERED,

    /**
     * Allow privileged SCSI I/O.
     *
     * @date 5 Jun 2019
     * @status added
     */
    UNFILTERED,

    /**
     * Disable SCSI passthrough.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 5 Jun 2019
     * @status added
     * @since 4.3.5
     */
    DISABLED;
}
