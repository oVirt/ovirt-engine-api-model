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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Certificate;

/**
 * A service to view certificates for external provider.
 *
 * @author Piotr Kliczewski <pkliczew@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Service
@Area("Infrastructure")
public interface ExternalProviderCertificatesService {
    /**
     * Returns the chain of certificates presented by the external provider.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/externalhostproviders/123/certificates
     * ----
     *
     * And here is sample response:
     *
     * [source,xml]
     * ----
     * <certificates>
     *   <certificate id="789">...</certificate>
     *   ...
     * </certificates>
     * ----
     *
     * The order of the returned certificates is always guaranteed to be the sign order: the first is the
     * certificate of the server itself, the second the certificate of the CA that signs the first, so on.
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    interface List {
        /**
         * List containing certificate details.
         *
         * @author Piotr Kliczewski <pkliczew@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @Out Certificate[] certificates();

        /**
         * Sets the maximum number of certificates to return. If not specified all the certificates are returned.
         */
        @In Integer max();
    }

    /**
     * Reference to service that manages a specific certificate
     * for this external provider.
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    @Service ExternalProviderCertificateService certificate(String id);
}
