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
 * This enumerated type is used to what type of operating system is used by the host.
 */
@Type
public enum HostType {
    /**
     * The host contains a full RHEL, CentOS or Fedora installation.
     */
    RHEL,

    /**
     * The host contains a small scaled version of RHEL, CentOS or Fedora, used solely to host virtual machines.
     */
    RHEV_H,

    /**
     * The host is NGN (Next Generation Node) - a new implementation of RHEV_H which is like RHEL, CentOS or Fedora installation.
     * The main difference between NGN and legacy RHEV-H is that NGN has a writeable file system and will handle its installation
     * instead of pushing RPMs to it by the engine in legacy RHEV-H.
     */
    OVIRT_NODE,
}
