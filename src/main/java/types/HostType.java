/*
The oVirt Project - oVirt Engine Api Model

Copyright oVirt Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the Apache License, Version 2.0 is included with the program
in the file ASL2.
*/

package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * This enumerated type is used to determine which type of operating system is used by the host.
 *
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 31 Oct 2016
 * @status updated_by_docs
 */
@Type
public enum HostType {
    /**
     * The host contains a full Red Hat Enterprise Linux, CentOS, or Fedora installation.
     *
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 31 Oct 2016
     * @status updated_by_docs
     */
    RHEL,

    /**
     * The host contains Red Hat Enterprise Virtualization Hypervisor (RHEV-H), a small-scaled version of Red Hat
     * Enterprise Linux, CentOS, or Fedora, used solely to host virtual machines.
     *
     * This property is no longer relevant since Vintage Node is no longer supported, and has been deprecated.
     *
     * @author Tahlia Richardson <trichard@redhat.com>
     * @author Dana Elfassy <delfassy@redhat.com>
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 17 Jul 2019
     * @status updated_by_docs
     */
    @Deprecated
    RHEV_H,

    /**
     * The host contains Red Hat Virtualization Host (RHVH): a new implementation of
     * Red Hat Enterprise Virtualization Hypervisor (RHEV-H) which uses the same installer as Red Hat Enterprise Linux,
     * CentOS, or Fedora. The main difference between RHVH and legacy RHEV-H is that RHVH has a writeable file system
     * and will handle its own installation instead of having RPMs pushed to it by the Manager like in legacy RHEV-H.
     *
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 04 Nov 2016
     * @status updated_by_docs
     */
    OVIRT_NODE,
}
