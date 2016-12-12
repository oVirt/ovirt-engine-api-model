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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Product information.
 *
 * The entry point contains a `product_info` element to help an API user determine the legitimacy of the
 * {product-name} environment. This includes the name of the product, the `vendor` and the `version`.
 *
 * Verify a genuine {product-name} environment
 *
 * The follow elements identify a genuine {product-name} environment:
 *
 * [source]
 * ----
 * <api>
 * ...
 * <product_info>
 *   <name>oVirt Engine</name>
 *   <vendor>ovirt.org</vendor>
 *   <version>
 *     <build>0</build>
 *     <full_version>4.1.0_master</full_version>
 *     <major>4</major>
 *     <minor>1</minor>
 *     <revision>0</revision>
 *   </version>
 * </product_info>
 * ...
 * </api>
 * ----
 *
 * @author Piotr Kliczewski <pkliczew@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface ProductInfo {
    /**
     * The name of the product, for example `oVirt Engine`.
     */
    String name();

    /**
     * The name of the vendor, for example `ovirt.org`.
     */
    String vendor();

    /**
     * The version number of the product.
     */
    Version version();
}
