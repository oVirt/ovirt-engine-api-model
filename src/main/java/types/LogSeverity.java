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
 * Enum representing a severity of an event.
 *
 * @author Oved Ourfali <oourfali@redhat.com>
 * @date 30 Nov 2016
 * @status added
 */
@Type
public enum LogSeverity {
    /**
     * Normal severity.
     * Used for information events.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    NORMAL,

    /**
     * Warning severify.
     * Used to warn something might be wrong.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    WARNING,

    /**
     * Error severity.
     * Used to specify that there is an error that needs
     * to be examined.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    ERROR,

    /**
     * Alert severity.
     * Used to specify a condition that requires
     * an immediate attention.
     *
     * @author Oved Ourfali <oourfali@redhat.com>
     * @date 30 Nov 2016
     * @status added
     */
    ALERT;
}
