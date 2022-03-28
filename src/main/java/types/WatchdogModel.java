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
 * This type represents the watchdog model.
 *
 * @author Lukas Svaty <lsvaty@redhat.com>
 * @date 12 Dec
 * @status added
 */
@Type
public enum WatchdogModel {
    /**
     * PCI based watchdog model.
     *
     * Use the I6300ESB watchdog for x86_64 and PPC64 virtual
     * machines.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @author Viktor Mihajlovski <mihajlov@linux.vnet.ibm.com>
     * @date 22 Sep 2017
     * @status added
     */
    I6300ESB,

    /**
     * The watchdog model for S390X machines.
     *
     * S390X has an integrated watchdog facility that
     * is controlled via the DIAG288 instruction.
     * Use this model for S390X virtual machines.
     *
     * @author Viktor Mihajlovski <mihajlov@linux.vnet.ibm.com>
     * @date 09 Oct 2017
     * @status added
     * @since 4.2
     */
    DIAG288;
}
