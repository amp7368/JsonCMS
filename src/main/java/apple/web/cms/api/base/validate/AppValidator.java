package apple.web.cms.api.base.validate;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.Validator;
import java.util.List;

public class AppValidator<T> {

    private final Validator<T> validator;

    public AppValidator(List<CreateValidator<T>> validatorCreators) {
        this.validator = joinValidators(validatorCreators);
    }

    public static <T> Validator<T> joinValidators(List<CreateValidator<T>> validatorCreators) {
        ValidatorBuilder<T> builder = ValidatorBuilder.of();
        for (CreateValidator<T> create : validatorCreators) {
            builder = create.apply(builder);
        }
        return builder.build();
    }

    public Validator<T> validator() {
        return this.validator;
    }

}
