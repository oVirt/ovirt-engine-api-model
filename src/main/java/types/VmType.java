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
 * Type representing what the virtual machine is optimized for.
 *
 * @author Arik Hadas <ahadas@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public enum VmType {
    /**
     * The virtual machine is intended to be used as a desktop.
     * Currently, its implication is that a sound device will be automatically added to the virtual machine.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    DESKTOP,

     /**
     * The virtual machine is intended to be used as a server.
     * Currently, its implication is that a sound device will not be automatically added to the virtual machine.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    SERVER;
}
