package apple.web.cms.api.base.validate;

import am.ik.yavi.builder.ValidatorBuilder;
import java.util.function.Function;

@FunctionalInterface
public interface CreateValidator<T> extends Function<ValidatorBuilder<T>, ValidatorBuilder<T>> {

}
