package dev.acobano.pcm.validation;

import dev.acobano.pcm.validation.validator.ValidCurrencyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidCurrencyValidator.class)
public @interface ValidCurrency {
    String message() default "Invalid currency. Must be validated from the supported list";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
