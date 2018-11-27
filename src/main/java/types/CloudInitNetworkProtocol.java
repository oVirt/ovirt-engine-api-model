package types;

import org.ovirt.api.metamodel.annotations.Type;

/**
 * Defines the values for the cloud-init protocol.
 * This protocol decides how the cloud-init network
 * parameters are formatted before being passed to
 * the virtual machine in order to be processed by cloud-init.
 *
 * Protocols supported are cloud-init version dependent.
 * For more information, see
 * http://cloudinit.readthedocs.io/en/latest/topics/network-config.html#network-configuration-sources[Network Configuration Sources]
 *
 * @author Eitan Raviv <eraviv@redhat.com>
 * @author Steve Goodman <sgoodman@redhat.com>
 * @date 03 Dec 2018
 * @since 4.2.8
 * @status updated_by_docs
 */
@Type
public enum CloudInitNetworkProtocol {

    /**
     * Legacy protocol. Does not support IPv6.
     * For more information, see
     * http://cloudinit.readthedocs.io/en/latest/topics/network-config-format-eni.html#network-config-eni[Network Configuration ENI (Legacy)]
     *
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Steve Goodman <sgoodman@redhat.com>
     * @date 03 Dec 2018
     * @since 4.2.8
     * @status updated_by_docs
     */
    ENI,

    /**
     * Successor of the ENI protocol, with support for IPv6 and more.
     * This is the default value.
     * For more information, see
     * http://specs.openstack.org/openstack/nova-specs/specs/liberty/implemented/metadata-service-network-info[API: Proxy neutron configuration to guest instance]
     *
     * @author Eitan Raviv <eraviv@redhat.com>
     * @author Steve Goodman <sgoodman@redhat.com>
     * @date 03 Dec 2018
     * @since 4.2.8
     * @status updated_by_docs
     */
    OPENSTACK_METADATA
}
