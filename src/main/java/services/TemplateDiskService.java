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
import mixins.Follow;
import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.Disk;
import types.StorageDomain;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;
import static org.ovirt.api.metamodel.language.ApiLanguage.or;

@Service
@Area("Storage")
public interface TemplateDiskService {
    /**
     * Copy the specified disk attached to the template to a specific storage domain.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @date 18 Jan 2017
     * @status added
     */
    interface Copy {
        @InputDetail
        default void inputDetail() {
            or(mandatory(storageDomain().id()), mandatory(storageDomain().name()));
        }
        @In StorageDomain storageDomain();
        /**
         * Indicates if the copy should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    interface Export {
        @InputDetail
        default void inputDetail() {
            or(mandatory(storageDomain().id()), mandatory(storageDomain().name()));
        }
        @In StorageDomain storageDomain();
        /**
         * Indicates if the export should be performed asynchronously.
         */
        @In Boolean async();

        /**
         * Indicates if the results should be filtered according to the permissions of the user.
         */
        @In Boolean filter();
    }

    interface Get extends Follow {
        @Out Disk disk();
    }

    interface Remove {
        /**
         * Indicates if the remove should be performed asynchronously.
         */
        @In Boolean async();
    }
}
