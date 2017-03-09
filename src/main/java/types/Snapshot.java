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

import java.util.Date;

/**
 * Represents a snapshot object.
 *
 * Example XML representation:
 *
 * [source,xml]
 * ----
 * <snapshot id="456" href="/ovirt-engine/api/vms/123/snapshots/456">
 *   <actions>
 *     <link rel="restore" href="/ovirt-engine/api/vms/123/snapshots/456/restore"/>
 *   </actions>
 *   <vm id="123" href="/ovirt-engine/api/vms/123"/>
 *   <description>Virtual Machine 1 - Snapshot A</description>
 *   <type>active</type>
 *   <date>2010-08-16T14:24:29</date>
 *   <persist_memorystate>false</persist_memorystate>
 * </snapshot>
 * ----
 *
 * @author Daniel Erez <derez@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 28 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface Snapshot extends Vm {
    Date date();
    SnapshotStatus snapshotStatus();

    /**
     * Indicates if the content of the memory of the virtual machine is included in the snapshot.
     *
     * When a snapshot is created the default value is `true`.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 9 Mar 2017
     * @status added
     */
    Boolean persistMemorystate();

    SnapshotType snapshotType(); //revisit. Had to be done because of VmBase.type changed to VmType

    @Link Vm vm();
}
