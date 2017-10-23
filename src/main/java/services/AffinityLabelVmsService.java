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

package services;

import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import types.Vm;

/**
 * This service represents list of vms that have a specific
 * label when accessed through the affinitylabels/vms
 * subcollection.
 */
@Service
public interface AffinityLabelVmsService {
    /**
     * Add a label to a vm.
     */
    interface Add {
        @In @Out Vm vm();
    }

    /**
     * List all virtual machines with the label.
     *
     * The order of the returned virtual machines isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernancez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out Vm[] vms();
    }

    /**
     * A link to the specific label-vm assignment to
     * allow label removal.
     */
    @Service AffinityLabelVmService vm(String id);
}
