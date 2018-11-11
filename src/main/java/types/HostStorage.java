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

  // For managed block storage:
  /**
   * The driver name as expected by cinder.
   * For available drivers and their names, see https://docs.openstack.org/cinder/rocky/configuration/block-storage/volume-drivers.html[Volume drivers].
   *
   * @author Benny Zlotnik <bzlotnik@redhat.com>
   * @author Steve Goodman <sgoodman@redhat.com>
   * @since 4.3
   * @date 18 Dec 2018
   * @status updated_by_docs
   */
  String driverName();

  /**
   * The options to be passed when creating a storage domain
   * using a cinder driver.
   *
   *
   * For example (Kaminario backend):
   * [source]
   * ----
   * POST /ovirt-engine/api/storagedomains/
   * ----
   *
   * [source,xml]
   * ----
   * <storage_domain>
   *  <name>kamniraio-cinder</name>
   *  <type>managed_block_storage</type>
   *  <storage>
   *    <type>managed_block_storage</type>
   *    <driver_name>kaminario</driver_name>
   *    <driver_options>
   *      <property>
   *        <name>san_ip</name>
   *        <value>192.168.1.1</value>
   *      </property>
   *      <property>
   *        <name>san_login</name>
   *        <value>username</value>
   *      </property>
   *      <property>
   *        <name>san_password</name>
   *        <value>password</value>
   *      </property>
   *      <property>
   *        <name>use_multipath_for_image_xfer</name>
   *        <value>true</value>
   *      </property>
   *      <property>
   *        <name>volume_driver</name>
   *        <value>cinder.volume.drivers.kaminario.kaminario_iscsi.KaminarioISCSIDriver</value>
   *      </property>
   *    </driver_options>
   *  </storage>
   *  <host>
   *    <name>host</name>
   *    </host>
   * </storage_domain>
   * ----
   *
   * @author Benny Zlotnik <bzlotnik@redhat.com>
   * @author Steve Goodman <sgoodman@redhat.com>
   * @date 18 Dec 2018
   * @since 4.3
   * @status updated_by_docs
   */
  Property[] driverOptions();


  // Link to the host:
  @Link Host host();
}
