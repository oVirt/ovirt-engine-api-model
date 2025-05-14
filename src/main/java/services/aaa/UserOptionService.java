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
public interface UserOptionService {
    /**
     * Returns a user profile property of type JSON.
     *
     * Example request(for user with identifier `123` and option with identifier `456`):
     *
     * ```http
     * GET /ovirt-engine/api/users/123/options/456
     * ```
     *
     * The result will be the following XML document:
     *
     * ```xml
     *   <user_option href="/ovirt-engine/api/users/123/options/456" id="456">
     *     <name>SomeName</name>
     *     <content>["any", "JSON"]</content>
     *     <user href="/ovirt-engine/api/users/123" id="123"/>
     *   </user_option>
     * ```
     *
     * @author Radoslaw Szwajkowski <rszwajko@redhat.com>
     * @date 11 Jan 2021
     * @status added
     * @since 4.4.5
     */
    interface Get {
        @Out UserOption option();
    }

    /**
     * Deletes an existing property of type JSON.
     *
     * Example request(for user with identifier `123` and option with identifier `456`):
     *
     * ```http
     * DELETE /ovirt-engine/api/users/123/options/456
     * ```
     *
     * @author Radoslaw Szwajkowski <rszwajko@redhat.com>
     * @date 11 Jan 2021
     * @status added
     * @since 4.4.5
     */
    interface Remove {
    }
}
