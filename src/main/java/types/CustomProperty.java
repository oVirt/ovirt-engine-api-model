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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Custom property representation.
 *
 * @author Irit Goihman <igoihman@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public interface CustomProperty {
    /**
     * Property name.
     *
     * @author Irit Goihman <igoihman@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String name();

    /**
     * Property value.
     *
     * @author Irit Goihman <igoihman@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String value();

    /**
     * A regular expression defining the available values a custom property can get.
     *
     * @author Irit Goihman <igoihman@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    String regexp();
}
