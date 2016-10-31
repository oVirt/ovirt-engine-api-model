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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

@Type
public interface HostStorage extends Identified {
  // Common to all types of storages:
  String address();
  StorageType type();

  // For NFS:
  String path();
  String mountOptions();
  String vfsType();
  NfsVersion nfsVersion();

  /**
   * The time in tenths of a second to wait for a response before retrying NFS requests. The value must be in the
   * range of 0 to 65535. For more details see the description of the `timeo` mount option in the `nfs` man page.
   *
   * @author Juan Hernandez <juan.hernandez@redhat.com>
   * @author Tahlia Richardson <trichard@redhat.com>
   * @date 31 Oct 2016
   * @status updated_by_docs
   */
  Integer nfsTimeo();

  /**
   * The number of times to retry a request before attempting further recovery actions. The value must be in the
   * range of 0 to 65535. For more details see the description of the `retrans` mount option in the `nfs` man page.
   *
   * @author Juan Hernandez <juan.hernandez@redhat.com>
   * @author Tahlia Richardson <trichard@redhat.com>
   * @date 31 Oct 2016
   * @status updated_by_docs
   */
  Integer nfsRetrans();

  // For iSCSI:
  Integer port();
  String target();
  String username();
  String password();
  String portal();
  LogicalUnit[] logicalUnits();
  VolumeGroup volumeGroup();
  Boolean overrideLuns();

  // Link to the host:
  @Link Host host();
}
