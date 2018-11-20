/*
Copyright (c) 2018 Red Hat, Inc.

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
 * The vGPU placement strategy.
 *
 * It can either put vGPUs on the first available physical
 * cards, or spread them over multiple physical cards.
 *
 * @author Milan Zamazal <mzamazal@redhat.com>
 * @author Tahlia Richardson <trichard@redhat.com>
 * @date 26 Nov 2018
 * @status updated_by_docs
 * @since 4.3
 */
@Type
public enum VgpuPlacement {

   /**
     * Use consolidated placement. Each vGPU is placed on the first physical card with available space.
     *
     * This is the default placement, utilizing all available space on the physical cards.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 26 Nov 2018
     * @status updated_by_docs
     * @since 4.3
     */
    CONSOLIDATED,

   /**
     * Use separated placement. Each vGPU is placed on a separate physical card, if possible.
     *
     * This can be useful for improving vGPU performance.
     *
     * @author Milan Zamazal <mzamazal@redhat.com>
     * @author Tahlia Richardson <trichard@redhat.com>
     * @date 26 Nov 2018
     * @status updated_by_docs
     * @since 4.3
     */
    SEPARATED;
}
