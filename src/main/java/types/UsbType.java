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
public enum UsbType {
    /**
     * A legacy USB type.
     *
     * This USB type has been deprecated since version 3.6 of the
     * engine, and has been completely removed in version 4.1. It
     * is preserved only to avoid syntax errors in existing
     * scripts. If it is used it will be automatically replaced
     * by `native`.
     *
     * @author Tomas Jelinek <tjelinek@redhat.com>
     * @date 14 Nov 2016
     * @status added
     */
    @Deprecated
    LEGACY,

    NATIVE;
}
