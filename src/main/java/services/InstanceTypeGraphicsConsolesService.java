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
public interface InstanceTypeGraphicsConsolesService {

    /**
     * Add new graphics console to the instance type.
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
     * Lists all the configured graphics consoles of the instance type.
     *
     * The order of the returned list of graphics consoles isn't guaranteed.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 31 Oct 2016
     * @status added
     */
    interface List extends Follow {

        /**
         * The list of graphics consoles of the instance type.
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
    }

    /**
     * Returns a reference to the service that manages a specific instance type graphics console.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 31 Oct 2016
     * @status added
     */
    @Service InstanceTypeGraphicsConsoleService console(String id);
}
