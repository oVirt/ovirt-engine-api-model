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
 * Information describing the operating system. Used for virtual machines and hosts.
 */
@Type
public interface OperatingSystem {
    String type();
    Boot boot();
    String kernel();
    String initrd();
    String cmdline();

    /**
     * Host kernel command line as reported by a running host.
     *
     * Read-only attribute. Attempts to change this attribute are silently ignored.
     *
     * NOTE: This attribute is currently only used for hosts.
     */
    String reportedKernelCmdline();

    /**
     * A custom part of the host kernel command line. This will be merged with the existing kernel command line.
     *
     * You must re-install and then reboot the host to apply the changes implemented by this attribute.
     *
     * Parameters merging: During each host deploy procedure, kernel parameters that were added
     * in the previous host deploy procedure are removed using
     * `grubby --update-kernel DEFAULT --remove-args <previous_custom_params>` and the current
     * kernel command line customization is applied using
     * `grubby --update-kernel DEFAULT --args <custom_params>. The Engine internally keeps track of the
     * last applied kernel parameters customization.
     *
     * NOTE: This attribute is currently only used for hosts.
     */
    String customKernelCmdline();
    Version version();
}
