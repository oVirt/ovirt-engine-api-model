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

package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing a version of a virtual machine template.
 *
 * @author Arik Hadas <ahadas@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public interface TemplateVersion {
    /**
     * The index of this version in the versions hierarchy of the template.
     * The index 1 represents the original version of a template that is also called base version.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    Integer versionNumber();

    /**
     * The name of this version.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    String versionName();

    /**
     * References the template that this version is associated with.
     *
     * @author Arik Hadas <ahadas@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    @Link Template baseTemplate();
}
