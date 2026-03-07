package dev.acobano.pcm.validation;

import dev.acobano.pcm.validation.validator.UniqueCustomerTaxIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueCustomerTaxIdValidator.class)
public @interface UniqueCustomerTaxId {
    String message() default "This Tax ID already exists in the database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

