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

package services.openstack;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.DataCenter;
import types.OpenStackNetwork;

@Service
@Area("Network")
public interface OpenstackNetworkService {
    interface Get extends Follow {
        @Out OpenStackNetwork network();
    }

    /**
     * This operation imports an external network into {product-name}.
     * The network will be added to the specified data center.
     *
     * @author Marcin Mirecki <mmirecki@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 19 April 2018
     * @status updated_by_docs
     */
    interface Import {

        /**
         * The data center into which the network is to be imported.
         * Data center is mandatory, and can be specified
         * using the `id` or `name` attributes. The rest of
         * the attributes will be ignored.
         *
         * NOTE: If <<types/open_stack_network_provider/attributes/auto_sync,`auto_sync`>> is
         * enabled for the provider, the network might be imported automatically. To
         * prevent this, automatic import can be disabled by setting the `auto_sync` to false,
         * and enabling it again after importing the network.
         *
         * @author Marcin Mirecki <mmirecki@redhat.com>
         * @author Dominik Holler <dholler@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 19 April 2018
         * @status updated_by_docs
         */
        @In DataCenter dataCenter();

        /**
         * Indicates if the import should be performed asynchronously.
         *
         * @author Marcin Mirecki <mmirecki@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 19 April 2018
         * @status updated_by_docs
         */
        @In Boolean async();
    }

    @Service OpenstackSubnetsService subnets();
}
