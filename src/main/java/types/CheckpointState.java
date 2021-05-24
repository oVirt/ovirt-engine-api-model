/*
Copyright (c) 2021 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Type;

@Type
public enum CheckpointState {
    /**
     * The initial state of the checkpoint. It is set on entity creation.
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 24 May 2021
     * @status added
     * @since 4.4.7
     */
    CREATED,

    /**
     * The INVALID state set when a checkpoint cannot be used anymore for incremental backup
     * and should be removed (For example, after committing to an older VM snapshot).
     *
     * @author Eyal Shenitzky <eshenitz@redhat.com>
     * @date 24 May 2021
     * @status added
     * @since 4.4.7
     */
    INVALID,
}
