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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.StorageConnectionExtension;

import static org.ovirt.api.metamodel.language.ApiLanguage.optional;

@Service
@Area("Storage")
public interface StorageServerConnectionExtensionService {
    interface Get {
        @Out StorageConnectionExtension extension();
    }

    /**
     * Update a storage server connection extension for the given host.
     *
     * To update the storage connection `456` of host `123` send a request like this:
     *
     * [source]
     * ----
     * PUT /ovirt-engine/api/hosts/123/storageconnectionextensions/456
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <storage_connection_extension>
     *   <target>iqn.2016-01.com.example:mytarget</target>
     *   <username>myuser</username>
     *   <password>mypassword</password>
     * </storage_connection_extension>
     * ----
     *
     * @author Tal Nisan <tnisanredhat.com>
     * @date 15 Sep 2016
     * @status added
     * */
    interface Update {
        @InputDetail
        default void inputDetail() {
            optional(extension().password());
            optional(extension().target());
            optional(extension().username());
        }
        @In @Out StorageConnectionExtension extension();

        /**
         * Indicates if the update should be performed asynchronously.
         */
        @In Boolean async();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
