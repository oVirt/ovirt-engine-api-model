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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * This type represents all groups in the directory service.
 *
 * @author Irit Goihman <igoihman@redhat.com>
 * @date 12 Dec 2016
 * @status added
 *
 */
@Type
public interface Group extends Identified {
  /**
   * The containing directory service domain id.
   *
   * @author Irit Goihman <igoihman@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  String domainEntryId();

  /**
   * Namespace where group resides.
   *
   * @author Irit Goihman <igoihman@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  String namespace();

  /**
   * A link to the domain containing this group.
   *
   * @author Irit Goihman <igoihman@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  @Link Domain domain();

  /**
   * A link to the roles sub-collection for roles attached to this group.
   *
   * Used only to represent the initial role assignments for a new group; thereafter, modification of role assignments
   * is only supported via the `roles` sub-collection.
   *
   * @author Irit Goihman <igoihman@redhat.com>
   * @author Tahlia Richardson <trichard@redhat.com>
   * @date 12 Dec 2016
   * @status updated_by_docs
   */
  @Link Role[] roles();

  /**
   * A link to the permissions sub-collection for permissions attached to this group.
   *
   * @author Irit Goihman <igoihman@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  @Link Permission[] permissions();

  /**
   * A link to the tags sub-collection for tags attached to this group.
   *
   * @author Irit Goihman <igoihman@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  @Link Tag[] tags();
}
