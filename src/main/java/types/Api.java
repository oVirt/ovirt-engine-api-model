/*
Copyright (c) 2016 Red Hat, Inc.

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

import java.util.Date;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * This type contains the information returned by the root service of the API.
 *
 * To get that information send a request like this:
 *
 * ....
 * GET /ovirt-engine/api
 * ....
 *
 * The result will be like this:
 *
 * [source,xml]
 * ----
 * <api>
 *   <link rel="hosts" href="/ovirt-engine/api/hosts"/>
 *   <link rel="vms" href="/ovirt-engine/api/vms"/>
 *   ...
 *   <product_info>
 *     <name>oVirt Engine</name>
 *     <vendor>ovirt.org</vendor>
 *     <version>
 *       <build>0</build>
 *       <full_version>4.1.0_master</full_version>
 *       <major>4</major>
 *       <minor>1</minor>
 *       <revision>0</revision>
 *     </version>
 *   </product_info>
 *   <special_objects>
 *     <link rel="templates/blank" href="..."/>
 *     <link rel="tags/root" href="..."/>
 *   </special_objects>
 *   <summary>
 *     <vms>
 *       <total>10</total>
 *       <active>3</active>
 *     </vms>
 *     <hosts>
 *       <total>2</total>
 *       <active>2</active>
 *     </hosts>
 *     <users>
 *       <total>8</total>
 *       <active>2</active>
 *     </users>
 *     <storage_domains>
 *       <total>2</total>
 *       <active>2</active>
 *     </storage_domains>
 *   </summary>
 *   <time>2016-12-12T12:22:25.866+01:00</time>
 * </api>
 * ----
 *
 * @author Piotr Kliczewski <pkliczew@redhat.com>
 * @author Megan Lewis <melewis@redhat.com>
 * @date 12 Dec 2016
 * @status updated_by_docs
 */
@Type
public interface Api {
    /**
     * References to special objects, such as the blank template and the root of the hierarchy of tags.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     */
    SpecialObjects specialObjects();

    /**
     * Information about the product, such as its name, the name of the vendor, and the version.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 2 Dec 2016
     * @status updated_by_docs
     */
    ProductInfo productInfo();

    /**
     * A summary containing the total number of relevant objects, such as virtual machines, hosts, and storage domains.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016
     * @status updated_by_docs
     */
    ApiSummary summary();

    /**
     * The date and time when this information was generated.
     *
     * @author Megan Lewis <melewis@redhat.com>
     * @date 15 Nov 2016
     * status updated_by_docs
     */
    Date time();

    /**
     * Reference to the authenticated user.
     *
     * The authenticated user is the user whose credentials were verified in order to accept the current request. In the
     * current version of the system the authenticated user and the effective user are always the same. In the future,
     * when support for user impersonation is introduced, they will be potentially different.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 1 Nov 2017
     * @status added
     * @since 4.2
     */
    @Link User authenticatedUser();

    /**
     * Reference to the effective user.
     *
     * The effective user is the user whose permissions apply during the current request. In the current version of the
     * system the authenticated user and the effective user are always the same. In the future, when support for user
     * impersonation is introduced, they will be potentially different.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 1 Nov 2017
     * @status added
     * @since 4.2
     */
    @Link User effectiveUser();
}
