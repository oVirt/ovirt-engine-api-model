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

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.SystemOption;

/**
 * A service that provides values of specific configuration option of the system.
 *
 * @author Miroslava Voglova <mvoglova@redhat.com>
 * @date 18 Sep 2017
 * @status added
 * @since 4.2
 */
@Service
public interface SystemOptionService {
    /**
     * Get the values of specific configuration option.
     *
     * For example to retrieve the values of configuration option `MigrationPolicies` send a request like this:
     *
     * ```http
     * GET /ovirt-engine/api/options/MigrationPolicies
     * ```
     *
     * The response to that request will be the following:
     *
     * ```xml
     * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * <system_option href="/ovirt-engine/api/options/MigrationPolicies" id="MigrationPolicies">
     *     <name>MigrationPolicies</name>
     *     <values>
     *         <system_option_value>
     *             <value>[{"id":{"uuid":"80554327-0569-496b-bdeb-fcbbf52b827b"},...}]</value>
     *             <version>4.2</version>
     *         </system_option_value>
     *         <system_option_value>
     *             <value>[{"id":{"uuid":"80554327-0569-496b-bdeb-fcbbf52b827b"},...}]</value>
     *             <version>4.3</version>
     *         </system_option_value>
     *         <system_option_value>
     *             <value>[{"id":{"uuid":"80554327-0569-496b-bdeb-fcbbf52b827b"},...}]</value>
     *             <version>4.4</version>
     *         </system_option_value>
     *         <system_option_value>
     *             <value>[{"id":{"uuid":"80554327-0569-496b-bdeb-fcbbf52b827b"},...}]</value>
     *             <version>4.5</version>
     *         </system_option_value>
     *         <system_option_value>
     *             <value>[{"id":{"uuid":"80554327-0569-496b-bdeb-fcbbf52b827b"},...}]</value>
     *             <version>4.6</version>
     *         </system_option_value>
     *         <system_option_value>
     *             <value>[{"id":{"uuid":"80554327-0569-496b-bdeb-fcbbf52b827b"},...}]</value>
     *             <version>4.7</version>
     *         </system_option_value>
     *     </values>
     * </system_option>
     * ```
     *
     * NOTE: The appropriate permissions are required to query configuration options. Some options can be queried
     * only by users with administrator permissions.
     *
     * [IMPORTANT]
     * ====
     * There is NO backward compatibility and no guarantee about the names or values of the options. Options may be
     * removed and their meaning can be changed at any point.
     *
     * We strongly discourage the use of this service for applications other than the ones that are released
     * simultaneously with the engine. Usage by other applications is not supported. Therefore there will be no
     * documentation listing accessible configuration options.
     * ====
     *
     * @author Miroslava Voglova <mvoglova@redhat.com>
     * @date 12 Jul 2022
     * @status updated
     * @since 4.2
     */
    interface Get{
        /**
         * The returned configuration option of the system.
         *
         * @author Miroslava Voglova <mvoglova@redhat.com>
         * @date 18 Sep 2017
         * @status added
         * @since 4.2
         */
        @Out SystemOption option();

        /**
         * Optional version parameter that specifies that only particular version of the configuration option
         * should be returned.
         * If this parameter isn't used then all the versions will be returned.
         *
         * For example, to get the value of the `MigrationPolicies` option but only for version `4.2` send
         * a request like this:
         *
         * ```http
         * GET /ovirt-engine/api/options/MigrationPolicies?version=4.2
         * ```
         *
         * The response to that request will be like this:
         *
         * ```xml
         * <system_option href="/ovirt-engine/api/options/MigrationPolicies" id="MigrationPolicies">
         *     <name>MigrationPolicies</name>
         *     <values>
         *         <system_option_value>
         *             <value>[{"id":{"uuid":"80554327-0569-496b-bdeb-fcbbf52b827b"},...}]</value>
         *             <version>4.2</version>
         *         </system_option_value>
         *     </values>
         * </system_option>
         * ```
         *
         * @author Miroslava Voglova <mvoglova@redhat.com>
         * @date 12 Jul 2022
         * @status updated
         * @since 4.2
         */
        @In String version();
    }
}
