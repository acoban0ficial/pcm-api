package dev.acobano.pcm.validation;

import dev.acobano.pcm.validation.validator.ValidEmployeeRegisteredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidEmployeeRegisteredValidator.class)
public @interface ValidEmployeeRegistered {
    String message() default "There is no employee registered with the provided ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
