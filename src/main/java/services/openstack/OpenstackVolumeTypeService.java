/*
Copyright (c) 2015-2021 Red Hat, Inc.

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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.OpenStackVolumeType;

/**
 * Openstack Volume (Cinder) integration has been replaced by Managed Block Storage.
 *
 * @author Shani Leviim <sleviim@redhat.com>
 * @date 14 Oct 2021
 * @status added
 */

@Deprecated
@Service
@Area("Storage")
public interface OpenstackVolumeTypeService {
    interface Get extends Follow {
        @Out OpenStackVolumeType type();
    }
}
