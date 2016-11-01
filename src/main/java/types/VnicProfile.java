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

@Type
public interface VnicProfile extends Identified {
    Boolean portMirroring();
    CustomProperty[] customProperties();

    VnicPassThrough passThrough();

    /**
     * Marks, whether `pass_through` NIC is migratable or not.
     *
     * If `pass_through.mode` is set to `disabled` this option has no meaning, and it will be considered to be `true`.
     * If you omit this option from request, by default, this will be set to `true`.
     *
     * When migrating virtual machine, this virtual machine will be migrated only if all `pass_through` NICs are
     * flagged as `migratable`.
     *
     * @author Martin Mucha <mmucha@redhat.com>
     * @date 1 Nov 2016
     * @status added
     * @since 4.1
     */
    Boolean migratable();

    @Link Network network();
    @Link Qos qos();

    /**
     * Network filter will enhance the admin ability to manage the network packets traffic from/to the participated VMs.
     */
    @Link NetworkFilter networkFilter();
    @Link Permission[] permissions();
}
