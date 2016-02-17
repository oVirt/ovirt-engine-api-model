package types;

import org.ovirt.api.metamodel.annotations.Type;

@Type
public enum NetworkUsage {
    DISPLAY,
    VM,
    MIGRATION,
    MANAGEMENT;
}
