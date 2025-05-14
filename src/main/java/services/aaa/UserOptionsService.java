package services.aaa;

import static org.ovirt.api.metamodel.language.ApiLanguage.mandatory;

import org.ovirt.api.metamodel.annotations.In;
import org.ovirt.api.metamodel.annotations.InputDetail;
import org.ovirt.api.metamodel.annotations.Out;
import org.ovirt.api.metamodel.annotations.Service;

import annotations.Area;
import types.UserOption;

@Service
@Area("Infrastructure")
public interface UserOptionsService {
    /**
     * Adds a new user profile property of type JSON.
     *
     * Example request(for user with identifier `123`):
     *
     * ```http
     * POST /ovirt-engine/api/users/123/options
     * ```
     *
     * Payload:
     *
     * ```xml
     *   <user_option>
     *     <name>SomeName</name>
     *     <content>["any", "JSON"]</content>
     *   </user_option>
     * ```
     *
     * @author Radoslaw Szwajkowski <rszwajko@redhat.com>
     * @date 11 Jan 2021
     * @status added
     * @since 4.4.5
     */
    interface Add {
        @InputDetail
        default void inputDetail() {
            mandatory(option().content());
            mandatory(option().name());
        }

        @In @Out UserOption option();
    }

    /**
     * Returns a list of user profile properties of type JSON.
     *
     * Example request(for user with identifier `123`):
     *
     * ```http
     * GET /ovirt-engine/api/users/123/options
     * ```
     *
     * The result will be the following XML document:
     *
     * ```xml
     * <user_options>
     *   <user_option href="/ovirt-engine/api/users/123/options/456" id="456">
     *     <name>SomeName</name>
     *     <content>["any", "JSON"]</content>
     *     <user href="/ovirt-engine/api/users/123" id="123"/>
     *   </user_option>
     * </user_options>
     * ```
     *
     * @author Radoslaw Szwajkowski <rszwajko@redhat.com>
     * @date 11 Jan 2021
     * @status added
     * @since 4.4.5
     */
    interface List {
        @Out UserOption[] options();
    }

    /**
     * @author Radoslaw Szwajkowski <rszwajko@redhat.com>
     * @date 11 Jan 2021
     * @status added
     * @since 4.4.5
     */
    @Service UserOptionService option(String id);
}
