/*
Copyright (c) 2015-2016 Red Hat, Inc.

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

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;
import services.gluster.GlusterHookStatus;

@Type
public interface GlusterHook extends Identified {
    String glusterCommand();
    HookStage stage();
    HookContentType contentType();
    String checksum();
    String content();
    Integer conflictStatus();
    String conflicts();
    GlusterHookStatus status();

    @Link Cluster cluster();
    @Link GlusterServerHook[] serverHooks();
}
