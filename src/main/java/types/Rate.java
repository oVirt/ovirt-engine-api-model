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
 * Determines maximum speed of consumption of bytes from random number generator device.
 *
 * @author Jakub Niedermertl <jniederm@redhat.com>
 * @date 13 Dec 2016
 * @state added
 */
@Type
public interface Rate {

    /**
     * Number of bytes allowed to consume per period.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 13 Dec 2016
     * @state added
     */
    Integer bytes();

    /**
     * Duration of one period in milliseconds.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 13 Dec 2016
     * @state added
     */
    Integer period();
}
