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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * This interface is the base model for all types that represent objects with an identifier.
 *
 * @author Megan Lewis <melewis@redhat.com>
 * @date 2 Dec 2016
 * @status updated_by_docs
 */
@Type
public interface Identified {
    /**
     * A unique identifier.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 29 Nov 2016
     * @status added
     */
    String id();

    /**
     * A human-readable name in plain text.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 2 Dec 2016
     * @status updated_by_docs
     */
    String name();

    /**
     * A human-readable description in plain text.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @author Megan Lewis <melewis@redhat.com>
     * @date 2 Dec 2016
     * @status updated_by_docs
     */
    String description();

    /**
     * Free text containing comments about this object.
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     */
    String comment();
}
