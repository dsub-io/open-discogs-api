package io.dsub.sweatboys.opendiscogs.api.core.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

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
