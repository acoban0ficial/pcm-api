package dev.acobano.pcm.validation;


import dev.acobano.pcm.validation.validator.ValidCustomerRegisteredValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCustomerRegisteredValidator.class)
public @interface ValidCustomerRegistered {
    String message() default "There is no customer registered with the provided ID";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
