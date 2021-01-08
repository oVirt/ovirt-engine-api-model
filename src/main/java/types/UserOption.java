package types;

import org.ovirt.api.metamodel.annotations.Link;
import org.ovirt.api.metamodel.annotations.Type;

/**
 * User options allow you to save key/value properties
 * which are used to customize the settings per individual
 * user.
 */
@Type
public interface UserOption extends Identified {

    /**
     * JSON content encoded as string. Any valid JSON is supported.
     */
    String content();

    @Link User user();
}
