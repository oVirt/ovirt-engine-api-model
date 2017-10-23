/*
Copyright (c) 2017 Red Hat, Inc.

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
package mixins;

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Mixin;

@Mixin
public interface Follow {
    /**
     * Indicates which inner links should be _followed_. The objects referenced by these links will be fetched as part
     * of the current request. See <<documents/003_common_concepts/follow, here>> for details.
     *
     * @author Ori Liel <oliel@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 27 Oct 2017
     * @status updated_by_docs
     * @since 4.2
     */
    @In String follow();
}
