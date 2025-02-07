/*
The oVirt Project - oVirt Engine Api Model

Copyright oVirt Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the Apache License, Version 2.0 is included with the program
in the file ASL2.
*/
package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * The protocol used by the engine to communicate with
 * a host.
 *
 * WARNING: Since version 4.1 of the engine the protocol
 * is always set to `stomp` since `xml` was removed.
 *
 * @author Piotr Kliczewski <pkliczew@redhat.com>
 * @date 30 Nov 2016
 * @status added
 */
@Type
public enum HostProtocol {
   /**
    * XML-RPC protocol.
    *
    * @author Oved Ourfali <oourfali@redhat.com>
    * @date 01 Dec 2016
    * @status added
    */
    XML,

   /**
    * JSON-RPC protocol on top of STOMP.
    *
    * @author Oved Ourfali <oourfali@redhat.com>
    * @date 01 Dec 2016
    * @status added
    */
    STOMP;
}
