/*
Copyright (c) 2020 Red Hat, Inc.

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
 * Represents an enumeration of backup modes
 *
 * @author Eyal Shenitzky <eshenitz@redhat.com>
 * @date 06 Oct 2020
 * @status added
 * @since 4.4.3
 */
@Type
public enum DiskBackupMode {
    /**
     * This disk supports full backup.
     * You can query zero extents and download all disk data.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 06 Oct 2020
     * @status added
     * @since 4.4.3
     */
    FULL,

    /**
     * This disk supports incremental backup.
     * You can query dirty extents and download changed blocks.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 06 Oct 2020
     * @status added
     * @since 4.4.3
     */
    INCREMENTAL
}
