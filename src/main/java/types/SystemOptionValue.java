/*
Copyright (c) 2017 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing a pair of value and version of a configuration option.
 *
 * @author Miroslava Voglova <mvoglova@redhat.com>
 * @date 18 Sep 2017
 * @status added
 * @since 4.2
 */
@Type
public interface SystemOptionValue {
    /**
     * Configuration option's value for specific version.
     *
     * @author Miroslava Voglova <mvoglova@redhat.com>
     * @date 18 Sep 2017
     * @status added
     * @since 4.2
     */
    String value();

    /**
     * Configuration option's version.
     *
     * @author Miroslava Voglova <mvoglova@redhat.com>
     * @date 18 Sep 2017
     * @status added
     * @since 4.2
     */
    String version();
}
