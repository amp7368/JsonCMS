package apple.web.cms.api.auth.signup;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.constraint.ObjectConstraint;
import am.ik.yavi.constraint.password.PasswordPolicy;
import apple.web.cms.api.base.validate.AppValidator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class SignupRequest {

    private static final Predicate<String> USERNAME_REGEX = Pattern.compile("\\w+").asMatchPredicate();
    public static final AppValidator<SignupRequest> VALIDATOR = new AppValidator<>(
        List.of(SignupRequest::passwordValidation, SignupRequest::usernameValidation));

    public String username;
    public String password;

    public SignupRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private static ValidatorBuilder<SignupRequest> usernameValidation(ValidatorBuilder<SignupRequest> validator) {
        return validator._charSequence(req -> req.username, "username",
            c -> c.notNull().cast()
                .predicate(USERNAME_REGEX, "user.username", "Username must follow [a-zA-Z0-9_]+. {} was provided."));
    }

    private static ValidatorBuilder<SignupRequest> passwordValidation(ValidatorBuilder<SignupRequest> validator) {
        return validator.constraintOnObject(req -> req.password, "password", SignupRequest::passwordPolicy);
    }

    private static ObjectConstraint<SignupRequest, String> passwordPolicy(ObjectConstraint<SignupRequest, String> constraint) {
        return constraint.notNull().password(
            p -> p.required(PasswordPolicy.LOWERCASE, PasswordPolicy.UPPERCASE, PasswordPolicy.NUMBERS).build());
    }
}

