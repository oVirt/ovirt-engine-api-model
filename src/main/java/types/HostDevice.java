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
public interface HostDevice extends Identified {
    String capability();
    Product product();
    Vendor vendor();
    Integer iommuGroup();
    HostDevice physicalFunction();
    Integer virtualFunctions();
    Boolean placeholder();

    /**
     * The name of the driver this device is bound to.
     *
     * For example: `pcieport` or `uhci_hcd`.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @status updated_by_docs
     * @since 4.1.3
     */
    String driver();

    /**
    * List of all supported mdev types on the physical device,
    *
    * @author Lucia Jelinkova <ljelinko@redhat.com>
    * @date 18 Nov 2019
    * @status added
    * @since 4.4
    */
    MDevType[] mDevTypes();

    @Link Host host();
    @Link HostDevice parentDevice();
    @Link Vm vm();
}
