/*
Copyright (c) 2015 Red Hat, Inc.

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

/**
 * Type representing the type of Gluster Volume.
 *
 * @author Ramesh Nachimuthu <rnachimu@redhat.com>
 * @date 12 Dec 2016
 * @status added
 */
@Type
public enum GlusterVolumeType {
    /**
     * Distributed volumes distributes files throughout the bricks in the volume.
     *
     * Distributed volumes can be used where the requirement is to scale storage and the redundancy is either not
     * important or is provided by other hardware/software layers.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    DISTRIBUTE,

    /**
     * Replicated volumes replicates files across bricks in the volume.
     *
     * Replicated volumes can be used in environments where high-availability and high-reliability are critical.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    REPLICATE,

    /**
     * Distributed replicated volumes distributes files across replicated bricks in the volume.
     *
     * Distributed replicated volumes can be used in environments where the requirement is to scale storage and
     * high-reliability is critical. Distributed replicated volumes also offer improved read performance in most
     * environments.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    DISTRIBUTED_REPLICATE,

    /**
     * Striped volumes stripes data across bricks in the volume.
     *
     * For best results, striped volumes should only in high concurrency environments accessing very large files.
     *
     * Note: With the introduction of Sharding in Glusterfs 3.7 releases, striped volumes are not recommended and it
     * will be removed in future release.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    STRIPE,

    /**
     * Distributed striped volumes stripe data across two or more nodes in the cluster.
     *
     * Distributed striped volumes should be used where the requirement is to scale storage and in high concurrency
     * environments accessing very large files is critical.
     *
     * Note: With the introduction of Sharding in Glusterfs 3.7 releases, striped volumes are not recommended and it
     * will be removed in future release.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    DISTRIBUTED_STRIPE,

    /**
     * Striped replicated volumes stripes data across replicated bricks in the cluster.
     *
     * For best results, striped replicated volumes should be used in highly concurrent environments where there is
     * parallel access of very large files and performance is critical.
     *
     * Note: With the introduction of Sharding in Glusterfs 3.7 releases, striped volumes are not recommended and it
     * will be removed in future release.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    STRIPED_REPLICATE,

    /**
     * Distributed striped replicated volumes distributes striped data across replicated bricks in the cluster.
     *
     * For best results, distributed striped replicated volumes should be used in highly concurrent environments where
     * parallel access of very large files and performance is critical.
     *
     * Note: With the introduction of Sharding in Glusterfs 3.7 releases, striped volumes are not recommended and it
     * will be removed in future release.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    DISTRIBUTED_STRIPED_REPLICATE,

    /**
     * Dispersed volumes are based on erasure codes, providing space-efficient protection against disk or server
     * failures.
     *
     * Dispersed volumes an encoded fragment of the original file to each brick in a way that only a subset of the
     * fragments is needed to recover the original file. The number of bricks that can be missing without losing access
     * to data is configured by the administrator on volume creation time.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    DISPERSE,

    /**
     * Distributed dispersed volumes distribute files across dispersed subvolumes.
     *
     * This has the same advantages of distribute replicate volumes, but using disperse to store the data into the
     * bricks.
     *
     * @author Ramesh Nachimuthu <rnachimu@redhat.com>
     * @date 12 Dec 2016
     * @status added
     */
    DISTRIBUTED_DISPERSE;
}
