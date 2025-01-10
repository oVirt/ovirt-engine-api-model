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

package types;

import java.util.Date;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Engine Backup
 *
 * Contains information about when certain parts of the ovirt-engine instance were last succesfully backed up
 *
 * @author Jasper Berton <jasper.berton@team.blue>
 * @date 03 Feb 2025
 * @status added
 * @since 4.6.1
 */
@Type
public interface EngineBackupInfo {

    /**
     * Date of when last succesfull backup was made of the engine.
     *
     * @author Jasper Berton <jasper.berton@team.blue>
     * @date 03 Feb 2025
     * @status added
     * @since 4.6.1
     */
    Date lastEngineBackup();

    /**
     * Date of when last succesfull backup was made of the database.
     *
     * @author Jasper Berton <jasper.berton@team.blue>
     * @date 03 Feb 2025
     * @status added
     * @since 4.6.1
     */
    Date lastDbBackup();

    /**
     * Date of when last succesfull backup was made of the DWH.
     *
     * @author Jasper Berton <jasper.berton@team.blue>
     * @date 03 Feb 2025
     * @status added
     * @since 4.6.1
     */
    Date lastDwhBackup();

    /**
     * Date of when last succesfull backup was made of the Cinderlib.
     *
     * @author Jasper Berton <jasper.berton@team.blue>
     * @date 03 Feb 2025
     * @status added
     * @since 4.6.1
     */
    Date lastCinderBackup();

    /**
     * Date of when last succesfull backup was made of the keycloack DB.
     *
     * @author Jasper Berton <jasper.berton@team.blue>
     * @date 03 Feb 2025
     * @status added
     * @since 4.6.1
     */
    Date lastKeycloakBackup();

    /**
     * Date of when last succesfull backup was made of the Grafana.
     *
     * @author Jasper Berton <jasper.berton@team.blue>
     * @date 03 Feb 2025
     * @status added
     * @since 4.6.1
     */
    Date lastGrafanaBackup();

}
