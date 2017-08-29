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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Represents an image entity.
 *
 * @author Shani Leviim <sleviim@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 25 Aug 2017
 * @status updated_by_docs
 */
@Type
public interface Image extends Identified {

    /**
     * The storage domain associated with this image.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 25 Aug 2017
     * @status updated_by_docs
     */
    @Link StorageDomain storageDomain();

    /**
     * The type of the image file.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 25 Aug 2017
     * @status updated_by_docs
     * @since 4.2.0
     */
    ImageFileType type();

    /**
     * The size of the image file.
     *
     * @author Shani Leviim <sleviim@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 25 Aug 2017
     * @status updated_by_docs
     * @since 4.2.0
     */
    Integer size();
}
