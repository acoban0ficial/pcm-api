package dev.acobano.pcm.validation.validator;

import dev.acobano.pcm.model.enumerated.Country;
import dev.acobano.pcm.validation.ValidCountry;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class ValidCountryValidator implements ConstraintValidator<ValidCountry, Country> {

    @Override
    public boolean isValid(Country value, ConstraintValidatorContext context) {
        if (Objects.isNull(value) || value.toString().isBlank()) {
            return true;
        }

        try {
            Country.valueOf(value.toString());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}

