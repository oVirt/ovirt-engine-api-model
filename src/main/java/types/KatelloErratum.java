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

import java.util.Date;

/**
 * Type representing a Katello erratum.
 *
 * @author Moti Asayag <masayag@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface KatelloErratum extends Identified {

  /**
   * The title of the Katello erratum.
   *
   * @author Moti Asayag <masayag@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  String title();

  /**
   * The type of the Katello erratum.
   *
   * The supported types are `bugfix`, `enhancement` or `security`.
   *
   * @author Moti Asayag <masayag@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  String type();

  /**
   * The date when the Katello erratum was issued.
   *
   * @author Moti Asayag <masayag@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  Date issued();

  /**
   * The severity of the Katello erratum.
   *
   * The supported severities are `moderate`, `important` or `critical`.
   *
   * @author Moti Asayag <masayag@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  String severity();

  /**
   * The solution for the issue described by the Katello erratum.
   *
   * @author Moti Asayag <masayag@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  String solution();

  /**
   * The summary of the Katello erratum.
   *
   * @author Moti Asayag <masayag@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  String summary();

  /**
   * The list of packages which solve the issue reported by the Katello erratum.
   *
   * @author Moti Asayag <masayag@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  Package[] packages();

  /**
   * Reference to the host that the Katello erratum is assigned to.
   *
   * @author Moti Asayag <masayag@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  @Link Host host();

  /**
   * Reference to the virtual machine that the Katello erratum is assigned to.
   *
   * @author Moti Asayag <masayag@redhat.com>
   * @date 12 Dec 2016
   * @status added
   */
  @Link Vm vm();
}
