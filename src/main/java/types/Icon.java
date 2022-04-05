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
 * Icon of virtual machine or template.
 *
 * @author Jakub Niedermertl <jniederm@redhat.com>
 * @date 13 Dec 2016
 * @state added
 */
@Type
public interface Icon extends Identified {

    /**
     * Format of icon file.
     *
     * One of:
     *
     * * `image/jpeg`
     * * `image/png`
     * * `image/gif`
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 13 Dec 2016
     * @state added
     */
    String mediaType();

    /**
     * Base64 encode content of the icon file.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 13 Dec 2016
     * @state added
     */
    String data();
}
