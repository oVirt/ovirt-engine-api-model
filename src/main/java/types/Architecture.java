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

@Type
public enum Architecture {
    UNDEFINED,
    X86_64,
    PPC64,

    /**
     * IBM S390 CPU architecture.
     *
     * Needs to be specified for virtual machines and clusters running
     * on the S390 architecture.
     *
     * @author Viktor Mihajlovski <mihajlov@linux.vnet.ibm.com>
     * @date 22 Sep 2017
     * @status added
     * @since 4.2
     */
    S390;
}
