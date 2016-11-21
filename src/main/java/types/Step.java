/*
Copyright (c) 2015-2016 Red Hat, Inc.

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

import java.util.Date;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

@Type
public interface Step extends Identified {
    StepEnum type();
    Integer number();
    StepStatus status();
    Date startTime();
    Date endTime();
    Boolean external();
    ExternalSystemType externalType();

    @Link Step parentStep();
    @Link Job job();
    @Link Statistic[] statistics();

    /**
     * The step progress (if reported) in percentages.
     *
     * @author Liron Aravot <laravot@redhat.com>
     * @date 21 Nov 2016
     * @status added
     * @since 4.1
     */
    Integer progress();
}
