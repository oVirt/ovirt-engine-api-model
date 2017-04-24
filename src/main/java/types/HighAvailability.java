/*
Copyright (c) 2017 Red Hat, Inc.

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
 * Type representing high availability of a virtual machine.
 *
 * @author Lukas Svaty <lsvaty@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public interface HighAvailability {
    /**
     * Define if the virtual machine should be consider highly available.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    Boolean enabled();

    /**
     * Indicates the priority of the virtual machine inside the run and migration queues.
     *
     * Virtual machines with higher priorities will be started and migrated before virtual machines with lower
     * priorities.
     *
     * The value is an integer between 0 and 100. The higher the value, the higher the priority.
     *
     * The graphical user interface (GUI) does not allow specifying all the possible values, instead
     * it only allows you to select _Low_, _Medium_ or _High_. When the value is set using the API, the GUI
     * will set the label as follows:
     *
     * |===
     * |API Value |GUI Label
     *
     * |0 - 25 |Low
     * |26 - 74 |Medium
     * |75 - 100 |High
     *
     * |===
     *
     * When the label is selected using the GUI, the value in the API will be set as follows:
     *
     * |===
     * |GUI Label |API Value
     *
     * |Low |1
     * |Medium |50
     * |High |100
     *
     * |===
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Byron Graveorst <bgraveno@redhat.com>
     * @date 31 Mar 2017
     * @status updated_by_docs
     */
    Integer priority();
}
