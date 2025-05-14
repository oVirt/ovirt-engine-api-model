/*
The oVirt Project - oVirt Engine Api Model

Copyright oVirt Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the Apache License, Version 2.0 is included with the program
in the file ASL2.
*/

package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Information describing the operating system. This is used for both virtual machines and hosts.
 *
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 17 Nov 2016
 * @status updated_by_docs
 */
@Type
public interface OperatingSystem {

    /**
     * Operating system name in human readable form.
     *
     * For example `Fedora` or `RHEL`. In general one of the names returned by
     * the xref:services/operating_systems[operating system] service.
     *
     * NOTE: Read only for hosts.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 24 Apr 2017
     * @state added
     */
    String type();

    /**
     * Configuration of the boot sequence.
     *
     * NOTE: Not used for hosts.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 24 Apr 2017
     * @state added
     */
    Boot boot();

    /**
     * Path to custom kernel on ISO storage domain if Linux operating system is used.
     *
     * For example `iso://vmlinuz-3.10.0-514.6.1.el7.x86_64`.
     *
     * NOTE: Not used for hosts.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 24 Apr 2017
     * @state added
     */
    String kernel();

    /**
     * Path to custom initial ramdisk on ISO storage domain if Linux operating system is used.
     *
     * For example `iso://initramfs-3.10.0-514.6.1.el7.x86_64.img`.
     *
     * NOTE: Not used for hosts.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @date 24 Apr 2017
     * @state added
     */
    String initrd();

    /**
     * Custom kernel parameters for starting the virtual machine if Linux operating system is used.
     *
     * NOTE: Not used for hosts.
     *
     * @author Jakub Niedermertl <jniederm@redhat.com>
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 4 Mar 2020
     * @status updated_by_docs
     */
    String cmdline();

    /**
     * The host kernel command line as reported by a running host.
     *
     * This is a read-only attribute. Attempts to change this attribute are silently ignored.
     *
     * NOTE: This attribute is currently only used for hosts.
     *
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 17 Nov 2016
     * @status updated_by_docs
     */
    String reportedKernelCmdline();

    /**
     * A custom part of the host kernel command line. This will be merged with the existing kernel command line.
     *
     * You must reinstall and then reboot the host to apply the changes implemented by this attribute.
     *
     * During each host deploy procedure, kernel parameters that were added
     * in the previous host deploy procedure are removed using
     * `grubby --update-kernel DEFAULT --remove-args <previous_custom_params>`, and the current
     * kernel command line customization is applied using
     * `grubby --update-kernel DEFAULT --args <custom_params>`. The Manager internally keeps track of the
     * last-applied kernel parameters customization.
     *
     * NOTE: This attribute is currently only used for hosts.
     *
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 17 Nov 2016
     * @status updated_by_docs
     */
    String customKernelCmdline();

    /**
     * Specific description of the operating system. Gives a more detailed type of the OS unlike the `type` attribute.
     * For example returns `CentOS Stream 9` instead of `RHEL`. Value aligns with the OS description in the engine UI.
     * 
     * NOTE: this attribute is currently only used for hosts.
     * 
     * @author Jasper Berton <jasper.berton@team.blue>
     * @date 26 May 2025
     * @status added
     * @since 4.6.1
     */
    String description();

    Version version();
}
