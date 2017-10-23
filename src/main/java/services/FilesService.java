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
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.File;


/**
 * Provides a way for clients to list available files.
 *
 * This service is specifically targeted to ISO storage domains, which contain ISO images and virtual floppy disks
 * (VFDs) that an administrator uploads.
 *
 * The addition of a CD-ROM device to a virtual machine requires an ISO image from the files of an ISO storage domain.
 *
 * @author Maor Lipchuk <mlipchuk@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 14 Jul 2017
 * @status updated_by_docs
 */
@Service
@Area("Virtualization")
public interface FilesService {
    /**
     * Returns the list of ISO images and virtual floppy disks available in the storage domain. The order of
     * the returned list is not guaranteed.
     *
     * If the `refresh` parameter is `false`, the returned list may not reflect recent changes to the storage domain;
     * for example, it may not contain a new ISO file that was recently added. This is because the
     * server caches the list of files to improve performance. To get the very latest results, set the `refresh`
     * parameter to `true`.
     *
     * The default value of the `refresh` parameter is `true`, but it can be changed using the configuration value
     * `ForceRefreshDomainFilesByDefault`:
     *
     * [source]
     * ----
     * # engine-config -s ForceRefreshDomainFilesByDefault=false
     * ----
     *
     * IMPORTANT: Setting the value of the `refresh` parameter to `true` has an impact on the performance of the
     * server. Use it only if necessary.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 14 Jul 2017
     * @status updated_by_docs
     */
    interface List extends Follow {
        @Out File[] file();

        /**
         * Sets the maximum number of files to return. If not specified, all the files are returned.
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 14 Jul 2017
         * @status updated_by_docs
         */
        @In Integer max();

        /**
         * A query string used to restrict the returned files.
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 14 Jul 2017
         * @status updated_by_docs
         */
        @In String search();

        /**
         * Indicates if the search performed using the `search` parameter should take case into
         * account. The default value is `true`.
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 14 Jul 2017
         * @status updated_by_docs
         */
        @In Boolean caseSensitive();

        /**
         * Indicates whether the list of files should be refreshed from the storage domain, rather than showing cached
         * results that are updated at certain intervals.
         *
         * @author Tal Nisan <tnisan@redhat.com>
         * @author Tahlia Richardson <trichard@redhat.com>
         * @date 14 Jul 2017
         * @status updated_by_docs
         * @since 4.1.5
         */
        @In Boolean refresh();
    }

    @Service FileService file(String id);
}
