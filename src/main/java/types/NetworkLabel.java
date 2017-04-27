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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents a label which can be added to a host network interface and to a network.
 * The label binds the network to the host network interface by the label `id`.
 *
 * @author Marcin Mirecki <mmirecki@redhat.com>
 * @author Alona Kaplan <alkaplan@redhat.com>
 * @date 24 April 2017
 * @status added
 */
@Type
public interface NetworkLabel extends Identified {
    /**
     * A reference to the network which contains this label.
     *
     * @author Alona Kaplan <alkaplan@redhat.com>
     * @date 24 April 2017
     * @status added
     */
    @Link Network network();

    /**
     * A reference to the host network interface which contains this label.
     *
     * @author Alona Kaplan <alkaplan@redhat.com>
     * @date 24 April 2017
     * @status added
     */
    @Link HostNic hostNic();
}
