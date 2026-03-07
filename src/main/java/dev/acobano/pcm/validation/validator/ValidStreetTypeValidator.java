package dev.acobano.pcm.validation.validator;

import dev.acobano.pcm.model.enumerated.StreetType;
import dev.acobano.pcm.validation.ValidStreetType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class ValidStreetTypeValidator implements ConstraintValidator<ValidStreetType, StreetType> {

    @Override
    public boolean isValid(StreetType value, ConstraintValidatorContext context) {
        if (Objects.isNull(value) || value.toString().isBlank()) {
            return true;
        }

        try {
            StreetType.valueOf(value.toString());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

