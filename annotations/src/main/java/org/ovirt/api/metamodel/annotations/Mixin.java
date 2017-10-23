/*
Copyright (c) 2017 Red Hat, Inc.

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

package org.ovirt.api.metamodel.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark interfaces whose contents will be included in other types, services or methods. For
 * example, the a {@code follow} parameter common to multiple operations could be defined like this:
 *
 * <pre>
 * @Mixin
 * public interface Follow {
 *     @In String follow();
 * }
 * </pre>
 *
 * And then included in multiple methods without having to repeat the complete definition:
 *
 * <pre>
 * interface Get extends Follow {
 *     ...
 * }
 *
 * interface List extends Follow {
 *     ...
 * }
 * </pre>
 */
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.SOURCE)
public @interface Mixin {
}

