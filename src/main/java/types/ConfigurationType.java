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

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Configuration format types.
 *
 * @author Sharon Gratch <sgratch@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public enum ConfigurationType {
    /**
     * ConfigurationType of type OVF.
     *
     * OVF stands for Open Virtualization Format, and it is is an open standard for packaging and distributing of a 
     * virtual machine and software to be run in a virtual machine.
     *
     * @author Sharon Gratch <sgratch@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    OVF;
}
