package dev.acobano.pcm.validation;

import dev.acobano.pcm.validation.validator.ValidProjectStatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidProjectStatusValidator.class)
public @interface ValidProjectStatus {
    String message() default "Invalid project status";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

