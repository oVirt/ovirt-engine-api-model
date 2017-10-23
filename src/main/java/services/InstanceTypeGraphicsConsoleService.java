/*
Copyright (c) 2016 Red Hat, Inc.

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

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.GraphicsConsole;

@Service
@Area("Virtualization")
public interface InstanceTypeGraphicsConsoleService {

    /**
     * Gets graphics console configuration of the instance type.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 31 Oct 2016
     * @status added
     */
    interface Get extends Follow {
        /**
         * The information about the graphics console of the instance type.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 25 Oct 2016
         * @status added
         */
        @Out GraphicsConsole console();
    }

    /**
     * Remove the graphics console from the instance type.
     *
     * @author Ondra Machacek <omachace@redhat.com>
     * @date 31 Oct 2016
     * @status added
     */
    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         *
         * @author Ondra Machacek <omachace@redhat.com>
         * @date 31 Oct 2016
         * @status added
         */
        @In Boolean async();
    }
}