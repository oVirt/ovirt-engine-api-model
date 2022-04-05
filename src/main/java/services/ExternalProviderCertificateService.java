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

package services;

import annotations.Area;
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Certificate;

/**
 * A service to view specific certificate for external provider.
 *
 * @author Piotr Kliczewski <pkliczew@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Service
@Area("Infrastructure")
public interface ExternalProviderCertificateService {

    /**
     * Get specific certificate.
     *
     * [source]
     * ----
     * GET /ovirt-engine/api/externalhostproviders/123/certificate/0
     * ----
     *
     * And here is sample response:
     *
     * [source,xml]
     * ----
     * <certificate id="0">
     *   <organization>provider.example.com</organization>
     *   <subject>CN=provider.example.com</subject>
     *   <content>...</content>
     * </certificate>
     * ----
     *
     * @author Piotr Kliczewski <pkliczew@redhat.com>
     * @date 24 Apr 2017
     * @status added
     *
     */
    interface Get extends Follow {
        /**
         * The details of the certificate.
         *
         * @author Piotr Kliczewski <pkliczew@redhat.com>
         * @date 24 Apr 2017
         * @status added
         */
        @Out Certificate certificate();
    }
}
