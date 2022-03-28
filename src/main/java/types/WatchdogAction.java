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
 * This type describes available watchdog actions.
 *
 * @author Lukas Svaty <lsvaty@redhat.com>
 * @date 12 Dec
 * @status added
 */
@Type
public enum WatchdogAction {
    /**
     * No action will be performed when watchdog action is triggered. However log message will still be generated.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 12 Dec
     * @status added
     */
    NONE,
    /**
     * Virtual machine will be rebooted when watchdog action is triggered.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 12 Dec
     * @status added
     */
    RESET,
    /**
     * Virtual machine will be powered off when watchdog action is triggered.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 12 Dec
     * @status added
     */
    POWEROFF,
    /**
     * Virtual machine will be paused when watchdog action is triggered.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 12 Dec
     * @status added
     */
    PAUSE,
    /**
     * Virtual machine process will get core dumped to the default path on the host.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 12 Dec
     * @status added
     */
    DUMP;
}
