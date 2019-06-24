package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing a subset of events in the {product-name} server:
 * those which a user may subscribe to receive a notification about.
 *
 * @author Ori Liel <oliel@redhat.com>
 * @date 17 June 2019
 * @status added
 * @since 4.4.0
 */
@Type
public enum NotifiableEvent {
    /**
     * Engine has stopped
     */
    ENGINE_STOP,
    /**
     * Engine backup started
     */
    ENGINE_BACKUP_STARTED,
    /**
     * Engine backup completed successfully
     */
    ENGINE_BACKUP_COMPLETED,
    /**
     * Engine backup failed
     */
    ENGINE_BACKUP_FAILED,
    /**
     * Engine CA's certification is about to expire
     */
    ENGINE_CA_CERTIFICATION_IS_ABOUT_TO_EXPIRE,
    /**
     * Engine CA's certification has expired
     */
    ENGINE_CA_CERTIFICATION_HAS_EXPIRED,
    /**
     * Engine's certification is about to expire
     */
    ENGINE_CERTIFICATION_IS_ABOUT_TO_EXPIRE,
    /**
     * Engine's certification has expired
     */
    ENGINE_CERTIFICATION_HAS_EXPIRED,
    /**
     * HA Reservation check has failed
     */
    CLUSTER_ALERT_HA_RESERVATION,
    /**
     * Display network was updated on cluster with an active VM
     */
    NETWORK_UPDATE_DISPLAY_FOR_CLUSTER_WITH_ACTIVE_VM,
    /**
     * HA Reservation check has passed
     */
    CLUSTER_ALERT_HA_RESERVATION_DOWN,
    /**
     * Host is non responsive
     */
    HOST_FAILURE,
    /**
     * Host has available updates
     */
    HOST_UPDATES_ARE_AVAILABLE,
    /**
     * Host has available packages to update
     */
    HOST_UPDATES_ARE_AVAILABLE_WITH_PACKAGES,
    /**
     * Host was switched to Maintenance Mode
     */
    USER_HOST_MAINTENANCE,
    /**
     * Host was switched to Maintenance Mode, but Hosted Engine HA maintenance mode could not be enabled
     */
    USER_HOST_MAINTENANCE_MANUAL_HA,
    /**
     * Failed to switch Host to Maintenance mode
     */
    USER_HOST_MAINTENANCE_MIGRATION_FAILED,
    /**
     * Host was activated, but the Hosted Engine HA service may still be in maintenance mode
     */
    HOST_ACTIVATE_MANUAL_HA,
    /**
     * Failed to activate Host
     */
    HOST_ACTIVATE_FAILED,
    /**
     * Host failed to recover
     */
    HOST_RECOVER_FAILED,
    /**
     * Failed to approve Host
     */
    HOST_APPROVE_FAILED,
    /**
     * Host installation failed
     */
    HOST_INSTALL_FAILED,
    /**
     * Host has time-drift
     */
    HOST_TIME_DRIFT_ALERT,
    /**
     * Host state was set to non-operational
     */
    HOST_SET_NONOPERATIONAL,
    /**
     * Host state was set to non-operational due to a missing Interface
     */
    HOST_SET_NONOPERATIONAL_IFACE_DOWN,
    /**
     * Host free memory is under defined threshold
     */
    HOST_LOW_MEM,
    /**
     * Host memory usage exceeded defined threshold
     */
    HOST_HIGH_MEM_USE,
    /**
     * Host network interface usage exceeded defined threshold
     */
    HOST_INTERFACE_HIGH_NETWORK_USE,
    /**
     * Host cpu usage exceeded defined threshold
     */
    HOST_HIGH_CPU_USE,
    /**
     * Host swap memory usage exceeded defined threshold
     */
    HOST_HIGH_SWAP_USE,
    /**
     * Host free swap memory is under defined threshold
     */
    HOST_LOW_SWAP,
    /**
     * Host's interface changed state to down
     */
    HOST_INTERFACE_STATE_DOWN,
    /**
     * Host's slave of bond changed state to down
     */
    HOST_BOND_SLAVE_STATE_DOWN,
    /**
     * Display network was updated on host with an active VM
     */
    NETWORK_UPDATE_DISPLAY_FOR_HOST_WITH_ACTIVE_VM,
    /**
     * Host's certification is about to expire
     */
    HOST_CERTIFICATION_IS_ABOUT_TO_EXPIRE,
    /**
     * Host's certification has expired
     */
    HOST_CERTIFICATION_HAS_EXPIRED,
    /**
     * Host state was set to non-operational due to inaccessible Storage Domain
     */
    HOST_SET_NONOPERATIONAL_DOMAIN,
    /**
     * Failed electing an SPM for the Data-Center
     */
    SYSTEM_CHANGE_STORAGE_POOL_STATUS_NO_HOST_FOR_SPM,
    /**
     * Storage Domain state was set to inactive
     */
    SYSTEM_DEACTIVATED_STORAGE_DOMAIN,
    /**
     * VM cannot be found on Host
     */
    VM_FAILURE,
    /**
     * Starting migration of VM
     */
    VM_MIGRATION_START,
    /**
     * Migration failed
     */
    VM_MIGRATION_FAILED,
    /**
     * Migration of VM to a destination host failed
     */
    VM_MIGRATION_TO_SERVER_FAILED,
    /**
     * VM is not responding
     */
    VM_NOT_RESPONDING,
    /**
     * VM status restored
     */
    VM_STATUS_RESTORED,
    /**
     * Highly-Available VM restart failed
     */
    HA_VM_RESTART_FAILED,
    /**
     * Highly-Available VM failed
     */
    HA_VM_FAILED,
    /**
     * VM console connected
     */
    VM_CONSOLE_CONNECTED,
    /**
     * VM console disconnected
     */
    VM_CONSOLE_DISCONNECTED,
    /**
     * VM console session initiated
     */
    VM_SET_TICKET,
    /**
     * VM is down with error
     */
    VM_DOWN_ERROR,
    /**
     * Failed to restart VM on a different host
     */
    HOST_INITIATED_RUN_VM_FAILED,
    /**
     * VM has been paused
     */
    VM_PAUSED,
    /**
     * VM has been paused due to a storage I/O error
     */
    VM_PAUSED_EIO,
    /**
     * VM has been paused due to lack of storage space
     */
    VM_PAUSED_ENOSPC,
    /**
     * VM has been paused due to storage read/write permissions problem
     */
    VM_PAUSED_EPERM,
    /**
     * VM has been paused due to unknown storage error
     */
    VM_PAUSED_ERROR,
    /**
     * VM has recovered from paused back to up
     */
    VM_RECOVERED_FROM_PAUSE_ERROR,
    /**
     * VM with external MAC address
     */
    MAC_ADDRESS_IS_EXTERNAL,
    /**
     * Slow storage response time
     */
    HOST_SLOW_STORAGE_RESPONSE_TIME,
    /**
     * Failed to access Storage
     */
    IRS_FAILURE,
    /**
     * Low disk space
     */
    IRS_DISK_SPACE_LOW,
    /**
     * Confirmed low disk space
     */
    IRS_CONFIRMED_DISK_SPACE_LOW,
    /**
     * Critically low disk space
     */
    IRS_DISK_SPACE_LOW_ERROR,
    /**
     * Storage Domain's number of LVs exceeded threshold
     */
    NUMBER_OF_LVS_ON_STORAGE_DOMAIN_EXCEEDED_THRESHOLD,
    /**
     * Gluster Volume Created
     */
    GLUSTER_VOLUME_CREATE,
    /**
     * Gluster Volume could not be created
     */
    GLUSTER_VOLUME_CREATE_FAILED,
    /**
     * Gluster Volume Option added
     */
    GLUSTER_VOLUME_OPTION_ADDED,
    /**
     * Gluster Volume Option modified
     */
    GLUSTER_VOLUME_OPTION_MODIFIED,
    /**
     * Gluster Volume Option could not be set
     */
    GLUSTER_VOLUME_OPTION_SET_FAILED,
    /**
     * Gluster volume started
     */
    GLUSTER_VOLUME_START,
    /**
     * Gluster Volume could not be started
     */
    GLUSTER_VOLUME_START_FAILED,
    /**
     * Gluster volume stopped
     */
    GLUSTER_VOLUME_STOP,
    /**
     * Gluster Volume could not be stopped
     */
    GLUSTER_VOLUME_STOP_FAILED,
    /**
     * Gluster Volume Options reset
     */
    GLUSTER_VOLUME_OPTIONS_RESET,
    /**
     * All the Gluster Volume Options reset
     */
    GLUSTER_VOLUME_OPTIONS_RESET_ALL,
    /**
     * Gluster Volume Options could not be reset
     */
    GLUSTER_VOLUME_OPTIONS_RESET_FAILED,
    /**
     * Gluster Volume deleted
     */
    GLUSTER_VOLUME_DELETE,
    /**
     * Gluster Volume could not be deleted
     */
    GLUSTER_VOLUME_DELETE_FAILED,
    /**
     * Gluster Volume brick(s) added
     */
    GLUSTER_VOLUME_ADD_BRICK,
    /**
     * Failed to add brick(s) on Gluster Volume
     */
    GLUSTER_VOLUME_ADD_BRICK_FAILED,
    /**
     * Gluster Volume Bricks Removed
     */
    GLUSTER_VOLUME_REMOVE_BRICKS,
    /**
     * Gluster Volume Bricks could not be removed
     */
    GLUSTER_VOLUME_REMOVE_BRICKS_FAILED,
    /**
     * Started removing bricks from Volume
     */
    START_REMOVING_GLUSTER_VOLUME_BRICKS,
    /**
     * Could not remove volume bricks
     */
    START_REMOVING_GLUSTER_VOLUME_BRICKS_FAILED,
    /**
     * Stopped removing bricks from Gluster Volume
     */
    GLUSTER_VOLUME_REMOVE_BRICKS_STOP,
    /**
     * Failed to stop remove bricks from Gluster Volume
     */
    GLUSTER_VOLUME_REMOVE_BRICKS_STOP_FAILED,
    /**
     * Gluster Volume Rebalance started
     */
    GLUSTER_VOLUME_REBALANCE_START,
    /**
     * Gluster Volume Rebalance could not be started
     */
    GLUSTER_VOLUME_REBALANCE_START_FAILED,
    /**
     * Gluster Volume Rebalance stopped
     */
    GLUSTER_VOLUME_REBALANCE_STOP,
    /**
     * Gluster Volume Rebalance could not be stopped
     */
    GLUSTER_VOLUME_REBALANCE_STOP_FAILED,
    /**
     * Gluster Volume Replace Brick Failed
     */
    GLUSTER_VOLUME_REPLACE_BRICK_FAILED,
    /**
     * Gluster Volume Replace Brick Started
     */
    GLUSTER_VOLUME_REPLACE_BRICK_START,
    /**
     * Gluster Volume Replace Brick could not be started
     */
    GLUSTER_VOLUME_REPLACE_BRICK_START_FAILED,
    /**
     * Gluster Volume Brick Replaced
     */
    GLUSTER_VOLUME_BRICK_REPLACED,
    /**
     * Detected start of rebalance on gluster volume from CLI
     */
    GLUSTER_VOLUME_REBALANCE_START_DETECTED_FROM_CLI,
    /**
     * Detected start of brick removal for bricks on volume from CLI
     */
    START_REMOVING_GLUSTER_VOLUME_BRICKS_DETECTED_FROM_CLI,
    /**
     * Could not find information for rebalance on volume from CLI. Marking it as unknown.
     */
    GLUSTER_VOLUME_REBALANCE_NOT_FOUND_FROM_CLI,
    /**
     * Could not find information for remove brick on volume from CLI. Marking it as unknown.
     */
    REMOVE_GLUSTER_VOLUME_BRICKS_NOT_FOUND_FROM_CLI,
    /**
     * Snapshot ${snapname} created for volume ${glusterVolumeName} on cluster ${clusterName}.
     */
    GLUSTER_VOLUME_SNAPSHOT_CREATED,
    /**
     * Could not create snapshot for volume ${glusterVolumeName} on cluster ${clusterName}.
     */
    GLUSTER_VOLUME_SNAPSHOT_CREATE_FAILED,
    /**
     * Failed to Add Gluster Server
     */
    GLUSTER_SERVER_ADD_FAILED,
    /**
     * Gluster Server Removed
     */
    GLUSTER_SERVER_REMOVE,
    /**
     * Failed to Remove Gluster Server
     */
    GLUSTER_SERVER_REMOVE_FAILED,
    /**
     * Gluster Volume Profile started
     */
    GLUSTER_VOLUME_PROFILE_START,
    /**
     * Failed to start Gluster Volume Profile
     */
    GLUSTER_VOLUME_PROFILE_START_FAILED,
    /**
     * Gluster Volume Profile stopped
     */
    GLUSTER_VOLUME_PROFILE_STOP,
    /**
     * Failed to stop Gluster Volume Profile
     */
    GLUSTER_VOLUME_PROFILE_STOP_FAILED,
    /**
     * Gluster Hook Enabled
     */
    GLUSTER_HOOK_ENABLE,
    /**
     * Failed to Enable Gluster Hook
     */
    GLUSTER_HOOK_ENABLE_FAILED,
    /**
     * Gluster Hook Disabled
     */
    GLUSTER_HOOK_DISABLE,
    /**
     * Failed to Disable Gluster Hook
     */
    GLUSTER_HOOK_DISABLE_FAILED,
    /**
     * Detected new Gluster Hook
     */
    GLUSTER_HOOK_DETECTED_NEW,
    /**
     * Detected conflict in Gluster Hook
     */
    GLUSTER_HOOK_CONFLICT_DETECTED,
    /**
     * Detected removal of Gluster Hook
     */
    GLUSTER_HOOK_DETECTED_DELETE,
    /**
     * Added Gluster Hook
     */
    GLUSTER_HOOK_ADDED,
    /**
     * Failed to add Gluster Hook on conflicting servers
     */
    GLUSTER_HOOK_ADD_FAILED,
    /**
     * Removed Gluster Hook
     */
    GLUSTER_HOOK_REMOVED,
    /**
     * Failed to remove Gluster Hook from cluster
     */
    GLUSTER_HOOK_REMOVE_FAILED,
    /**
     * Gluster Service started
     */
    GLUSTER_SERVICE_STARTED,
    /**
     * Failed to start Gluster service
     */
    GLUSTER_SERVICE_START_FAILED,
    /**
     * Gluster Service stopped
     */
    GLUSTER_SERVICE_STOPPED,
    /**
     * Failed to stop Gluster service
     */
    GLUSTER_SERVICE_STOP_FAILED,
    /**
     * Gluster Service re-started
     */
    GLUSTER_SERVICE_RESTARTED,
    /**
     * Failed to re-start Gluster Service
     */
    GLUSTER_SERVICE_RESTART_FAILED,
    /**
     * Detected change in status of brick
     */
    GLUSTER_BRICK_STATUS_CHANGED,
    /**
     * Snapshot deleted on volume
     */
    GLUSTER_VOLUME_SNAPSHOT_DELETED,
    /**
     * Failed to delete snapshot on volume
     */
    GLUSTER_VOLUME_SNAPSHOT_DELETE_FAILED,
    /**
     * All the snapshots deleted on the volume
     */
    GLUSTER_VOLUME_ALL_SNAPSHOTS_DELETED,
    /**
     * Failed to delete snapshots on the volume
     */
    GLUSTER_VOLUME_ALL_SNAPSHOTS_DELETE_FAILED,
    /**
     * Snapshot activated on the volume
     */
    GLUSTER_VOLUME_SNAPSHOT_ACTIVATED,
    /**
     * Failed to activate snapshot on the volume
     */
    GLUSTER_VOLUME_SNAPSHOT_ACTIVATE_FAILED,
    /**
     * Snapshot de-activated on the volume
     */
    GLUSTER_VOLUME_SNAPSHOT_DEACTIVATED,
    /**
     * Failed to de-activate snapshot on the volume
     */
    GLUSTER_VOLUME_SNAPSHOT_DEACTIVATE_FAILED,
    /**
     * Snapshot restore on the volume
     */
    GLUSTER_VOLUME_SNAPSHOT_RESTORED,
    /**
     * Failed to restore snapshot on the volume
     */
    GLUSTER_VOLUME_SNAPSHOT_RESTORE_FAILED,
    /**
     * Low space for volume confirmed
     */
    GLUSTER_VOLUME_CONFIRMED_SPACE_LOW,
    /**
     * ETL Service Stopped
     */
    DWH_STOPPED,
    /**
     * ETL Service Error
     */
    DWH_ERROR,
    /**
     * Gluster Volume rebalance finished
     */
    GLUSTER_VOLUME_REBALANCE_FINISHED,
    /**
     * Gluster Volume migration of data for remove brick finished
     */
    GLUSTER_VOLUME_MIGRATE_BRICK_DATA_FINISHED,
    /**
     * Host state was set to non-operational. Host is untrusted by the attestation service
     */
    HOST_UNTRUSTED,
    /**
     * VM moved from trusted cluster to non-trusted cluster
     */
    USER_UPDATE_VM_FROM_TRUSTED_TO_UNTRUSTED,
    /**
     * VM moved from non-trusted cluster to trusted cluster
     */
    USER_UPDATE_VM_FROM_UNTRUSTED_TO_TRUSTED,
    /**
     * Import VM from trusted cluster into non-trusted cluster
     */
    IMPORTEXPORT_IMPORT_VM_FROM_TRUSTED_TO_UNTRUSTED,
    /**
     * Import VM from non-trusted cluster into trusted cluster
     */
    IMPORTEXPORT_IMPORT_VM_FROM_UNTRUSTED_TO_TRUSTED,
    /**
     * A non-trusted VM was created from trusted Template
     */
    USER_ADD_VM_FROM_TRUSTED_TO_UNTRUSTED,
    /**
     * A trusted VM was created from non-trusted Template
     */
    USER_ADD_VM_FROM_UNTRUSTED_TO_TRUSTED,
    /**
     * Template imported from trusted cluster into non-trusted cluster
     */
    IMPORTEXPORT_IMPORT_TEMPLATE_FROM_TRUSTED_TO_UNTRUSTED,
    /**
     * Template imported from non-trusted cluster into trusted cluster
     */
    IMPORTEXPORT_IMPORT_TEMPLATE_FROM_UNTRUSTED_TO_TRUSTED,
    /**
     * A non-trusted Template was created from trusted VM
     */
    USER_ADD_VM_TEMPLATE_FROM_TRUSTED_TO_UNTRUSTED,
    /**
     * A trusted Template was created from non-trusted VM
     */
    USER_ADD_VM_TEMPLATE_FROM_UNTRUSTED_TO_TRUSTED,
    /**
     * Template moved from trusted cluster to non-trusted cluster
     */
    USER_UPDATE_VM_TEMPLATE_FROM_TRUSTED_TO_UNTRUSTED,
    /**
     * Template moved from a non-trusted cluster to a trusted cluster
     *
     * @author Eli Marcus <emarcus@redhat.com>
     * @date 15 Jul 2019
     * @status updated_by_docs
     */
    USER_UPDATE_VM_TEMPLATE_FROM_UNTRUSTED_TO_TRUSTED;
}
