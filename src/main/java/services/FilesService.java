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
import types.File;


/**
 * Provides a way for clients to list available files.
 *
 * This services is specifically targeted to ISO storage domains, which contain ISO images and virtual floppy disks
 * (VFDs) that an administrator uploads.
 *
 * The addition of a CDROM device to a virtual machine requires an ISO image from the files of an ISO storage domain.
 *
 * @author Maor Lipchuk <mlipchuk@redhat.com>
 * @date 14 Sep 2016
 * @status added
 */
@Service
@Area("Virtualization")
public interface FilesService {
    interface List {
        @Out File[] file();

        /**
         * Sets the maximum number of files to return. If not specified all the files are returned.
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned files.
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should be performed taking case into
         * account. The default value is `true`, which means that case is taken into account. If you want to search
         * ignoring case set it to `false`.
         */
        @In Boolean caseSensitive();
    }

    @Service FileService file(String id);
}
