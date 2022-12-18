package io.dsub.sweatboys.opendiscogs.api.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;

@Constraint(validatedBy = SortParamsValidator.class)
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SortableParams {
    String message() default "sorts only allowed for {value}";
    @AliasFor("params")
    String[] value() default {};

    @AliasFor("value")
    String[] params() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
