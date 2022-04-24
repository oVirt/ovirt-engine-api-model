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
 * Type representing kind of operating system.
 *
 * WARNING: This type has been deprecated with the introduction of the xref:types/operating_system_info[OperatingSystemInfo] type.
 * Operating systems are available as a top-level collection in the API: xref:services/operating_systems[operating_systems].
 *
 * The end-user declares the type of the operating system installed in the virtual machine (guest operating system) by
 * selecting one of these values. This declaration enables the system to tune the virtual machine configuration for
 * better user experience. For example, the system chooses devices that are most suitable for the operating system. Note
 * that the system rely on user's selection and does not verify it by inspecting the actual guest operating system
 * installed.
 *
 * @author Arik Hadas <ahadas@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
@Deprecated
public enum OsType {
    /**
     * This value is mapped to `other`.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    UNASSIGNED,

    /**
     * Windows XP.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    WINDOWS_XP,

    /**
     * Windows 2003 32-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    WINDOWS_2003,

    /**
     * Windows 2008 32-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    WINDOWS_2008,

    /**
     * Distribution of Linux other than those specified by the other values.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    OTHER_LINUX,

    /**
     * Other type of operating system, not specified by the other values.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    OTHER,

    /**
     * Red Hat Enterprise Linux 5 32-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    RHEL_5,

    /**
     * Red Hat Enterprise Linux 4 32-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    RHEL_4,

    /**
     * Red Hat Enterprise Linux 3 32-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    RHEL_3,

    /**
     * Windows 2003 64-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    WINDOWS_2003X64,

    /**
     * Windows 7 32-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    WINDOWS_7,

    /**
     * Windows 7 64-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    WINDOWS_7X64,

    /**
     * Red Hat Enterprise Linux 5 64-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    RHEL_5X64,

    /**
     * Red Hat Enterprise Linux 4 64-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    RHEL_4X64,

    /**
     * Red Hat Enterprise Linux 3 64-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    RHEL_3X64,

    /**
     * Windows 2008 64-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    WINDOWS_2008X64,

    /**
     * Windows 2008 R2 64-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    WINDOWS_2008R2X64,

    /**
     * Red Hat Enterprise Linux 6 32-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    RHEL_6,

    /**
     * Red Hat Enterprise Linux 6 64-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    RHEL_6X64,

    /**
     * Windows 8 32-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    WINDOWS_8,

    /**
     * Windows 8 64-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    WINDOWS_8X64,

    /**
     * Windows 2012 64-bit.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    WINDOWS_2012X64;
}
