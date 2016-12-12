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
 * This type represents the storage related
 * configuration in the fencing policy.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 06 Dec 2016
 * @status added
 */
@Type
public interface SkipIfSdActive {
    /**
     * If enabled, we will skip fencing in case the host
     * maintains its lease in the storage. It means that
     * if the host still has storage access then it won't
     * get fenced.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 06 Dec 2016
     * @status added
     */
    Boolean enabled();
}
