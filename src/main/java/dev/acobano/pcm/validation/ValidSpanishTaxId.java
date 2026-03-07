package dev.acobano.pcm.validation;

import dev.acobano.pcm.validation.validator.ValidSpanishTaxIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidSpanishTaxIdValidator.class)
public @interface ValidSpanishTaxId {
    String message() default "Invalid Tax ID format. Must be a valid Spanish identifier (format: 12345678X or X1234567)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

