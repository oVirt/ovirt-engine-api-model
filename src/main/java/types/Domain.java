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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * This type represents a directory service domain.
 *
 * @author Irit Goihman <igoihman@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface Domain extends Identified {
    User user();

    /**
     * A reference to all groups in the directory service.
     *
     * @author Irit Goihman <igoihman@redhat.com>
     * @date 12 Dec 2016
     * @status added
     *
     */
    @Link Group[] groups();

    /**
     * A reference to a list of all users in the directory service.
     * This information is used to add new users to the {product-name} environment.
     *
     * @author Irit Goihman <igoihman@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link User[] users();
}
