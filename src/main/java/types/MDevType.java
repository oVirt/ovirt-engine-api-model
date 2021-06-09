/*
Copyright (c) 2019 Red Hat, Inc.

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
 *
 * Mediated device is a software device that allows to divide physical device's resources.
 *
 * See https://libvirt.org/drvnodedev.html#MDEV[Libvirt-MDEV] for further details.
 *
 * @author Lucia Jelinkova <ljelinko@redhat.com>
 * @date 18 Nov 2019
 * @status added
 * @since 4.4
 */
@Type
public interface MDevType {
    /**
     * MDev type name.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @date 18 Nov 2019
     * @status added
     * @since 4.4
     */
    String name();

    /**
     * MDev type human readable name.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @date 09 Jun 2021
     * @status added
     * @since 4.4.7
     */
    String humanReadableName();

    /**
     * MDev type available instances count.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @date 18 Nov 2019
     * @status added
     * @since 4.4
     */
    Integer availableInstances();

    /**
     * MDev type description.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @date 18 Nov 2019
     * @status added
     * @since 4.4
     */
    String description();
}
