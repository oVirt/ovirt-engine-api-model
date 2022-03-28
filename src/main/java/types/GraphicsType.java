/*
Copyright (c) 2015 Red Hat, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package types;

import org.ovirt.api.metamodel.annotations.Type;
/**
 * The graphics protocol used to connect to the graphic console.
 *
 * @author Lukas Svaty <lsvaty@redhat.com>
 * @date 24 Apr 2017
 * @status added
 */
@Type
public enum GraphicsType {
    /**
     * Graphics protocol of type SPICE.
     * See link:https://www.spice-space.org[SPICE documentation] for more details.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    SPICE,

    /**
     * Graphics protocol of type VNC. VNC stands for Virtual
     * Network Computing, and it is a graphical
     * desktop sharing system that uses RFB
     * (Remote Frame Buffer) protocol to remotely
     * control another machine.
     *
     * @author Lukas Svaty <lsvaty@redhat.com>
     * @date 24 Apr 2017
     * @status added
     */
    VNC;
}
