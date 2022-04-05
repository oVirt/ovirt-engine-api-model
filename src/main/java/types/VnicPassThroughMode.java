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
package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Describes whether the vNIC is to be implemented as a pass-through device or a virtual one.
 *
 * @author Megan Lewis <melewis@redhat.com>
 * @date 17 Jan 17
 * @status updated_by_docs
 */
 @Type
public enum VnicPassThroughMode {
    /**
     * To be implemented as a pass-through device.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 20 Feb 17
     * @status updated_by_docs
     */
    ENABLED,

    /**
     * To be implemented as a virtual device.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 20 Feb 17
     * @status updated_by_docs
     */
    DISABLED;
}
