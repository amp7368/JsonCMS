package apple.web.cms.api.auth.grant;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Constraint;
import apple.web.cms.api.base.validate.AppValidator;
import apple.web.cms.model.auth.permission.DRole;
import java.util.ArrayList;
import java.util.List;

public class GrantRequest {

    public static final AppValidator<GrantRequest> VALIDATOR = new AppValidator<>(
        List.of(GrantRequest::usernameValidation, GrantRequest::rolesValidation));
    public String username;
    public String[] roles;

    private static ValidatorBuilder<GrantRequest> usernameValidation(ValidatorBuilder<GrantRequest> validator) {
        return validator.constraintOnObject(req -> req.username, "username", Constraint::notNull);
    }

    private static ValidatorBuilder<GrantRequest> rolesValidation(ValidatorBuilder<GrantRequest> validator) {
        return validator._objectArray(req -> req.roles, "roles", c -> c.notNull().notEmpty());
    }

    public List<DRole> getRoles() {
        ArrayList<DRole> roles = new ArrayList<>();
        for (String rawRole : this.roles) {
            DRole role = DRole.find(rawRole);
            if (role != null) roles.add(role);
        }
        return roles;
    }

}
