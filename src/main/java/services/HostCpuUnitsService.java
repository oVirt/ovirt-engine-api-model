package services;

import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import mixins.Follow;
import types.HostCpuUnit;

@Service
@Area("Virtualization")
public interface HostCpuUnitsService {

    /**
     * Returns the List of all host's CPUs with detailed information
     * about the topology (socket, core) and with information
     * about the current CPU pinning.
     *
     * @author Lucia Jelinkova <ljelinko@redhat.com>
     * @since 4.5
     * @date 15 Feb 2022
     * @status added
     */
    interface List extends Follow {

        @Out HostCpuUnit[] cpuUnits();

    }
}
