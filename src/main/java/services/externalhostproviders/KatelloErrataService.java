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

package services.externalhostproviders;

import annotations.Area;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.KatelloErratum;

/**
 * A service to manage Katello errata.
 * The information is retrieved from Katello.
 *
 * @author Moti Asayag <masayag@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Service
@Area("Infrastructure")
public interface KatelloErrataService {

    /**
     * Retrieves the representation of the Katello errata.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/katelloerrata
     * ----
     *
     * You will receive response in XML like this one:
     *
     * [source,xml]
     * ----
     * <katello_errata>
     *   <katello_erratum href="/ovirt-engine/api/katelloerrata/123" id="123">
     *     <name>RHBA-2013:XYZ</name>
     *     <description>The description of the erratum</description>
     *     <title>some bug fix update</title>
     *     <type>bugfix</type>
     *     <issued>2013-11-20T02:00:00.000+02:00</issued>
     *     <solution>Few guidelines regarding the solution</solution>
     *     <summary>Updated packages that fix one bug are now available for XYZ</summary>
     *     <packages>
     *       <package>
     *         <name>libipa_hbac-1.9.2-82.11.el6_4.i686</name>
     *       </package>
     *       ...
     *     </packages>
     *   </katello_erratum>
     *   ...
     * </katello_errata>
     * ----
     *
     * The order of the returned list of erratum isn't guaranteed.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    interface List {
        /**
         * A representation of Katello errata.
         *
         * @author Moti Asayag <masayag@redhat.com>
         * @date 12 Dec 2016
         * @status added
         */
        @Out KatelloErratum[] errata();

        /**
         * Sets the maximum number of errata to return. If not specified all the errata are returned.
         */
        @In Integer max();
    }

    /**
     * Reference to the Katello erratum service.
     * Use this service to view the erratum by its id.
     *
     * @author Moti Asayag <masayag@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Service KatelloErratumService katelloErratum(String id);
}
