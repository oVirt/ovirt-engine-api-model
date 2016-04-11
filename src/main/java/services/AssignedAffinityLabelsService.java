/*
Copyright (c) 2016 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.AffinityLabel;

/**
 * This service is used to list and manipulate affinity labels that are
 * assigned to supported entities when accessed using entities/affinitylabels.
 */
@Service
public interface AssignedAffinityLabelsService {
    /**
     * Attaches a label to an entity.
     */
    interface Add {
        @In @Out AffinityLabel label();
    }

    /**
     * Lists all labels that are attached to an entity.
     */
    interface List {
        @Out AffinityLabel[] label();
    }

    /**
     * Link to the specific entity-label assignment to allow
     * removal.
     */
    @Service AssignedAffinityLabelService label(String id);
}
