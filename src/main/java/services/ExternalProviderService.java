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

package services;

import annotations.Area;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Service;
import types.Certificate;

/**
 * Provides capability to manage external providers.
 *
 * @author Piotr Kliczewski <pkliczew@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Service
@Area("Infrastructure")
public interface ExternalProviderService {
    interface ImportCertificates {
        @In Certificate[] certificates();
    }

    /**
     * In order to test connectivity for external provider we need
     * to run following request where 123 is an id of a provider.
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/externalhostproviders/123/testconnectivity
     * ----
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface TestConnectivity {
        /**
         * Indicates if the test should be performed asynchronously.
         */
        @In Boolean async();
    }

    /**
     * A service to view certificates for this external provider.
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Service ExternalProviderCertificatesService certificates();
}
