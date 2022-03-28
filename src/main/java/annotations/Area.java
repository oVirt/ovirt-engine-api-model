/*
Copyright (c) 2016 Red Hat, Inc.

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

package annotations;

/**
 * This annotation is intended to specify what oVirt area is the annotated concept related to. Currently the following
 * areas are in use, and they are closely related to the oVirt teams, but not necessarily the same:
 *
 * - Infrastructure
 * - Network
 * - SLA
 * - Storage
 * - Virtualization
 *
 * A concept may be associated to more than one area, or to no area.
 *
 * The value of this annotation is intended for reporting only, and it doesn't affect at all the generated code or the
 * validity of the model
 */
public @interface Area {
    String[] value();
}
