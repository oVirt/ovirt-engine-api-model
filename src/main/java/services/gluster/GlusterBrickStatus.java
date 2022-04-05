/*
Copyright (c) 2016 Red Hat, Inc.

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

package services.gluster;

import org.ovirt.api.metamodel.annotations.Type;

@Type
public enum GlusterBrickStatus {
    /**
     * Brick is in `up` state, the data can be stored or retrieved from it.
     */
    UP,

    /**
     * Brick is in `down` state, the data cannot be stored or retrieved from it.
     */
    DOWN,

    /**
     * When the status cannot be determined due to host being non-responsive.
     */
    UNKNOWN;
}