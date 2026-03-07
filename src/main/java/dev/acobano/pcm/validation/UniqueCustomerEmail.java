package dev.acobano.pcm.validation;

import dev.acobano.pcm.validation.validator.UniqueCustomerEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCustomerEmailValidator.class)
public @interface UniqueCustomerEmail {
    String message() default "This email already exists in the database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

