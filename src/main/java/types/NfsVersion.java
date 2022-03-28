/*
Copyright (c) 2015-2017 Red Hat, Inc.

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

@Type
public enum NfsVersion {
    AUTO,
    V3,
    V4,

    /**
     * NFS 4.0.
     *
     * @author Fedor Gavrilov <fgavrilo@redhat.com>
     * @date 1 Jul 2019
     * @status added
     * @since 4.3.6
     */
    V4_0,

    V4_1,

    /**
     * NFS 4.2.
     *
     * @author Tal Nisan <tnisan@redhat.com>
     * @date 20 Feb 2017
     * @status added
     * @since 4.1.1
     */
    V4_2;
}
