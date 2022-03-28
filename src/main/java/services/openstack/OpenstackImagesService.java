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

package services.openstack;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.OpenStackImage;

@Service
@Area("Storage")
public interface OpenstackImagesService {
    /**
     * Lists the images of a Glance image storage domain.
     *
     * The order of the returned list of images isn't guaranteed.
     *
     * @author Daniel Erez <derez@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    interface List extends Follow {
        @Out OpenStackImage[] images();

        /**
         * Sets the maximum number of images to return. If not specified all the images are returned.
         */
        @In Integer max();
    }

    /**
     * Returns a reference to the service that manages a specific image.
     *
     * @author @author Daniel Erez <derez@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    @Service OpenstackImageService image(String id);
}
