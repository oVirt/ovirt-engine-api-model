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
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;
import types.NetworkLabel;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

/**
 * Manages the ser of labels attached to a network or to a host NIC.
 *
 * @author Juan Hernandez <juan.hernandez@redhat.com>
 * @date 15 Apr 2017
 * @status added
 */
@Service
@Area("Network")
public interface NetworkLabelsService {
    /**
     * Attaches label to logical network.
     *
     * You can attach labels to a logical network to automate the association of that logical network with physical host
     * network interfaces to which the same label has been attached.
     *
     * For example, to attach the label `mylabel` to a logical network having id `123` send a request like this:
     *
     * [source]
     * ----
     * POST /ovirt-engine/api/networks/123/networklabels
     * ----
     *
     * With a request body like this:
     *
     * [source,xml]
     * ----
     * <network_label id="mylabel"/>
     * ----
     *
     * @author Dominik Holler <dholler@redhat.com>
     * @date 08 Apr 2019
     * @status updated
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(label().id());
        }
        @In @Out NetworkLabel label();
    }

    /**
     * Returns the list of labels attached to the network or host NIC.
     *
     * The order of the returned list of labels isn't guaranteed.
     *
     * @author Juan Hernandez <juan.hernandez@redhat.com>
     * @date 15 Apr 2017
     * @status added
     */
    interface List extends Follow {
        @Out NetworkLabel[] labels();

        /**
         * Sets the maximum number of labels to return. If not specified all the labels are returned.
         */
        @In Integer max();
    }

    @Service NetworkLabelService label(String id);
}
