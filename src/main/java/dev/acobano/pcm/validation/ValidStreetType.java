package dev.acobano.pcm.validation;

import dev.acobano.pcm.validation.validator.ValidStreetTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidStreetTypeValidator.class)
public @interface ValidStreetType {
    String message() default "Invalid street type. Must be one of: STREET, AVENUE, ROAD, BOULEVARD, DRIVE, LANE, ALLEY, SQUARE, COURT, PLACE, TERRACE, CRESCENT, HIGHWAY, PATH, TRAIL, WAY, RUE, STRASSE, VIA, CALLE, CAMINO, AVENIDA, RUA";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

