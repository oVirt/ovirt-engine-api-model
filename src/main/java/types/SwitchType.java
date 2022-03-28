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
package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Describes all switch types supported by the Manager.
 *
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 28 Dec 2016
 * @status updated_by_docs
 */
@Type
public enum SwitchType {
    /**
     * The native switch type.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 28 Nov 2016
     * @status updated_by_docs
     */
    LEGACY,

    /**
     * The Open vSwitch type.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 28 Nov 2016
     * @status updated_by_docs
     */
    OVS,
}
