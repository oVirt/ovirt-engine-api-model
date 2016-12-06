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

@Type
public interface SkipIfConnectivityBroken {
    /**
     * If enabled, we will not fence a host
     * in case more than a configurable percentage
     * of hosts in the cluster lost connectivity as well.
     * This comes to prevent fencing _storm_ in cases
     * where there is a global networking issue in the
     * cluster.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 06 Dec 2016
     * @status added
     */
    Boolean enabled();

    /**
     * Threshold for connectivity testing.
     * If at least the threshold percentage of hosts in the cluster
     * lost connectivity then fencing will not take place.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 06 Dec 2016
     * @status added
     */
    Integer threshold();
}
