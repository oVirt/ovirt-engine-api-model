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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing the policy of a Serial Number.
 *
 * @author Martin Betak <mbetak@redhat.com>
 * @author Eli Marcus <emarcus@redhat.com>
 * @date 10 Jun 2019
 * @status updated_by_docs
 */
@Type
public enum SerialNumberPolicy {
    /**
     * This policy is the legacy policy. It will use the Host ID as the Serial Number.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 11 Jul 2019
     * @status updated_by_docs
     */
    HOST,

    /**
     * This policy will use the Virtual Machine ID as the Serial Number.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 11 Jul 2019
     * @status updated_by_docs
     */
    VM,

    /**
     * This policy allows the user to provide an arbitrary string as the Serial Number.
     *
     * @author Martin Betak <mbetak@redhat.com>
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 10 Jun 2019
     * @status updated_by_docs
     */
    CUSTOM,

    /**
     * This policy is used to remove the Serial Number Policy, moving it to default: null.
     *
     * @author Liran Rotenberg <lrotenbe@redhat.com>
     * @date 3 Jun 2019
     * @status added
     * @since 4.4.0
     */
    NONE;
}
