/*
Copyright (c) 2017 Red Hat, Inc.

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

package services;

import org.ovirt.api.metamodel.annotations.Service;

/**
 * Service that provides values of configuration options of the system.
 *
 * @author Miroslava Voglova <mvoglova@redhat.com>
 * @date 18 Sep 2017
 * @status added
 * @since 4.2
 */
@Service
public interface SystemOptionsService {
    /**
     * Returns a reference to the service that provides values of specific configuration option.
     *
     * @author Miroslava Voglova <mvoglova@redhat.com>
     * @date 18 Sep 2017
     * @status added
     * @since 4.2
     */
    @Service SystemOptionService option(String id);
}
