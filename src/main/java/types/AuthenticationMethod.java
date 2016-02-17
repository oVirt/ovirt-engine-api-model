package types;

import org.ovirt.api.metamodel.annotations.Type;

@Type
public enum AuthenticationMethod {
    PASSWORD,
    PUBLICKEY;
}

