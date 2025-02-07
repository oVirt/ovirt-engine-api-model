/*
The oVirt Project - oVirt Engine Api Model

Copyright oVirt Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the Apache License, Version 2.0 is included with the program
in the file ASL2.
*/

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Describes the capabilities supported by a specific cluster level.
 *
 * @author Byron Gravenorst <bgraveno@redhat.com>
 * @date 3 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface ClusterLevel extends Identified {
    /**
     * The CPU types supported by this cluster level.
     *
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 3 Nov 2016
     * @status updated_by_docs
     */
    CpuType[] cpuTypes();

    /**
     * The permits supported by this cluster level.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 3 Nov 2016
     * @status updated_by_docs
     */
    Permit[] permits();

    /**
     * The additional features supported by this cluster level.
     *
     * @author Sahina Bose <sabose@redhat.com>
     * @date 31 Jul 2017
     * @status added
     * @since 4.1.6
     */
    @Link ClusterFeature[] clusterFeatures();
}
