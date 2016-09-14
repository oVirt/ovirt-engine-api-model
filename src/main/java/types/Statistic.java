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
 * A generic type used for all kinds of statistics.
 *
 * @author Shmuel Melamud <smelamud@redhat.com>
 * @date 14 Sep 2016
 * @status added
 */
@Type
public interface Statistic extends Identified {

    /**
     * A data set that contains `datum`.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    Value[] values();

    /**
     * The type of statistic measures.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    StatisticKind kind();

    /**
     * The data type for the statistical values that follow.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    ValueType type();

    /**
     * The unit or rate to measure of the statistical values.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    StatisticUnit unit();


    /**
     * A relationship to the containing `disk` resource.
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @date 14 Sep 2016
     * @status added
     */
    @Link Disk disk();

    @Link Host host();
    @Link HostNic hostNic();
    @Link NumaNode hostNumaNode();
    @Link Nic nic();
    @Link Vm vm();
    @Link GlusterBrick brick();
    @Link Step step();
    @Link GlusterVolume glusterVolume();
}
