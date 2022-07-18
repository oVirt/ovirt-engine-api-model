package types;

import org.ovirt.api.metamodel.annotations.Type;

@Type
public enum TpmSupport {
    UNSUPPORTED,
    /** TPM is supported but optional */
    SUPPORTED,
    /** TPM is required by the operating system */
    REQUIRED;
}
