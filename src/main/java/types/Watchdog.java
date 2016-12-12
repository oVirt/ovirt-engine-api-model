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
 * This type represents a watchdog configuration.
 *
 * @author Lukas Svaty <lsvaty@redhat.com>
 * @date 12 Dec
 * @status added
 */
@Type
public interface Watchdog extends Device {
    /**
     * Model of watchdog device. Currently supported only I6300ESB.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 12 Dec
     * @status added
     */
    WatchdogModel model();
    /**
     * Watchdog action to be performed when watchdog is triggered.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 12 Dec
     * @status added
     */
    WatchdogAction action();
}
