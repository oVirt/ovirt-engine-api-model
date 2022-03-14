package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * Type representing a physical CPU of a host with
 * the current pinning status.
 *
 * @author Lucia Jelinkova <ljelinko@redhat.com>
 * @since 4.5
 * @date 15 Feb 2022
 * @status added
 */
@Type
public interface HostCpuUnit extends Identified {

    /**
     * The id of the socket the CPU belongs to.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @since 4.5
     * @date 15 Feb 2022
     * @status added
     */
    Integer socketId();

    /**
     * The id of the core the CPU belongs to.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @since 4.5
     * @date 15 Feb 2022
     * @status added
     */
    Integer coreId();

    /**
     * The id of the CPU.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @since 4.5
     * @date 15 Feb 2022
     * @status added
     */
    Integer cpuId();

    /**
     * A list of VMs that has its virtual CPU pinned to this physical CPU.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @since 4.5
     * @date 15 Feb 2022
     * @status added
     */
    @Link Vm[] vms();

    /**
     * A flag indicating that the CPU runs the VDSM
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @since 4.5
     * @date 15 Feb 2022
     * @status added
     */
    Boolean runsVdsm();
}
