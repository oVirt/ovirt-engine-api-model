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
 * This type contains information about the product, including its name, the name of the vendor, and the version.
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
