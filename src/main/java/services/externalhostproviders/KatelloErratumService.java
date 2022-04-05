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

package services.externalhostproviders;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.KatelloErratum;

/**
 * A service to manage a Katello erratum.
 *
 * @author Moti Asayag <masayag@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface KatelloErratumService {

    /**
     * Retrieves a Katello erratum.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/katelloerrata/123
     * ----
     *
     * You will receive response in XML like this one:
     *
     * [source,xml]
     * ----
     * <katello_erratum href="/ovirt-engine/api/katelloerrata/123" id="123">
     *   <name>RHBA-2013:XYZ</name>
     *   <description>The description of the erratum</description>
     *   <title>some bug fix update</title>
     *   <type>bugfix</type>
     *   <issued>2013-11-20T02:00:00.000+02:00</issued>
     *   <solution>Few guidelines regarding the solution</solution>
     *   <summary>Updated packages that fix one bug are now available for XYZ</summary>
     *   <packages>
     *     <package>
     *       <name>libipa_hbac-1.9.2-82.11.el6_4.i686</name>
     *     </package>
     *     ...
     *   </packages>
     * </katello_erratum>
     * ----
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface Get extends Follow {
        /**
         * Retrieves the representation of the Katello erratum.
         *
         * @author Moti Asayag <masayag@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out KatelloErratum erratum();
    }
}
