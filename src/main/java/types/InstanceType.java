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

/**
 * Describes the hardware configuration of virtual machines.
 *
 * For example `medium` instance type includes 1 virtual CPU and 4 GiB of memory. It is a top-level
 * entity (e.g. not bound to any data center or cluster). The attributes that are used for instance
 * types and are common to virtual machine and template types are:
 *
 * - `console`
 * - `cpu`
 * - `custom_cpu_model`
 * - `custom_emulated_machine`
 * - `display`
 * - `high_availability`
 * - `io`
 * - `memory`
 * - `memory_policy`
 * - `migration`
 * - `migration_downtime`
 * - `os`
 * - `rng_device`
 * - `soundcard_enabled`
 * - `usb`
 * - `virtio_scsi`
 *
 * When creating a virtual machine from both an instance type and a template, the virtual machine
 * will inherit the hardware configurations from the instance type
 *
 * NOTE: An instance type inherits it's attributes from the template entity although most template
 * attributes are not used in instance types.
 *
 * @author Sefi Litmanovich <slitmano@redhat.com>
 * @date 12 Dec 2016
 * @status draft
 */
@Type
public interface InstanceType extends Template {
}
