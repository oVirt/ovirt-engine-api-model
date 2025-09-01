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

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.GraphicsConsole;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

@Service
@Area("Virtualization")
public interface VmGraphicsConsolesService {

    /**
     * Add new graphics console to the virtual machine.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 31 Oct 2016
     * @status added
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(console().protocol());
        }
        @In @Out GraphicsConsole console();
    }

    /**
     * Lists all the configured graphics consoles of the virtual machine.
     *
     * IMPORTANT: By default, when the `current` parameter is not specified, the data returned
     * corresponds to the next execution of the virtual machine. In the current implementation of
     * the system this means that the `address` and `port` attributes will not be populated because
     * the system does not know what address and port will be used for the next execution. Since in most
     * cases those attributes are needed, it is strongly advised to aways explicitly include the
     * `current` parameter with the value `true`.
     *
     * The order of the returned list of graphics consoles is not guaranteed.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 25 Jul 2017
     * @status updated_by_docs
     */
    interface List extends Follow {

        /**
         * The list of graphics consoles of the virtual machine.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 31 Oct 2016
         * @status added
         */
        @Out GraphicsConsole[] consoles();

        /**
         * Sets the maximum number of consoles to return. If not specified all the consoles are returned.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 31 Oct 2016
         * @status added
         */
        @In Integer max();

        /**
         * Specifies if the data returned should correspond to the next execution of
         * the virtual machine, or to the current execution.
         *
         * IMPORTANT: The `address` and `port` attributes will not be populated unless the value is
         * `true`.
         *
         * For example, to get data for the current execution of the virtual machine, including the
         * `address` and `port` attributes, send a request like this:
         *
         * ```http
         * GET /ovirt-engine/api/vms/123/graphicsconsoles?current=true HTTP/1.1
         * ```
         *
         * The default value is `false`.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @author Juan Hernandez <juan.hernandez@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 25 Jul 2017
         * @status updated_by_docs
         */
        @In Boolean current();
    }

    /**
     * Returns a reference to the service that manages a specific virtual machine graphics console.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 31 Oct 2016
     * @status added
     */
    @Service VmGraphicsConsoleService console(String id);
}
